package com.pfb.controller.notify;

import com.pfb.biz.IDoCustomerBiz;
import com.pfb.biz.common.CommonConfig;
import com.pfb.biz.common.SHA256Utils;
import com.pfb.biz.service.IOrderBiz;
import com.pfb.biz.upper.common.UpperCommonConfig;
import com.pfb.biz.utils.BRCBUtil;
import com.pfb.biz.utils.ConvertUtil;
import com.pfb.biz.utils.SignatureUtil;
import com.pfb.biz.vo.outer.verify.OuterVerifySignResponse;
import com.pfb.common.exception.IllegalParameterException;
import com.pfb.common.exception.SignatureVerifyException;
import com.pfb.common.util.JSONUtil;
import com.pfb.common.util.StringUtil;
import com.pfb.controller.base.BaseAPIController;
import com.pfb.entity.CustomerRelationEntity;
import com.pfb.entity.OrderEntity;

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
 * 
* @desc: 验证签名
* @author: cts
* @createTime: 2017年12月6日 上午10:06:38
* @history:
* @version: v1.0
 */
@Controller
@RequestMapping("/notify")
public class VerifySignController extends BaseAPIController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IDoCustomerBiz doCustomerBiz;
    @Resource
    private IOrderBiz orderBiz;
    
    @Resource
    private UpperCommonConfig upperCommonConfig;
    @Resource
    private CommonConfig commonConfig;
    @ResponseBody
    @RequestMapping("/verifySign")
    public Map<String, Object> process(HttpServletRequest request) {
        // 开始记录日志
        if (allowPrintLog()) {
            start(request);
        }

        // step1 定义接口返回值
        Map<String, Object> resultMap = null;
        try {
            resultMap = new HashedMap();

            // step2 转义请求数据
            String req = ConvertUtil.parseRequest2JsonStr(request);
            if (StringUtil.isBlank(req)) {
                resultMap.put("returnCode", "FAIL");
                resultMap.put("returnMsg", "异步通知请求参数为空");
                return resultMap;
            }

            // step3 验证参数合法性
            Map<String, Object> map = JSONUtil.parseObject(req, Map.class);
            logger.info("通知系统参数 {}",JSONUtil.toJSONString(map));
            // step4 验证签名合法性
            String key = upperCommonConfig.getUpperKey();
            boolean verifySignature = verifySignatureMt(map, key);
            if (!verifySignature) {
                resultMap.put("returnCode", "FAIL");
                resultMap.put("returnMsg", "请求签名错误");
                return resultMap;
            }

            // step5 执行业务逻辑操作
            Map<String, String> verifyMap = JSONUtil.parseObject(req, Map.class);
            String orderNum=verifyMap.get("outTradeNo");
            //get customer apiKey
            CustomerRelationEntity queryParam = new CustomerRelationEntity();
            OrderEntity order = orderBiz.getOrderByOrderNum(orderNum);
            
            
//            String mchId = verifyMap.get("merchantId");
            queryParam.setCustomerNum(order.getCustomerNum());
            queryParam.setProductType(order.getOrderType());
            CustomerRelationEntity customerRelation = doCustomerBiz.doCustomerReflactionQuery(queryParam);
            if (customerRelation == null) {
                resultMap.put("returnCode", "FAIL");
                resultMap.put("returnMsg", "未找到对应的商户映射关系");
                return resultMap;
            }
            //返回值映射
            OuterVerifySignResponse verifySignResponse = new OuterVerifySignResponse();
            verifySignResponse.setCustomerNum(customerRelation.getCustomerNum());
            verifySignResponse.setMchId(customerRelation.getMchId());
            verifySignResponse.setNonceStr(BRCBUtil.getRandomStringByLength(5));
            verifySignResponse.setOrderNum(verifyMap.get("outTradeNo"));

            // step 6 resp sign
            key = commonConfig.getSignKey();
            resultMap = ConvertUtil.objectToMap(verifySignResponse, null);
            String sign = SignatureUtil.getSign(resultMap, key);
            resultMap.put("sign", sign);
        } catch (IllegalParameterException e) {
            logger.error("出错：{}", e);
            resultMap = new HashMap<>();
            resultMap.put("returnCode", "FAIL");
            resultMap.put("returnMsg", e.getMessage());
        } catch (SignatureVerifyException e) {
            logger.error("出错：{}", e);
            resultMap = new HashMap<>();
            resultMap.put("returnCode", "FAIL");
            resultMap.put("returnMsg", e.getMessage());
        } catch (Exception e) {
            logger.error("出错：{}", e);
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

}
