package com.pfb.controller.customer;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pfb.biz.IDoCustomerBiz;
import com.pfb.biz.common.CommonConfig;
import com.pfb.biz.utils.ConvertUtil;
import com.pfb.biz.utils.SignatureUtil;
import com.pfb.biz.vo.outer.customer.entry.OuterCustomerEntryRequest;
import com.pfb.biz.vo.outer.customer.entry.OuterCustomerEntryResponse;
import com.pfb.common.exception.IllegalParameterException;
import com.pfb.common.exception.SignatureVerifyException;
import com.pfb.common.util.JSONUtil;
import com.pfb.common.util.StringUtil;
import com.pfb.controller.base.BaseAPIController;
import com.pfb.enums.CommonCodeEnum;

/**
 * 商户进件
 *
 * @author zhang.mingmeng@pufubao.net
 * @version 1.0
 * @date: 2017.9.28
 */
@Controller
@RequestMapping("/customer")
public class CustomerEntryController extends BaseAPIController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private CommonConfig commonConfig;
    @Resource
    private IDoCustomerBiz doCustomerBiz;

    @ResponseBody
    @RequestMapping("/customerEntry")
    public Map<String, Object> process(HttpServletRequest request) {
        // 开始记录日志
        if (allowPrintLog()) {
            start(request);
        }

        // step1 定义接口返回值
        Map<String, Object> resultMap = null;
        try {
            resultMap = new HashMap<String,Object>();
            // step2 验证参数合法性
            String req = ConvertUtil.parseRequest2JsonStr(request);
            if (StringUtil.isBlank(req)) {
                resultMap.put("returnCode", CommonCodeEnum.PARAM_REQUIRED.val());
                resultMap.put("returnMsg", "商户进件请求参数为空");
                return resultMap;
            }
            @SuppressWarnings("unchecked")
			Map<String,Object> map = JSONUtil.parseObject(req, Map.class);
            // step3 验证签名合法性
            String key = commonConfig.getSignKey();
            boolean checkSign = verifySignature(map, key);
            if(!checkSign){
            	resultMap.put("returnCode", CommonCodeEnum.SIGN_ERROR.val());
            	resultMap.put("resultMsg", "商户进件签名错误");
            	return resultMap;
            }
         // step4 验证参数合法性
            boolean verify = verifyParameter(req, resultMap);
            if (!verify) {
                return resultMap;
            }
            // step5 执行业务逻辑操作
//            OuterCustomerEntryRequest outerCustomerEntryRequest = JSONUtil.parseObject(req, OuterCustomerEntryRequest.class);
            OuterCustomerEntryRequest outerCustomerEntryRequest = (OuterCustomerEntryRequest)resultMap.get("data");
            OuterCustomerEntryResponse outerCustomerEntryResponse = doCustomerBiz.doEntry(outerCustomerEntryRequest);
            resultMap = ConvertUtil.objectToMap(outerCustomerEntryResponse, null);
            String sign = SignatureUtil.getSign(resultMap, key);
            resultMap.put("sign", sign);
        } catch (Exception e) {
            logger.error("出错：{}", e);
            resultMap = new HashMap<>();
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
        OuterCustomerEntryRequest outerCustomerEntryRequest = null;
        try {
        	outerCustomerEntryRequest = JSONUtil.parseObject(req, OuterCustomerEntryRequest.class);
        } catch (Exception e) {
            logger.info("商户进件参数转换异常 {}",req);
            resultMap.put("returnCode", CommonCodeEnum.PARAM_TRANS_ERROR.val());
            resultMap.put("returnMsg", CommonCodeEnum.PARAM_TRANS_ERROR.error());
            return false;
        }
        String customerNum = outerCustomerEntryRequest.getCustomerNum();
        String sign = outerCustomerEntryRequest.getSign();
        if (StringUtil.isBlank(customerNum)) {
            resultMap.put("returnCode", CommonCodeEnum.PARAM_REQUIRED.val());
            resultMap.put("returnMsg", "商户号不能为空");
            return false;
        }
        if (StringUtil.isBlank(outerCustomerEntryRequest.getAgentNum())) {
        	resultMap.put("returnCode", CommonCodeEnum.PARAM_REQUIRED.val());
        	resultMap.put("returnMsg", "代理商编号不能为空");
        	return false;
        }
        
        if (StringUtil.isBlank(sign)) {
            resultMap.put("returnCode", CommonCodeEnum.SIGN_ERROR.val());
            resultMap.put("returnMsg", "签名字段不能为空");
            return false;
        }
        resultMap.put("data", outerCustomerEntryRequest);
        return true;
    }

}
