package in.clouthink.daas.audit.sample.mongodb.spiImpl;

import in.clouthink.daas.audit.core.AuditEvent;
import in.clouthink.daas.audit.spi.AuditEventPersister;
import in.clouthink.daas.audit.sample.mongodb.repository.AuditEventRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class AuditEventPersisterImpl implements AuditEventPersister {

	@Autowired
	@Lazy
	private AuditEventRepository auditEventRepository;

	@Override
	public in.clouthink.daas.audit.sample.mongodb.model.AuditEvent saveAuditEvent(AuditEvent o) {
		in.clouthink.daas.audit.sample.mongodb.model.AuditEvent auditEvent = new in.clouthink.daas.audit.sample.mongodb.model.AuditEvent();
		BeanUtils.copyProperties(o, auditEvent);
		return auditEventRepository.save(auditEvent);
	}

}
