package xmvc.core;


import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xmvc.core.exception.ExceptionProcess;
import xmvc.core.interceptor.InterceptorFactory;
import xmvc.core.urlmapping.URLConvert;
import xmvc.core.utils.SpringBeanUtils;



public class XServlet extends HttpServlet {
	private static final long serialVersionUID = -5412164370493300706L;
	public static final Logger log = LoggerFactory.getLogger(XServlet.class);
	public static final String MODEL_CONVERT_PLUGIN = "model-convert-plugin.properties";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("在线程中保存request,response");
		ActionHelper.initServletContext(req, resp);
		resp.setContentType("text/html;charset=UTF-8");
		try {
			XAction.exec(req, resp);
		} catch (Exception e) {
			ExceptionProcess.process(e,req,resp);
		}
	}
	public void init() throws ServletException {
		log.info("启动XMVC core，当前版本:{}",XMVCVersion.getVersion());
		try {
			URLConvert.load(this.getInitParameter("urlConfigPath"));
			log.info("URL配置文件加载完毕.");
			ModelConvert.loadFormJar(MODEL_CONVERT_PLUGIN);
			log.info("系统预定义model转换器加载完毕.");
			Set<String> otherConvertProp = URLConvert.getModelConvertPropPaths();
			if(otherConvertProp != null && otherConvertProp.size()>0){
				log.info("加载自定义model转换器");
				for(String s : otherConvertProp){
					ModelConvert.load(s);
				}
				log.info("自定义model转换器加载完毕.");
			}
			log.info("实例化拦截器");
			InterceptorFactory.initInterceptor(URLConvert.getInterceptorMappings());
			SpringBeanUtils.holdApplicationContext(this.getServletContext());
		} catch (Exception e) {
			throw new RuntimeException("读取配置文件url-config.xml异常",e);
		}
		super.init();
	}
	
	
}
