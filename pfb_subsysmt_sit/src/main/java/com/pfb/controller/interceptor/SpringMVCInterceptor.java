package com.pfb.controller.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SpringMVCInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out
				.println("-------------------afterCompletion-------------------");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		System.out.println("-------------------postHandle-------------------");
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse arg1, Object arg2) throws Exception {
		System.out.println("-------------------preHandle-------------------");
		Enumeration e = request.getParameterNames();
		Map<String, String> map = new HashMap<String, String>();
		while (e.hasMoreElements()) {
			String param = (String) e.nextElement();
			map.put(param, request.getParameter(param));
		}
		logger.info("拦截到新交易,请求数据为：{}", map.toString());
		return true;
	}

}
