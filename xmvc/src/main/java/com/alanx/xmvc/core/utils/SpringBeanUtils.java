package com.alanx.xmvc.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;

import com.alanx.xmvc.core.utils.reflection.ReflectionUtils;


public class SpringBeanUtils {
	
	private static String SPRING_CONTEXT="org.springframework.web.context.WebApplicationContext.ROOT";
	private static String SPRING_AUTOWIRED_CLASSNAME="org.springframework.beans.factory.annotation.Autowired";
	private static Object applicationContext;

	
	public static void holdApplicationContext(ServletContext servletContext){
		if(applicationContext != null) throw new RuntimeException("WebApplicationContext只能被装载一次");
		applicationContext = servletContext.getAttribute(SPRING_CONTEXT);
		if(applicationContext ==null) {
			throw new RuntimeException("装载Spring Context失败，请检查应用启动时，spring是否被载入到servletContext上下文中.");
		}
	}
	
	private SpringBeanUtils(){
	}
	
	public static void autowire(Object subAction) {
		if(subAction == null) return;
		if(applicationContext == null) throw new RuntimeException("获取ServletContext异常，请确保应用启动时ServletContext被正确装载.");
		
		try {
			Method method = applicationContext.getClass().getMethod("getBean",String.class);
			Field[] fields = subAction.getClass().getDeclaredFields();
			if(fields !=null && fields.length > 0){
				for(Field field:fields){
					Annotation[] annotations = field.getAnnotations();
					if(annotations !=null && annotations.length > 0){
						for(Annotation annotation:annotations){
							
							String className = annotation.annotationType().getName();
							if(className.equals(SPRING_AUTOWIRED_CLASSNAME)){
								Object[] args = {field.getName()};
								Object springBean = method.invoke(applicationContext, args);
								field.setAccessible(true);
								field.set(subAction, springBean);
								field.setAccessible(false);
							}
						}
					}
				}
			}
			Field field = subAction.getClass().getDeclaredField("testService");
			
		} catch (Throwable e) {
			throw new RuntimeException("Action中注入Spring Bean失败.",e);
		}
		
	}

}
