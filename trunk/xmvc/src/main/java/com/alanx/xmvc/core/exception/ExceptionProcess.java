package com.alanx.xmvc.core.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alanx.xmvc.core.XServlet;






/**
 * 异常处理类，负责异常日志的生成，异常的转换和处理
 * 
 * @author xiaoqulai
 */
public final class ExceptionProcess {
	
	/**
	 * 不允许被实例化
	 */
	private ExceptionProcess() {
	}

	/** 自定义错误码444表示服务器执行请求阶段出现异常 */
	public static final int RESPONSE_STATUS = 444;

	/** 日志记录 */
	public static final Logger log = LoggerFactory.getLogger(XServlet.class);

	public static final String NA = "N/A";
	private static String getStackTrace(final Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}

	private static String getQueryString(final HttpServletRequest request) {
		if (request == null || request.getQueryString() == null)
			return "";
		String queryString = request.getQueryString().trim();

		try {
			queryString = new String(queryString.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e2) {
			queryString = "[中文转码失败:]" + request.getQueryString();
		}
		return queryString;
	}
	
	/**
	 * 生成的UUID可以作为数据库主键（与oracle guid的生成策略相同，为32位十六进制编码）
	 * @return UUID
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	
	private static ExceptionLog initExceptionLog(final Exception e,  final HttpServletRequest request) {
		String queryString = ExceptionProcess.getQueryString(request);
		
		final ExceptionLog exceptionLog = new ExceptionLog();
		exceptionLog.setId(ExceptionProcess.uuid());
		exceptionLog.setCreateTime(new Date());
		exceptionLog.setDescription(getStackTrace(e));
		exceptionLog.setExceptionName(e.getClass().getName());
		
		exceptionLog.setLocalAddr(request.getLocalAddr());
		exceptionLog.setLocalPort(String.valueOf(request.getLocalPort()));
		exceptionLog.setRemoteAddr(getRequestIp(request));
		if (request.getRequestURL() != null) {
			exceptionLog.setRequestUrl(request.getRequestURL().toString());
		}
		
		exceptionLog.setMessage(e.getMessage());
		exceptionLog.setOperator(request.getSession().getId());
		exceptionLog.setQueryString(queryString);
		
		return exceptionLog;
	}

	private static String getRequestIp(HttpServletRequest request){
		String clientIp = "";
		
		String clientIp1 = request.getHeader("X-Forwarded-For");
		String clientIp2 = request.getHeader("Proxy-Client-IP");
		String clientIp3 = request.getHeader("WL-Proxy-Client-IP");
		String clientIp4 = request.getRemoteAddr();
		
		String clientIp5 = clientIp1+","+clientIp2+","+clientIp3+","+clientIp4;
		clientIp5 = clientIp5.replace("unknown", "");
		String[] clientIps = clientIp5.split(",");
		if(clientIps != null){
			Set<String> clientIpss = new HashSet<String>();
			for(String temp : clientIps){
				clientIpss.add(temp);
			}
			for(String temp : clientIpss){
				if(temp != null && !temp.equalsIgnoreCase("null")){
					clientIp = clientIp + temp + " ";
				}
			}
		}
		return clientIp;
	}
	

	/**
	 * 异常处理
	 * @param e 异常
	 * @param req 请求
	 * @param resp 响应
	 * @throws IOException 
	 */
	public static void process(Exception e, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		ExceptionLog el = initExceptionLog(e,req);
		log.error(el.getDescription());
		resp.setStatus(RESPONSE_STATUS);
		resp.getWriter().println(errorMessage(el));
		resp.getWriter().close();
	}

	private static String errorMessage(ExceptionLog el) {
		StringBuilder sb = new StringBuilder("");
		sb.append("<h2>ERROR:").append(el.getMessage()).append("</h2>")
		.append("<hr/>")
		.append("<table>")
		.append("<tr>").append("<td width='100px'>错误编码:</td>").append("<td>").append(el.getId()).append("</td>").append("</tr>")
		.append("<tr>").append("<td>异常类型:</td>").append("<td>").append(el.getExceptionName()).append("</td>").append("</tr>")
		.append("<tr>").append("<td>客户端地址:</td>").append("<td>").append(el.getRemoteAddr()).append("</td>").append("</tr>")
		.append("<tr>").append("<td>服务器地址:</td>").append("<td>").append(el.getLocalAddr()).append("</td>").append("</tr>")
		.append("<tr>").append("<td>服务器端口:</td>").append("<td>").append(el.getLocalPort()).append("</td>").append("</tr>")
		.append("<tr>").append("<td>请求地址:</td>").append("<td>").append(el.getRequestUrl()).append("</td>").append("</tr>")
		.append("<tr>").append("<td>请求参数:</td>").append("<td>").append(el.getQueryString()).append("</td>").append("</tr>")
		.append("<tr>").append("<td>操作人信息:</td>").append("<td>").append(el.getOperator()).append("</td>").append("</tr>")
		.append("<tr>").append("<td>出错时间:</td>").append("<td>").append(el.getCreateTime()).append("</td>").append("</tr>")
		.append("<tr>").append("<td>出错日志:</td>").append("<td>").append(el.getDescription().replaceAll("\\\n", "<br/>")).append("</td>").append("</tr>")
		.append("</table>");
		return sb.toString();
	}

}
