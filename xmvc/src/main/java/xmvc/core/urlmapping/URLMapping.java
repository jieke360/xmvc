package xmvc.core.urlmapping;

public class URLMapping {
	public enum Type{
		URI,CLASSNAME,METHODNAME,ACTION
	}
	private String uri;
	private String className;
	private String methodName;
	private String action;
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
}
