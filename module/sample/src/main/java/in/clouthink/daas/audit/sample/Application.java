package in.clouthink.daas.audit.sample;

import in.clouthink.daas.audit.annotation.EnableAudit;
import in.clouthink.daas.audit.configure.AuditConfigurer;
import in.clouthink.daas.audit.core.AuditExecutionConfigurer;
import in.clouthink.daas.audit.sample.mongodb.spiImpl.AuditEventPersisterImpl;
import in.clouthink.daas.audit.security.SecurityContext;
import in.clouthink.daas.audit.spi.AuditEventPersister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@ComponentScan(value = "in.clouthink.daas.audit.sample")
@EnableMongoRepositories({"in.clouthink.daas.audit.sample.mongodb.repository"})
@EnableWebMvc
@EnableAudit
public class Application {

	@Bean
	public AuditEventPersister auditEventPersisterImpl() {
		return new AuditEventPersisterImpl();
	}

	@Bean
	public AuditConfigurer auditConfigurer() {
		return new AuditConfigurer() {

			@Override
			public void configure(AuditExecutionConfigurer auditExecutionConfigurer) {
				auditExecutionConfigurer.setSecurityContext(() -> "configured-sample-user");
				auditExecutionConfigurer.setAuditEventPersister(auditEventPersisterImpl());
				auditExecutionConfigurer.setErrorDetailRequired(true);
			}

		};
	}

	public static void main(String[] args) {
		SpringApplication.run(new Object[]{Application.class}, args);
	}

}
