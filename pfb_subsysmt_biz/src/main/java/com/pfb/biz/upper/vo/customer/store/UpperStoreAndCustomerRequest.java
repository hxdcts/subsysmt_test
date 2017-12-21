package com.pfb.biz.upper.vo.customer.store;


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
public class UpperStoreAndCustomerRequest extends UpperRequestBase {
	/**
	 * 商户名称
	 */
    private String merchantName;
    
    /**
     * 省市县三级地理位置
     */
    private String location;
    
    /**
     * 经营类目
     */
    private String cateId;
    
    /**
     * 地址
     */
    private String address;
    /**
     *商户手机号
     */
    private String merchantPhone;
    /**
     * 经度
     */
    private String latitude;
    /**
     * 纬度
     */
    private String longitude;
    /**
     * 上单用户名称
     */
    private String userName;
    /**
	 * 商户名称
	 */
	private String bankAccountType;
	private String cardNo;
	private String accountName;
	private String bankId;
	private String bankBranchId;
	private String bankProvinceId;
	private String bankCityId;
	private String accountPersonIdCode;
	private String contactPhone;
	private String wxRate;
	private String aliRate;
	private String businessLicenceName;
	private String businessLicenseNumber;
	private String businessLicenceType;
	private String businessLicencePhotoCdcId;
	private String wxSubAppId;
	private String wxSubscribeAppId;
    
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMerchantPhone() {
		return merchantPhone;
	}
	public void setMerchantPhone(String merchantPhone) {
		this.merchantPhone = merchantPhone;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
        Field[] fields = this.getClass().getDeclaredFields();
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
