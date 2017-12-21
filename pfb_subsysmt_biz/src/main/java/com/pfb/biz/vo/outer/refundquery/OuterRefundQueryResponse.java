package com.pfb.biz.vo.outer.refundquery;

import com.pfb.biz.vo.base.OuterResponseBase;

@SuppressWarnings("serial")
public class OuterRefundQueryResponse extends OuterResponseBase {
	
	private String orderNum;
	
	private String fee_type;
	
	private String refundCount;
	
	private String refundOrders;
	
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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getRefundOrders() {
		return refundOrders;
	}

	public void setRefundOrders(String refundOrders) {
		this.refundOrders = refundOrders;
	}

	public String getRefundCount() {
		return refundCount;
	}

	public void setRefundCount(String refundCount) {
		this.refundCount = refundCount;
	}
}
