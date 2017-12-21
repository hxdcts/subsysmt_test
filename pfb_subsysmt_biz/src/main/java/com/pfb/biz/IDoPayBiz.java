package com.pfb.biz;

import java.util.HashMap;

import com.pfb.biz.vo.outer.micro.OuterMicroRequest;
import com.pfb.biz.vo.outer.micro.OuterMicroResponse;
import com.pfb.biz.vo.outer.query.OuterOrderQueryRequest;
import com.pfb.biz.vo.outer.query.OuterOrderQueryResponse;
import com.pfb.biz.vo.outer.refund.OuterRefundRequest;
import com.pfb.biz.vo.outer.refund.OuterRefundResponse;
import com.pfb.biz.vo.outer.refundquery.OuterRefundQueryRequest;
import com.pfb.biz.vo.outer.refundquery.OuterRefundQueryResponse;
import com.pfb.biz.vo.outer.scan.OuterScanRequest;
import com.pfb.biz.vo.outer.scan.OuterScanResponse;
import com.pfb.biz.vo.outer.webpay.OuterWebpayRequest;
import com.pfb.biz.vo.outer.webpay.OuterWebpayResponse;

public interface IDoPayBiz {
	/**
     * 条码业务
     *
     * @param microRequest
     * @param logger
     * @return
     */
    public OuterMicroResponse doMicro(OuterMicroRequest microRequest);
    /**
     * 订单查询
    * @author: cts
    * @createTime: 2017年12月12日
    * @history:
    * @param orderQueryRequest
    * @return OuterOrderQueryResponse
     */
    public OuterOrderQueryResponse doOrderQuery(OuterOrderQueryRequest orderQueryRequest);
    
    /**
     * 扫码业务
     *
     * @param scanRequest
     * @param logger
     * @return
     */
    OuterScanResponse doScan(OuterScanRequest scanRequest);
    /**
     * 授权
    * @author: cts
    * @createTime: 2017年12月13日 下午3:17:41
    * @history:
    * @param webpayRequest
    * @return String
     */
    public HashMap<String, String> doWebpayAuth(OuterWebpayRequest webpayRequest);

    /**
     * 公众号业务
    * @author: cts
    * @createTime: 2017年12月13日 下午2:15:39
    * @history:
    * @param detailId 订单请求详细表ID
    * @param openId   
    * @return UpperWebPayResponse
     */
    public OuterWebpayResponse doWebpay(String detailId,String openId);
    /**
     * 退款
    * @author: cts
    * @createTime: 2017年12月18日 下午3:54:17
    * @history:
    * @param refundRequest
    * @return OuterRefundResponse
     */
    public OuterRefundResponse doRefund(OuterRefundRequest refundRequest);
    /**
     * 退款查询
    * @author: cts
    * @createTime: 2017年12月18日 下午3:54:58
    * @history:
    * @param outerRefundQueryRequest
    * @return OuterRefundQueryResponse
     */
    public OuterRefundQueryResponse doRefundQuery(OuterRefundQueryRequest outerRefundQueryRequest);
}
