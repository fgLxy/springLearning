package org.springframework.beans.factory.config;

import org.springframework.beans.factory.ObjectFactory;

public interface Scope {
	
	Object get(String name, ObjectFactory<?> objectFasctory);
	
	Object remove(String name);
	
	void registerDesctructionCallback(String name, Runnable callback);
	
	Object resolveContextualObject(String key);
	
	String getConversationId();
	
}
