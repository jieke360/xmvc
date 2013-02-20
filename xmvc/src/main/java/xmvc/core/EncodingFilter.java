package xmvc.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncodingFilter implements Filter
{
	public static final Logger log = LoggerFactory.getLogger(EncodingFilter.class);
	public static final String DEFAULT_ENCODING="UTF-8";
	public static final String DEFAULT_FORCEENCODING="true";
	private static String encoding=DEFAULT_ENCODING;
	private static String forceEncoding=DEFAULT_FORCEENCODING;
	public void destroy() {
		//nothing to do.
	}


	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

		log.info("设置 request encoding:{}",encoding);
		req.setCharacterEncoding(encoding);
		if(forceEncoding.equalsIgnoreCase(DEFAULT_FORCEENCODING)){
			log.info("设置 response encoding:{}",encoding);
			resp.setCharacterEncoding(encoding);
			((HttpServletResponse)resp).setHeader("content-type","text/html;charset=UTF-8");
		}
		chain.doFilter(req, resp);
		
	}

	public void init(FilterConfig filterconfig) throws ServletException {
		try {
			
			log.info("设置Encoding");
			encoding = filterconfig.getInitParameter("encoding");
			forceEncoding = filterconfig.getInitParameter("forceEncoding");
			
			if(encoding !=null){
				encoding = encoding.trim();
			}else{
				encoding = DEFAULT_ENCODING;
			}
			if(forceEncoding !=null){
				forceEncoding = forceEncoding.trim();
			}else{
				forceEncoding = DEFAULT_FORCEENCODING;
			}
			
		} catch (Exception e) {
			throw new RuntimeException("读取配置文件url-config.xml异常",e);
		}
	}

}
