package com.pfb.biz.upper.service;

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

/**
 * 支付业务入口
 *
 * @author zhanghui
 * @version 1.0
 * @date: 26/09/2017 17:37
 */
public interface IUpperPayBiz {


    /**
     * 条码业务
     *
     * @param abstractBase
     * @param logger
     * @return
     */
	UpperMicroPayResponse doUpperMicro(UpperMicroPayRequest upperMicroPayRequest);
   /**
    * 订单查询
   * @author: cts
   * @createTime: 2017年12月5日 下午5:30:59
   * @history:
   * @param upperOrderQueryRequest
   * @return UpperQueryResponse
    */
	UpperQueryResponse doUpperOrderQuery(UpperOrderQueryRequest upperOrderQueryRequest);
	/**
	 * 扫码向通知系统发通知
	* @author: cts
	* @createTime: 2017年12月5日 下午6:05:05
	* @history:
	* @param outerPayNoticeVo void
	 */
	void doSendToNoticeSys(OuterPayNoticeVo outerPayNoticeVo);
	/**
	 * 向通知系统发通知
	* @author: cts
	* @createTime: 2017年12月19日 上午10:32:16
	* @history:
	* @param upperRefundResponseMq void
	 */
	void doSendToNoticeSys(UpperRefundResponseMq upperRefundResponseMq);
    /**
     * 公众号上游请求支付
    * @author: cts
    * @createTime: 2017年12月13日 下午5:35:52
    * @history:
    * @param upperWebPayRequest
    * @return UpperWebPayResponse
     */
	UpperWebPayResponse doUpperWebPay(UpperWebPayRequest upperWebPayRequest);
	/**
     * 扫码业务
     *
     * @param abstractBase
     * @param logger
     * @return
     */
	UpperScanPayResponse doUpperScan(UpperScanPayRequest upperScanPayRequest);
	
	/**
	 * 退款
	* @author: cts
	* @createTime: 2017年12月18日 下午3:58:11
	* @history:
	* @param upperRefundRequest
	* @return UpperRefundResponse
	 */
    public UpperRefundResponse doUpperRefund(UpperRefundRequest upperRefundRequest);
    /**
     * 退款查询业务
    * @author: cts
    * @createTime: 2017年12月18日 下午3:59:48
    * @history:
    * @param upperRefundQueryRequest
    * @return UpperRefundQueryResponse
     */
    UpperRefundQueryResponse doUpperRefundQuery(UpperRefundQueryRequest upperRefundQueryRequest);
}
