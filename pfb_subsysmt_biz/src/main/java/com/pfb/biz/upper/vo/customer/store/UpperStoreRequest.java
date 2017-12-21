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
public class UpperStoreRequest extends UpperRequestBase {
	/**
	 * 商户名称
	 */
    public String merchantName;
    
    /**
     * 省市县三级地理位置
     */
    public String location;
    
    /**
     * 经营类目
     */
    public String cateId;
    
    /**
     * 地址
     */
    public String address;
    /**
     *商户手机号
     */
    public String merchantPhone;
    /**
     * 经度
     */
    public String latitude;
    /**
     * 纬度
     */
    public String longitude;
    /**
     * 上单用户名称
     */
    public String userName;
    
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
