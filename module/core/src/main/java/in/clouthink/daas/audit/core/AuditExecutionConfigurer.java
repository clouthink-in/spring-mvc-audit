package in.clouthink.daas.audit.core;

import in.clouthink.daas.audit.security.SecurityContext;
import in.clouthink.daas.audit.spi.AuditEventPersister;
import in.clouthink.daas.audit.spi.AuditEventResolver;

import java.lang.annotation.Annotation;

/**
 */
public interface AuditExecutionConfigurer {

	/**
	 * The default SecurityContexts.getContext() will be taking if not configured, .
	 *
	 * @param securityContext
	 */
	void setSecurityContext(SecurityContext securityContext);

	/**
	 * RequestMapping is taking by default
	 *
	 * @param auditAnnotationType
	 */
	void setAuditAnnotationType(Class<? extends Annotation> auditAnnotationType);

	/**
	 * DefaultAuditEventResolver is taking by default
	 *
	 * @param auditEventResolver
	 */
	void setAuditEventResolver(AuditEventResolver auditEventResolver);

	/**
	 * DefaultAuditEventPersister is taking by default
	 *
	 * @param auditEventPersister
	 */
	void setAuditEventPersister(AuditEventPersister auditEventPersister);

	/**
	 * @param errorDetailRequired true : the full stack of the error
	 *                            false (default) : only the error message
	 */
	void setErrorDetailRequired(boolean errorDetailRequired);

}
