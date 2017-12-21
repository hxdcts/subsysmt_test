package com.pfb.biz.vo.base;


import com.pfb.entity.base.AbstractBase;

/**
 * 下游商户返回参数基类
 */
@SuppressWarnings("serial")
public class OuterResponseBase extends AbstractBase {

    /* 响应码 */
    public String returnCode = "000000";
    /* 响应信息 */
    public String returnMsg = "SUCCESS";

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

}
