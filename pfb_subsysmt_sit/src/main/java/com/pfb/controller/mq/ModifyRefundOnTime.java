package com.pfb.controller.mq;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pfb.biz.common.CommonConstant;
import com.pfb.biz.common.MqTopicFac;
import com.pfb.biz.service.ICustomerRelationBiz;
import com.pfb.biz.service.IOrderBiz;
import com.pfb.biz.upper.common.UpperCommonConfig;
import com.pfb.biz.upper.common.UpperCommonConstant;
import com.pfb.biz.upper.service.IUpperPayBiz;
import com.pfb.biz.upper.vo.pay.query.UpperOrderQueryRequest;
import com.pfb.biz.upper.vo.pay.query.UpperQueryResponse;
import com.pfb.biz.upper.vo.pay.refund.UpperRefundResponseMq;
import com.pfb.biz.upper.vo.pay.refundquery.UpperRefundDetailResponse;
import com.pfb.biz.upper.vo.pay.refundquery.UpperRefundQueryRequest;
import com.pfb.biz.upper.vo.pay.refundquery.UpperRefundQueryResponse;
import com.pfb.biz.utils.ConvertUtil;
import com.pfb.biz.utils.SignatureUtil;
import com.pfb.common.util.DateUtil;
import com.pfb.common.util.UUIDUtil;
import com.pfb.consumer.AbsBaseTaskImpl;
import com.pfb.entity.CustomerRelationEntity;
import com.pfb.entity.OrderEntity;
import com.pfb.entity.RefundEntity;
import com.pfb.enums.CommonCodeEnum;
import com.pfb.redis.service.RedisUtilsService;
import com.pfb.service.RefundServiceImpl;

import net.sf.json.JSONArray;

/**
 * 退款定时任务
* @desc: pfb_subsysmt_sit
* @author: cts
* @createTime: 2017年12月18日 下午6:40:54
* @history:
* @version: v1.0
 */
@Component
public class ModifyRefundOnTime extends AbsBaseTaskImpl{

	@Resource
	private RefundServiceImpl refundServiceImpl;
	@Resource
	private IOrderBiz orderBiz;
	@Resource
	private ICustomerRelationBiz customerRelationBiz;
	@Resource
	private IUpperPayBiz upperPayBiz;
	@Resource
	private RedisUtilsService redisUtilsService ;
	@Resource
    private MqTopicFac mqTopicFac;
	@Resource
    private UpperCommonConfig upperCommonConfig;
    
