package in.clouthink.daas.audit.core;

import in.clouthink.daas.audit.annotation.Ignored;
import in.clouthink.daas.audit.spi.AuditEventDispatcher;
import in.clouthink.daas.audit.spi.AuditEventPersister;
import in.clouthink.daas.audit.spi.AuditEventResolver;
import in.clouthink.daas.audit.spi.impl.DefaultAuditEventDispatcher;
import in.clouthink.daas.edm.Edms;
import in.clouthink.daas.edm.EventListener;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Future;

public class AuditExecutionInterceptor
		implements EventListener<AuditEvent>, BeanFactoryAware, MethodInterceptor, Ordered {

	protected final Log logger = LogFactory.getLog(getClass());

	private AuditEventResolver auditEventResolver;

	private AuditEventPersister auditEventPersister;

	private AuditEventDispatcher auditEventDispatcher = new DefaultAuditEventDispatcher();

	private BeanFactory beanFactory;

	/**
	 * @param auditEventResolver
	 * @param auditEventPersister
	 */
	public AuditExecutionInterceptor(AuditEventResolver auditEventResolver, AuditEventPersister auditEventPersister) {
		this.auditEventResolver = auditEventResolver;
		this.auditEventPersister = auditEventPersister;
		Edms.getEdm().register(AuditEventDispatcher.EVENT_PATH, this);
	}

	/**
	 * @param auditEventDispatcher
	 */
	public void setAuditEventDispatcher(AuditEventDispatcher auditEventDispatcher) {
		this.auditEventDispatcher = auditEventDispatcher;
	}

	/**
	 * @param auditEventResolver
	 */
	public void setAuditEventResolver(AuditEventResolver auditEventResolver) {
		this.auditEventResolver = auditEventResolver;
	}

	/**
	 * @param auditEventPersister
	 */
	public void setAuditEventPersister(AuditEventPersister auditEventPersister) {
		this.auditEventPersister = auditEventPersister;
	}

	/**
	 * Set the {@link BeanFactory} to be used
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	/**
	 * Intercept the given method invocation, submit the actual calling of the
	 * method to the audit.
	 *
	 * @param invocation the method to intercept and make it auditable
	 * @return {@link Future} if the original method
	 * returns {@code Future}; {@code null} otherwise.
	 */
	@Override
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		long currentTimeMillis = System.currentTimeMillis();
		Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
		Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
		final Method userDeclaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

		if (targetClass.isAnnotationPresent(Ignored.class)) {
			return invocation.proceed();
		}

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		MutableAuditEvent auditEvent = auditEventResolver.resolve(
				new DefaultAuditEventContext(request, targetClass, userDeclaredMethod));
		auditEvent.setRequestedAt(new Date(currentTimeMillis));
		try {
			Object result = invocation.proceed();
			auditEvent.setError(false);
			return result;
		} catch (Throwable e) {
			auditEventResolver.handleException(auditEvent, e);
			throw e;
		} finally {
			auditEvent.setTimeCost(System.currentTimeMillis() - currentTimeMillis);
			auditEventDispatcher.dispatch(auditEvent);
		}

	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public void onEvent(AuditEvent o) {
		auditEventPersister.saveAuditEvent(o);
	}

}
