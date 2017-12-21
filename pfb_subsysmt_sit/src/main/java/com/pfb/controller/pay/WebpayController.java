package com.pfb.controller.pay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pfb.biz.IDoPayBiz;
import com.pfb.biz.common.CommonConfig;
import com.pfb.biz.upper.common.UpperCommonConfig;
import com.pfb.biz.upper.vo.pay.webpay.UpperWebPayRequest;
import com.pfb.biz.utils.ConvertUtil;
import com.pfb.biz.vo.outer.webpay.OuterWebpayRequest;
import com.pfb.biz.vo.outer.webpay.OuterWebpayResponse;
import com.pfb.common.util.JSONUtil;
import com.pfb.common.util.StringUtil;
import com.pfb.controller.base.BaseAPIController;
import com.pfb.enums.CommonCodeEnum;

/**
 * 公众号支付入口
* @desc: pfb_subsysmt_sit
* @author: cts
* @createTime: 2017年12月13日 下午2:34:58
* @history:
* @version: v1.0
 */
@Controller
@RequestMapping("/pay")
public class WebpayController extends BaseAPIController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private CommonConfig commonConfig;
    @Resource
    private UpperCommonConfig upperCommonConfig;
    @Resource
    private IDoPayBiz doPayBiz;

    @ResponseBody
    @RequestMapping("/prepay")
    public Map<String, Object> pay(HttpServletRequest request,HttpServletResponse response,Model model) {
        // 开始记录日志
        if (allowPrintLog()) {
            start(request);
        }
        Map<String, Object> resultMap = new HashMap<String,Object>();
        // step1 定义接口返回值
        try {
        	logger.info("request param {}",request.getParameter("openId")+"=="+request.getParameter("detailId"));
        	String req = ConvertUtil.parseRequest2JsonStr(request);
            logger.info("Convert web prepay request param {}",req);
            Map map = JSONUtil.parseObject(req, Map.class);
        	if(null == map.get("openId") || "".equals(map.get("openId"))){
        		resultMap.put("returnCode", CommonCodeEnum.PARAM_REQUIRED.val());
                resultMap.put("returnMsg", "公众号支付请求参数openId为空");
                return resultMap;
        	}
        	if(null == map.get("detailId") || "".equals(map.get("detailId"))){
        		resultMap.put("returnCode", CommonCodeEnum.PARAM_REQUIRED.val());
                resultMap.put("returnMsg", "公众号支付请求参数detailId为空");
                return resultMap;
        	}
        	String detailId = map.get("detailId").toString();
        	String openId = map.get("openId").toString();
        	OuterWebpayResponse outerWebpayResponse = doPayBiz.doWebpay(detailId,openId);
        	resultMap = ConvertUtil.objectToMap(outerWebpayResponse, null);
        } catch (Exception e) {
            logger.error("公众号下单出错：{}", e.getMessage());
            resultMap.put("returnCode", CommonCodeEnum.FAIL.val());
            resultMap.put("returnMsg", "公众号支付失败");
        }
        return resultMap;

    }

    @RequestMapping("/webpay")
    public String process(HttpServletRequest request,HttpServletResponse response,Model model) {
        // 开始记录日志
        if (allowPrintLog()) {
            start(request);
        }
        try {
            String req = ConvertUtil.parseRequest2JsonStr(request);
            logger.info("web pay meituan request param {}",req);
            if (StringUtil.isBlank(req)) {
            	response.getWriter().write("FAIL");
            }
            OuterWebpayRequest webpayRequest = JSONUtil.parseObject(req, OuterWebpayRequest.class);
            HashMap<String, String>  map = doPayBiz.doWebpayAuth(webpayRequest);
            if(map == null || map.isEmpty()){
            	response.getWriter().write("FAIL");
            }else{
            	response.getWriter().write(createHtml(map));
            }
        } catch (Exception e) {
            logger.error("公众号下单出错：{}", e);
            try {
				response.getWriter().write("FAIL");
			} catch (IOException e1) {
				logger.error("公众号下单出错：{}", e);
			}
        }
        
        return null;

    }
	/**
     * 验证参数
     *
     * @param req
     * @param resultMap
     * @return
     */
    @SuppressWarnings("unchecked")
	protected boolean verifyParameter(String req, Map resultMap) {
        OuterWebpayRequest outerWebpayRequest = null;
        try {
            outerWebpayRequest = JSONUtil.parseObject(req, OuterWebpayRequest.class);
        } catch (Exception e) {
            logger.info("公众号（服务窗）参数转换异常");
            resultMap.put("returnCode", "FAIL");
            resultMap.put("returnMsg", "公众号（服务窗）参数转换异常");
            return false;
        }
        String customerNum = outerWebpayRequest.getCustomerNum();
        String sign = outerWebpayRequest.getSign();
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
    private String createHtml(HashMap<String, String> map) {
    	StringBuilder html = new StringBuilder("<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"></head><body>");
    	html.append("<form action='"+map.get("upperTransUrl")+"' id=\"form\" method=\"get\">")
    	.append("<input name='bizId' id='bizId' value='"+map.get("bizId")+"'>")
    	.append("<input name='mchId' id='mchId' value='"+map.get("mchId")+"'>")
    	.append("<input name='redirect_uri' id='redirect_uri' value='"+map.get("redirect_uri")+"'>")
    	.append("</form><script type=\"text/javascript\">")
    	.append("document.getElementById(\"form\").submit();")
    	.append("</script></body></html>");
		return html.toString();
	}
}
