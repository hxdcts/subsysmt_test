package com.pfb.biz;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.pfb.aliyunmq.producer.MQProducerImpl;
import com.pfb.aliyunmq.vo.MessageBean;
import com.pfb.biz.common.CommonConstant;
import com.pfb.biz.common.MqTopicFac;
import com.pfb.biz.common.MtOrderUtil;
import com.pfb.biz.service.ICustomerRelationBiz;
import com.pfb.biz.service.IOrderBiz;
import com.pfb.biz.upper.common.UpperCommonConfig;
import com.pfb.biz.upper.common.UpperCommonConstant;
import com.pfb.biz.upper.service.IUpperPayBiz;
import com.pfb.biz.upper.vo.pay.micro.UpperMicroPayRequest;
import com.pfb.biz.upper.vo.pay.micro.UpperMicroPayResponse;
import com.pfb.biz.upper.vo.pay.query.UpperOrderQueryRequest;
import com.pfb.biz.upper.vo.pay.query.UpperQueryResponse;
import com.pfb.biz.upper.vo.pay.refund.UpperRefundRequest;
import com.pfb.biz.upper.vo.pay.refund.UpperRefundResponse;
import com.pfb.biz.upper.vo.pay.refundquery.UpperRefundDetailResponse;
import com.pfb.biz.upper.vo.pay.refundquery.UpperRefundQueryRequest;
import com.pfb.biz.upper.vo.pay.refundquery.UpperRefundQueryResponse;
import com.pfb.biz.upper.vo.pay.scan.UpperScanPayRequest;
import com.pfb.biz.upper.vo.pay.scan.UpperScanPayResponse;
import com.pfb.biz.upper.vo.pay.webpay.UpperWebPayRequest;
import com.pfb.biz.upper.vo.pay.webpay.UpperWebPayResponse;
import com.pfb.biz.vo.outer.micro.OuterMicroRequest;
import com.pfb.biz.vo.outer.micro.OuterMicroResponse;
import com.pfb.biz.vo.outer.query.OuterOrderQueryRequest;
import com.pfb.biz.vo.outer.query.OuterOrderQueryResponse;
import com.pfb.biz.vo.outer.refund.OuterRefundRequest;
import com.pfb.biz.vo.outer.refund.OuterRefundResponse;
import com.pfb.biz.vo.outer.refundquery.OuterRefundDetailResponse;
import com.pfb.biz.vo.outer.refundquery.OuterRefundQueryRequest;
import com.pfb.biz.vo.outer.refundquery.OuterRefundQueryResponse;
import com.pfb.biz.vo.outer.scan.OuterScanRequest;
import com.pfb.biz.vo.outer.scan.OuterScanResponse;
import com.pfb.biz.vo.outer.webpay.OuterWebpayRequest;
import com.pfb.biz.vo.outer.webpay.OuterWebpayResponse;
import com.pfb.common.util.DateUtil;
import com.pfb.common.util.JSONUtil;
import com.pfb.common.util.StringUtil;
import com.pfb.common.util.UUIDUtil;
import com.pfb.entity.CustomerRelationEntity;
import com.pfb.entity.OrderDetailEntity;
import com.pfb.entity.OrderEntity;
import com.pfb.entity.RefundEntity;
import com.pfb.enums.CommonCodeEnum;
import com.pfb.redis.service.RedisUtilsService;
import com.pfb.service.OrderDetailServiceImpl;
import com.pfb.service.OrderServiceImpl;
import com.pfb.service.RefundServiceImpl;

import net.sf.json.JSONArray;

