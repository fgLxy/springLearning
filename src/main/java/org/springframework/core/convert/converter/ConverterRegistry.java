package org.springframework.core.convert.converter;

public interface ConverterRegistry {
	
	void addConverter(Converter<?, ?> converter);
	
	void addConverter(Class<?> sourceType, Class<?> targetType, Converter<?, ?> converter);
	
	void addConverter(GenericConverter converter);
	
	void addConverterFactory(ConverterFactory<?, ?> converterFactory);
	
	void removeConvertible(Class<?> sourceType, Class<?> targetType);
}
