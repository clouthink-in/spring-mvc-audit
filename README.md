# Introduction

The simple and quick audit lib for spring mvc http request.

# Dependencies

* Spring Mvc Framework (Over 3)
* in.clouthink:daas-edm:1.0.1 
* io.swagger:swagger-annotations:1.5.x 

# Usage

So far the following version is available 

module name | latest version
------|------
daas-audit |1.0.1

## Maven

    <dependency>
        <groupId>in.clouthink.daas</groupId>
        <artifactId>daas-audit</artifactId>
        <version>${daas.audit.version}</version>
    </dependency>

## Gradle

    compile "in.clouthink.daas:daas-audit:${daas_audit_version}"


## Spring Configuration

Use `@EnableAudit` to get started 

    @Configuration
    @EnableAudit
    public class Application {}


The principal who triggers audit event is very important, but we don't know which security framework is chosen by end user. 

To make it run , the end user has to supply the minimized implementation as follow

    public class SecurityContextImpl implements SecurityContext {
    
        @Override
        public String getPrincipal() {
            //TODO
        }
    
    }

And add service file which named `in.clouthink.daas.audit.security.SecurityContext` to META-INF/services.
The content of the service file should be the full quantifier path of the implementation.


## How to customize

We supply the following extension points to make the customization easy and happy.

* in.clouthink.daas.audit.spi.AuditEventResolver
* in.clouthink.daas.audit.spi.AuditEventPersister

The default implementations list as follow:

* in.clouthink.daas.audit.spi.impl.DefaultAuditEventResolver
* in.clouthink.daas.audit.spi.impl.DefaultAuditEventPersister

The end user can supply their own implementations and override the default value by `AuditConfigurer`.

    @Bean
	public AuditConfigurer auditConfigurer() {
		return new AuditConfigurer() {

			@Override
			public void configure(AuditExecutionConfigurer auditExecutionConfigurer) {
			    //do configure
			}

		};
	}

Here is the definition of `AuditExecutionConfigurer` , four ways to customize the audit features.

    public interface AuditExecutionConfigurer {
    
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


