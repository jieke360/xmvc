package com.alanx.xmvc.core.urlmapping;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class URLConfig {
	private Map<String,URLMapping> urlMappings = new HashMap<String,URLMapping>();
	private Set<String> modelConvertPropPaths = new HashSet<String>();
	private Set<String> imports = new HashSet<String>();
	private Set<InterceptorMapping> interceptorMappings = new HashSet<InterceptorMapping>();
	public Map<String, URLMapping> getUrlMappings() {
		return urlMappings;
	}
	public void setUrlMappings(Map<String, URLMapping> urlMappings) {
		this.urlMappings = urlMappings;
	}
	public Set<String> getModelConvertPropPaths() {
		return modelConvertPropPaths;
	}
	public void setModelConvertPropPaths(Set<String> modelConvertPropPaths) {
		this.modelConvertPropPaths = modelConvertPropPaths;
	}
	public Set<String> getImports() {
		return imports;
	}
	public void setImports(Set<String> imports) {
		this.imports = imports;
	}
	public Set<InterceptorMapping> getInterceptorMappings() {
		return interceptorMappings;
	}
	public void setInterceptorMappings(Set<InterceptorMapping> interceptorMappings) {
		this.interceptorMappings = interceptorMappings;
	}
	
	
}
