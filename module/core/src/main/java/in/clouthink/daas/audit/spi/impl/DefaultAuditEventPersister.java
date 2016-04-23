package in.clouthink.daas.audit.spi.impl;

import in.clouthink.daas.audit.core.AuditEvent;
import in.clouthink.daas.audit.spi.AuditEventPersister;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The default impl which is only logging the audit detail
 */
public class DefaultAuditEventPersister implements AuditEventPersister {

	private static final Log logger = LogFactory.getLog(DefaultAuditEventPersister.class);

	@Override
	public AuditEvent saveAuditEvent(AuditEvent auditEvent) {
		logger.debug(auditEvent);
		return auditEvent;
	}

}
