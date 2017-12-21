package com.pfb.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: Constant
 * @Description: 公共定义
 * @author wang.shuran@pufubao.net
 * @version 1.0
 * @date 2016年6月19日 下午3:09:20
 */
public class Constant {

	public static final String xxxx = "xxxx";
	public static Map<String, Object> SYSTEM_ERROR_MAP = null;
	public static List<Object> XXX = null;

	static {

		SYSTEM_ERROR_MAP = new HashMap<String, Object>();
		SYSTEM_ERROR_MAP.put("XXX", "999999");
		SYSTEM_ERROR_MAP.put("XXX", "网络连接超时");
	}
}
