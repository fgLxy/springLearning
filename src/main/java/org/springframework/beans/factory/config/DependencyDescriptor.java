package org.springframework.beans.factory.config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.core.GenericCollectionTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.Assert;

public class DependencyDescriptor implements Serializable {

	private static final long serialVersionUID = 1387575413333084519L;
	
	private transient MethodParameter methodParameter;
	
	private transient Field field;
	
	private Class<?> declaringClass;
	
	private String methodName;
	
	private Class<?>[] parameterTypes;
	
	private int parameterIndex;
	
	private String fieldName;
	
	private final boolean required;
	
	private final boolean eager;
	
	private int nestingLevel = 1;
	
	private transient Annotation[] fieldAnnotations;
	
	public DependencyDescriptor(MethodParameter methodParameter, boolean required) {
		this(methodParameter, required, true);
	}
	
	public DependencyDescriptor(MethodParameter methodParameter, boolean required, boolean eager) {
		Assert.notNull(methodParameter, "methodParameter must not be null");
		this.methodParameter = methodParameter;
		this.declaringClass = methodParameter.getDeclaringClass();
		if(this.methodParameter.getMethod() != null) {
			this.methodName = methodParameter.getMethod().getName();
			this.parameterTypes = methodParameter.getMethod().getParameterTypes();
		} else {
			this.parameterTypes = methodParameter.getConstructor().getParameterTypes();
		}
		this.parameterIndex = methodParameter.getParameterIndex();
		this.required = required;
		this.eager = eager;
	}
	
	public  DependencyDescriptor(Field field, boolean required) {
		this(field, required, true);
	}
	
	public DependencyDescriptor(Field field, boolean required, boolean eager) {
		Assert.notNull(field, "Field must not be null");
		this.field = field;
		this.declaringClass = field.getDeclaringClass();
		this.fieldName = field.getName();
		this.required = required;
		this.eager = eager;
	}
	
	public DependencyDescriptor(DependencyDescriptor original) {
		this.methodParameter = (original.methodParameter != null ? new MethodParameter(original.methodParameter) : null);
		this.field = original.field;
		this.declaringClass = original.declaringClass;
		this.methodName = original.methodName;
		this.parameterTypes = original.parameterTypes;
		this.parameterIndex = original.parameterIndex;
		this.fieldName = original.fieldName;
		this.required = original.required;
		this.eager = original.eager;
		this.nestingLevel = original.nestingLevel;
		this.fieldAnnotations = original.fieldAnnotations;
	}
	
	public MethodParameter getMethodParameter() {
		return this.methodParameter;
	}
	
	public Field getField() {
		return this.field;
	}
	
	public boolean isRequired() {
		return this.required;
	}
	
	public boolean isEager() {
		return this.eager;
	}
	
	public void increaseNestingLevel() {
		this.nestingLevel++;
		if (this.methodParameter != null) {
			this.methodParameter.increaseNestingLevel();
		}
	}
	
	public void initParameterNameDiscovery(ParameterNameDiscoverer parameterNameDiscoverer) {
		if (this.methodParameter != null) {
			this.methodParameter.initParameterNameDiscovery(parameterNameDiscoverer);
		}
	}
	
	public Class<?> getDependencyType() {
		if (this.field != null) {
			if (this.nestingLevel > 1) {
				Type type = this.field.getGenericType();
				if (type instanceof ParameterizedType) {
					Type arg = ((ParameterizedType) type).getActualTypeArguments()[0];
					if (arg instanceof Class) {
						return (Class<?>) arg;
					}
					else if (arg instanceof ParameterizedType) {
						arg = ((ParameterizedType) arg).getRawType();
						if (arg instanceof Class) {
							return (Class<?>) arg;
						}
					}
				}
				return Object.class;
			}
			else {
				return this.field.getType();
			}
		}
		else {
			return this.methodParameter.getNestedParameterType();
		}
	}
	
	public Class<?> getCollectionType() {
		return (this.field != null ?
				GenericCollectionTypeResolver.getCollectionFieldType(this.field, this.nestingLevel) :
				GenericCollectionTypeResolver.getCollectionParameterType(this.methodParameter));
	}
	
	public Class<?> getMapKeyType() {
		return (this.field != null ?
				GenericCollectionTypeResolver.getMapKeyFieldType(this.field, this.nestingLevel) :
				GenericCollectionTypeResolver.getMapKeyParameterType(this.methodParameter));
	}
	
	public Class<?> getMapValueType() {
		return (this.field != null ?
				GenericCollectionTypeResolver.getMapValueFieldType(this.field, this.nestingLevel) :
				GenericCollectionTypeResolver.getMapValueParameterType(this.methodParameter));
	}
	
	public Annotation[] getAnnotations() {
		if (this.field != null) {
			if (this.fieldAnnotations == null) {
				this.fieldAnnotations = this.field.getAnnotations();
			}
			return this.fieldAnnotations;
		}
		else {
			return this.methodParameter.getParameterAnnotations();
		}
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		
		try {
			if (this.fieldName != null) {
				this.field = this.declaringClass.getDeclaredField(this.fieldName);
			}
			else {
				if (this.methodName != null) {
					this.methodParameter = new MethodParameter(
							this.declaringClass.getDeclaredConstructor(this.parameterTypes), this.parameterIndex);
				}
				else {
					this.methodParameter = new MethodParameter(
							this.declaringClass.getDeclaredConstructor(this.parameterTypes), this.parameterIndex);
				}
				for (int i = 1; i < this.nestingLevel; i++) {
					this.methodParameter.increaseNestingLevel();
				}
			}
		}
		catch (Throwable ex) {
			throw new IllegalStateException("Could not find original class structure", ex);
		}
	}
	
}
