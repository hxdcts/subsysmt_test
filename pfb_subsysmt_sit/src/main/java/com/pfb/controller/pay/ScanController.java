package com.pfb.controller.pay;

import com.pfb.biz.IDoPayBiz;
import com.pfb.biz.common.CommonConfig;
import com.pfb.biz.utils.ConvertUtil;
import com.pfb.biz.utils.SignatureUtil;
import com.pfb.biz.vo.outer.scan.OuterScanRequest;
import com.pfb.biz.vo.outer.scan.OuterScanResponse;
import com.pfb.common.exception.IllegalParameterException;
import com.pfb.common.exception.SignatureVerifyException;
import com.pfb.common.util.JSONUtil;
import com.pfb.common.util.StringUtil;
import com.pfb.controller.base.BaseAPIController;
import com.pfb.enums.CommonCodeEnum;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 扫码入口
 *
 * @author zhanghui
 * @version 1.0
 * @date: 25/09/2017 22:36
 */
@Controller
@RequestMapping("/pay")
public class ScanController extends BaseAPIController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private CommonConfig commonConfig;
    @Resource
    private IDoPayBiz doPayBiz;

    @ResponseBody
    @RequestMapping("/scanPay")
    public Map<String, Object> process(HttpServletRequest request) {
        // 开始记录日志
        if (allowPrintLog()) {
            start(request);
        }

        // step1 定义接口返回值
        Map<String, Object> resultMap = new HashMap<String,Object>();
        try {
            // step2 验证参数合法性
            String req = ConvertUtil.parseRequest2JsonStr(request);
            if (StringUtil.isBlank(req)) {
                resultMap.put("returnCode", CommonCodeEnum.PARAM_REQUIRED.val());
                resultMap.put("returnMsg", "扫码请求参数为空");
                return resultMap;
            }
            // step3 验证参数合法性
            boolean verify = verifyParameter(req, resultMap);
            if (!verify) {
                return resultMap;
            }
            Map<String,Object> map = JSONUtil.parseObject(req, Map.class);
            // step4 验证签名合法性
            String key = commonConfig.getSignKey();
            boolean checkSign = verifySignature(map, key);
            if(checkSign){
            	// step5 执行业务逻辑操作
                OuterScanRequest outerScanRequest = JSONUtil.parseObject(req, OuterScanRequest.class);
                OuterScanResponse outerScanResponse = doPayBiz.doScan(outerScanRequest);
                resultMap = ConvertUtil.objectToMap(outerScanResponse, null);
                String sign = SignatureUtil.getSign(resultMap, key);
                resultMap.put("sign", sign);
            }else{
            	resultMap.put("returnCode", CommonCodeEnum.SIGN_ERROR.val());
                resultMap.put("returnMsg", "主扫业务签名验证失败");
            }
            
        } catch (IllegalParameterException e) {
            logger.error("出错：{}", e);
            resultMap.put("returnCode", CommonCodeEnum.FAIL.val());
            resultMap.put("returnMsg", e.getMessage());
        } catch (SignatureVerifyException e) {
            logger.error("出错：{}", e);
            resultMap.put("returnCode", CommonCodeEnum.FAIL.val());
            resultMap.put("returnMsg", e.getMessage());
        } catch (Exception e) {
            logger.error("出错：{}", e);
            resultMap.put("returnCode", CommonCodeEnum.FAIL.val());
            resultMap.put("returnMsg", e.getMessage());
        }

        // 结束日志记录
        if (allowPrintLog()) {
            end(request, resultMap);
        }
        return resultMap;

    }

    /**
     * 验证参数
     *
     * @param req
     * @param resultMap
     * @return
     */
    protected boolean verifyParameter(String req, Map<String,Object> resultMap) {
        OuterScanRequest outterScanRequest = null;
        try {
            outterScanRequest = JSONUtil.parseObject(req, OuterScanRequest.class);
        } catch (Exception e) {
            logger.info("扫码参数转换异常");
            resultMap.put("returnCode", CommonCodeEnum.PARAM_TRANS_ERROR.val());
            resultMap.put("returnMsg", "扫码参数转换异常");
            return false;
        }
        String customerNum = outterScanRequest.getCustomerNum();
        String sign = outterScanRequest.getSign();
        if (StringUtil.isBlank(customerNum)) {
            resultMap.put("returnCode", CommonCodeEnum.PARAM_REQUIRED.val());
            resultMap.put("returnMsg", "商户号不能为空");
            return false;
        }
        if (StringUtil.isBlank(sign)) {
            resultMap.put("returnCode", CommonCodeEnum.PARAM_REQUIRED.val());
            resultMap.put("returnMsg", "签名字段不能为空");
            return false;
        }
        return true;
    }

}

