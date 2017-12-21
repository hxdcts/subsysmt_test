package com.pfb.biz.upper.vo.pay.refund;


import com.pfb.biz.common.SHA256Utils;
import com.pfb.biz.upper.vo.base.UpperRequestBase;
import com.pfb.common.util.StringUtil;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class UpperRefundRequest extends UpperRequestBase {
	
	public String outTradeNo;
	public String refundNo;
	public String refundFee;
	public String refundReason;

	
    public UpperRefundRequest() {
		super();
	}

	public UpperRefundRequest(String merchantId, String appId,  String random,
			String wxSubAppId, String outTradeNo, String refundFee, String refundNo,String refundReason, String key) {
		setAppId(appId);
		setMerchantId(merchantId);
		setRandom(random);
		setWxSubAppId(wxSubAppId);
		setOutTradeNo(outTradeNo);
		setRefundFee(refundFee);
		setRefundNo(refundNo);
		setRefundReason(refundReason);
		String sign = SHA256Utils.getSHA256Str(toMap(), key);
		setSign(sign);
		
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

	public String getRefundFee() {
	
		return refundFee;
	}

	public void setRefundFee(String refundFee) {
	
		this.refundFee = refundFee;
	}

	public String getRefundReason() {
	
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
	
		this.refundReason = refundReason;
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
