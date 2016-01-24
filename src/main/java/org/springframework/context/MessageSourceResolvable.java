package org.springframework.context;

public interface MessageSourceResolvable {
	
	String[] getCodes();
	
	Object[] getArguments();
	
	String getDefaultMessage();
	
}
