package com.pfb.biz.vo.outer.micro;

import com.pfb.vo.OuterResponseBase;
/**
 * 
* @desc: 返给网关的响应参数
* @author: cts
* @createTime: 2017年12月5日 下午2:38:43
* @history:
* @version: v1.0
 */
public class OuterMicroResponse extends OuterResponseBase {
	/**
	* TODO
	*/
		
	private static final long serialVersionUID = 1L;
	/**
	 * 商户号
	 */
    private String customerNum;
    /**
     * 平台订单号
     */
    private String orderNum;
    /**
     * 三方订单号
     */
    private String channelNum;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 交易状态
     */
    private String orderStatus;
    /**
     * 订单金额
     */
    private String totalFee;
    /**
     * 实际付款金额
     */
    private String actualFee;
    /**
     * 活动金额
     */
    private String activityFee;
    /**
     * 奖励金额来源
     */
    private String activityList;
    /**
     * 货币类型
     */
    private String feeType;
    /**
     * 付款卡类型
     */
    private String fundType;
    /**
     * 支付完成时间
     */
    private String completeTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 拓展
     */
    private String expand;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 签名
     */
    private String sign;

    public String getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(String customerNum) {
        this.customerNum = customerNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getActualFee() {
        return actualFee;
    }

    public void setActualFee(String actualFee) {
        this.actualFee = actualFee;
    }

    public String getActivityFee() {
        return activityFee;
    }

    public void setActivityFee(String activityFee) {
        this.activityFee = activityFee;
    }

    public String getActivityList() {
        return activityList;
    }

    public void setActivityList(String activityList) {
        this.activityList = activityList;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
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
