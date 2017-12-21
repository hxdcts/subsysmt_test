package com.pfb.biz.vo.outer.customer.query;

import com.pfb.biz.vo.base.OuterRequestBase;

@SuppressWarnings("serial")
public class OuterCustomerQueryRequest extends OuterRequestBase {
	
	private String agentNum;
	
	private String customerNum;
	
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
