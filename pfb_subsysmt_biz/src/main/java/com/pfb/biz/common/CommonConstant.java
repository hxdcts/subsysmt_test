package com.pfb.biz.common;

/**
 * 公共常量
 *
 * @author zhang.hui@pufubao.net
 * @version v1.0
 * @date 2016年10月13日 下午3:49:00
 */
public class CommonConstant {

    /* ========================公共=============================== */
    /* 是 */
    public static final String Y = "Y";
    /* 否 */
    public static final String N = "N";
    /* 成功 */
    public static final String SUCCESS = "SUCCESS";
    /* 失败 */
    public static final String FAIL = "FAIL";
    /* true */
    public static final boolean TRUE = true;
    /* false */
    public static final boolean FALSE = false;
    /* 1 */
    public static final int ALLOW = 1;
    /* 0 */
    public static final int NOT_ALLOW = 0;
    /* 1 */
    public static final int DELETED = 1;
    /* 0 */
    public static final int UNDELETED = 0;
    
    public static final String ORDER_TYPE_WECHAT = "WECHAT";
    public static final String ORDER_TYPE_ALIPAY = "ALIPAY";

    public static final String MQ_MAG_TOPIC_CHECK_ORDER_FILE = "CHECK_ORDER_FILE";

    /** 订单状态：初始化 */
	public static final String ORDER_STATUS_INIT = "INIT";
	/** 订单状态：成功 */
	public static final String ORDER_STATUS_SUCCESS = "SUCCESS";
	/** 订单状态：失败 */
	public static final String ORDER_STATUS_FAIL = "FAIL";
	/** 订单状态：PROCESSING—退款处理中 */
	public static final String ORDER_STATUS_PROCESSING = "PROCESSING";
	/** 订单状态：REFUND—转入退款 */
	public static final String ORDER_STATUS_NOTPAY = "NOTPAY";
	/** 订单状态：CLOSED—已关闭 */
	public static final String ORDER_STATUS_CLOSED = "CLOSED";
	/** 订单状态：USERPAYING--用户支付中 */
	public static final String ORDER_STATUS_USERPAYING = "USERPAYING";
	/** 订单状态：PAYERROR--支付失败 */
	public static final String ORDER_STATUS_PAYERROR = "PAYERROR";
	
	/** 服务类型-扫码 */
	public static final String SERVICE_TYPE_SCANNED = "SCANNED";
	/** 服务类型-刷卡(小额) */
	public static final String SERVICE_TYPE_MICRO = "MICRO";
	/** 服务类型-公众号支付 */
	public static final String SERVICE_TYPE_WEBPAY = "WEBPAY";
	/** 服务类型-APP支付 */
	public static final String SERVICE_TYPE_APP = "APP";
	/** 服务类型-订单查询 */
	public static final String SERVICE_TYPE_ORDERQUERY = "ORDERQUERY";
	/** 服务类型-关闭订单 */
	public static final String SERVICE_TYPE_CLOSEORDER = "CLOSEORDER";
	/** 服务类型-撤销 */
	public static final String SERVICE_TYPE_REVERSE = "REVERSEORDER";
	/** 服务类型-退款 */
	public static final String SERVICE_TYPE_REFUND = "REFUND";
	/** 服务类型-退款查询 */
	public static final String SERVICE_TYPE_REFUNDQUERY = "REFUNDQUERY";
	/** 服务类型-下载对账单 */
	public static final String SERVICE_TYPE_DOWNLOADBILL = "DOWNLOADBILL";
	
	/** 订单交易类型 PAY:支付*/
	public static final String ORDER_TRADE_TYPE_PAY = "PAY";
	/** 订单交易类型 REVOKED:撤销*/
	public static final String ORDER_TRADE_TYPE_REVOKED = "REVOKED";
	/** 订单交易类型 REFUND:退款*/
	public static final String ORDER_TRADE_TYPE_REFUND = "REFUND";
	
	/** 入件状态 WAIT: 入件中*/
	public static final String CUSTOMER_ENTRY_STATUS_WAIT = "WAIT";
	/** 入件状态 ALREADY: 已经入件*/
	public static final String CUSTOMER_ENTRY_STATUS_ALREADY = "ALREADY";
	/** 入件状态REJECT: 入件失败*/
	public static final String ORDER_TRADE_TYPE_REJECT = "REJECT";
	
	
	/** 订单交易标记 NORMAL:正常*/
	public static final String ORDER_FLAG_NORMAL = "NORMAL";
	/** 订单交易标记 CLOSE:关闭*/
	public static final String ORDER_FLAG_CLOSED = "CLOSED";
	/** 订单交易标记 REVOKED:已撤销*/
	public static final String ORDER_FLAG_REVOKED = "REVOKED";
	/** 订单交易标记 REFUND:已退款*/
	public static final String ORDER_FLAG_REFUND = "REFUND";
	/** 订单交易标记 REFUNDFAIL:退款失败*/
	public static final String ORDER_FLAG_REFUNDFAIL = "REFUNDFAIL";
	/** 订单交易标记 REFUNDFAIL:撤销失败*/
	public static final String ORDER_FLAG_REVERSFAIL = "REVERSFAIL";
	/** 刷卡交易订单检查查询 **/
	public static final String MT_MICRO_ORDER_CHECK = "MT_MICRO_ORDER_CHECK";
	/** 待检查订单前缀+单号 **/
	public static final String MT_CACHE_CHECK_ORDER_PHIX = "MT_CHECK_";
	/**
	 * 通知系统URL地址
	 */
	public static final String NOTICE_SYS_SIT_URL = "http://notify.pay.pufubao.net/notify/brcb/pay";
	
	/** 订单结算模式 T0*/
	public static final String ORDER_SETTLE_MODE_T0 = "T0";
	/** 订单结算模式 T1*/
	public static final String ORDER_SETTLE_MODE_T1 = "T1";
	

}
