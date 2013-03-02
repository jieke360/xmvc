package com.alanx.xmvc.core.interceptor;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public interface XInterceptor {
	void intercept(Object action, ServletRequest req, ServletResponse resp);
}
