package com.pfb.biz.upper.vo.pay.webpay;


import com.pfb.biz.common.SHA256Utils;
import com.pfb.biz.upper.vo.base.UpperRequestBase;
import com.pfb.biz.utils.SignatureUtil;
import com.pfb.common.util.StringUtil;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class UpperWebPayRequest extends UpperRequestBase {
    public  String outTradeNo;
    public String totalFee;
    public String subject;
    public String body;
    public String channel;
    public String expireMinutes;
    public String tradeType;
    public String notifyUrl;
    public String openId;
    

    public String getOpenId() {
	
		return openId;
	}

	public void setOpenId(String openId) {
	
		this.openId = openId;
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

	public String getNotifyUrl() {
	
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
	
		this.notifyUrl = notifyUrl;
	}

	public UpperWebPayRequest(String outTradeNo, String totalFee, String subject, String body,
                              String channel, String expireMinutes, String tradeType, String notifyUrl, String merchantId, String appId,
                              String random, String wxSubAppId, String key,String openId) {
        setOutTradeNo(outTradeNo);
        setTotalFee(totalFee);
        setSubject(subject);
        setBody(body);
        setChannel(channel);
        setExpireMinutes(expireMinutes);
        setTradeType(tradeType);
        setNotifyUrl(notifyUrl);
        setAppId(appId);
        setMerchantId(merchantId);
        setRandom(random);
        setWxSubAppId(wxSubAppId);
        setOpenId(openId);
    	String sign = SHA256Utils.getSHA256Str(toMap(),key);
        setSign(sign);
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
