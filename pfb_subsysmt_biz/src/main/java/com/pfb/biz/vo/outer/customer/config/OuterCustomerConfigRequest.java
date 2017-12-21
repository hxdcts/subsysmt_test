package com.pfb.biz.vo.outer.customer.config;

import com.pfb.biz.vo.base.OuterRequestBase;

@SuppressWarnings("serial")
public class OuterCustomerConfigRequest extends OuterRequestBase {
	
	private String agentNum;
	private String customerNum;
	private String channelProduct;
	private String jsapiPath;
	private String appid;
	private String scribeAppid;
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
	public String getChannelProduct() {
		return channelProduct;
	}
	public void setChannelProduct(String channelProduct) {
		this.channelProduct = channelProduct;
	}
	public String getJsapiPath() {
		return jsapiPath;
	}
	public void setJsapiPath(String jsapiPath) {
		this.jsapiPath = jsapiPath;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getScribeAppid() {
		return scribeAppid;
	}
	public void setScribeAppid(String scribeAppid) {
		this.scribeAppid = scribeAppid;
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
