package xmvc.core.exception;

import xmvc.core.utils.ObjectUtils;


public abstract class BeansException extends NestedRuntimeException {

	/**
	 * Create a new BeansException with the specified message.
	 * @param msg the detail message
	 */
	public BeansException(String msg) {
		super(msg);
	}

	/**
	 * Create a new BeansException with the specified message
	 * and root cause.
	 * @param msg the detail message
	 * @param cause the root cause
	 */
	public BeansException(String msg, Throwable cause) {
		super(msg, cause);
	}


	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BeansException)) {
			return false;
		}
		BeansException otherBe = (BeansException) other;
		return (getMessage().equals(otherBe.getMessage()) &&
				ObjectUtils.nullSafeEquals(getCause(), otherBe.getCause()));
	}

	public int hashCode() {
		return getMessage().hashCode();
	}

}