	private static final Logger logger = LoggerFactory.getLogger(ModifyRefundOnTime.class);
	@Override
	public void doTask() {
		logger.info("-----------美团定时查询退款补单任务执行------------------");
		List<RefundEntity> refundList = refundServiceImpl.queryUserRefundingOrders();
		if(null != refundList && refundList.size()>0){
			logger.info("3到6分钟内退款中待补的单有：{}",refundList.size());
			for(RefundEntity refund  : refundList){
				 	CustomerRelationEntity queryCustomerRelation = new CustomerRelationEntity();
		            String customerNum = refund.getCustomerNum();
		            String interfaceChannel = refund.getRefundChannel();
		            queryCustomerRelation.setCustomerNum(customerNum);
		            queryCustomerRelation.setProductType(interfaceChannel);
		            List<CustomerRelationEntity> relationList = customerRelationBiz.getRelationsByCustomerRelation(queryCustomerRelation);
		            if (relationList.size() == 0) {
		                logger.error("未找到商户的映射关系， 商户号：{}", customerNum);
		                return;
		            }
		            CustomerRelationEntity customerRelationEntity = relationList.get(0);

		            //step 4 拼接上游数据
		            String key = upperCommonConfig.getUpperKey();
		            String appId = upperCommonConfig.getAppId();//接入方标识
		            String refundNo = refund.getRefundNum();
		            String merchantId=customerRelationEntity.getMchId();
		            String nonceStr = UUIDUtil.randomUUID();
		            UpperRefundQueryRequest upperRefundQueryRequest=new UpperRefundQueryRequest(merchantId, appId, nonceStr, "", refund.getOrderNum(), refundNo, key);
		            UpperRefundQueryResponse upperRefundQueryResponse = upperPayBiz.doUpperRefundQuery(upperRefundQueryRequest);
		            if (upperRefundQueryResponse == null) {
		                logger.error("退款上游查询返回信息为空：{}", CommonCodeEnum.UPPER_QUERY_ERROR.val());
		                continue;
		            }
		            if(CommonConstant.SUCCESS.equals(upperRefundQueryResponse.getStatus())){
		            	boolean update=false;
		            	String returnCode = refund.getRefundStatus();
		            	String returnMsg = "",payStatus="";
		            	
		            	if(UpperCommonConstant.TRADE_SUCCESS.equals(upperRefundQueryResponse.getOrderRefundStatus())){
		            		refund.setRefundStatus(CommonConstant.SUCCESS);
		            		refund.setModifyTime(new Date());
		            		refund.setRefundEndTime(new Date());
		            		refundServiceImpl.update(refund);
		            		OrderEntity order=new OrderEntity();
		            		order.setOrderNum(refund.getRefundNum());
		            		order.setOrderStatus(CommonConstant.SUCCESS);
		            		order.setTimeEnd(new Date());
		            		orderBiz.updateOrder(order);
		            		returnCode = CommonConstant.SUCCESS;
		            		payStatus = CommonConstant.SUCCESS;
		            		returnMsg = upperRefundQueryResponse.getErrMsg();
		            		update=true;
		            	}else if(UpperCommonConstant.TRADE_FAILED.equals(upperRefundQueryResponse.getOrderRefundStatus())){
		            		refund.setRefundStatus(CommonConstant.ORDER_STATUS_FAIL);
		            		refund.setModifyTime(new Date());
		            		refundServiceImpl.update(refund);
		            		OrderEntity order=new OrderEntity();
		            		order.setOrderNum(refund.getRefundNum());
		            		order.setOrderStatus(CommonConstant.ORDER_STATUS_FAIL);
		            		orderBiz.updateOrder(order);
		            		returnCode = CommonConstant.SUCCESS;
		            		payStatus = CommonConstant.FAIL;
		            		returnMsg = upperRefundQueryResponse.getErrMsg();
		            		update=true;
		            	}
		            	if(update){
	                    	//发送信息到  通知系统
		            		UpperRefundResponseMq upperQueryResponse=new UpperRefundResponseMq();
		            		upperQueryResponse.setReturnCode(returnCode);
		            		upperQueryResponse.setReturnMsg(returnMsg);
		            		upperQueryResponse.setTradeType(CommonConstant.SERVICE_TYPE_REFUND);
		            		upperQueryResponse.setMerchantId(customerRelationEntity.getMchId());
		            		upperQueryResponse.setInterfaceChannel(interfaceChannel);
		            		upperQueryResponse.setOutTradeNo(refund.getRefundNum());
		            		upperQueryResponse.setOrderNo(refund.getOrderNum());
		            		upperQueryResponse.setTradeStatus(payStatus);
		            		upperQueryResponse.setPayTime(String.valueOf(new Date().getTime()));
		            		upperQueryResponse.setNonceStr(nonceStr);
		            		upperQueryResponse.setIsFrom("SUBSYS");
	                    	Map<String,Object> queryParam = ConvertUtil.objectToMap(upperQueryResponse, null);
	                    	String sign = SignatureUtil.getSign(queryParam, customerRelationEntity.getApiKey());
	                    	upperQueryResponse.setSign(sign);
	                    	upperPayBiz.doSendToNoticeSys(upperQueryResponse);
	                    }
		            }
			}
		}
	}

	@Override
	public String getTaskName() {
		return mqTopicFac.getMtRefundModifyOrderOntime();
	}
	@Override
	public boolean isNeedNotify() {
		return false;
	}

}
