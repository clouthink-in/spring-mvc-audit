package in.clouthink.daas.audit.spi;

import in.clouthink.daas.audit.core.AuditEvent;

/**
 */
public interface AuditEventPersister<T extends AuditEvent> {

	/**
	 * @param auditEvent
	 * @return the saved audit event
	 */
	T saveAuditEvent(AuditEvent auditEvent);

}
