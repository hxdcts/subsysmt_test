package com.pfb.biz.upper.vo.pay.scan;


import com.pfb.biz.common.SHA256Utils;
import com.pfb.biz.upper.vo.base.UpperRequestBase;
import com.pfb.biz.utils.SignatureUtil;
import com.pfb.common.util.StringUtil;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class UpperScanPayRequest extends UpperRequestBase {
	/**
	 * 公共参数
	 */
	/* 接入方订单号，由接入方生成，不能重复 */
    private String outTradeNo;
    /* 订单金额，单位为分 */
    private String totalFee;
    /* 订单主题 */
    private String subject;
    /* 订单内容,不超过64个字符 */
    private String body;
    /* 
    支付渠道：
	'wx_scan_pay':微信扫码支付 
	'ali_scan_pay':支付宝扫码支付
	 mtdp_scan_pay 代表美团点评扫码
     */
    private String channel;
    /* 暂未启用，创建支付订单后，订单关闭时间，目前固定2分钟 */
    private String expireMinutes;
    /* 
    交易类型
	'NATIVE'： 返回二维码url (动态二维码)
	'JSAPI'： 返回会话标识等信息调起客户端支付sdk(静态二维码) 
	*/
    private String tradeType;
    /* 支付成功通知回调地址 */
    private String notifyUrl;
    /* 签名 */
    private String sign;
    /**
     * 微信/支付宝 特有参数
     */
    /* 微信主扫且tradeType为JSAPI时必填,为支付宝或微信各自的openId，获取方式参考H5接口 */
    private String openId;
    /**
     * 美团支付 特有参数  （ 暂时不用！）
     */
    /* 美团点评用户id */
    private String userid;
    /* 用来区分美团点评 1是美团 2是点评 */
    private String from;
    
    /*上游商户号*/
    private  String merchantId;
	/*上游appId*/
    private String appId;
	/*随机数*/
    private String random;
	/*小程序ID*/
    private String wxSubAppId;

    public UpperScanPayRequest(String outTradeNo,String totalFee, String subject,String body,String channel,
    		String expireMinutes, String tradeType, String notifyUrl,String openid,String userid,String from,
    		 String merchantId, String appid,  String random,String key) {
        setOutTradeNo(outTradeNo);
        setTotalFee(totalFee);
        setSubject(subject);
        setBody(body);
        setChannel(channel);
        setExpireMinutes(expireMinutes);
        setTradeType(tradeType);
        setNotifyUrl(notifyUrl);
        setOpenId(openid);
        setMerchantId(merchantId);
        setAppId(appid);
        setRandom(random);
        String sign = SHA256Utils.getSHA256Str(toMap(), key);
        setSign(sign);
    }

    
    public String getOutTradeNo() {
		return outTradeNo;
	}


	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}


	public String getTotalFee() {
		return totalFee;
	}


	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public String getChannel() {
		return channel;
	}


	public void setChannel(String channel) {
		this.channel = channel;
	}


	public String getExpireMinutes() {
		return expireMinutes;
	}


	public void setExpireMinutes(String expireMinutes) {
		this.expireMinutes = expireMinutes;
	}


	public String getTradeType() {
		return tradeType;
	}


	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}


	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public String getNotifyUrl() {
		return notifyUrl;
	}


	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	

	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}
	

	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}
	

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

    public Map<String, String> toStringMap() {
        Map<String, String> map = new HashMap<String, String>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            String obj;
            try {
                obj = (String) field.get(this);
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
