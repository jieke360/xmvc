package com.alanx.xmvc.core.urlmapping;

import java.util.Set;

public class InterceptorMapping {
	private String className;
	private Set<String> urlPattern;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Set<String> getUrlPattern() {
		return urlPattern;
	}
	public void setUrlPattern(Set<String> urlPattern) {
		this.urlPattern = urlPattern;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InterceptorMapping other = (InterceptorMapping) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		return true;
	}
	
	
}
