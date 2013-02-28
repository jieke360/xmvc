package xmvc.core.exception;

import java.util.Date;

/**
 * 统一异常日志类
 * @author sheqing
 *
 */
public class ExceptionLog  implements java.io.Serializable{
	
	private static final long serialVersionUID = -4282155318746383154L;
	
	private String id;
	private String exceptionCode;	//异常编码
	private String exceptionName;	//异常名称
	private String message;			//错误提示
	private String description;		//异常描述
	private String requestUrl;		//请求的URL
	private String queryString;		//查询条件
	private String remoteAddr;		//客服端IP
	private String localAddr;		//服务器端IP
	private String localPort;		//服务器端口号
	private String operator;		//引起异常的操作人
	private Date createTime;		//异常发生时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	public String getExceptionName() {
		return exceptionName;
	}
	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public String getLocalAddr() {
		return localAddr;
	}
	public void setLocalAddr(String localAddr) {
		this.localAddr = localAddr;
	}
	public String getLocalPort() {
		return localPort;
	}
	public void setLocalPort(String localPort) {
		this.localPort = localPort;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
