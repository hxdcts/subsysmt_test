package com.pfb.biz.vo.outer.refund;

import com.pfb.biz.vo.base.OuterResponseBase;

public class OuterRefundResponse extends OuterResponseBase {
		
	private static final long serialVersionUID = 1L;
	private String refundNum;
    private String channelRefundNum;
    private String refundFee;
    private String settlementFee;
    private String fee_type;
    private String nonceStr;
    private String sign;

    @Override
    public String getReturnCode() {
        return returnCode;
    }

    @Override
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    @Override
    public String getReturnMsg() {
        return returnMsg;
    }

    @Override
    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getRefundNum() {
        return refundNum;
    }

    public void setRefundNum(String refundNum) {
        this.refundNum = refundNum;
    }

    public String getChannelRefundNum() {
        return channelRefundNum;
    }

    public void setChannelRefundNum(String channelRefundNum) {
        this.channelRefundNum = channelRefundNum;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public String getSettlementFee() {
        return settlementFee;
    }

    public void setSettlementFee(String settlementFee) {
        this.settlementFee = settlementFee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
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
