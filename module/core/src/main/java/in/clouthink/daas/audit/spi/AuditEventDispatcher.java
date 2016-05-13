package in.clouthink.daas.audit.spi;

import in.clouthink.daas.audit.core.AuditEvent;

/**
 */
public interface AuditEventDispatcher {

	String QUEUE_NAME = AuditEvent.class.getName();

	String EVENT_PATH = AuditEvent.class.getName();

	/**
	 * dispatch the audit event, the event listener should be observed and handle the event.
	 *
	 * @param auditEvent
	 */
	void dispatch(AuditEvent auditEvent);

}
