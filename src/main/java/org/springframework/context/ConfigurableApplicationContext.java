package org.springframework.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;


public interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle {
	String CONFIG_LOCATION_DELIMITERS = ",; \t\n";
	String CONVERSION_SERVICE_BEAN_NAME = "conversionService";
	String LOAD_TIME_WEAVER_BEAN_NAME = "loadTimeWeaver";
	String ENVIRONMENT_BEAN_NAME = "environment";
	String SYSTEM_PROPERTIES_BEAN_NAME = "systemProperties";
	String SYSTEM_ENVIRONMENT_BEAN_NAME = "systemEnvironment";
	
	void setId(String id);
	
	void setParent(ApplicationContext parent);
	
	ConfigurableEnvironment getEnvironment();
	
	void setEnvironment(ConfigurableEnvironment environment);
	
	void addBeanFactoryPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor);
	
	void addApplicationListener(ApplicationListener<?> listener);
	
	void refresh() throws BeansException, IllegalStateException;
	
	void registerShutdownHook();
	
	void close();
	
	boolean isActive();
	
	ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
}
