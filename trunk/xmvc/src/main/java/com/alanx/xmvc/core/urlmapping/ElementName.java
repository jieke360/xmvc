package com.alanx.xmvc.core.urlmapping;

public enum ElementName {
	ROOT_NAME("url-config"),
	URLMAPPING_NAME("URLMapping"),
	IMPORT("import"),
	MODEL_CONVERT_PROP_PATH("ModelConvertPropPath"),
	URI_NAME("uri"),
	CLASS_NAME("className"),
	METHODNAME_NAME("methodName"),
	ACTION_NAME("action"),
	INTERCEPTOR("Interceptor"),
	URL_PATTERN("urlPattern");
	
	protected String value;
	
	private ElementName(String value){
		this.value = value;
	}
	
}