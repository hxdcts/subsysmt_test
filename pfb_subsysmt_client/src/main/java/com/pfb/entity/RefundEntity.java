package com.pfb.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.pfb.entity.base.AbstractBase;

public class RefundEntity extends AbstractBase {

	private Long id;

    private Integer version;

    private String refundNum;

    private String orderNum;

    
    private String platRelationNo;
    
    private String upperRefundNo;

    private String customerNum;

    private BigDecimal refundFee;

    private String refundChannel;

    private String refundAccount;

    private String refundStatus;
    
    private String deviceInfo;

    private Date refundEndTime;
    
    private String notifyUrl;

    private Integer deleted;

    private Date deleteTime;

    private Date createTime;

    private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(String refundNum) {
		this.refundNum = refundNum;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getPlatRelationNo() {
		return platRelationNo;
	}

	public void setPlatRelationNo(String platRelationNo) {
		this.platRelationNo = platRelationNo;
	}

	public String getUpperRefundNo() {
		return upperRefundNo;
	}

	public void setUpperRefundNo(String upperRefundNo) {
		this.upperRefundNo = upperRefundNo;
	}

	public String getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}

	public BigDecimal getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(BigDecimal refundFee) {
		this.refundFee = refundFee;
	}

	public String getRefundChannel() {
		return refundChannel;
	}

	public void setRefundChannel(String refundChannel) {
		this.refundChannel = refundChannel;
	}

	public String getRefundAccount() {
		return refundAccount;
	}

	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public Date getRefundEndTime() {
		return refundEndTime;
	}

	public void setRefundEndTime(Date refundEndTime) {
		this.refundEndTime = refundEndTime;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
    
}