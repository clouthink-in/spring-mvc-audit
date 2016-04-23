package in.clouthink.daas.audit.spi;

import in.clouthink.daas.audit.core.AuditEventContext;
import in.clouthink.daas.audit.core.MutableAuditEvent;

/**
 */
public interface AuditEventResolver {

	/**
	 * @param context
	 * @returnsdfghj
	 */
	MutableAuditEvent resolve(AuditEventContext context);

	void handleException(MutableAuditEvent auditEvent, Throwable e);

}
