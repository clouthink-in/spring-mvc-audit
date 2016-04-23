package in.clouthink.daas.audit.spi.impl;

import in.clouthink.daas.audit.core.AuditEventContext;
import in.clouthink.daas.audit.core.DefaultAuditEvent;
import in.clouthink.daas.audit.core.MutableAuditEvent;
import in.clouthink.daas.audit.security.SecurityContexts;
import in.clouthink.daas.audit.spi.AuditEventResolver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

/**
 * The default impl which resolve the metadata from request & swagger2 annotation
 */
public class DefaultAuditEventResolver implements AuditEventResolver {

	private boolean errorDetailRequired;

	public DefaultAuditEventResolver() {
		this.errorDetailRequired = false;
	}

	public DefaultAuditEventResolver(boolean errorDetailRequired) {
		this.errorDetailRequired = errorDetailRequired;
	}

	@Override
	public MutableAuditEvent resolve(AuditEventContext context) {
		HttpServletRequest request = context.getHttpServletRequest();
		Class targetClazz = context.getTargetClass();
		Method userDeclaredMethod = context.getTargetMethod();

		DefaultAuditEvent auditEvent = new DefaultAuditEvent();
		auditEvent.setRequestedUrl(request.getRequestURL().toString());
		auditEvent.setHttpMethod(request.getMethod());
		// handle the client real ip address
		// since the proxy between the client and backend server, the real
		// ip is hidden in http header
		auditEvent.setClientAddress(request.getRemoteAddr());
		auditEvent.setForwardedFor(request.getHeader("X-Forwarded-For"));
		auditEvent.setUserAgent(request.getHeader("User-Agent"));
		auditEvent.setRequestedBy(SecurityContexts.getContext().getPrincipal());

		Api classAuditMetadata = (Api) targetClazz.getAnnotation(Api.class);
		ApiOperation methodAuditMetadata = userDeclaredMethod.getAnnotation(ApiOperation.class);
		if (classAuditMetadata != null && !StringUtils.isEmpty(classAuditMetadata.description())) {
			auditEvent.setCategory(classAuditMetadata.description());
		}

		if (methodAuditMetadata != null && !StringUtils.isEmpty(methodAuditMetadata.value())) {
			auditEvent.setDescription(methodAuditMetadata.value());
		}

		if (StringUtils.isEmpty(auditEvent.getCategory())) {
			auditEvent.setCategory(targetClazz.getName());
		}

		if (StringUtils.isEmpty(auditEvent.getDescription())) {
			auditEvent.setCategory(userDeclaredMethod.getName());
		}

		auditEvent.setServiceName(targetClazz.getName());
		auditEvent.setMethodName(userDeclaredMethod.getName());
		return auditEvent;
	}

	@Override
	public void handleException(MutableAuditEvent auditEvent, Throwable e) {
		auditEvent.setError(true);
		auditEvent.setErrorMessage(getExceptionSummary(e));
		if (errorDetailRequired) {
			auditEvent.setErrorDetail(getExceptionDetail(e));
		}
	}

	String getExceptionSummary(Throwable e) {
		return String.format("%s: %s", e.getClass().getName(), e.getMessage());
	}

	String getExceptionDetail(Throwable e) {
		try {
			StringWriter sWriter = new StringWriter();
			PrintWriter pWriter = new PrintWriter(sWriter);
			e.printStackTrace(pWriter);
			return sWriter.toString();
		} catch (Exception ex) {
			return "";
		}
	}

}
