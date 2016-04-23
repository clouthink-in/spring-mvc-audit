package in.clouthink.daas.audit.core;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * The default impl for AuditEventContext
 */
public class DefaultAuditEventContext implements AuditEventContext {

	private HttpServletRequest request;
	private Class targetClazz;
	private Method targetMethod;

	public DefaultAuditEventContext(HttpServletRequest request, Class targetClazz, Method targetMethod) {
		this.request = request;
		this.targetClazz = targetClazz;
		this.targetMethod = targetMethod;
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
