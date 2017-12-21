package com.pfb.controller.base;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
	private Logger logger = Logger.getLogger(BaseController.class);
	
	
	/** 前台登录用户标志，可以通过此标识从SESSION中获取前台“当前登录用户” */
	public static final String CACHED_LOGIN_USER = "_cached_login_user";
	public static final String CACHED_SALES_DETAIL = "_cached_sales_detail";
	public static final String USER_ROLES = "user_roles";
	/** 注册验证码   */
	public static final String REG_CHECK_CODE = "register_check_code";
	/** 发送手机短信验证码    */
	public static final String PHONE_VALID = "phone_valid";
	/** 一个小时之内同一IP允许发送短信验证的最大条数 */
	public static final int SEND_PHONE_VALID_COUNT = 30;
	public static final String ERROR = "error";
	public static final String ERROR_PAGE = "boss/common/error";
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	protected HttpServletRequest request;  
    protected HttpServletResponse response;
    
    @ModelAttribute
    public void setWebAttr(HttpServletRequest request, HttpServletResponse response){
    	this.request = request;
    	this.response = response;
    }

	/**
	 * 获取用户IP
	 * @author 于增佳
	 * @since 2013-6-26 下午4:14:34
	 * @param request
	 * @return
	 */
	protected String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	
	/**
	 * 父子分离，要求实体类中必须有id,parentId,children字段.
	 *
	 * @param list
	 * @param parentId
	 * @return
	 * @author 尹有福 
	 * @throws Exception 
	 * @date: 2015年10月15日 下午4:40:53
	 */
	public List<?> separate(List<?> list, long parentId) throws Exception {
		List<Object> result = new ArrayList<Object>();
		if(list != null && !list.isEmpty()){
			for(int i=0; i<list.size(); i++){
				Object t = list.get(i);
				Class<?> cls = t.getClass();
				Method pidReadMethod = (new PropertyDescriptor("parentId", cls)).getReadMethod();
				Object pid = pidReadMethod.invoke(t);
				if(pid != null 
						&& Long.parseLong(String.valueOf(pid)) == parentId){
					result.add(t);
					
					Method childWriteMethod = (new PropertyDescriptor("children", cls)).getWriteMethod();
					Method idReadMethod = (new PropertyDescriptor("id", cls)).getReadMethod();
					childWriteMethod.invoke(t, separate(list, Long.parseLong(String.valueOf(idReadMethod.invoke(t)))));
				}
			}
		}
		return result;
	}
	
	
	/**
	 * @Desc 	判断是否是微信浏览器
	 * @Author 	King 
	 * @Date 	2015年11月1日下午11:53:15
	 * @Package com.mvc.core.controller.mobile.filter
	 * @param request 作用域参数
	 * @return 返回参数（是：true 否 ：false）
	 */
	public boolean isWeixin(HttpServletRequest request){
		String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
			logger.info("=====================微信浏览器=======================");
			return true;
		}else{
			logger.info("=====================其他浏览器=======================");
			return false;
		}
	}
	
}