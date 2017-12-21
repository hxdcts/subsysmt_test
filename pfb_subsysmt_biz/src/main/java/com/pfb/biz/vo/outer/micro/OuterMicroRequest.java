package com.pfb.biz.vo.outer.micro;

import com.pfb.biz.common.SHA256Utils;
import com.pfb.biz.utils.SignatureUtil;
import com.pfb.common.util.StringUtil;
import com.pfb.vo.OuterRequestBase;

import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
/**
 * 
* @desc: 网关请求参数
* @author: cts
* @createTime: 2017年12月5日 下午2:39:45
* @history:
* @version: v1.0
 */
public class OuterMicroRequest extends OuterRequestBase {
	/**
	* TODO
	*/
		
	private static final long serialVersionUID = 1L;
	/**
	 * 代理商编号
	 */
    private String agentNum;
    /**
     * 商户号
     */
    private String customerNum;
    /**
     * outTradeNum
     */
    private String orderNum;
    /**
     * orderType
     */
    private String orderType;
    /**
     * subject
     */
    private String subject;
    
    private String totalFee;
    /**
     * description
     */
    private String description;
    /**
     * 设备授权码
     */
    private String authCode;
    /**
     * 指定支付方式
     */
    private String limitPay;
    /**
     * 订单描述
     */
    private String duration;
    /**
     * 商品标记
     */
    private String goodsTag;
    /**
     * 设备机具号
     */
    private String deviceInfo;
    /**
     * 设备IP
     */
    private String outerIp;
    /**
     * 备注
     */
    private String remark;
    /**
     * 拓展
     */
    private String expand;
    /**
     * 随机字符串
     */
    private String nonceStr;
    private String settleMode;
    /**
     * 签名
     */
    private String sign;
    
    private String expireMinutes;
    

    public OuterMicroRequest() {
    }

    public OuterMicroRequest(String key, String agentNum, String customerNum, String orderNum, String orderType, String subject,
                             String totalFee, String description, String authCode, String limitPay, String duration, String goodsTag,
                             String deviceInfo, String outerIp, String remark, String expand, String nonceStr, String settleMode,String expireMinutes, Logger logger) {
        setAgentNum(agentNum);
        setCustomerNum(customerNum);
        setOrderNum(orderNum);
        setOrderType(orderType);
        setSubject(subject);
        setTotalFee(totalFee);
        setDescription(description);
        setAuthCode(authCode);
        setLimitPay(limitPay);
        setDuration(duration);
        setGoodsTag(goodsTag);
        setDeviceInfo(deviceInfo);
        setOuterIp(outerIp);
        setRemark(remark);
        setExpand(expand);
        setNonceStr(nonceStr);
        setSettleMode(settleMode);
        setExpireMinutes(settleMode);
        String sign = SignatureUtil.getSign(toMap(), key);
        setSign(sign);
    }
    
	public String getExpireMinutes() {
	
		return expireMinutes;
	}

	public void setExpireMinutes(String expireMinutes) {
	
		this.expireMinutes = expireMinutes;
	}

	public String getAgentNum() {
        return agentNum;
    }

    public void setAgentNum(String agentNum) {
        this.agentNum = agentNum;
    }

    public String getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(String customerNum) {
        this.customerNum = customerNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getOuterIp() {
        return outerIp;
    }

    public void setOuterIp(String outerIp) {
        this.outerIp = outerIp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    

    public String getSettleMode() {
		return settleMode;
	}

	public void setSettleMode(String settleMode) {
		this.settleMode = settleMode;
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
