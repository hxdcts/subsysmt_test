package com.pfb.biz.vo.outer.query;

import com.pfb.customer.vo.base.OuterResponseBase;

/**
 * 
* @desc: 响应给网关的订单查询结果
* @author: cts
* @createTime: 2017年12月5日 下午5:20:43
* @history:
* @version: v1.0
 */
public class OuterOrderQueryResponse extends OuterResponseBase {

		
	private static final long serialVersionUID = 1L;
	private String customerNum;
    private String orderNum;
    private String channelNum;
    private String orderType;
    private String orderStatus;
    private String totalFee;
    private String actualFee;
    private String activityFee;
    private String activityList;
    private String feeType;
    private String fundType;
    private String completeTime;
    private String remark;
    private String expand;
    private String nonceStr;
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
