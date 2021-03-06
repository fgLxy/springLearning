package org.springframework.core;

public abstract class NestedRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 5439915454935047936L;
	
	static {
		//这一步好像是为了避免在使用osgi时造成的死锁。具体文档记录在SPR-5607中。
		NestedExceptionUtils.class.getName();
	}
	
	public NestedRuntimeException(String msg) {
		super(msg);
	}
	
	public NestedRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
	@Override
	public String getMessage() {
		return NestedExceptionUtils.buildMessage(super.getMessage(), getCause());
	}
	
	public Throwable getRootCause() {
		Throwable rootCause = null;
		Throwable cause = getCause();
		while(cause != null && cause != rootCause) {
			rootCause = cause;
			cause = cause.getCause();
		}
		return rootCause;
	}
	
	public Throwable getMostSpecificCasuse() {
		Throwable rootCause = getRootCause();
		return rootCause == null ? this : rootCause;
	}
	
	public boolean contains(Class<?> exType) {
		if(exType == null) {
			return false;
		}
		if(exType.isInstance(this)) {
			return true;
		}
		Throwable cause = getCause();
		if(cause == this) {
			return false;
		}
		if(cause instanceof NestedRuntimeException) {
			return ((NestedRuntimeException) cause).contains(exType);
		} else {
			while(cause != null) {
				if(exType.isInstance(cause)) {
					return true;
				}
				if(cause.getCause() == cause) {
					break;
				}
				cause = cause.getCause();
			}
			return false;
		}
	}
}
