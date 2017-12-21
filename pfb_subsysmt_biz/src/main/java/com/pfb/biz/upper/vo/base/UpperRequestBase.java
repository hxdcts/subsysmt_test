package com.pfb.biz.upper.vo.base;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.pfb.entity.base.AbstractBase;

public class UpperRequestBase extends AbstractBase {


	/**
	* TODO
	*/
		
	private static final long serialVersionUID = 1L;
	/*上游商户号*/
	public  String merchantId;
	/*上游appId*/
	public String appId;
	/*签名*/
	public String sign;
	/*随机数*/
	public String random;
	/*小程序ID*/
	public String wxSubAppId;
	
	
    public String getMerchantId() {
		return merchantId;
	}


	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}


	public String getAppId() {
		return appId;
	}


	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}


	public String getRandom() {
		return random;
	}


	public void setRandom(String random) {
		this.random = random;
	}


	public String getWxSubAppId() {
		return wxSubAppId;
	}


	public void setWxSubAppId(String wxSubAppId) {
		this.wxSubAppId = wxSubAppId;
	}
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