@Service
public class DoPayBizImpl implements IDoPayBiz {
	private static final Logger logger = LoggerFactory.getLogger(DoPayBizImpl.class);
	@Resource
    OrderServiceImpl orderServiceImpl;
    @Resource
    OrderDetailServiceImpl orderDetailServiceImpl;
    @Resource
    private UpperCommonConfig upperCommonConfig;
    @Resource
    private IUpperPayBiz upperPayBiz;
    @Resource
    private MqTopicFac mqTopicFac;
    @Resource
    private MQProducerImpl mqProducerImpl;
    @Resource
    private RedisUtilsService redisUtilsService;
    @Resource
    private ICustomerRelationBiz customerRelationBiz;
    @Resource
    private IOrderBiz orderBiz;
    @Resource
    private RefundServiceImpl refundServiceImpl;
    /**
     * 美团刷卡支付业务
     */
	@Override
	public OuterMicroResponse doMicro(OuterMicroRequest microRequest) {
		OuterMicroResponse outerMicroResponse = new OuterMicroResponse();
		logger.info("美团刷卡支付业务开始------------------");
		//下单
        OrderEntity order = new OrderEntity();
        order.setVersion(1);
        order.setCustomerNum(microRequest.getCustomerNum());
        order.setOrderNum(microRequest.getOrderNum());
        order.setOrderStatus(CommonConstant.ORDER_STATUS_INIT);
        order.setTotalAmount(new BigDecimal(microRequest.getTotalFee()));
        order.setActivityAmount(BigDecimal.ZERO);
        order.setActualAmount(new BigDecimal(microRequest.getTotalFee()));
        if(StringUtil.isBlank(microRequest.getOrderType())){
        	outerMicroResponse.setReturnCode(CommonCodeEnum.PARAME_REQUIRED.val());
        	outerMicroResponse.setReturnMsg("订单类型不能为空");
        	return outerMicroResponse;
        }
        order.setOrderType(microRequest.getOrderType());
        order.setServiceType(CommonConstant.SERVICE_TYPE_MICRO);
        order.setTradeType(CommonConstant.ORDER_TRADE_TYPE_PAY);
        order.setOrderFlag(CommonConstant.ORDER_FLAG_NORMAL);
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        order.setSettleMode(microRequest.getSettleMode());
        orderServiceImpl.add(order);
        
        OrderDetailEntity orderDetail = new OrderDetailEntity();
        orderDetail.setOrderNum(microRequest.getOrderNum());
        orderDetail.setReqData(JSONUtil.toJSONString(microRequest));
        orderDetail.setCreateTime(new Date());
        orderDetailServiceImpl.add(orderDetail);
        logger.info("刷卡下单数据: {} {}", order, orderDetail);
        
        CustomerRelationEntity customerRelationEntity=new CustomerRelationEntity();
        customerRelationEntity.setCustomerNum(microRequest.getCustomerNum());
        customerRelationEntity.setProductType(microRequest.getOrderType());
        
        logger.info("查询key和渠道商户号条件 : {}", JSONUtil.toJSONString(customerRelationEntity));
        List<CustomerRelationEntity> relationList = customerRelationBiz.getRelationsByCustomerRelation(customerRelationEntity);
        if(null == relationList || relationList.isEmpty()){
        	
        	
        	outerMicroResponse.setReturnCode(CommonCodeEnum.UPPER_CUSTOMER_NOT_EXIST.val());
        	outerMicroResponse.setReturnMsg("渠道报备信息不存在");
        	return outerMicroResponse;
        }
        logger.info("查询key和渠道商户号结果 : {}", relationList);
        CustomerRelationEntity customerRelation = relationList.get(0);
        //拼接上游请求数据
        String merchantId = customerRelation.getMchId();// "164723298"//开放平台分配的商户id
        String appId = upperCommonConfig.getAppId();//接入方标识
        String channel = microRequest.getOrderType();
        channel = channel.equals("WECHAT")?"wx_barcode_pay":"ali_barcode_pay";
        String outTradeNo = microRequest.getOrderNum();
        String authCode = microRequest.getAuthCode();
        String totalFee = microRequest.getTotalFee();
        String subject = microRequest.getSubject();
        String body = microRequest.getDescription();
        String expireMinutes = "5";//创建支付订单后，订单关闭时间，单位为分钟。默认设置为5分钟,
        String key = upperCommonConfig.getUpperKey();
        String random = microRequest.getNonceStr();
        UpperMicroPayRequest upperMicroPayRequest = new UpperMicroPayRequest(channel, outTradeNo,
        		authCode, totalFee, subject, body, expireMinutes, merchantId, appId, random, key);
        UpperMicroPayResponse upperMicroPayResponse = upperPayBiz.doUpperMicro(upperMicroPayRequest);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setOrderNum(microRequest.getOrderNum());
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        orderDetailEntity.setId(orderDetail.getId());
        orderDetailEntity.setOrderNum(microRequest.getOrderNum());
        //条码支付中，微信将 用户支付中, 当成错误码返回 "errCode":"TRADE_PAY_UNKOWN_ERROR","errMsg":"需要用户输入支付密码" 也算是支付中
        if (UpperCommonConstant.SUCCESS.equals(upperMicroPayResponse.getStatus()) && (UpperCommonConstant.ORDER_SUCCESS.equals(upperMicroPayResponse.getOrderStatus()) || UpperCommonConstant.ORDER_NEW.equals(upperMicroPayResponse.getOrderStatus()))|| "TRADE_PAY_UNKOWN_ERROR".equals(upperMicroPayResponse.getErrCode())|| "TRANSFER_TIMEOUT_ERR0R".equals(upperMicroPayResponse.getErrCode())|| "TRADE_PAYING_ERROR".equals(upperMicroPayResponse.getErrCode())) {
        	orderEntity.setOrderStatus(CommonConstant.ORDER_STATUS_USERPAYING);
        	orderEntity.setChannelOrderNum(upperMicroPayResponse.getOutTradeNo());
        	orderEntity.setPlatRelationNo(upperMicroPayResponse.getTradeNo());
            outerMicroResponse.setOrderStatus(order.getOrderStatus());
            outerMicroResponse.setOrderType(order.getOrderType());
            outerMicroResponse.setTotalFee(totalFee);
            outerMicroResponse.setCustomerNum(microRequest.getCustomerNum());
            outerMicroResponse.setNonceStr(UUIDUtil.randomUUID());
            MessageBean microMes = new MessageBean();
            microMes.setMsgTopic(mqTopicFac.getMtMicroOrderCheck());
            microMes.setObjVal(orderEntity);
            mqProducerImpl.sendMsg(microMes);
        } else {
        	orderEntity.setOrderStatus(CommonConstant.ORDER_STATUS_FAIL);
            outerMicroResponse.setReturnCode(CommonCodeEnum.FAIL.val());
            outerMicroResponse.setReturnMsg(upperMicroPayResponse.getErrMsg());
        }
        orderDetailEntity.setRspData(JSONUtil.toJSONString(outerMicroResponse));
        //修改数据库
        orderServiceImpl.update(orderEntity);
        try {
        	redisUtilsService.setByKey(CommonConstant.MT_CACHE_CHECK_ORDER_PHIX+orderEntity.getOrderNum(), "tag", 180);
		} catch (Exception e) {
			logger.debug("micro redis error {}",e);
		}
        
        orderDetailServiceImpl.update(orderDetailEntity);

        return outerMicroResponse;
	}
	/**
	 * 订单查询
	 */
	@Override
	public OuterOrderQueryResponse doOrderQuery(OuterOrderQueryRequest orderQueryRequest) {
		OuterOrderQueryResponse orderQueryResponse = new OuterOrderQueryResponse();
        //step 1 查询系统订单查询
        String orderNum = orderQueryRequest.getOrderNum();
        OrderEntity order = orderBiz.getOrderByOrderNum(orderNum);
        if (order == null) {
            orderQueryResponse.setReturnCode(CommonCodeEnum.ORDER_NOT_EXIST.val());
            orderQueryResponse.setReturnMsg(CommonCodeEnum.ORDER_NOT_EXIST.error());
            logger.info("未找到订单， 订单号：{}", orderNum);
            return orderQueryResponse;
        }
        //step 2 订单如果为终态，直接返回
        String orderStatus = order.getOrderStatus();
        if (!CommonConstant.ORDER_STATUS_USERPAYING.equals(orderStatus)) {
            orderQueryResponse.setCustomerNum(order.getCustomerNum());
            orderQueryResponse.setOrderNum(order.getOrderNum());
            orderQueryResponse.setOrderType(order.getOrderType());
            orderQueryResponse.setOrderStatus(order.getOrderStatus());
            orderQueryResponse.setTotalFee(String.valueOf(order.getTotalAmount()));
            orderQueryResponse.setFundType(order.getFundType());
            Date timeEnd = order.getTimeEnd();
            if (timeEnd != null) {
                String completeTime = DateUtil.parseDate(timeEnd, DateUtil.DATE_STYLE_YYYYMMDDHHMMSS);
                orderQueryResponse.setCompleteTime(completeTime);
            }
            orderQueryResponse.setNonceStr(orderQueryRequest.getNonceStr());
            return orderQueryResponse;
        }

        //step 3 查询商户映射关系
        CustomerRelationEntity queryCustomerRelation = new CustomerRelationEntity();
        String customerNum = orderQueryRequest.getCustomerNum();
        String orderType = order.getOrderType();
        queryCustomerRelation.setCustomerNum(customerNum);
        queryCustomerRelation.setProductType(orderType);
        List<CustomerRelationEntity> relationList = customerRelationBiz.getRelationsByCustomerRelation(queryCustomerRelation);
        if (relationList.size() == 0) {
            orderQueryResponse.setReturnCode(CommonCodeEnum.UPPER_CUSTOMER_NOT_EXIST.val());
            orderQueryResponse.setReturnMsg("商户映射关系未找到");
            logger.info("未找到商户的映射关系， 商户号：{}", customerNum);
            return orderQueryResponse;
        }
        CustomerRelationEntity customerRelationEntity = relationList.get(0);

        //step 4 拼接上游数据
        String key = upperCommonConfig.getUpperKey();
        String merchantId = customerRelationEntity.getMchId();//"164723298";
        String appId = upperCommonConfig.getAppId();//接入方标识
        String outTradeNo = orderNum;
        String nonceStr = orderQueryRequest.getNonceStr();
        UpperOrderQueryRequest upperOrderQueryRequest = new UpperOrderQueryRequest(outTradeNo, merchantId, appId, nonceStr, "", key);
        UpperQueryResponse upperQueryResponse = upperPayBiz.doUpperOrderQuery(upperOrderQueryRequest);
        if (upperQueryResponse == null) {
            orderQueryResponse.setReturnCode(CommonCodeEnum.UPPER_QUERY_ERROR.val());
            orderQueryResponse.setReturnMsg("上游查询返回信息为空");
            return orderQueryResponse;
        }

        //step 5 转义下游数据
        String returnCode = upperQueryResponse.getStatus();
        String returnMsg = upperQueryResponse.getErrMsg();
        if (!UpperCommonConstant.SUCCESS.equals(returnCode)) {
            orderQueryResponse.setReturnCode(CommonCodeEnum.UPPER_QUERY_ERROR.val());
            orderQueryResponse.setReturnMsg(returnMsg);
            return orderQueryResponse;
        }

        //step 6 更新本地订单状态&更新order对象信息
        String payStatus = upperQueryResponse.getOrderStatus();
        String channelOrderNum = upperQueryResponse.getOutTradeNo();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNum(order.getOrderNum());
        orderEntity.setPlatRelationNo(upperQueryResponse.getTradeNo());
        orderEntity.setOrderStatus(payStatus);
        orderEntity.setChannelOrderNum(channelOrderNum);
        String completeTime = upperQueryResponse.getPayTime();
        if (StringUtil.isNotBlank(completeTime)) {
            Date timeEnd = DateUtil.parseDate(completeTime, DateUtil.DATE_STYLE_YYYYMMDDHHMMSS);
            orderEntity.setTimeEnd(timeEnd);
        }
        orderEntity.setModifyTime(new Date());
        orderServiceImpl.update(orderEntity);
        //step 7 返回值映射
        orderQueryResponse.setCustomerNum(customerNum);
        orderQueryResponse.setOrderNum(orderNum);
        orderQueryResponse.setOrderType(orderType);
        orderQueryResponse.setOrderStatus(MtOrderUtil.getOrderStatus(payStatus));
        orderQueryResponse.setTotalFee(String.valueOf(order.getTotalAmount()));
        orderQueryResponse.setFundType(order.getFundType());
        orderQueryResponse.setCompleteTime(completeTime);
        orderQueryResponse.setNonceStr(orderQueryRequest.getNonceStr());

        return orderQueryResponse;
			
	}
	
	
	@Override
	public OuterScanResponse doScan(OuterScanRequest scanRequest) {
		OuterScanResponse outerScanResponse = new OuterScanResponse();
		outerScanResponse.setOrderNum(scanRequest.getOrderNum());
		outerScanResponse.setCustomerNum(scanRequest.getCustomerNum());
    	logger.info("美团主扫支付业务开始------------------");
        //step 1 下单
        OrderEntity order = new OrderEntity();
        order.setVersion(1);
        order.setCustomerNum(scanRequest.getCustomerNum());
        order.setOrderNum(scanRequest.getOrderNum());
        order.setOrderStatus(CommonConstant.ORDER_STATUS_INIT);
        order.setTotalAmount(new BigDecimal(scanRequest.getTotalFee()));
        order.setActivityAmount(BigDecimal.ZERO);
        order.setActualAmount(new BigDecimal(scanRequest.getTotalFee()));
        order.setOrderType(scanRequest.getOrderType());
        order.setServiceType(CommonConstant.SERVICE_TYPE_SCANNED);
        order.setTradeType(CommonConstant.ORDER_TRADE_TYPE_PAY);
        order.setOrderFlag(CommonConstant.ORDER_FLAG_NORMAL);
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        order.setSettleMode(scanRequest.getSettleMode());
        orderServiceImpl.add(order);
        //orderdetail
        OrderDetailEntity orderDetail = new OrderDetailEntity();
        orderDetail.setOrderNum(scanRequest.getOrderNum());
        orderDetail.setReqData(JSONUtil.toJSONString(scanRequest));
        orderDetail.setCreateTime(new Date());
        orderDetailServiceImpl.add(orderDetail);
        logger.info("下单数据: {} {}", order, orderDetail);

        CustomerRelationEntity customerRelationEntity=new CustomerRelationEntity();
        customerRelationEntity.setCustomerNum(scanRequest.getCustomerNum());
        customerRelationEntity.setProductType(scanRequest.getOrderType());
        logger.info("查询key和渠道商户号条件 : {}", JSONObject.toJSONString(customerRelationEntity));
        List<CustomerRelationEntity> relationList = customerRelationBiz.getRelationsByCustomerRelation(customerRelationEntity);
        if(null == relationList || relationList.isEmpty()){
        	outerScanResponse.setReturnCode(CommonCodeEnum.UPPER_CUSTOMER_NOT_EXIST.val());
        	outerScanResponse.setReturnMsg("渠道报备信息不存在");
        	return outerScanResponse;
        }
        logger.info("查询key和渠道商户号结果 : {}", relationList);
        CustomerRelationEntity customerRelation = relationList.get(0);
        //step 2 拼接上游请求数据
        String outTradeNo = scanRequest.getOrderNum();
        String totalFee = scanRequest.getTotalFee();
        String subject = scanRequest.getSubject();
        String body = scanRequest.getDescription();
        String channel = scanRequest.getOrderType().equals("WECHAT")?"wx_scan_pay":"ali_scan_pay";;
        String expireMinutes = "2";
        String tradeType = "NATIVE";
        String notityUrl = upperCommonConfig.getNotifyUrl();
//        String openid = scanRequest.getNonceStr();
        String openid = "";
        String merchantId = customerRelation.getMchId();
        String appid = upperCommonConfig.getAppId();
        String random = scanRequest.getNonceStr();
        String key = upperCommonConfig.getUpperKey();
        UpperScanPayRequest upperScanPayRequest = new UpperScanPayRequest(outTradeNo, totalFee, subject, body, channel, expireMinutes, tradeType,
        		notityUrl, openid, null, null, merchantId, appid, random, key);
        //step 3 发起请求
        UpperScanPayResponse upperScanPayResponse = upperPayBiz.doUpperScan(upperScanPayRequest);
        //step 5 返回值
        OrderEntity orderEntity = new OrderEntity();
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        orderEntity.setOrderNum(scanRequest.getOrderNum());
        orderDetailEntity.setOrderNum(scanRequest.getOrderNum());
        if (UpperCommonConstant.SUCCESS.equals(upperScanPayResponse.getStatus())) {
            orderEntity.setOrderStatus(CommonConstant.ORDER_STATUS_USERPAYING);
            orderEntity.setChannelOrderNum(upperScanPayRequest.getOutTradeNo());
            outerScanResponse.setCustomerNum(scanRequest.getCustomerNum());
            outerScanResponse.setCodeUrl(upperScanPayResponse.getQrCode());
            outerScanResponse.setOrderStatus(orderEntity.getOrderStatus());
            outerScanResponse.setNonceStr(UUIDUtil.randomUUID());
        } else {
            orderEntity.setOrderStatus(CommonConstant.ORDER_STATUS_FAIL);
            outerScanResponse.setReturnCode(CommonCodeEnum.FAIL.val());
            outerScanResponse.setReturnMsg(upperScanPayResponse.getErrMsg());
        }
        orderDetailEntity.setRspData(JSONUtil.toJSONString(outerScanResponse));
        //修改数据库
        orderServiceImpl.update(orderEntity);
        orderDetailServiceImpl.update(orderDetailEntity);
        return outerScanResponse;
	}
	
	
	@Override
	public HashMap<String, String> doWebpayAuth(OuterWebpayRequest webpayRequest){
		logger.info("公众号支付业务开始------------------");
        HashMap<String, String> map = new HashMap<>();

		//step 1 下单
		OrderEntity order = new OrderEntity();
		order.setVersion(1);
		order.setCustomerNum(webpayRequest.getCustomerNum());
		order.setOrderNum(webpayRequest.getOrderNum());
		order.setOrderStatus(CommonConstant.ORDER_STATUS_INIT);
		order.setTotalAmount(new BigDecimal(webpayRequest.getTotalFee()));
		order.setActivityAmount(BigDecimal.ZERO);
		order.setActualAmount(new BigDecimal(webpayRequest.getTotalFee()));
		order.setOrderType(webpayRequest.getOrderType());
		order.setServiceType(CommonConstant.SERVICE_TYPE_WEBPAY);
        order.setSettleMode(webpayRequest.getSettleMode());
        order.setTradeType(CommonConstant.ORDER_TRADE_TYPE_PAY);
        order.setOrderFlag(CommonConstant.ORDER_FLAG_NORMAL);
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        orderServiceImpl.add(order);
        //orderdetail
        OrderDetailEntity orderDetail = new OrderDetailEntity();
        orderDetail.setOrderNum(webpayRequest.getOrderNum());
        orderDetail.setReqData(JSONUtil.toJSONString(webpayRequest));
        orderDetail.setCreateTime(new Date());
        orderDetail = orderDetailServiceImpl.add(orderDetail);
        logger.info("下单数据: {} {}", order, orderDetail);

        CustomerRelationEntity customerRelationEntity=new CustomerRelationEntity();
        customerRelationEntity.setCustomerNum(webpayRequest.getCustomerNum());
        customerRelationEntity.setProductType(webpayRequest.getOrderType());
        
        logger.info("查询key和渠道商户号条件 : {}", customerRelationEntity);
        List<CustomerRelationEntity> relationList = customerRelationBiz.getRelationsByCustomerRelation(customerRelationEntity);
        logger.info("查询key和渠道商户号结果 : {}", relationList);
        if(null == relationList || relationList.isEmpty()){
	      	  return map;
	    }
        String appId = upperCommonConfig.getAppId();
        String merchantId =relationList.get(0).getMchId(); // "164723298";
        String authUrl = upperCommonConfig.getAuthUrl();
        String webPayNotifyUrl = upperCommonConfig.getWebPayNotifyUrl()+"?detailId="+orderDetail.getId();
        StringBuffer sBuffer = new StringBuffer();
        try {
        	map.put("bizId", appId);
        	map.put("mchId", merchantId);
        	map.put("redirect_uri", URLEncoder.encode(webPayNotifyUrl,"utf-8"));
        	map.put("upperTransUrl", authUrl);
//			sBuffer.append(authUrl).append("?").append("bizId=").append(appId).append("&mchId=").append(merchantId).append("&redirect_uri=").append(URLEncoder.encode(webPayNotifyUrl,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("美团公众号支付encode出错 {}",e.getMessage());
		}
        
		return map;
	}
	
	
	@Override
	public OuterWebpayResponse doWebpay(String detailId,String openId) {
		OuterWebpayResponse webPayResponse = new OuterWebpayResponse();
		OrderDetailEntity orderDetailEntity =  orderDetailServiceImpl.getById(Long.valueOf(detailId));
		if(orderDetailEntity==null){
			webPayResponse.setReturnCode(CommonCodeEnum.PARAM_REQUIRED.val());
			webPayResponse.setReturnMsg("公众号支付查询订单详情失败");
			return webPayResponse;
		}
		if(StringUtil.isBlank(orderDetailEntity.getReqData())){
			webPayResponse.setReturnCode(CommonCodeEnum.PARAM_REQUIRED.val());
			webPayResponse.setReturnMsg("公众号支付查询订单请求信息失败");
			return webPayResponse;
		}
		OuterWebpayRequest outerWebpayRequest = JSONUtil.parseObject(orderDetailEntity.getReqData(), OuterWebpayRequest.class);
		CustomerRelationEntity customerRelationEntity=new CustomerRelationEntity();
        customerRelationEntity.setCustomerNum(outerWebpayRequest.getCustomerNum());
        customerRelationEntity.setProductType(outerWebpayRequest.getOrderType());  
		List<CustomerRelationEntity> relationList = customerRelationBiz.getRelationsByCustomerRelation(customerRelationEntity);
	    if(null == relationList || relationList.isEmpty()){
	    	  webPayResponse.setReturnCode(CommonCodeEnum.UPPER_CUSTOMER_NOT_EXIST.val());
	    	  webPayResponse.setReturnMsg("渠道报备信息不存在");
	      	  return webPayResponse;
	    }
        CustomerRelationEntity customerRelation = relationList.get(0);

		String merchantId = customerRelation.getMchId(); //开放平台分配的商户id
        String appId = upperCommonConfig.getAppId();//接入方标识
        String channel = outerWebpayRequest.getOrderType();
        channel = channel.equals("WECHAT")?"wx_scan_pay":"ali_scan_pay";
        String key = upperCommonConfig.getUpperKey();
        UpperWebPayRequest upperWebPayRequest = new UpperWebPayRequest(outerWebpayRequest.getOrderNum(), outerWebpayRequest.getTotalFee(), outerWebpayRequest.getSubject(), outerWebpayRequest.getDescription(), channel, "2", "JSAPI", upperCommonConfig.getNotifyUrl(), merchantId, appId, outerWebpayRequest.getNonceStr(), "", key,openId);
        UpperWebPayResponse upperWebPayResponse=upperPayBiz.doUpperWebPay(upperWebPayRequest);
        OuterWebpayResponse outerWebpayResponse = new OuterWebpayResponse();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNum(orderDetailEntity.getOrderNum());
        
        if(UpperCommonConstant.FAIL.equals(upperWebPayResponse.getStatus())){
        	orderEntity.setOrderStatus(UpperCommonConstant.FAIL);
        	orderServiceImpl.update(orderEntity);
        	outerWebpayResponse.setReturnCode(UpperCommonConstant.FAIL);
        	outerWebpayResponse.setReturnMsg(UpperCommonConstant.FAIL);
        	return outerWebpayResponse;
        }
        
        MessageBean microMes = new MessageBean();
        microMes.setMsgTopic(mqTopicFac.getMtMicroOrderCheck());
        microMes.setObjVal(orderEntity);
        mqProducerImpl.sendMsg(microMes);
        
        if(channel.equals("wx_scan_pay")){
        	outerWebpayResponse.setAppId(upperWebPayResponse.getAppId());
            outerWebpayResponse.setTimeStamp(upperWebPayResponse.getTimeStamp());
            outerWebpayResponse.setNonceStr(upperWebPayResponse.getNonceStr());
            outerWebpayResponse.setPrepayId(upperWebPayResponse.getPrepayId());
            outerWebpayResponse.setSignType(upperWebPayResponse.getSignType());
            outerWebpayResponse.setPaySign(upperWebPayResponse.getPaySign());
        }else{
        	outerWebpayResponse.setTransactionId(upperWebPayResponse.getTransactionId());
        }
        
        try {
        	if(StringUtil.isNotBlank(outerWebpayRequest.getCallbackUrl())){
    			outerWebpayResponse.setRedirectUri(URLEncoder.encode(outerWebpayRequest.getCallbackUrl(),"utf-8"));
        	}else{
        		outerWebpayResponse.setRedirectUri(URLEncoder.encode(upperCommonConfig.getRedirectUrl(),"utf-8"));
        	}

		} catch (UnsupportedEncodingException e) {
			logger.error("encode失败",e);
		}
        outerWebpayResponse.setBizId(appId);
        outerWebpayResponse.setOrderType(outerWebpayRequest.getOrderType());
        OrderDetailEntity orderDetail = new OrderDetailEntity();
        orderDetail.setId(orderDetailEntity.getId());
        orderDetail.setOrderNum(orderDetailEntity.getOrderNum());
        orderDetailEntity.setRspData(JSONUtil.toJSONString(upperWebPayResponse));
        orderDetailServiceImpl.update(orderDetailEntity);
        orderEntity.setOrderStatus(UpperCommonConstant.USERPAYING);
    	orderServiceImpl.update(orderEntity);
        return outerWebpayResponse;
	}
	@Override
    public OuterRefundResponse doRefund(OuterRefundRequest refundRequest) {
        OuterRefundResponse refundResponse = new OuterRefundResponse();
        logger.info("------------------退款业务开始----------------");
        logger.info("退款接受参数 {}",JSONUtil.toJSONString(refundRequest));
        
        //1查询原单
        OrderEntity queryParam=new OrderEntity();
        queryParam.setOrderNum(refundRequest.getOrderNum());
        List<OrderEntity> orderList = orderServiceImpl.queryListByParam(queryParam);
        if(orderList.isEmpty()){
        	refundResponse.setReturnCode(CommonCodeEnum.ORDER_NOT_EXIST.val());
        	refundResponse.setReturnMsg(CommonCodeEnum.ORDER_NOT_EXIST.error());
        	return refundResponse;
        }
        //2.下单
        Date now=new Date();
        OrderEntity order = orderList.get(0);
        RefundEntity refund=null;
        OrderEntity refundOrder=null;
        RefundEntity queryRefundParam=new RefundEntity();
        queryRefundParam.setRefundNum(refundRequest.getRefundNum());
        List<RefundEntity> refundList = refundServiceImpl.queryListByParam(queryRefundParam);
        
        if(refundList.isEmpty()){
        	 refund=new RefundEntity();
        	 refund.setRefundNum(refundRequest.getRefundNum());
             refund.setOrderNum(refundRequest.getOrderNum());
             refund.setPlatRelationNo(order.getPlatRelationNo());
             refund.setCustomerNum(refundRequest.getCustomerNum());
             refund.setRefundFee(new BigDecimal(refundRequest.getRefundFee()));
             refund.setRefundStatus(CommonConstant.ORDER_STATUS_INIT);
             refund.setRefundChannel(order.getOrderType());
             refund.setDeviceInfo(refundRequest.getDeviceInfo());
             refund.setDeleted(0);
             refund.setCreateTime(now);
             refund.setModifyTime(now);
             
             
             refundOrder=new OrderEntity();
             refundOrder.setVersion(1);
             refundOrder.setCustomerNum(refundRequest.getCustomerNum());
             refundOrder.setOrderNum(refundRequest.getRefundNum());
             refundOrder.setOrderStatus(CommonConstant.ORDER_STATUS_INIT);
             refundOrder.setTotalAmount(new BigDecimal(refundRequest.getRefundFee()));
             refundOrder.setActivityAmount(BigDecimal.ZERO);
             refundOrder.setActualAmount(new BigDecimal(refundRequest.getRefundFee()));
             refundOrder.setOrderType(order.getOrderType());
             refundOrder.setServiceType(order.getServiceType());
             refundOrder.setTradeType(CommonConstant.ORDER_TRADE_TYPE_REFUND);
             refundOrder.setOrderFlag(CommonConstant.ORDER_FLAG_NORMAL);
             refundOrder.setCreateTime(new Date());
             refundOrder.setModifyTime(new Date());
             refundOrder.setSettleMode(order.getSettleMode());
             logger.info("下单数据 {}{}",JSONUtil.toJSONString(refund),JSONUtil.toJSONString(refundOrder));
             refundServiceImpl.add(refund);
             orderBiz.addOrder(refundOrder);
        	
        }else{
        	refund=refundList.get(0);
        	refundOrder=orderBiz.getOrderByOrderNum(refundRequest.getRefundNum());
        }
        //3查询商户映射
        CustomerRelationEntity customerRelationEntity=new CustomerRelationEntity();
        customerRelationEntity.setCustomerNum(refund.getCustomerNum());
        customerRelationEntity.setProductType(order.getOrderType());
        
        logger.info("查询key和渠道商户号条件 : {}", customerRelationEntity);
        List<CustomerRelationEntity> relationList = customerRelationBiz.getRelationsByCustomerRelation(customerRelationEntity);
        if(null == relationList || relationList.isEmpty()){
        	refundResponse.setReturnCode(CommonCodeEnum.UPPER_CUSTOMER_NOT_EXIST.val());
        	refundResponse.setReturnMsg("渠道报备信息不存在");
        	return refundResponse;
        }
        logger.info("查询key和渠道商户号结果 : {}", relationList);
        
        //4拼接请求数据
        String merchantId=relationList.get(0).getMchId();
        String outRefundNo=refundRequest.getRefundNum();
        String orderNo=refundRequest.getOrderNum();
        String refundFee=refundRequest.getRefundFee();
        String nonceStr=refundRequest.getNonceStr();
        String appId = upperCommonConfig.getAppId();//接入方标识
        String key = upperCommonConfig.getUpperKey();
        UpperRefundRequest upperRefundRequest=new UpperRefundRequest(merchantId, appId, nonceStr, "", orderNo, refundFee, outRefundNo, "退款", key);
        UpperRefundResponse upperresponse = upperPayBiz.doUpperRefund(upperRefundRequest);
        refundResponse.setReturnCode(upperresponse.getStatus());
    	refundResponse.setReturnMsg(upperresponse.getErrMsg());
        if(CommonConstant.SUCCESS.equals(upperresponse.getStatus())){
        	refundOrder.setOrderStatus(CommonConstant.ORDER_STATUS_PROCESSING);
        	refundOrder.setChannelOrderNum(outRefundNo);
        	
        	refund.setUpperRefundNo(outRefundNo);
        	refund.setRefundStatus(CommonConstant.ORDER_STATUS_PROCESSING);
        	refundResponse.setRefundNum(refundRequest.getRefundNum());
        	refundResponse.setRefundFee(refundRequest.getRefundFee());
        	refundResponse.setSettlementFee(refundRequest.getRefundFee());
        	refundResponse.setNonceStr(refundRequest.getNonceStr());
        	refundResponse.setReturnCode(CommonCodeEnum.s_000000.val());
        	refund.setModifyTime(new Date());
            refundOrder.setModifyTime(new Date());
            refundServiceImpl.update(refund);
            orderBiz.updateOrder(refundOrder);
        }else if(CommonConstant.FAIL.equals(upperresponse.getStatus())){
        	refund.setRefundStatus(CommonConstant.ORDER_STATUS_FAIL);
        	refundOrder.setOrderStatus(CommonConstant.ORDER_STATUS_FAIL);
        	refund.setModifyTime(new Date());
        	refundOrder.setModifyTime(new Date());
        	refundServiceImpl.update(refund);
        	orderBiz.updateOrder(refundOrder);
        }
        return refundResponse;
    }
	@Override
	public OuterRefundQueryResponse doRefundQuery(OuterRefundQueryRequest outerRefundQueryRequest) {
	    	logger.info("------------------退款查询业务开始----------------");
	    	OuterRefundQueryResponse outerRefundQueryResponse=new OuterRefundQueryResponse();
	    	//1.查询退款订单
	    	
	    	RefundEntity queryParam=new RefundEntity();
	    	if(!StringUtil.isBlank(outerRefundQueryRequest.getOrderNum())){
	    		queryParam.setOrderNum(outerRefundQueryRequest.getOrderNum());
	    	}else{
	    		queryParam.setRefundNum(outerRefundQueryRequest.getRefundNum());
	    	}

	    	List<RefundEntity> refundList = refundServiceImpl.queryListByParam(queryParam);
	    	if(refundList.isEmpty()){
	    		outerRefundQueryResponse.setReturnCode(CommonCodeEnum.ORDER_NOT_EXIST.val());
	    		outerRefundQueryResponse.setReturnMsg(CommonCodeEnum.ORDER_NOT_EXIST.error());
	        	return outerRefundQueryResponse;
	    	}
	    	String customerNum = outerRefundQueryRequest.getCustomerNum();
	    	String orderNum=null;
	    	List<OuterRefundDetailResponse> detailList=new ArrayList<>();
	    	outerRefundQueryResponse.setRefundCount(refundList.size()+"");
	    	for (RefundEntity refundEntity : refundList) {
	    		OuterRefundDetailResponse detail=new OuterRefundDetailResponse();
	    		detail.setRefundNum(refundEntity.getRefundNum());
	    		detail.setRefundFee(refundEntity.getRefundFee().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				if(CommonConstant.ORDER_STATUS_PROCESSING.equals(refundEntity.getRefundStatus())){
					orderNum=refundEntity.getOrderNum();
					CustomerRelationEntity queryCustomerRelation = new CustomerRelationEntity();
		            String interfaceChannel = refundEntity.getRefundChannel();
		            queryCustomerRelation.setCustomerNum(customerNum);
		            queryCustomerRelation.setProductType(interfaceChannel);
		            List<CustomerRelationEntity> relationList = customerRelationBiz.getRelationsByCustomerRelation(queryCustomerRelation);
		            if (relationList.size() == 0) {
		                logger.error("未找到商户的映射关系， 商户号：{}", customerNum);
		                outerRefundQueryResponse.setReturnCode(CommonCodeEnum.UPPER_CUSTOMER_NOT_EXIST.val());
		        		outerRefundQueryResponse.setReturnMsg(CommonCodeEnum.UPPER_CUSTOMER_NOT_EXIST.error());
		                return outerRefundQueryResponse;
		            }
		            CustomerRelationEntity customerRelationEntity = relationList.get(0);
		            String merchantId=customerRelationEntity.getMchId();
		            String refundNo = refundEntity.getRefundNum();
		            String nonceStr = UUIDUtil.randomUUID();
		            String appId = upperCommonConfig.getAppId();//接入方标识
		            String key = upperCommonConfig.getUpperKey();
		            UpperRefundQueryRequest upperRefundQueryRequest=new UpperRefundQueryRequest(merchantId, appId, nonceStr, "", orderNum, refundNo, key);
		            UpperRefundQueryResponse upperRefundQueryResponse = upperPayBiz.doUpperRefundQuery(upperRefundQueryRequest);
		            if (upperRefundQueryResponse == null) {
		                logger.error("退款上游查询返回信息为空：{}", CommonCodeEnum.UPPER_QUERY_ERROR.val());
		                continue;
		            }
		            if(CommonConstant.SUCCESS.equals(upperRefundQueryResponse.getStatus())){
		            	 String refundStatus = upperRefundQueryResponse.getOrderRefundStatus();
		            	 if(refundStatus.equals(UpperCommonConstant.TRADE_SUCCESS)){
		            		 detail.setRefundStatus(CommonConstant.SUCCESS);
		            	 }else if(refundStatus.equals(UpperCommonConstant.TRADE_FAILED)){
		            		 detail.setRefundStatus(CommonConstant.FAIL);
		            	 }else{
		            		 detail.setRefundStatus(CommonConstant.ORDER_STATUS_PROCESSING);
		            	 }
		            }
				}else{
					detail.setRefundStatus(refundEntity.getRefundStatus());
				}
				detailList.add(detail);
			}
	    	outerRefundQueryResponse.setOrderNum(orderNum);
	    	outerRefundQueryResponse.setFee_type("CNY");
	    	outerRefundQueryResponse.setRefundOrders(JSONUtil.toJSONString(detailList));
	    	outerRefundQueryResponse.setNonceStr(UUIDUtil.randomUUID());
			return outerRefundQueryResponse;
			
	}
}
