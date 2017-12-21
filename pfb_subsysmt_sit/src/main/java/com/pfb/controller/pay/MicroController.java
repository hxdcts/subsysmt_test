package com.pfb.controller.pay;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pfb.biz.IDoPayBiz;
import com.pfb.biz.common.CommonConfig;
import com.pfb.biz.utils.ConvertUtil;
import com.pfb.biz.utils.SignatureUtil;
import com.pfb.biz.vo.outer.micro.OuterMicroRequest;
import com.pfb.biz.vo.outer.micro.OuterMicroResponse;
import com.pfb.common.exception.IllegalParameterException;
import com.pfb.common.exception.SignatureVerifyException;
import com.pfb.common.util.JSONUtil;
import com.pfb.common.util.StringUtil;
import com.pfb.controller.base.BaseAPIController;
import com.pfb.enums.CommonCodeEnum;
/**
 * 刷卡支付（商户扫用户）
 * @author cts
 *
 */
@Controller
@RequestMapping("/pay")
public class MicroController extends BaseAPIController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Resource
    private CommonConfig commonConfig;
    @Resource
    private IDoPayBiz doPayBiz;

    @ResponseBody
    @RequestMapping("/microPay")
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
                resultMap.put("returnMsg", "条码（刷卡，收付款）请求参数为空");
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
                OuterMicroRequest outerMicroRequest = (OuterMicroRequest)resultMap.get("data");
                OuterMicroResponse outerMicroResponse = doPayBiz.doMicro(outerMicroRequest);
                resultMap = ConvertUtil.objectToMap(outerMicroResponse, null);
                String sign = SignatureUtil.getSign(resultMap, key);
                resultMap.put("sign", sign);
            }else{
            	resultMap.put("returnCode", CommonCodeEnum.SIGN_ERROR.val());
                resultMap.put("returnMsg", CommonCodeEnum.SIGN_ERROR.error());
            }
            
        } catch (IllegalParameterException e) {
            logger.error("出错：{}", e);
            resultMap.put("returnCode", CommonCodeEnum.e_999999.val());
            resultMap.put("returnMsg", e.getMessage());
        } catch (SignatureVerifyException e) {
            logger.error("出错：{}", e);
            resultMap.put("returnCode", CommonCodeEnum.e_999999.val());
            resultMap.put("returnMsg", e.getMessage());
        } catch (Exception e) {
            logger.error("出错：{}", e);
            resultMap.put("returnCode", CommonCodeEnum.e_999999.val());
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
        OuterMicroRequest outerMicroRequest = null;
        try {
            outerMicroRequest = JSONUtil.parseObject(req, OuterMicroRequest.class);
        } catch (Exception e) {
            logger.info("条码（刷卡，收付款）参数转换异常");
            resultMap.put("returnCode", CommonCodeEnum.PARAM_TRANS_ERROR.val());
            resultMap.put("returnMsg", "条码（刷卡，收付款）参数转换异常");
            return false;
        }
        String customerNum = outerMicroRequest.getCustomerNum();
        String sign = outerMicroRequest.getSign();
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
        resultMap.put("data", outerMicroRequest);
        return true;
    }

}
