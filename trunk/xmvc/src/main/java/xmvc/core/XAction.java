package xmvc.core;

import java.io.IOException;
import java.io.Serializable;


import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xmvc.core.interceptor.InterceptorFactory;
import xmvc.core.urlmapping.URLConvert;
import xmvc.core.urlmapping.URLMapping;
import xmvc.core.utils.SpringBeanUtils;

public class XAction implements Serializable{
	private static final long serialVersionUID = 6291148630387055409L;

	public static final Logger log = LoggerFactory.getLogger(XAction.class);
	
	protected ServletRequest req;
	protected ServletResponse resp;
	protected URLMapping mapping;
	protected XAction(){
	}

	private XAction(ServletRequest req, ServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public static void exec(ServletRequest req, ServletResponse resp) throws ServletException, IOException{
		HttpServletRequest hreq = (HttpServletRequest) req;
		
		
		
		XAction action = new XAction(req,resp);
		if(req.getAttribute(URLConvert.SERVLET_PATH)!=null && ((String)req.getAttribute(URLConvert.SERVLET_PATH)).trim().length() > 0){
			//TODO:检查兼容性
			action.processURI((String)req.getAttribute(URLConvert.SERVLET_PATH));
		}else{
			action.processURI(hreq.getRequestURI());
		}
		Object subAction = action.getSubInstance();
		//注入SpringBean
		SpringBeanUtils.autowire(subAction);
		//拦截器
		InterceptorFactory.intercept(action.mapping.getAction(),subAction,req,resp);

		if (action.mapping!= null &&action.mapping.getMethodName() != null && action.mapping.getMethodName().trim().length() > 0) {
			try {
				if(req.getAttribute(URLConvert.SERVLET_PATH)==null || ((String)req.getAttribute(URLConvert.SERVLET_PATH)).trim().length() == 0){
					log.info("页面请求--{}{}",hreq.getRequestURL().toString(),hreq.getQueryString());
					ModelConvert.convert(subAction,req);
				}
				log.info("执行{}.{}()",action.mapping.getClassName(),action.mapping.getMethodName());
				subAction.getClass().getMethod(action.mapping.getMethodName()).invoke(subAction);
			} catch (Exception e) {
				throw new RuntimeException("处理器方法调用失败", e);
			}
		} else {
			throw new RuntimeException("处理器方法调用失败,请检查配置文件中methodName是否正确");
		}
	
		
		if (resp.isCommitted()){
			return;
		}
		String url = action.mapping.getUri();
		if (req.getAttribute(URLConvert.SERVLET_PATH) != null && ((String) req.getAttribute(URLConvert.SERVLET_PATH)).trim().length() > 0) {
			req.getRequestDispatcher(url).include(req, resp);
		} else {
			req.getRequestDispatcher(url).forward(req, resp);
		}
	}

	private Object getSubInstance() {
		try {
			if(mapping !=null && mapping.getClassName() != null && mapping.getClassName().trim().length() >0){
				Object object = Class.forName(mapping.getClassName()).newInstance();
				if(object instanceof XAction){
					XAction action = (XAction)object;
					action.req = this.req;
					action.resp = this.resp;
					return action;
				}
				return object;
			}else{
				return null;
			}
			
		} catch (Exception e) {
			throw new RuntimeException("实例化处理器失败,请检查配置文件className是否正确.",e);
		}
	}


	public void processURI(final String uri){
		if(uri == null || uri.trim().equals("") || !uri.endsWith(URLConvert.SUFFIX)){
			throw new RuntimeException("不规范的后缀,转向404");
		}
		
		String action = "";
		if(uri.startsWith("/")) {
			action = uri.substring(1);
		}
	
		mapping = URLConvert.getURLMappingByCondition(action,URLMapping.Type.ACTION);
		
		if(mapping == null || mapping.getUri() == null) {
			log.error("无法获取正确的url:{},请检查配置文件，确保url后缀是{}", uri,URLConvert.SUFFIX);
			throw new RuntimeException("无法获取正确的url,请检查配置文件，并确保url后缀正确.");
		}
	
	}
}
