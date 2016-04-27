package in.clouthink.daas.audit.core;

import in.clouthink.daas.audit.security.SecurityContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 */
public interface AuditEventContext {

	SecurityContext getSecurityContext();

	HttpServletRequest getHttpServletRequest();

	Class getTargetClass();

	Method getTargetMethod();

}
