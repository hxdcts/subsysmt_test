package com.pfb.biz.upper.vo.pay.micro;


import com.pfb.biz.common.SHA256Utils;
import com.pfb.biz.upper.vo.base.UpperRequestBase;
import com.pfb.common.util.StringUtil;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
/**
 * 刷卡支付（商户扫用户）请求参数
 * @author cts
 *
 */
@SuppressWarnings("serial")
public class UpperMicroPayRequest extends UpperRequestBase {
	/**
	 * 支付渠道
	 */
	public String channel;
    /**
     * 接入方订单号 
     */
	public String outTradeNo;
    /**
     * 用户付款码
     */
	public String authCode;
    /**
     * 总金额, 以分为单位
     */
	public String totalFee;
    /** 商品标题*/
	public String subject;
    /** 商品详情 */
	public String body;
    /** 创建支付订单后，订单关闭时间，单位为分钟 */
	public String expireMinutes;
    


    public UpperMicroPayRequest(String channel, String outTradeNo, String authCode, String totalFee,
                              String subject, String body, String expireMinutes, String merchantId,String appId,
                                String random,String key) {
        setChannel(channel);
    	setOutTradeNo(outTradeNo);
    	setAuthCode(authCode);
    	setTotalFee(totalFee);
    	setSubject(subject);
    	setBody(body);
    	setExpireMinutes(expireMinutes);
    	setMerchantId(merchantId);
    	setAppId(appId);
    	setRandom(random);
        String sign = SHA256Utils.getSHA256Str(toMap(), key);
        setSign(sign);
    }
    
    public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
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

	public String getExpireMinutes() {
		return expireMinutes;
	}

	public void setExpireMinutes(String expireMinutes) {
		this.expireMinutes = expireMinutes;
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
