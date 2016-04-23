package in.clouthink.daas.audit.core;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 */
public interface AuditEventContext {

	HttpServletRequest getHttpServletRequest();

	Class getTargetClass();

	Method getTargetMethod();

}
