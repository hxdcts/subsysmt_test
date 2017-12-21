package com.pfb.biz.vo.outer.customer.entry;

import com.pfb.biz.vo.base.OuterResponseBase;

@SuppressWarnings("serial")
public class OuterCustomerEntryResponse extends OuterResponseBase {
	
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
	
	
	
	
}
