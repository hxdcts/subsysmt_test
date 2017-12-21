package com.pfb.biz.vo.outer.webpay;

import com.pfb.biz.vo.base.OuterResponseBase;

public class OuterWebpayResponse extends OuterResponseBase {
	/**
	* TODO
	*/
		
	private static final long serialVersionUID = 1L;

	private String orderType;

    private String redirectUri;

    private String bizId;

    private String appId;

    private String timeStamp;

    private String nonceStr;

    private String prepayId;

    private String signType;
    private String paySign;
    private String transactionId;
	public String getOrderType() {
	
		return orderType;
	}
	public void setOrderType(String orderType) {
	
		this.orderType = orderType;
	}
	public String getRedirectUri() {
	
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
	
		this.redirectUri = redirectUri;
	}
	public String getBizId() {
	
		return bizId;
	}
	public void setBizId(String bizId) {
	
		this.bizId = bizId;
	}
	public String getAppId() {
	
		return appId;
	}
	public void setAppId(String appId) {
	
		this.appId = appId;
	}
	public String getTimeStamp() {
	
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
	
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
	
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
	
		this.nonceStr = nonceStr;
	}
	public String getPrepayId() {
	
		return prepayId;
	}
	public void setPrepayId(String prepayId) {
	
		this.prepayId = prepayId;
	}
	public String getSignType() {
	
		return signType;
	}
	public void setSignType(String signType) {
	
		this.signType = signType;
	}
	public String getPaySign() {
	
		return paySign;
	}
	public void setPaySign(String paySign) {
	
		this.paySign = paySign;
	}
	public String getTransactionId() {
	
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
	
		this.transactionId = transactionId;
	}
}
