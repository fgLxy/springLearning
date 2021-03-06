package org.springframework.core.env;

import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class MissingRequiredPropertiesException extends IllegalStateException {
	
	private final Set<String> missingRequiredProperties = new LinkedHashSet<String>();
	
	public Set<String> getMissingRequiredProperties() {
		return missingRequiredProperties;
	}
	
	void addMissingRequiredProperty(String key) {
		missingRequiredProperties.add(key);
	}
	
	@Override
	public String getMessage() {
		return String.format("The following properties were declared as required but could " +
				"not be resolved: %s", this.getMissingRequiredProperties());
	}
}
