package com.pfb.vo;

/**
 * 下游商户返回参数基类
 * 
 * @author zhanghaixia
 */
@SuppressWarnings("serial")
public class OuterResponseBase extends AbstractVO {

	/** 相应码 */
	private String returnCode="000000";
	/** 相应信息 */
	private String returnMsg="SUCCESS";
	/** 签名 */
	private String sign;

	
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
