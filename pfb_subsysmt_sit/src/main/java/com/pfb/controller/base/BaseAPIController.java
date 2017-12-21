package com.pfb.controller.base;

import com.alibaba.fastjson.JSON;
import com.pfb.biz.utils.SignatureUtil;
import com.pfb.common.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * API Controller基类
 *
 * @author zhang.hui@pufubao.net
 * @version v1.0
 * @date 2016年10月12日 下午5:31:48
 */
public abstract class BaseAPIController {

	private static final Logger logger  = LoggerFactory.getLogger(BaseAPIController.class);
    /* 请求开始时间 */
    private long startTime;
    /* 请求结束时间 */
    private long endTime;

    /**
     * 是否允许打印日志
     *
     * @return
     */
    protected boolean allowPrintLog() {
        return true;
    }

    /**
     * 接口处理开始前打印日志
     *
     * @param request 请求
     * @param logger  日志类型
     * @author zhang.hui@pufubao.net
     * @date 2016年10月12日 下午5:23:19
     */
    protected void start(HttpServletRequest request) {
        startTime = System.currentTimeMillis();
        String jsonString = JSONUtil.toJSONString(request.getParameterMap());
        logger.info("请求开始[URI:{}, Method:{}, Params:{}]", request.getRequestURI(), request.getMethod(), jsonString);
    }

    /**
     * 接口处理结束后打印日志
     *
     * @param request 请求
     * @param result
     * @param logger
     * @author zhang.hui@pufubao.net
     * @date 2016年10月12日 下午5:24:36
     */
    protected <T> void end(HttpServletRequest request, T result) {
        endTime = System.currentTimeMillis();
        logger.info("请求结束[URI:{}, 耗时:{}毫秒, Result:{}]", request.getRequestURI(), (endTime - startTime), JSON.toJSONString(result));
    }

    /**
     * 是否需要签名验证
     *
     * @return
     */
    protected boolean requiredVerifySign() {
        return false;
    }

    /**
     * 签名验证
     *
     * @param paramsMap 请求Map
     * @param logger    日志类型
     * @throws Exception 异常信息
     * @author zhang.hui@pufubao.net
     * @date 2016年10月12日 下午5:28:09
     */
    protected boolean verifySignature(Map<String, Object> paramsMap, String key) {
        if (requiredVerifySign() == false) {
            return true;
        }
        boolean checkSign = SignatureUtil.checkSign(paramsMap, key);
        logger.info("签名验证结果:" + checkSign);
        return checkSign;
    }
    /**
     * 美团签名验证
    * @author: cts
    * @createTime: 2017年12月6日 上午10:20:05
    * @history:
    * @param paramsMap
    * @param key
    * @return boolean
     */
    protected boolean verifySignatureMt(Map<String, Object> paramsMap, String key) {
        if (requiredVerifySign() == false) {
            return true;
        }
        boolean checkSign = SignatureUtil.checkSignToMt(paramsMap, key);
        logger.info("签名验证结果:" + checkSign);
        return checkSign;
    }

}
