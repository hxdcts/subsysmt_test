package com.pfb.controller.mq;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pfb.aliyunmq.consumer.AbsBaseConsumer;
import com.pfb.aliyunmq.producer.MQProducerImpl;
import com.pfb.aliyunmq.vo.MessageBean;
import com.pfb.biz.common.CommonConstant;
import com.pfb.biz.common.MqTopicFac;
import com.pfb.biz.common.MtOrderUtil;
import com.pfb.biz.common.SHA256Utils;
import com.pfb.biz.service.ICustomerRelationBiz;
import com.pfb.biz.service.IOrderBiz;
import com.pfb.biz.upper.common.UpperCommonConfig;
import com.pfb.biz.upper.common.UpperCommonConstant;
import com.pfb.biz.upper.service.IUpperPayBiz;
import com.pfb.biz.upper.vo.pay.query.UpperOrderQueryRequest;
import com.pfb.biz.upper.vo.pay.query.UpperQueryResponse;
import com.pfb.biz.utils.ConvertUtil;
import com.pfb.biz.vo.outer.query.OuterPayNoticeVo;
import com.pfb.common.util.JSONUtil;
import com.pfb.common.util.UUIDUtil;
import com.pfb.entity.CustomerRelationEntity;
import com.pfb.entity.OrderEntity;
import com.pfb.enums.CommonCodeEnum;
import com.pfb.redis.service.RedisUtilsService;

