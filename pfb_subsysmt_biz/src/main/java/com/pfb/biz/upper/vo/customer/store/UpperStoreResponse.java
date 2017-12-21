package com.pfb.biz.upper.vo.customer.store;


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
public class UpperStoreResponse extends UpperResponseBase {

	/**
	 * 美团门店ID
	 */
	private String merchantId;
	/**
	 * 美团门店ID
	 */
	private String auditStatus;
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
}
