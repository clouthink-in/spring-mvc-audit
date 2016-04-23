package in.clouthink.daas.audit.core;

import in.clouthink.daas.audit.spi.AuditEventPersister;
import in.clouthink.daas.audit.spi.AuditEventResolver;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.lang.annotation.Annotation;

public class AuditAnnotationBeanPostProcessor extends AbstractAdvisingBeanPostProcessor
		implements BeanFactoryAware, AuditExecutionConfigurer {

	private Class<? extends Annotation> auditAnnotationType;

	private AuditEventResolver auditEventResolver;

	private AuditEventPersister auditEventPersister;

	private boolean errorDetailRequired = false;

	public AuditAnnotationBeanPostProcessor() {
		setBeforeExistingAdvisors(true);
	}

	/**
	 * Set the 'audit' annotation type to be detected at either class or method
	 * level. By default, the {@link org.springframework.web.bind.annotation.RequestMapping} annotation will be detected.
	 * <p>
	 * This setter property exists so that developers can provide their own
	 * (non-Spring-specific) annotation type to indicate that a method (or all
	 * methods of a given class) should be invoked asynchronously.
	 *
	 * @param auditAnnotationType the desired annotation type
	 */
	public void setAuditAnnotationType(Class<? extends Annotation> auditAnnotationType) {
		this.auditAnnotationType = auditAnnotationType;
	}

	public Class<? extends Annotation> getAuditAnnotationType() {
		return auditAnnotationType;
	}

	public AuditEventResolver getAuditEventResolver() {
		return auditEventResolver;
	}

	@Override
	public void setAuditEventResolver(AuditEventResolver auditEventResolver) {
		this.auditEventResolver = auditEventResolver;
	}

	public AuditEventPersister getAuditEventPersister() {
		return auditEventPersister;
	}

	@Override
	public void setAuditEventPersister(AuditEventPersister auditEventPersister) {
		this.auditEventPersister = auditEventPersister;
	}

	public boolean isErrorDetailRequired() {
		return errorDetailRequired;
	}

	@Override
	public void setErrorDetailRequired(boolean errorDetailRequired) {
		this.errorDetailRequired = errorDetailRequired;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		AuditAnnotationAdvisor advisor = new AuditAnnotationAdvisor(auditEventResolver, auditEventPersister);
		if (this.auditAnnotationType != null) {
			advisor.setAuditAnnotationType(this.auditAnnotationType);
		}
		advisor.setBeanFactory(beanFactory);
		this.advisor = advisor;
	}

}
