package org.springframework.core.env;

public interface Environment {
	String[] getActiveProfiles();
	String[] getDefaultProfiles();
	boolean acceptProfiles(String... profiles);
}
