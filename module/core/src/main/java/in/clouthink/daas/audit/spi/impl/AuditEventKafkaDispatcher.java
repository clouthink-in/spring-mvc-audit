package in.clouthink.daas.audit.spi.impl;

import in.clouthink.daas.audit.core.AuditEvent;
import in.clouthink.daas.audit.spi.AuditEventDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * The default impl delegates the audit detail by kafka
 */
public class AuditEventKafkaDispatcher implements AuditEventDispatcher {

    @Autowired
    private KafkaTemplate<String, AuditEvent> kafkaTemplate;

    @Override
    public void dispatch(AuditEvent auditEvent) {
        kafkaTemplate.send(AuditEventDispatcher.QUEUE_NAME, auditEvent);
    }

}
