package com.pfb.biz.upper.vo.pay.refund;


import com.pfb.entity.base.AbstractBase;

@SuppressWarnings("serial")
public class UpperRefundResponseMq extends AbstractBase {
	/**
	 * 返回码
	 */
	private String returnCode;
	/**
	 * 返回信息
	 */
	private String returnMsg;
	/**
	 * 接口类型 SACAN,JSAPI
	 */
	private String interfaceType;
	
	private String tradeType;
	/**
	 * 接口业务
	 */
	private String interfaceChannel;
	/**
	 * 商户ID
	 */
	private String merchantId;
	/**
	 * 商户订单号（即聚合平台支付单号）
	 */
	private String outTradeNo;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 三方单号（微信，支付宝）
	 */
	private String transactionId;
	/**
	 * 订单支付状态
	 */
	private String tradeStatus;
	/**
	 * 订单总金额
	 */
	private String totalFee;
	/**
	 * 完成时间 yyyyMMddHHmmss
	 */
	private String payTime;
	/**
	 * 随机串
	 */
	private String nonceStr;
	/**
	 * 签名
	 */
	private String sign;
	private String isFrom;
	public String getReturnCode() {
	
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
	
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
	
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
	
		this.returnMsg = returnMsg;
	}
	public String getInterfaceType() {
	
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
	
		this.interfaceType = interfaceType;
	}
	public String getTradeType() {
	
		return tradeType;
	}
	public void setTradeType(String tradeType) {
	
		this.tradeType = tradeType;
	}
	public String getInterfaceChannel() {
	
		return interfaceChannel;
	}
	public void setInterfaceChannel(String interfaceChannel) {
	
		this.interfaceChannel = interfaceChannel;
	}
	public String getMerchantId() {
	
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
	
		this.merchantId = merchantId;
	}
	public String getOutTradeNo() {
	
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
	
		this.outTradeNo = outTradeNo;
	}
	public String getOrderNo() {
	
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
	
		this.orderNo = orderNo;
	}
	public String getTransactionId() {
	
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
	
		this.transactionId = transactionId;
	}
	public String getTradeStatus() {
	
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
	
		this.tradeStatus = tradeStatus;
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
	public String getIsFrom() {
	
		return isFrom;
	}
	public void setIsFrom(String isFrom) {
	
		this.isFrom = isFrom;
	}
	
		
}
