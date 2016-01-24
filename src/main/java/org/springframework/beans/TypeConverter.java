package org.springframework.beans;

import org.springframework.core.MethodParameter;

public interface TypeConverter {
	
	<T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
	
	<T> T convertIfNecessary(Object value, Class<T> requiredType, MethodParameter methodParam) 
			throws TypeMismatchException;
}
