package com.pfb.controller.mq;

import com.alibaba.fastjson.JSONObject;
import com.pfb.aliyunmq.consumer.AbsBaseConsumer;
import com.pfb.aliyunmq.vo.MessageBean;
import com.pfb.biz.common.MqTopicFac;
import com.pfb.biz.service.IOrderBiz;
import com.pfb.common.util.DateUtil;
import com.pfb.entity.OrderEntity;
import com.pfb.notice.vo.mt.MtTradePayNoticeVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class NotifyOrderMQ extends AbsBaseConsumer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IOrderBiz orderBiz;
    @Resource
    private MqTopicFac mqTopicFac;

    @Override
    public String getMsgTopic() {
        return mqTopicFac.getMtOrderNotify();
    }

    @Override
    public String getMsgTag() {
        return null;
    }

    @Override
    public void doConsume(MessageBean msg) {
        logger.info("订单状态消费异步mq------开始------");
        String orderStatus ="";
        OrderEntity order=null;
        try {
        	MtTradePayNoticeVo mtPayNoticeVo = (MtTradePayNoticeVo) msg.getObjVal();
            logger.info("订单状态消费  消息体：{}",JSONObject.toJSONString(mtPayNoticeVo));
            String outTradeNum = mtPayNoticeVo.getOutTradeNo();
            String channelNum = mtPayNoticeVo.getOutTradeNo();
            orderStatus = mtPayNoticeVo.getTradeStatus();
            String timeEnd = mtPayNoticeVo.getPayTime();
            String totalFee = mtPayNoticeVo.getTotalFee();
            String platRelationNo = mtPayNoticeVo.getProviderTradeNo();
            //查询订单
            order = orderBiz.getOrderByOrderNum(outTradeNum);
            String totalAmount = order.getTotalAmount().toString();
            if (!totalAmount.equals(totalFee)) {
                logger.info("金额不相等,orderNum:{}",order.getOrderNum());
            }
            order.setOrderStatus(orderStatus);
            order.setChannelOrderNum(channelNum);
            order.setPlatRelationNo(platRelationNo);
            Date completeTime = DateUtil.parseDate(timeEnd, DateUtil.DATE_STYLE_YYYYMMDDHHMMSS);
            order.setTimeEnd(completeTime);
            order.setModifyTime(new Date());
            //更新订单状态
            orderBiz.updateOrderFinalStatus(order);
        } catch (Exception e) {
            logger.info("订单状态消费异步通知mq出错：{}", e);
        }
        logger.info("订单状态消费异步通知mq------结束------");
    }


    @Override
    public boolean checkConsumed(MessageBean msg) {
        return false;
    }

    @Override
    public void writeWrongLog(MessageBean msg) {

    }

}
