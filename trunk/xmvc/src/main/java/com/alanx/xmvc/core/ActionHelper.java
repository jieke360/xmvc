package com.alanx.xmvc.core;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class ActionHelper{
	
	private static final ThreadLocal<ActionHelper> tl = new ThreadLocal<ActionHelper>();
	public static final Logger log = LoggerFactory.getLogger(ActionHelper.class);
	private ServletRequest req;
	private ServletResponse resp;
	
	public static void initServletContext(ServletRequest req,ServletResponse resp){
		
		ActionHelper sc = tl.get();
		if(sc == null){
			sc = new ActionHelper();
		}
		log.info("线程上下文中填入request,response对象");
		sc.req = req;
		sc.resp = resp;
		tl.set(sc);
	}
	
	public static ServletRequest getRequest(){
		ActionHelper sc = tl.get();
		return sc.req;
	}
	
	public static ServletResponse getResponse(){
		ActionHelper sc = tl.get();
		return sc.resp;
	}
	
	private ActionHelper(){
		
	}
}
