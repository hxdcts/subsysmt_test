package com.pfb.biz.upper.vo.customer.entry;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.pfb.biz.upper.vo.base.UpperRequestBase;
import com.pfb.common.util.StringUtil;
/**
 * 刷卡支付（商户扫用户）请求参数
 * @author cts
 *
 */
@SuppressWarnings("serial")
public class UpperCustomerEntryRequest extends UpperRequestBase {
	/**
	 * 商户名称
	 */
	public String merchantName;
	public String address;
	public String bankAccountType;
	public String cardNo;
	public String accountName;
	public String bankId;
	public String bankBranchId;
	public String bankProvinceId;
	public String bankCityId;
	public String accountPersonIdCode;
	public String contactPhone;
	public String wxRate;
	public String aliRate;
	public String businessLicenceName;
	public String businessLicenseNumber;
	public String businessLicenceType;
	public String businessLicencePhotoCdcId;
	public String wxSubAppId;
	public String wxSubscribeAppId;
	
	public String getMerchantName() {
		return merchantName;
	}


	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getBankAccountType() {
		return bankAccountType;
	}


	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}


	public String getCardNo() {
		return cardNo;
	}


	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public String getBankId() {
		return bankId;
	}


	public void setBankId(String bankId) {
		this.bankId = bankId;
	}


	public String getBankBranchId() {
		return bankBranchId;
	}


	public void setBankBranchId(String bankBranchId) {
		this.bankBranchId = bankBranchId;
	}


	public String getBankProvinceId() {
		return bankProvinceId;
	}


	public void setBankProvinceId(String bankProvinceId) {
		this.bankProvinceId = bankProvinceId;
	}


	public String getBankCityId() {
		return bankCityId;
	}


	public void setBankCityId(String bankCityId) {
		this.bankCityId = bankCityId;
	}


	public String getAccountPersonIdCode() {
		return accountPersonIdCode;
	}


	public void setAccountPersonIdCode(String accountPersonIdCode) {
		this.accountPersonIdCode = accountPersonIdCode;
	}


	public String getContactPhone() {
		return contactPhone;
	}


	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}


	public String getWxRate() {
		return wxRate;
	}


	public void setWxRate(String wxRate) {
		this.wxRate = wxRate;
	}


	public String getAliRate() {
		return aliRate;
	}


	public void setAliRate(String aliRate) {
		this.aliRate = aliRate;
	}


	public String getBusinessLicenceName() {
		return businessLicenceName;
	}


	public void setBusinessLicenceName(String businessLicenceName) {
		this.businessLicenceName = businessLicenceName;
	}


	public String getBusinessLicenseNumber() {
		return businessLicenseNumber;
	}


	public void setBusinessLicenseNumber(String businessLicenseNumber) {
		this.businessLicenseNumber = businessLicenseNumber;
	}


	public String getBusinessLicenceType() {
		return businessLicenceType;
	}


	public void setBusinessLicenceType(String businessLicenceType) {
		this.businessLicenceType = businessLicenceType;
	}


	public String getBusinessLicencePhotoCdcId() {
		return businessLicencePhotoCdcId;
	}


	public void setBusinessLicencePhotoCdcId(String businessLicencePhotoCdcId) {
		this.businessLicencePhotoCdcId = businessLicencePhotoCdcId;
	}


	public String getWxSubAppId() {
		return wxSubAppId;
	}


	public void setWxSubAppId(String wxSubAppId) {
		this.wxSubAppId = wxSubAppId;
	}


	public String getWxSubscribeAppId() {
		return wxSubscribeAppId;
	}


	public void setWxSubscribeAppId(String wxSubscribeAppId) {
		this.wxSubscribeAppId = wxSubscribeAppId;
	}


	public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if (obj != null && StringUtil.isNotBlank(String.valueOf(obj))) {
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
