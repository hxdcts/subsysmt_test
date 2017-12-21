package com.pfb.biz.upper.common;

/**
 * 上游配置信息
 */
public class UpperCommonConfig {

    /* 订单查询地址 */
    private String orderQueryUrl;
    /* 回调地址 */
    private String notifyUrl;
    /* 接入方标识 */
    private String appId;
    /* 密钥 */
    private String upperKey;
    /**
     * 进件
     */
    private String entryStoreUrl;
    /**
     * 进件
     */
    private String entryCustomerUrl;
    /**
     * 扫码支付（用户扫商户）接口
     */
    private String preCreateUrl;
    /**
     * 刷卡支付（商户扫用户）接口
     */
    private String microPayUrl;
    /**
     * 订单退款接口
     */
	private String refundUrl;
	/**
	 * 退款查询接口
	 */
	private String refundQueryUrl;
	/**
	 * 回调通知系统
	 */
	private String microNotifyUrl;
	/**
	 * h5支付获取appId的回调地址
	 */
	private String webPayNotifyUrl;
	/**
	 * 获取openID的请求地址
	 */
	private String authUrl;
	/**
	 * 美团支付H5回调
	 */
	private String redirectUrl;
	
	
	public String getRedirectUrl() {
	
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
	
		this.redirectUrl = redirectUrl;
	}
	public String getAuthUrl() {
	
		return authUrl;
	}
	public void setAuthUrl(String authUrl) {
	
		this.authUrl = authUrl;
	}
	public String getWebPayNotifyUrl() {
	
		return webPayNotifyUrl;
	}
	public void setWebPayNotifyUrl(String webPayNotifyUrl) {
	
		this.webPayNotifyUrl = webPayNotifyUrl;
	}
	public String getMicroNotifyUrl() {
	
		return microNotifyUrl;
	}
	public void setMicroNotifyUrl(String microNotifyUrl) {
	
		this.microNotifyUrl = microNotifyUrl;
	}
	public String getOrderQueryUrl() {
	
		return orderQueryUrl;
	}
	public void setOrderQueryUrl(String orderQueryUrl) {
	
		this.orderQueryUrl = orderQueryUrl;
	}
	public String getNotifyUrl() {
	
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
	
		this.notifyUrl = notifyUrl;
	}
	public String getAppId() {
	
		return appId;
	}
	public void setAppId(String appId) {
	
		this.appId = appId;
	}
	public String getUpperKey() {
	
		return upperKey;
	}
	public void setUpperKey(String upperKey) {
	
		this.upperKey = upperKey;
	}
	public String getPreCreateUrl() {
	
		return preCreateUrl;
	}
	public void setPreCreateUrl(String preCreateUrl) {
	
		this.preCreateUrl = preCreateUrl;
	}
	public String getMicroPayUrl() {
	
		return microPayUrl;
	}
	public void setMicroPayUrl(String microPayUrl) {
	
		this.microPayUrl = microPayUrl;
	}
	public String getRefundUrl() {
	
		return refundUrl;
	}
	public void setRefundUrl(String refundUrl) {
	
		this.refundUrl = refundUrl;
	}
	public String getRefundQueryUrl() {
	
		return refundQueryUrl;
	}
	public void setRefundQueryUrl(String refundQueryUrl) {
	
		this.refundQueryUrl = refundQueryUrl;
	}
	public String getEntryStoreUrl() {
		return entryStoreUrl;
	}
	public void setEntryStoreUrl(String entryStoreUrl) {
		this.entryStoreUrl = entryStoreUrl;
	}
	public String getEntryCustomerUrl() {
		return entryCustomerUrl;
	}
	public void setEntryCustomerUrl(String entryCustomerUrl) {
		this.entryCustomerUrl = entryCustomerUrl;
	}
	
}
