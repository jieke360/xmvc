package xmvc.core;

import java.io.IOException;
import java.io.Serializable;


import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





public class Processor implements Serializable{
	private static final long serialVersionUID = 6291148630387055409L;

	public static final Logger log = LoggerFactory.getLogger(Processor.class);
	
	protected ServletRequest req;
	protected ServletResponse resp;
	protected URLMapping mapping;
	protected Processor(){
	}

	private Processor(ServletRequest req, ServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public static void process(ServletRequest req, ServletResponse resp) throws ServletException, IOException{
		Processor p = new Processor(req,resp);
		if(req.getAttribute(URLConvert.SERVLET_PATH)!=null && ((String)req.getAttribute(URLConvert.SERVLET_PATH)).trim().length() > 0){
			//TODO:检查兼容性
			p.processURI((String)req.getAttribute(URLConvert.SERVLET_PATH));
		}else{
			p.processURI(((HttpServletRequest) req).getRequestURI());
		}
		
		if(!Authority.hasAuthority(p)){
			throw new RuntimeException("无权限访问此url");
		}
		
		
		Object processObject = p.getSubInstance();
		
		if(processObject == null){
			log.warn("URL没有发现对应的process,执行Processor.defaultProcess()");
			p.defaultProcess();
		}else{
			if (p.mapping!= null &&p.mapping.getMethodName() != null && p.mapping.getMethodName().trim().length() > 0) {
				try {
					if(req.getAttribute(URLConvert.SERVLET_PATH)==null || ((String)req.getAttribute(URLConvert.SERVLET_PATH)).trim().length() == 0){
						log.info("初始化请求参数--{}.{}()",p.mapping.getClassName(),p.mapping.getMethodName());
						ModelConvert.convert(processObject,req);
					}
					log.info("执行{}.{}()",p.mapping.getClassName(),p.mapping.getMethodName());
					processObject.getClass().getMethod(p.mapping.getMethodName()).invoke(processObject);
				} catch (Exception e) {
					throw new RuntimeException("处理器方法调用失败", e);
				}
			} else {
				throw new RuntimeException("处理器方法调用失败,请检查配置文件中methodName是否正确");
			}
		}
		
		if (resp.isCommitted()){
			return;
		}
		String url = p.mapping.getUri();
		if (req.getAttribute(URLConvert.SERVLET_PATH) != null && ((String) req.getAttribute(URLConvert.SERVLET_PATH)).trim().length() > 0) {
			req.getRequestDispatcher(url).include(req, resp);
		} else {
			req.getRequestDispatcher(url).forward(req, resp);
		}
	}
	
	private String defaultProcess(){
		return null;
	}
	
	private Object getSubInstance() {
		try {
			if(mapping !=null && mapping.getClassName() != null && mapping.getClassName().trim().length() >0){
				Object object = Class.forName(mapping.getClassName()).newInstance();
				if(object instanceof Processor){
					Processor subProcessor = (Processor)object;
					subProcessor.req = this.req;
					subProcessor.resp = this.resp;
					return subProcessor;
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
			throw new RuntimeException("无法获取正确的url,请检查配置文件，并确保url后缀是"+URLConvert.SUFFIX);
		}
	
	}
}
