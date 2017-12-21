package com.pfb.biz.upper.vo.pay.query;


import com.pfb.biz.common.SHA256Utils;
import com.pfb.biz.upper.vo.base.UpperRequestBase;
import com.pfb.biz.utils.SignatureUtil;
import com.pfb.common.util.StringUtil;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
/**
 * 
* @desc: 订单查询
* @author: cts
* @createTime: 2017年12月5日 下午5:21:19
* @history:
* @version: v1.0
 */
@SuppressWarnings("serial")
public class UpperOrderQueryRequest extends UpperRequestBase {
	public String outTradeNo;

    public UpperOrderQueryRequest() {
    }

    public UpperOrderQueryRequest(String outTradeNo, String merchantId, String appId, String random, String wxSubAppId,String key) {
       setOutTradeNo(outTradeNo);
       setAppId(appId);
       setMerchantId(merchantId);
       setRandom(random);
       setWxSubAppId(wxSubAppId);
       String sign =  SHA256Utils.getSHA256Str(toMap(), key);
       setSign(sign);
    }

    public String getOutTradeNo() {
	
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
	
		this.outTradeNo = outTradeNo;
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
