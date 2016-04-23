package in.clouthink.daas.audit.sample.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AuditEvents")
public class AuditEvent extends in.clouthink.daas.audit.core.DefaultAuditEvent {

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
