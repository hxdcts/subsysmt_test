package com.pfb.biz.upper.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pfb.biz.common.HttpClientUtil;
import com.pfb.biz.upper.common.UpperCommonConfig;
import com.pfb.biz.upper.common.UpperCommonConstant;
import com.pfb.biz.upper.service.IUpperPayBiz;
import com.pfb.biz.upper.vo.pay.micro.UpperMicroPayRequest;
import com.pfb.biz.upper.vo.pay.micro.UpperMicroPayResponse;
import com.pfb.biz.upper.vo.pay.query.UpperOrderQueryRequest;
import com.pfb.biz.upper.vo.pay.query.UpperQueryResponse;
import com.pfb.biz.upper.vo.pay.refund.UpperRefundRequest;
import com.pfb.biz.upper.vo.pay.refund.UpperRefundResponse;
import com.pfb.biz.upper.vo.pay.refund.UpperRefundResponseMq;
import com.pfb.biz.upper.vo.pay.refundquery.UpperRefundQueryRequest;
import com.pfb.biz.upper.vo.pay.refundquery.UpperRefundQueryResponse;
import com.pfb.biz.upper.vo.pay.scan.UpperScanPayRequest;
import com.pfb.biz.upper.vo.pay.scan.UpperScanPayResponse;
import com.pfb.biz.upper.vo.pay.webpay.UpperWebPayRequest;
import com.pfb.biz.upper.vo.pay.webpay.UpperWebPayResponse;
import com.pfb.biz.vo.outer.query.OuterPayNoticeVo;
import com.pfb.common.util.HttpclientMultiThreadJSONUtil;
import com.pfb.common.util.JSONUtil;
import com.pfb.service.OrderDetailServiceImpl;
import com.pfb.service.OrderServiceImpl;
@Service
public class UpperPayBizImpl implements IUpperPayBiz{
	private static final Logger logger = LoggerFactory.getLogger(UpperPayBizImpl.class);
    @Resource
    OrderServiceImpl orderServiceImpl;
    @Resource
    OrderDetailServiceImpl orderDetailServiceImpl;
    @Resource
    private UpperCommonConfig upperCommonConfig;
	@Override
	public UpperMicroPayResponse doUpperMicro(UpperMicroPayRequest upperMicroPayRequest) {
		String url = upperCommonConfig.getMicroPayUrl();
        String params = JSONUtil.toJSONString(upperMicroPayRequest);
        logger.info("刷卡支付请求上游刷卡数据 {}", params);
        HttpClientUtil httpclient = new HttpClientUtil(url);
        String respStr = httpclient.sendJsonPost(params, null);
        UpperMicroPayResponse upperMicroPayResponse = new UpperMicroPayResponse();
        try {
        	upperMicroPayResponse = JSONUtil.parseObject(respStr, UpperMicroPayResponse.class);
		} catch (Exception e) {
			logger.error("刷卡支付转换上游参数出错 {}",e.getMessage());
			upperMicroPayResponse.setStatus(UpperCommonConstant.FAIL);
		}
        logger.info("刷卡支付上游返回刷卡 数据 {}", JSONUtil.toJSONString(upperMicroPayResponse));
        return upperMicroPayResponse;
	}

