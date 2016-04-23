package in.clouthink.daas.audit.core;

import in.clouthink.daas.audit.spi.AuditEventPersister;
import in.clouthink.daas.audit.spi.AuditEventResolver;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;


public class AuditAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

	private Advice advice;

	private Pointcut pointcut;

	/**
	 * Create a new {@code AsyncAnnotationAdvisor} for bean-style configuration.
	 */
	public AuditAnnotationAdvisor(AuditEventResolver auditEventResolver, AuditEventPersister auditEventPersister) {
		this.advice = buildAdvice(auditEventResolver, auditEventPersister);
		this.pointcut = buildPointcut(RequestMapping.class);
	}

	/**
	 * Set the 'audit' annotation type.
	 * <p>
	 * The default audit annotation type is the {@link RequestMapping} annotation.
	 * <p>
	 * This setter property exists so that developers can provide their own
	 * (non-Spring-specific) annotation type to indicate that a method is to be
	 * executed auditable.
	 *
	 * @param auditAnnotationType the desired annotation type
	 */
	public void setAuditAnnotationType(Class<? extends Annotation> auditAnnotationType) {
		Assert.notNull(auditAnnotationType, "'auditAnnotationType' must not be null");
		this.pointcut = buildPointcut(auditAnnotationType);
	}

	/**
	 * Set the {@code BeanFactory} to be used when looking up executors by
	 * qualifier.
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		if (this.advice instanceof BeanFactoryAware) {
			((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
		}
	}

	@Override
	public Advice getAdvice() {
		return this.advice;
	}

	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}

	private Advice buildAdvice(AuditEventResolver auditEventResolver, AuditEventPersister auditEventPersister) {
		return new AuditExecutionInterceptor(auditEventResolver, auditEventPersister);
	}

	/**
	 * Calculate a pointcut for the given auditable annotation types, if any.
	 *
	 * @param auditAnnotationType the auditable annotation types to introspect
	 * @return the applicable Pointcut object, or {@code null} if none
	 */
	protected Pointcut buildPointcut(Class<? extends Annotation> auditAnnotationType) {
		return new AnnotationMatchingPointcut(auditAnnotationType, true);
	}

}
