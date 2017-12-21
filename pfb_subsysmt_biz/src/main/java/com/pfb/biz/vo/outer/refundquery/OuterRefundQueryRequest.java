package com.pfb.biz.vo.outer.refundquery;

import com.pfb.biz.vo.base.OuterRequestBase;

public class OuterRefundQueryRequest extends OuterRequestBase {
	
	private String agentNum;
	private String customerNum;
	private String orderNum;
	private String refundNum;
	private String outRefundNum;
	private String deviceInfo;
	private String outerIp;
	private String nonceStr;
	private String sign;
	public String getAgentNum() {
		return agentNum;
	}
	public void setAgentNum(String agentNum) {
		this.agentNum = agentNum;
	}
	public String getCustomerNum() {
		return customerNum;
	}
	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getRefundNum() {
		return refundNum;
	}
	public void setRefundNum(String refundNum) {
		this.refundNum = refundNum;
	}
	public String getOutRefundNum() {
		return outRefundNum;
	}
	public void setOutRefundNum(String outRefundNum) {
		this.outRefundNum = outRefundNum;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	public String getOuterIp() {
		return outerIp;
	}
	public void setOuterIp(String outerIp) {
		this.outerIp = outerIp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