	@Override
	public UpperQueryResponse doUpperOrderQuery(UpperOrderQueryRequest upperOrderQueryRequest) {
		String url = upperCommonConfig.getOrderQueryUrl();
        String params = JSONUtil.toJSONString(upperOrderQueryRequest);
        logger.info("订单查询请求上游数据 {}", params);
        HttpClientUtil httpclient = new HttpClientUtil(url);
        String respStr = httpclient.sendJsonPost(params,null);
        UpperQueryResponse upperQueryResponse = JSONUtil.parseObject(respStr, UpperQueryResponse.class);
        logger.info("订单查询上游返回数据 {}", respStr);
        return upperQueryResponse;
			
	}
	@Override
	public void doSendToNoticeSys(OuterPayNoticeVo outerPayNoticeVo) {
        String params = JSONUtil.toJSONString(outerPayNoticeVo);
        logger.info("发送到通知信息 请求数据 {}", params);
        String url=upperCommonConfig.getMicroNotifyUrl();
        HttpclientMultiThreadJSONUtil httpclient = new HttpclientMultiThreadJSONUtil(url);
        String respStr = httpclient.sendJsonPost(params,null);
        logger.info("发送到通知信息 返回数据 {}", respStr);
	}
	@Override
	public void doSendToNoticeSys(UpperRefundResponseMq upperRefundResponseMq) {
        String params = JSONUtil.toJSONString(upperRefundResponseMq);
        logger.info("发送到通知信息 请求数据 {}", params);
        String url=upperCommonConfig.getNotifyUrl();
        HttpclientMultiThreadJSONUtil httpclient = new HttpclientMultiThreadJSONUtil(url);
        String respStr = httpclient.sendJsonPost(params,null);
        logger.info("发送到通知信息 返回数据 {}", respStr);
	}
	public UpperWebPayResponse doUpperWebPay(UpperWebPayRequest upperWebPayRequest){
		String params = JSONUtil.toJSONString(upperWebPayRequest);
		String url = upperCommonConfig.getPreCreateUrl();
        logger.info("公众号支付请求上游数据 {}", params);
        HttpClientUtil httpclient = new HttpClientUtil(url);
        String respStr = httpclient.sendJsonPost(params,null);
        UpperWebPayResponse upperWebPayResponse = new UpperWebPayResponse();
        try {
        	upperWebPayResponse = JSONUtil.parseObject(respStr, UpperWebPayResponse.class);
		} catch (Exception e) {
			logger.error("公众号支付转换上游参数出错 {}",e.getMessage());
			upperWebPayResponse.setStatus(UpperCommonConstant.FAIL);
		}
        logger.info("公众号支付上游返回数据 {}", respStr);
        return upperWebPayResponse;
	}

	@Override
    public UpperScanPayResponse doUpperScan(UpperScanPayRequest upperScanPayRequest) {
    	String url = upperCommonConfig.getPreCreateUrl();
    	 String params = JSONUtil.toJSONString(upperScanPayRequest);
         logger.info("扫码支付请求上游数据 {}", params);
         HttpClientUtil httpclient = new HttpClientUtil(url);
         String respStr = httpclient.sendJsonPost(params, null);
        UpperScanPayResponse upperScanPayResponse = JSONUtil.parseObject(respStr, UpperScanPayResponse.class);
        logger.info("上游返回主扫 数据 {}", JSONUtil.toJSONString(upperScanPayResponse));
        return upperScanPayResponse;
    }
	@Override
    public UpperRefundResponse doUpperRefund(UpperRefundRequest upperRefundRequest) {
    	
    	String url = upperCommonConfig.getRefundUrl();
        String params = JSONUtil.toJSONString(upperRefundRequest);
        logger.info("订单退款请求上游数据 {}", params);
        HttpClientUtil httpclient = new HttpClientUtil(url);
        String respStr = httpclient.sendJsonPost(params,null);
        UpperRefundResponse upperRefundResponse = JSONUtil.parseObject(respStr, UpperRefundResponse.class);
        logger.info("订单退款上游返回数据 {}", respStr);
    	return upperRefundResponse;
    }

	@Override
	public UpperRefundQueryResponse doUpperRefundQuery(UpperRefundQueryRequest upperRefundQueryRequest) {
		String url = upperCommonConfig.getRefundQueryUrl();
        String params = JSONUtil.toJSONString(upperRefundQueryRequest);
        logger.info("订单退款请求上游数据 {}", params);
        HttpClientUtil httpclient = new HttpClientUtil(url);
        String respStr = httpclient.sendJsonPost(params,null);
        UpperRefundQueryResponse upperRefundQueryResponse = JSONUtil.parseObject(respStr, UpperRefundQueryResponse.class);
        logger.info("订单退款上游返回数据 {}", respStr);
    	return upperRefundQueryResponse;
			
	}
}

	