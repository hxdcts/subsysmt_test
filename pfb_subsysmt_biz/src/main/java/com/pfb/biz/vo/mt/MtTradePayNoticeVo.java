package com.pfb.biz.vo.mt;

import com.pfb.entity.base.AbstractBase;

/**
 * 
* @desc: 美团支付订单回调通知参数
* @author: cts
* @createTime: 2017年12月6日 上午11:19:08
* @history:
* @version: v1.0
 */
public class MtTradePayNoticeVo extends AbstractBase {
	private static final long serialVersionUID = -385876432832450952L;
	private String appId;	
	/**
	 * 商户订单号（即聚合平台支付单号）
	 */
	private String outTradeNo;	
	/**
	 * 完成时间 yyyyMMddHHmmss
	 */
	private String payTime;	
	private String transactionId;
	private String providerTradeNo;
    private String openId;
    private String sign;
    /**
	 * 订单总金额
	 */
    private String totalFee;
    private String nonce_str;
    /**
	 * 订单支付状态
	 */
    private String tradeStatus;
    private String subOpenId;
	public String getAppId() {
	
		return appId;
	}
	public void setAppId(String appId) {
	
		this.appId = appId;
	}
	public String getOutTradeNo() {
	
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
	
		this.outTradeNo = outTradeNo;
	}
	public String getPayTime() {
	
		return payTime;
	}
	public void setPayTime(String payTime) {
	
		this.payTime = payTime;
	}
	public String getTransactionId() {
	
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
	
		this.transactionId = transactionId;
	}
	public String getProviderTradeNo() {
	
		return providerTradeNo;
	}
	public void setProviderTradeNo(String providerTradeNo) {
	
		this.providerTradeNo = providerTradeNo;
	}
	public String getOpenId() {
	
		return openId;
	}
	public void setOpenId(String openId) {
	
		this.openId = openId;
	}
	public String getSign() {
	
		return sign;
	}
	public void setSign(String sign) {
	
		this.sign = sign;
	}
	public String getTotalFee() {
	
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
	
		this.totalFee = totalFee;
	}
	public String getNonce_str() {
	
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
	
		this.nonce_str = nonce_str;
	}
	public String getTradeStatus() {
	
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
	
		this.tradeStatus = tradeStatus;
	}
	public String getSubOpenId() {
	
		return subOpenId;
	}
	public void setSubOpenId(String subOpenId) {
	
		this.subOpenId = subOpenId;
	}
    
}
