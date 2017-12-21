package com.pfb.biz.upper.vo.pay.micro;


import com.pfb.biz.upper.vo.base.UpperResponseBase;
/**
 * 
* @desc: 刷卡支付响应参数
* @author: cts
* @createTime: 2017年12月5日 下午2:31:06
* @history:
* @version: v1.0
 */
@SuppressWarnings("serial")
public class UpperMicroPayResponse extends UpperResponseBase {

	/**
	 * 支付宝微信生成的订单号 (交易成功时返回)
	 */
	private String tradeNo;
	/**
	 * 接入方订单号
	 */
    private String outTradeNo;
    /**
     * 交易金额，单位为分
     */
    private String totalFee;
    /**
     * 成功支付时间 unix时间戳
     */
    private long payTime;
    /**
     * 支付成功返回，在支付成功页面的条码下显示
     */
    private String referno;
    /**
     * 订单状态
		'ORDER_NEW' ： 交易新建
		'ORDER_FAILED'：交易失败
		'ORDER_REVERSALING'：交易冲正中
		'ORDER_REVERSAL'：交易冲正
		'ORDER_SUCCESS': 交易成功
		'ORDER_PART_REFUND'：交易半退
		'ORDER_ALL_REFUND'： 交易全退
		'ORDER_REFUNDING'：退款中
		'ORDER_CLOSE'：交易失败
     */
    private String orderStatus;
    /**
     * SUCCESS： 请求成功；FAIL：请求失败 请重试
     */
    private String status;
    /**
     * 错误编码（status 为失败的时候有）
     */
    private String errCode;
    /**
     * 错误消息描述（status 为失败的时候有）
     */
    private String errMsg;
    /**
     * 子错误码（status 为失败的时候有）
     */
    private String subCode;
    /**
     * 子错误码的描述（status 为失败的时候有）
     */
    private String subMsg;
    /**
     * 随机数（status为成功的时候有）
     */
    private String random;
    /**
     * 签名（status为成功的时候有）
     */
    private String sign;
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public long getPayTime() {
		return payTime;
	}
	public void setPayTime(long payTime) {
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getSubMsg() {
		return subMsg;
	}
	public void setSubMsg(String subMsg) {
		this.subMsg = subMsg;
	}
	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
    

}
