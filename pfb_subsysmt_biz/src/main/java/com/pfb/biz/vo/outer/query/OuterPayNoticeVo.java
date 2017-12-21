package com.pfb.biz.vo.outer.query;


/**
 * 
* @desc: 回掉通知系统
* @author: cts
* @createTime: 2017年12月6日 下午12:01:09
* @history:
* @version: v1.0
 */
@SuppressWarnings("serial")
public class OuterPayNoticeVo  {

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
	 * 银行单号
	 */
	private String orderNo;
	/**
	 * 三方单号（微信，支付宝）
	 */
	private String upperNo;
	/**
	 * 订单支付状态
	 */
	private String payStatus;
	/**
	 * 订单总金额
	 */
	private String totalFee;
	/**
	 * 完成时间 yyyyMMddHHmmss
	 */
	private String completeTime;
	/**
	 * 随机串
	 */
	private String nonceStr;
	/**
	 * 签名
	 */
	private String sign;


	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

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

	public String getUpperNo() {
		return upperNo;
	}

	public void setUpperNo(String upperNo) {
		this.upperNo = upperNo;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
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
