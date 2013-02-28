package xmvc.core.interceptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import xmvc.core.urlmapping.InterceptorMapping;
import xmvc.core.utils.reflection.BeanUtils;
import xmvc.core.utils.reflection.ClassUtils;

public class InterceptorFactory {
	private Map<String,Set<Object>> interceptors;
	private static InterceptorFactory  inf;
	public static void initInterceptor(Set<InterceptorMapping> interceptorMappings) {
		
		if(interceptorMappings == null || interceptorMappings.size() == 0){
			return;
		}
		
		if(inf != null){
			throw new RuntimeException("InterceptorFactory只允许被初始化一次.");
		}
		inf = new InterceptorFactory();
		
		try {
			for(InterceptorMapping im : interceptorMappings){
				String className = im.getClassName();
				Object interceptor = BeanUtils.instantiateClass(ClassUtils.forName(className));
				Set<String> urlPatterns = im.getUrlPattern();
				if(urlPatterns !=null){
					for(String up : urlPatterns){
						if(inf.interceptors.keySet().contains(up)){
							Set<Object> interceptorss = inf.interceptors.get(up);
							interceptorss.add(interceptor);
						}else{
							Set<Object> interceptorss = new HashSet<Object>();
							interceptorss.add(interceptor);
							inf.interceptors.put(up,interceptorss);
						}
					}
				}
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	
	private InterceptorFactory(){
		interceptors = new HashMap<String,Set<Object>>();
	}
	
	public static void intercept(String actionUrl,Object action,ServletRequest req, ServletResponse resp){
		if(inf == null) throw new RuntimeException("拦截器加载失败,无法拦截任何Action.");
		Set interceptors = inf.getInterceptForAction(actionUrl,inf.interceptors);
		if(interceptors != null){
			for(Object xi : interceptors){
				((XInterceptor) xi).intercept(action, req, resp);
			}
		}
		
	}
	
	private Set getInterceptForAction(String actionUrl,Map<String,Set<Object>> ints){
		Set intss = new HashSet();
		if(ints !=null && ints.keySet() !=null){
			for(String up:ints.keySet()){
				if(isMatch(up,actionUrl)){
					intss.addAll(ints.get(up));
				}
			}
		}
		
		return intss;
	}

	public boolean isMatch(String up, String actionUrl) {
		if(up.startsWith("/")) up = up.substring(1);
		if(!up.contains("*")){
			return up.equalsIgnoreCase(actionUrl);
		}else{
			return Pattern.matches(up, actionUrl);
		}
		
	}
}
