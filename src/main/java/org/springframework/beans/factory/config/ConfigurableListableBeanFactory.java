package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

public interface ConfigurableListableBeanFactory
		extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
	
	void ingnoreDependencyType(Class<?> type);
	
	void ignoreDependencyInterface(Class<?> ifc);
	
	void registerResolvableDependency(Class<?> dependencyType, Object autowiredValue);
	
	boolean isAutowireCandidate(String beanName, DependencyDescriptor descriptor)
			throws NoSuchBeanDefinitionException;
	
	BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;
	
	void freezeConfiguration();
	
	boolean isConfigurationFrozen();
	
	void preInstantiateSingletons() throws BeansException;
	
}
