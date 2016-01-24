package org.springframework.context;

import java.util.Locale;

public class NoSuchMessageException extends RuntimeException {
	
	private static final long serialVersionUID = -7064705978242020845L;

	public NoSuchMessageException(String code, Locale locale) {
		super("No message found under code '" + code + "' for locale '" + locale + "'.");
	}
	
	public NoSuchMessageException(String code) {
		super("No message found under code '" + code + "' for locale '" + Locale.getDefault() + "'.");
	}
	
}
