package in.clouthink.daas.audit.core;

import in.clouthink.daas.audit.security.SecurityContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * The default impl for AuditEventContext
 */
public class DefaultAuditEventContext implements AuditEventContext {

	private SecurityContext securityContext;
	private HttpServletRequest request;
	private Class targetClazz;
	private Method targetMethod;

	public DefaultAuditEventContext(SecurityContext securityContext,
									HttpServletRequest request,
									Class targetClazz,
									Method targetMethod) {
		this.securityContext = securityContext;
		this.request = request;
		this.targetClazz = targetClazz;
		this.targetMethod = targetMethod;
	}

	@Override
	public SecurityContext getSecurityContext() {
		return securityContext;
	}

	@Override
	public HttpServletRequest getHttpServletRequest() {
		return request;
	}

	@Override
	public Class getTargetClass() {
		return targetClazz;
	}

	@Override
	public Method getTargetMethod() {
		return targetMethod;
	}

}
