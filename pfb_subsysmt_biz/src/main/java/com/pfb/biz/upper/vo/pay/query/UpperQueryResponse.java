package com.pfb.biz.upper.vo.pay.query;


import com.pfb.biz.upper.vo.base.UpperResponseBase;

@SuppressWarnings("serial")
public class UpperQueryResponse extends UpperResponseBase {

	private String outTradeNo;
    private String tradeNo;
    private String totalFee;
    private String payTime;
    private String referno;
    private String orderStatus;
	public String getOutTradeNo() {
	
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
	
		this.outTradeNo = outTradeNo;
	}
	public String getTradeNo() {
	
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
	
		this.tradeNo = tradeNo;
	}
	public String getTotalFee() {
	
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
	
		this.totalFee = totalFee;
	}
	public String getPayTime() {
	
		return payTime;
	}
	public void setPayTime(String payTime) {
	
		this.payTime = payTime;
	}
	public String getReferno() {
	
		return referno;
	}
	public void setReferno(String referno) {
	
		this.referno = referno;
	}
	public String getOrderStatus() {
	
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
	
		this.orderStatus = orderStatus;
	}
    
}
