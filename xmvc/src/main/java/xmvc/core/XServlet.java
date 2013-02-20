package xmvc.core;


import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class XServlet extends HttpServlet {
	private static final long serialVersionUID = -5412164370493300706L;
	public static final Logger log = LoggerFactory.getLogger(XServlet.class);
	public static final String MODEL_CONVERT_PLUGIN = "model-convert-plugin.properties";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("在线程中保存request,response");
		ServletContext.initServletContext(req, resp);
		resp.setContentType("text/html;charset=UTF-8");
		try {
			Processor.process(req, resp);
		} catch (Exception e) {
			ExceptionProcess.process(e,req,resp);
		}
	}
	public void init() throws ServletException {
		try {
			URLConvert.load(this.getInitParameter("urlConfigPath"));
			log.info("URL配置文件加载完毕");
			if(this.getInitParameter("modelConvertPropPath") != null){
				ModelConvert.loadFormJar(MODEL_CONVERT_PLUGIN);
				ModelConvert.load(this.getInitParameter("modelConvertPropPath"));
				log.info("ModelConvert属性文件加载完毕");
			}
			
		} catch (Exception e) {
			throw new RuntimeException("读取配置文件url-config.xml异常",e);
		}
		super.init();
	}
	
	
}
