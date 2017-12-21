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
import com.pfb.biz.common.MtOrderUtil;
import com.pfb.biz.common.SHA256Utils;
import com.pfb.biz.service.ICustomerRelationBiz;
import com.pfb.biz.service.IOrderBiz;
import com.pfb.biz.upper.common.UpperCommonConfig;
import com.pfb.biz.upper.common.UpperCommonConstant;
import com.pfb.biz.upper.service.IUpperPayBiz;
import com.pfb.biz.upper.vo.pay.query.UpperOrderQueryRequest;
import com.pfb.biz.upper.vo.pay.query.UpperQueryResponse;
import com.pfb.biz.utils.BRCBUtil;
import com.pfb.biz.utils.ConvertUtil;
import com.pfb.biz.utils.SignatureUtil;
import com.pfb.biz.vo.outer.query.OuterPayNoticeVo;
import com.pfb.common.util.DateUtil;
import com.pfb.common.util.UUIDUtil;
import com.pfb.consumer.AbsBaseTaskImpl;
import com.pfb.entity.CustomerRelationEntity;
import com.pfb.entity.OrderEntity;
import com.pfb.enums.CommonCodeEnum;
import com.pfb.redis.service.RedisUtilsService;

/**
 * 
* @desc: 定时补单功能：查询前 2分钟至15分别
* @author: cts
* @createTime: 2017年12月7日 下午2:07:25
* @history:
* @version: v1.0
 */
@Component
public class ModifyOrderOnTime extends AbsBaseTaskImpl{

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
	private static final Logger logger = LoggerFactory.getLogger(ModifyOrderOnTime.class);
	@Override
	public void doTask() {
		logger.info("-----------定时查询补单任务执行------------------");
		List<OrderEntity> payingOrders = orderBiz.getUserPayingOrders();
		if(null != payingOrders && payingOrders.size()>0){
			logger.info("3到6分钟内支付中待补的单有：{}",payingOrders.size());
			for(OrderEntity order : payingOrders){
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
		            String interfaceType = UpperCommonConstant.INTERFACE_TYPE_ORDERQUERY;
		            String interfaceChannel = orderType;
		            String merchantId = customerRelationEntity.getMchId();
		            String outTradeNo = order.getOrderNum();
		            String orderNo = order.getChannelOrderNum();
		            String nonceStr = UUIDUtil.randomUUID();
		            String key = upperCommonConfig.getUpperKey();
		            String appId = upperCommonConfig.getAppId();//接入方标识
		            UpperOrderQueryRequest upperOrderQueryRequest = new UpperOrderQueryRequest(outTradeNo, merchantId, appId, nonceStr, "", key);
		            
		            UpperQueryResponse upperQueryResponse = upperPayBiz.doUpperOrderQuery(upperOrderQueryRequest);
		            if (upperQueryResponse == null) {
		                logger.error("补单上游查询返回信息为空：{}", CommonCodeEnum.UPPER_QUERY_ERROR.val());
		                continue;
		            }
		            if(UpperCommonConstant.SUCCESS.equals(upperQueryResponse.getStatus())){
		            	//step 5 转义下游数据
//		                String returnCode = upperQueryResponse.getReturnCode();
//		                String returnMsg = upperQueryResponse.getReturnMsg();
		                if (UpperCommonConstant.ORDER_FAILED.equals(upperQueryResponse.getOrderStatus()) || null == upperQueryResponse.getOrderStatus()) {
		                	continue;
		                }
		                //更新订单状态
		                String orderStatus = (!UpperCommonConstant.ORDER_SUCCESS.equals(upperQueryResponse.getOrderStatus())&&!UpperCommonConstant.ORDER_NEW.equals(upperQueryResponse.getOrderStatus()))?UpperCommonConstant.FAIL:upperQueryResponse.getOrderStatus();
		                orderStatus = MtOrderUtil.getOrderStatus(orderStatus);
		                if(UpperCommonConstant.SUCCESS.equals(orderStatus) || UpperCommonConstant.FAIL.equals(orderStatus)){
		                	order.setOrderStatus(orderStatus);
		                	order.setChannelOrderNum(upperQueryResponse.getOutTradeNo());
		                    order.setPlatRelationNo(upperQueryResponse.getTradeNo());
		                    Date completeTime = DateUtil.parseDate(upperQueryResponse.getPayTime(), DateUtil.DATE_STYLE_YYYYMMDDHHMMSS);
		                    order.setTimeEnd(completeTime);
		                    order.setModifyTime(new Date());
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
		                    	upperQueryResponse.setSign(sign);
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
			}
		}
	}

	@Override
	public String getTaskName() {
		// TODO Auto-generated method stub
		return mqTopicFac.getMtTradeModifyOrderOntime();
	}

	@Override
	public boolean isNeedNotify() {
		// TODO Auto-generated method stub
		return false;
	}
}
