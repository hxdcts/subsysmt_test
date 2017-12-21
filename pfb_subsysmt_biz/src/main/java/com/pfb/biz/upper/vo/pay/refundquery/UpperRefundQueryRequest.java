package com.pfb.biz.upper.vo.pay.refundquery;


import com.pfb.biz.common.SHA256Utils;
import com.pfb.biz.upper.vo.base.UpperRequestBase;
import com.pfb.biz.utils.SignatureUtil;
import com.pfb.common.util.StringUtil;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class UpperRefundQueryRequest extends UpperRequestBase {
	
	public String outTradeNo;
	public String refundNo;
    public UpperRefundQueryRequest() {
		super();
	}
    


	public String getOutTradeNo() {
	
		return outTradeNo;
	}



	public void setOutTradeNo(String outTradeNo) {
	
		this.outTradeNo = outTradeNo;
	}



	public String getRefundNo() {
	
		return refundNo;
	}



	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}



	public UpperRefundQueryRequest(String merchantId, String appId, String random,String wxSubAppId,
			String outTradeNo,String refundNo, String key) {

		setMerchantId(merchantId);
		setAppId(appId);
		setRandom(random);
		setWxSubAppId(wxSubAppId);
		setOutTradeNo(outTradeNo);
		setRefundNo(refundNo);
		String sign = SHA256Utils.getSHA256Str(toMap(), key);
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

}
