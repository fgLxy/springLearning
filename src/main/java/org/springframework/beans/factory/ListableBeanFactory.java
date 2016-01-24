package org.springframework.beans.factory;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.BeansException;

public interface ListableBeanFactory extends BeanFactory {
	
	boolean containsBeanDefinition(String beanName);
	
	int getBeanDefinitionCount();
	
	String[] getBeanDefinitionNames();
	
	String[] getBeanNamesForType(Class<?> type);
	
	String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit);
	
	<T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
	
	<T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) 
			throws BeansException;
	
	Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) 
			throws BeansException;
	
	<A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType);
}
