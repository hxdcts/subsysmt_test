package com.pfb.biz.upper.vo.pay.refund;


import com.pfb.biz.upper.vo.base.UpperResponseBase;

@SuppressWarnings("serial")
public class UpperRefundResponse extends UpperResponseBase {
		
	private String outTradeNo;
	private String refundFee;
	private String totalFee;
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
	
}
