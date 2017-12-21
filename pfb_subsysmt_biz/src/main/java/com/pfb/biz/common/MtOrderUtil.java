package com.pfb.biz.common;

import com.pfb.biz.upper.common.UpperCommonConstant;
import com.pfb.common.util.StringUtil;

public class MtOrderUtil {
	/**
	 * 转换美团状态到 网关的状态 
	* @author: cts
	* @createTime: 2017年12月12日 下午4:48:58
	* @history:
	* @param status
	* @return String
	 */
	public static  String getOrderStatus(String status){
		String orderStatus = UpperCommonConstant.FAIL;
		if(StringUtil.isBlank(status)){
			return orderStatus;
		}else if(status.equals(UpperCommonConstant.ORDER_NEW)){
			status = UpperCommonConstant.USERPAYING;
		}else if(status.equals(UpperCommonConstant.ORDER_SUCCESS)){
			status = UpperCommonConstant.SUCCESS;
		}
		return status;
	}
}

	