@Component
public class MicroOrderCheckMQ extends AbsBaseConsumer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private MQProducerImpl mqProducerImpl;
    @Resource
    private IOrderBiz orderBiz;
    @Resource
    private ICustomerRelationBiz customerRelationBiz;
    @Resource
    private IUpperPayBiz upperPayBiz;
    @Resource
    private RedisUtilsService redisUtilsService;
    @Resource
    private MqTopicFac mqTopicFac;
    @Resource
    private UpperCommonConfig upperCommonConfig;
    
    @Override
    public String getMsgTopic() {
    	return mqTopicFac.getMtMicroOrderCheck();
    }

    @Override
    public String getMsgTag() {
        return null;
    }

    @Override
    public void doConsume(MessageBean msg) {
        logger.info("Micro订单状态消费异步mq------开始------");
        try {
            OrderEntity checkOrder = (OrderEntity) msg.getObjVal();
            
            //查询订单
            OrderEntity order = orderBiz.getOrderByOrderNum(checkOrder.getOrderNum());
            logger.info("Micro 刷卡order {}",JSONUtil.toJSONString(order));
            if(!CommonConstant.ORDER_STATUS_USERPAYING.equals(order.getOrderStatus())){
            	return;
            }
          //step 3 查询商户映射关系
            CustomerRelationEntity queryCustomerRelation = new CustomerRelationEntity();
            String customerNum = order.getCustomerNum();
            String orderType = order.getOrderType();
            queryCustomerRelation.setCustomerNum(customerNum);
            queryCustomerRelation.setProductType(orderType);
            List<CustomerRelationEntity> relationList = customerRelationBiz.getRelationsByCustomerRelation(queryCustomerRelation);
            if (relationList.size() == 0) {
                logger.error("未找到商户的映射关系， 商户号：{}", customerNum);
                return;
            }
            CustomerRelationEntity customerRelationEntity = relationList.get(0);

            //step 4 拼接上游数据
            String key = upperCommonConfig.getUpperKey();
            String merchantId = customerRelationEntity.getMchId();
            String appId = upperCommonConfig.getAppId();//接入方标识
            String outTradeNo = order.getOrderNum();
            String nonceStr = UUIDUtil.randomUUID();
            UpperOrderQueryRequest upperOrderQueryRequest = new UpperOrderQueryRequest(outTradeNo, merchantId, appId, nonceStr, "", key);
            UpperQueryResponse upperQueryResponse = upperPayBiz.doUpperOrderQuery(upperOrderQueryRequest);
            if (upperQueryResponse == null) {
                logger.error("上游查询返回信息为空：{}", CommonCodeEnum.UPPER_QUERY_ERROR.val());
                return;
            }
            if(UpperCommonConstant.SUCCESS.equals(upperQueryResponse.getStatus())){
            	//step 5 转义下游数据
//                String returnCode = upperQueryResponse.getReturnCode();
//                String returnMsg = upperQueryResponse.getReturnMsg();
                if (UpperCommonConstant.ORDER_NEW.equals(upperQueryResponse.getOrderStatus())) {
//                	if(redisUtilsService.isExists(CommonConstant.MT_CACHE_CHECK_ORDER_PHIX+order.getOrderNum())){
                		logger.info("-----------休眠3秒--------------");
                		Thread.sleep(3000);
                		MessageBean message = new MessageBean();
                    	message.setMsgTopic(mqTopicFac.getMtMicroOrderCheck());
                    	message.setObjVal(order);
                    	mqProducerImpl.sendMsg(message);
//                	}
                	return;
                }
                //更新订单状态
                String orderStatus = (!UpperCommonConstant.ORDER_SUCCESS.equals(upperQueryResponse.getOrderStatus())&&!UpperCommonConstant.ORDER_NEW.equals(upperQueryResponse.getOrderStatus()))?UpperCommonConstant.FAIL:upperQueryResponse.getOrderStatus();
                orderStatus = MtOrderUtil.getOrderStatus(orderStatus);
                if(UpperCommonConstant.SUCCESS.equals(orderStatus) || UpperCommonConstant.FAIL.equals(orderStatus)){
                	order.setOrderStatus(orderStatus);
                    boolean updated = orderBiz.updateOrderFinalStatus(order);
                    if(updated){
                    	OuterPayNoticeVo outerPayNoticeVo = new OuterPayNoticeVo();
                    	outerPayNoticeVo.setReturnCode(upperQueryResponse.getErrCode());
                    	outerPayNoticeVo.setReturnMsg(upperQueryResponse.getErrMsg());
                    	outerPayNoticeVo.setTotalFee(upperQueryResponse.getTotalFee());
                    	outerPayNoticeVo.setOrderNo(order.getOrderNum());
                    	outerPayNoticeVo.setInterfaceType(order.getServiceType());
                    	outerPayNoticeVo.setTradeType(order.getTradeType());
                    	outerPayNoticeVo.setMerchantId(order.getCustomerNum());
                    	outerPayNoticeVo.setOutTradeNo(order.getOrderNum());
                    	outerPayNoticeVo.setUpperNo(upperQueryResponse.getOutTradeNo());
                    	outerPayNoticeVo.setCompleteTime(upperQueryResponse.getPayTime());
                    	String payStatus=upperQueryResponse.getOrderStatus();
                    	if(UpperCommonConstant.ORDER_SUCCESS.equals(payStatus)){
                    		outerPayNoticeVo.setPayStatus(CommonConstant.SUCCESS);
                    	}else if(UpperCommonConstant.ORDER_NEW.equals(payStatus)){
                    		outerPayNoticeVo.setPayStatus(CommonConstant.ORDER_STATUS_USERPAYING);
                    	}else{
                    		outerPayNoticeVo.setPayStatus(CommonConstant.FAIL);
                    	}
                    	//发送信息到  通知系统
                    	 Map<String,Object> queryParam = ConvertUtil.objectToMap(upperQueryResponse, null);
                    	String sign = SHA256Utils.getSHA256Str(queryParam, key);
                    	outerPayNoticeVo.setSign(sign);
                    	upperPayBiz.doSendToNoticeSys(outerPayNoticeVo);
                    	try {
                    		if(redisUtilsService.isExists(CommonConstant.MT_CACHE_CHECK_ORDER_PHIX+order.getOrderNum())){
                    			redisUtilsService.delete(CommonConstant.MT_CACHE_CHECK_ORDER_PHIX+order.getOrderNum());
                    		}
        				} catch (Exception e) {
        					logger.debug("micro redis error {}",e);
        				}
                    	
                    }
                }
            }
            
        } catch (Exception e) {
            logger.info("订单状态消费异步通知mq出错：{}", e);
        }

        logger.info("Micro订单状态消费异步通知mq------结束------");
    }


    @Override
    public boolean checkConsumed(MessageBean msg) {
        return false;
    }

    @Override
    public void writeWrongLog(MessageBean msg) {

    }

}
