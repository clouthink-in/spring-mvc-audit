package in.clouthink.daas.audit.annotation;

import in.clouthink.daas.audit.configure.AuditConfigurer;
import in.clouthink.daas.audit.configure.AuditConfigurerAdapter;
import in.clouthink.daas.audit.core.AuditAnnotationAdvisor;
import in.clouthink.daas.audit.core.AuditAnnotationBeanPostProcessor;
import in.clouthink.daas.audit.core.AuditExecutionInterceptor;
import in.clouthink.daas.audit.spi.impl.DefaultAuditEventPersister;
import in.clouthink.daas.audit.spi.impl.DefaultAuditEventResolver;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Configuration
public class AuditConfiguration implements ImportAware, BeanFactoryAware {

	protected ListableBeanFactory beanFactory;

	protected BeanDefinitionRegistry beanDefinitionRegistry;

	protected AnnotationAttributes enableAudit;

	protected AuditConfigurer auditConfigurer = new AuditConfigurerAdapter();

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = (ListableBeanFactory) beanFactory;
		this.beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
	}

	@Autowired(required = false)
	void setConfigurers(Collection<AuditConfigurer> configurers) {
		if (CollectionUtils.isEmpty(configurers)) {
			return;
		}
		if (configurers.size() > 1) {
			throw new IllegalStateException("Only one AuditConfigurer may exist");
		}
		AuditConfigurer configurer = configurers.iterator().next();
		this.auditConfigurer = configurer;
	}

	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		this.enableAudit = AnnotationAttributes.fromMap(
				importMetadata.getAnnotationAttributes(EnableAudit.class.getName(), false));
		Assert.notNull(this.enableAudit,
					   "@EnableAudit is not present on importing class " + importMetadata.getClassName());
	}

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public AuditAnnotationBeanPostProcessor auditAnnotationBeanPostProcessor() {
		AuditAnnotationBeanPostProcessor result = new AuditAnnotationBeanPostProcessor();
		auditConfigurer.configure(result);
		if (result.getAuditEventPersister() == null) {
			result.setAuditEventPersister(new DefaultAuditEventPersister());
		}
		if (result.getAuditEventResolver() == null) {
			result.setAuditEventResolver(new DefaultAuditEventResolver(result.isErrorDetailRequired()));
		}
		if (result.getAuditAnnotationType() == null) {
			result.setAuditAnnotationType(RequestMapping.class);
		}
		result.setOrder(Ordered.LOWEST_PRECEDENCE);
		return result;
	}

}
