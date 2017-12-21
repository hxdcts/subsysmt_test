package com.pfb.biz.vo.outer.verify;

import com.pfb.biz.vo.base.OuterResponseBase;

public class OuterVerifySignResponse extends OuterResponseBase {

	private static final long serialVersionUID = 1L;
	private String customerNum;
    private String mchId;
    private String orderNum;
    private String nonceStr;
    private String sign;

    public String getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(String customerNum) {
        this.customerNum = customerNum;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
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
