package com.pfb.biz.vo.outer.refundquery;

import com.pfb.biz.vo.base.OuterResponseBase;

@SuppressWarnings("serial")
public class OuterRefundDetailResponse extends OuterResponseBase {
	
	/**
	   * 平台退款单号
	   */
	  private String refundNum;
	  /**
	   * 申请退款金额
	   */
	  private String refundFee;
	  /**
	   * 退款状态
	   */
	  private String refundStatus;
	public String getRefundNum() {
		return refundNum;
	}
	public void setRefundNum(String refundNum) {
		this.refundNum = refundNum;
	}
	public String getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	  
}
