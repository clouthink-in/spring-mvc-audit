package in.clouthink.daas.audit.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AuditConfiguration.class)
@Documented
public @interface EnableAudit {
}
