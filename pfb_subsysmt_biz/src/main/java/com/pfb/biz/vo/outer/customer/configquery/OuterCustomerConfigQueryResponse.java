package com.pfb.biz.vo.outer.customer.configquery;

import com.pfb.biz.vo.base.OuterResponseBase;

@SuppressWarnings("serial")
public class OuterCustomerConfigQueryResponse extends OuterResponseBase {

	private String jsapiPathList;
	
	private String appidConfList;
	
	private String nonceStr;
	
	private String sign;

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

	public String getJsapiPathList() {
		return jsapiPathList;
	}

	public void setJsapiPathList(String jsapiPathList) {
		this.jsapiPathList = jsapiPathList;
	}

	public String getAppidConfList() {
		return appidConfList;
	}

	public void setAppidConfList(String appidConfList) {
		this.appidConfList = appidConfList;
	}
}
