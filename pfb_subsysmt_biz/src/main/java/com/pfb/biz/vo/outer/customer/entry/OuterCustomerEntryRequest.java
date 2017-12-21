package com.pfb.biz.vo.outer.customer.entry;

import com.pfb.annotation.PropertyBindingAnnotation;
import com.pfb.biz.vo.base.OuterRequestBase;

@SuppressWarnings("serial")
public class OuterCustomerEntryRequest extends OuterRequestBase {
	
	private String agentNum;
	
	private String customerNum;
	private String productNum;
	private String customerType;
	@PropertyBindingAnnotation(propertyName="merchantName")
	private String customerName;
	private String applicationAddress;
	private String licenseNum;
	@PropertyBindingAnnotation(propertyName="accountPersonIdCode")
	private String legalIdCard;
	private String legalName;
	@PropertyBindingAnnotation(propertyName="cateId")
	private String businessType;
	private String businessName;
	@PropertyBindingAnnotation(propertyName="address")
	private String businessAddress;
	private String businessProvince;
	private String businessCity;
	private String businessDistrict;
	private String contactName;
	@PropertyBindingAnnotation(propertyName="merchantPhone")
	private String contactPhone;
	private String contactEmail;
	private String servicePhone;
	private String rate;
	private String plusFee;
	private String isCapped;
	private String cappedFee;
	private String settleMode;
//	@PropertyBindingAnnotation(propertyName="t0Status",autoWired=true,propertyFrom={"S"},propertyTarget={"SUCCESS"})
	private String t0Status;
	private String t0Rate;
	private String t0PlusFee;
	private String t0CappedFee;
	@PropertyBindingAnnotation(propertyName="cardNo")
	private String bankCard;
	@PropertyBindingAnnotation(propertyName="bankAccountType",autoWired=true,propertyFrom={"PERSONAL","COMPANY"},propertyTarget={"2","1"})
	private String cardAccountType;
	private String cardBankName;
	@PropertyBindingAnnotation(propertyName="accountName")
	private String cardAccountName;
	@PropertyBindingAnnotation(propertyName="contactPhone")
	private String cardPhone;
	private String cardIdCard;
	@PropertyBindingAnnotation(propertyName="bankProvinceId")
	private String cardProvince;
	private String cardCity;
	private String cardDistrict;
	private String cardBankAddress;
	private String cardAlliedBankNum;
	private String frontIdCard;
	private String obverseIdCard;
	private String handingIdCard;
	private String frontBankCard;
	private String frontLicense;
	private String frontopeningLicense;
	private String frontStore;
	private String attach;
	private String expand;
	private String remark;
	private String nonceStr;
	private String sign;
	
	public String getAgentNum() {
		return agentNum;
	}
	public void setAgentNum(String agentNum) {
		this.agentNum = agentNum;
	}
	
