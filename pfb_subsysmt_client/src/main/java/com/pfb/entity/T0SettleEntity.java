package com.pfb.entity;

import com.pfb.entity.base.AbstractBase;

import java.math.BigDecimal;
import java.util.Date;

public class T0SettleEntity extends AbstractBase {
    private Long id;

    private Integer version;

    private String settleNum;

    private String orderNum;
    
    private String customerNum;
    
    private BigDecimal totalAmount;

    private BigDecimal settleAmount;

    private BigDecimal actualSettleAmount;
    
    private BigDecimal settleFee;
    
    private String settleStatus;
    
    private String upperSettleNum;

    private Integer deleted;

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

	public String getSettleNum() {
		return settleNum;
	}

	public void setSettleNum(String settleNum) {
		this.settleNum = settleNum;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	public BigDecimal getActualSettleAmount() {
		return actualSettleAmount;
	}

	public void setActualSettleAmount(BigDecimal actualSettleAmount) {
		this.actualSettleAmount = actualSettleAmount;
	}

	public String getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
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

	public BigDecimal getSettleFee() {
		return settleFee;
	}

	public void setSettleFee(BigDecimal settleFee) {
		this.settleFee = settleFee;
	}

	public String getUpperSettleNum() {
		return upperSettleNum;
	}

	public void setUpperSettleNum(String upperSettleNum) {
		this.upperSettleNum = upperSettleNum;
	}
}