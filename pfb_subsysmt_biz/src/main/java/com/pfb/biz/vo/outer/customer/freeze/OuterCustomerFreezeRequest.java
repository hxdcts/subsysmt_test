package com.pfb.biz.vo.outer.customer.freeze;

import com.pfb.biz.vo.base.OuterRequestBase;

@SuppressWarnings("serial")
public class OuterCustomerFreezeRequest extends OuterRequestBase {
	private String customerNum;
	
	private String sign;
	

	public String getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
