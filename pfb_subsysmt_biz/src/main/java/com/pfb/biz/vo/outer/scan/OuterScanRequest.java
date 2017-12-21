package com.pfb.biz.vo.outer.scan;

import com.pfb.biz.utils.SignatureUtil;
import com.pfb.biz.vo.base.OuterRequestBase;
import com.pfb.common.util.StringUtil;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class OuterScanRequest extends OuterRequestBase {

    private String agentNum;
    private String customerNum;
    private String orderNum;
    private String orderType;
    private String subject;
    private String totalFee;
    private String description;
    private String limitPay;
    private String duration;
    private String goodsTag;
    private String deviceInfo;
    private String settleMode;
    private String settleNum;
    private String outerIp;
    private String remark;
    private String expand;
    private String nonceStr;
    private String sign;

    public OuterScanRequest() {
    }

    public OuterScanRequest(String key, String agentNum, String customerNum, String orderNum, String orderType, String subject,
                            String totalFee, String description, String limitPay, String duration, String goodsTag, String deviceInfo,
                            String outerIp, String remark, String expand, String nonceStr, String settleMode,String settleNum,Logger logger) {
        setAgentNum(agentNum);
        setCustomerNum(customerNum);
        setOrderNum(orderNum);
        setOrderType(orderType);
        setSubject(subject);
        setTotalFee(totalFee);
        setDescription(description);
        setLimitPay(limitPay);
        setDuration(duration);
        setGoodsTag(goodsTag);
        setDeviceInfo(deviceInfo);
        setOuterIp(outerIp);
        setRemark(remark);
        setExpand(expand);
        setNonceStr(nonceStr);
        setSettleMode(settleMode);
        setSettleNum(settleNum);
        String sign = SignatureUtil.getSign(toMap(), key);
        setSign(sign);

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
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
	
	public String getSettleNum() {
		return settleNum;
	}

	public void setSettleNum(String settleNum) {
		this.settleNum = settleNum;
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
