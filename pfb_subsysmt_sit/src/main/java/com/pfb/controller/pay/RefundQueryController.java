package com.pfb.controller.pay;

import com.pfb.biz.IDoPayBiz;
import com.pfb.biz.common.CommonConfig;
import com.pfb.biz.utils.ConvertUtil;
import com.pfb.biz.utils.SignatureUtil;
import com.pfb.biz.vo.outer.refundquery.OuterRefundQueryRequest;
import com.pfb.biz.vo.outer.refundquery.OuterRefundQueryResponse;
import com.pfb.common.exception.IllegalParameterException;
import com.pfb.common.exception.SignatureVerifyException;
import com.pfb.common.util.JSONUtil;
import com.pfb.common.util.StringUtil;
import com.pfb.controller.base.BaseAPIController;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 退款查询
* @desc: pfb_subsysmt_sit
* @author: cts
* @createTime: 2017年12月18日 下午5:10:52
* @history:
* @version: v1.0
 */
@Controller
@RequestMapping("/pay")
public class RefundQueryController extends BaseAPIController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private CommonConfig commonConfig;
    @Resource
    private IDoPayBiz doPayBiz;

    @ResponseBody
    @RequestMapping("/refundQuery")
    public Map<String, Object> process(HttpServletRequest request) {
        // 开始记录日志
        if (allowPrintLog()) {
            start(request);
        }

        // step1 定义接口返回值
        Map<String, Object> resultMap = null;
        try {
            resultMap = new HashedMap();
            // step2 验证参数合法性
            String req = ConvertUtil.parseRequest2JsonStr(request);
            if (StringUtil.isBlank(req)) {
                resultMap.put("returnCode", "FAIL");
                resultMap.put("returnMsg", "退款查询请求参数为空");
                return resultMap;
            }
            // step3 验证参数合法性
            boolean verify = verifyParameter(req, resultMap);
            if (!verify) {
                return resultMap;
            }
            Map map = JSONUtil.parseObject(req, Map.class);
            // step4 验证签名合法性
            String key = commonConfig.getSignKey();
            verifySignature(map, key);
            // step5 执行业务逻辑操作
            OuterRefundQueryRequest outerRefundQueryRequest = JSONUtil.parseObject(req, OuterRefundQueryRequest.class);
            OuterRefundQueryResponse outerRefundQueryResponse = doPayBiz.doRefundQuery(outerRefundQueryRequest);
            resultMap = ConvertUtil.objectToMap(outerRefundQueryResponse, null);
            String sign = SignatureUtil.getSign(resultMap, key);
            resultMap.put("sign", sign);
        } catch (IllegalParameterException e) {
            logger.error("退款查询出错：{}", e);
            resultMap = new HashMap<>();
            resultMap.put("returnCode", "FAIL");
            resultMap.put("returnMsg", e.getMessage());
        } catch (SignatureVerifyException e) {
            logger.error("退款查询出错：{}", e);
            resultMap = new HashMap<>();
            resultMap.put("returnCode", "FAIL");
            resultMap.put("returnMsg", e.getMessage());
        } catch (Exception e) {
            logger.error("退款查询出错：{}", e);
            resultMap = new HashMap<>();
            resultMap.put("returnCode", "FAIL");
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
    protected boolean verifyParameter(String req, Map resultMap) {
        OuterRefundQueryRequest outerRefundQueryRequest = null;
        try {
        	outerRefundQueryRequest = JSONUtil.parseObject(req, OuterRefundQueryRequest.class);
        } catch (Exception e) {
            logger.info("退款查询参数转换异常");
            resultMap.put("returnCode", "FAIL");
            resultMap.put("returnMsg", "退款查询参数转换异常");
            return false;
        }
        String customerNum = outerRefundQueryRequest.getCustomerNum();
        String sign = outerRefundQueryRequest.getSign();
        if (StringUtil.isBlank(customerNum)) {
            resultMap.put("returnCode", "FAIL");
            resultMap.put("returnMsg", "商户号不能为空");
            return false;
        }
        if (StringUtil.isBlank(sign)) {
            resultMap.put("returnCode", "FAIL");
            resultMap.put("returnMsg", "签名字段不能为空");
            return false;
        }
        return true;
    }

}
