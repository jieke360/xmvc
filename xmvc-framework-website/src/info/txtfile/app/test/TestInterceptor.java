package info.txtfile.app.test;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.alanx.xmvc.core.interceptor.XInterceptor;

public class TestInterceptor implements XInterceptor {
	public void intercept(Object action, ServletRequest req, ServletResponse resp) {
		HttpServletRequest hsr = (HttpServletRequest) req;
		System.out.println(hsr.getQueryString());
	}

}
