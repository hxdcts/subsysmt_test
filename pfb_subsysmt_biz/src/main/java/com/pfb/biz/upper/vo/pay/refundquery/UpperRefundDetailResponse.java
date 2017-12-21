package com.pfb.biz.upper.vo.pay.refundquery;


import com.pfb.biz.upper.vo.base.UpperResponseBase;

@SuppressWarnings("serial")
public class UpperRefundDetailResponse extends UpperResponseBase {

	private String upperRefundNo;
	private String refundNo;
	private String outRefundNo;
	private String applyFee;
	private String refundStatus;
	
	public String getUpperRefundNo() {
		return upperRefundNo;
	}
	public void setUpperRefundNo(String upperRefundNo) {
		this.upperRefundNo = upperRefundNo;
	}
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public String getOutRefundNo() {
		return outRefundNo;
	}
	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}
	public String getApplyFee() {
		return applyFee;
	}
	public void setApplyFee(String applyFee) {
		this.applyFee = applyFee;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	
	
	


}
