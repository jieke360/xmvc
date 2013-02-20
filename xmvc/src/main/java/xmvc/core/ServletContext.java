package xmvc.core;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class ServletContext{
	
	private static final ThreadLocal<ServletContext> tl = new ThreadLocal<ServletContext>();
	public static final Logger log = LoggerFactory.getLogger(ServletContext.class);
	private ServletRequest req;
	private ServletResponse resp;
	
	public static void initServletContext(ServletRequest req,ServletResponse resp){
		
		ServletContext sc = tl.get();
		if(sc == null){
			sc = new ServletContext();
		}
		log.info("线程上下文中填入request,response对象");
		sc.req = req;
		sc.resp = resp;
		tl.set(sc);
	}
	
	public static ServletRequest getRequest(){
		ServletContext sc = tl.get();
		return sc.req;
	}
	
	public static ServletResponse getResponse(){
		ServletContext sc = tl.get();
		return sc.resp;
	}
	
	private ServletContext(){
		
	}
}