	public String getCustomerNum() {
		return customerNum;
	}
	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getApplicationAddress() {
		return applicationAddress;
	}
	public void setApplicationAddress(String applicationAddress) {
		this.applicationAddress = applicationAddress;
	}
	public String getLicenseNum() {
		return licenseNum;
	}
	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}
	public String getLegalIdCard() {
		return legalIdCard;
	}
	public void setLegalIdCard(String legalIdCard) {
		this.legalIdCard = legalIdCard;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessAddress() {
		return businessAddress;
	}
	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}
	public String getBusinessProvince() {
		return businessProvince;
	}
	public void setBusinessProvince(String businessProvince) {
		this.businessProvince = businessProvince;
	}
	public String getBusinessCity() {
		return businessCity;
	}
	public void setBusinessCity(String businessCity) {
		this.businessCity = businessCity;
	}
	public String getBusinessDistrict() {
		return businessDistrict;
	}
	public void setBusinessDistrict(String businessDistrict) {
		this.businessDistrict = businessDistrict;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getServicePhone() {
		return servicePhone;
	}
	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getIsCapped() {
		return isCapped;
	}
	public void setIsCapped(String isCapped) {
		this.isCapped = isCapped;
	}
	public String getCappedFee() {
		return cappedFee;
	}
	public void setCappedFee(String cappedFee) {
		this.cappedFee = cappedFee;
	}
	public String getSettleMode() {
		return settleMode;
	}
	public void setSettleMode(String settleMode) {
		this.settleMode = settleMode;
	}
	public String getT0Status() {
		return t0Status;
	}
	public void setT0Status(String t0Status) {
		this.t0Status = t0Status;
	}
	public String getT0Rate() {
		return t0Rate;
	}
	public void setT0Rate(String t0Rate) {
		this.t0Rate = t0Rate;
	}
	public String getPlusFee() {
		return plusFee;
	}
	public void setPlusFee(String plusFee) {
		this.plusFee = plusFee;
	}
	public String getT0CappedFee() {
		return t0CappedFee;
	}
	public void setT0CappedFee(String t0CappedFee) {
		this.t0CappedFee = t0CappedFee;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getCardAccountType() {
		return cardAccountType;
	}
	public void setCardAccountType(String cardAccountType) {
		this.cardAccountType = cardAccountType;
	}
	public String getCardBankName() {
		return cardBankName;
	}
	public void setCardBankName(String cardBankName) {
		this.cardBankName = cardBankName;
	}
	public String getCardAccountName() {
		return cardAccountName;
	}
	public void setCardAccountName(String cardAccountName) {
		this.cardAccountName = cardAccountName;
	}
	public String getCardPhone() {
		return cardPhone;
	}
	public void setCardPhone(String cardPhone) {
		this.cardPhone = cardPhone;
	}
	public String getCardIdCard() {
		return cardIdCard;
	}
	public void setCardIdCard(String cardIdCard) {
		this.cardIdCard = cardIdCard;
	}
	public String getCardProvince() {
		return cardProvince;
	}
	public void setCardProvince(String cardProvince) {
		this.cardProvince = cardProvince;
	}
	public String getCardCity() {
		return cardCity;
	}
	public void setCardCity(String cardCity) {
		this.cardCity = cardCity;
	}
	public String getCardDistrict() {
		return cardDistrict;
	}
	public void setCardDistrict(String cardDistrict) {
		this.cardDistrict = cardDistrict;
	}
	public String getCardBankAddress() {
		return cardBankAddress;
	}
	public void setCardBankAddress(String cardBankAddress) {
		this.cardBankAddress = cardBankAddress;
	}
	public String getCardAlliedBankNum() {
		return cardAlliedBankNum;
	}
	public void setCardAlliedBankNum(String cardAlliedBankNum) {
		this.cardAlliedBankNum = cardAlliedBankNum;
	}
	public String getFrontIdCard() {
		return frontIdCard;
	}
	public void setFrontIdCard(String frontIdCard) {
		this.frontIdCard = frontIdCard;
	}
	public String getObverseIdCard() {
		return obverseIdCard;
	}
	public void setObverseIdCard(String obverseIdCard) {
		this.obverseIdCard = obverseIdCard;
	}
	public String getHandingIdCard() {
		return handingIdCard;
	}
	public void setHandingIdCard(String handingIdCard) {
		this.handingIdCard = handingIdCard;
	}
	public String getFrontBankCard() {
		return frontBankCard;
	}
	public void setFrontBankCard(String frontBankCard) {
		this.frontBankCard = frontBankCard;
	}
	public String getFrontLicense() {
		return frontLicense;
	}
	public void setFrontLicense(String frontLicense) {
		this.frontLicense = frontLicense;
	}
	public String getFrontopeningLicense() {
		return frontopeningLicense;
	}
	public void setFrontopeningLicense(String frontopeningLicense) {
		this.frontopeningLicense = frontopeningLicense;
	}
	public String getFrontStore() {
		return frontStore;
	}
	public void setFrontStore(String frontStore) {
		this.frontStore = frontStore;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getExpand() {
		return expand;
	}
	public void setExpand(String expand) {
		this.expand = expand;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getProductNum() {
		return productNum;
	}
	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getT0PlusFee() {
		return t0PlusFee;
	}
	public void setT0PlusFee(String t0PlusFee) {
		this.t0PlusFee = t0PlusFee;
	}
	
	
}
