package com.pfb.biz.upper.vo.pay.refundquery;


import com.pfb.biz.upper.vo.base.UpperResponseBase;

@SuppressWarnings("serial")
public class UpperRefundQueryResponse extends UpperResponseBase {

	private String refundNo;
	private String outTradeNo;
	private String refundFee;
	private String totalFee;
	private String orderRefundStatus;
	private String orderStatus;
	public String getRefundNo() {
	
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
	
		this.refundNo = refundNo;
	}
	public String getOutTradeNo() {
	
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
	
		this.outTradeNo = outTradeNo;
	}
	public String getRefundFee() {
	
		return refundFee;
	}
	public void setRefundFee(String refundFee) {
	
		this.refundFee = refundFee;
	}
	public String getTotalFee() {
	
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
	
		this.totalFee = totalFee;
	}
	public String getOrderRefundStatus() {
	
		return orderRefundStatus;
	}
	public void setOrderRefundStatus(String orderRefundStatus) {
	
		this.orderRefundStatus = orderRefundStatus;
	}
	public String getOrderStatus() {
	
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
	
		this.orderStatus = orderStatus;
	}
	
}
