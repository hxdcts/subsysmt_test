package com.pfb.biz.upper.common;

/**
 * 上游公共常量
 */
public class UpperCommonConstant {

    /* ========================公共=============================== */
    /* 是 */
    public static final String Y = "Y";
    /* 否 */
    public static final String N = "N";
    /* 成功 */
    public static final String SUCCESS = "SUCCESS";
    public static final String USERPAYING  = "USERPAYING";
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


    public static final String CHANNELNAME = "BRCB";

    public static final String ORDER_TYPE_WECHAT = "WECHAT";
    public static final String ORDER_TYPE_ALIPAY = "ALIPAY";

    /* 接口类型： */
    public static final String INTERFACE_TYPE_SCAN = "SCAN";
    public static final String INTERFACE_TYPE_BARCODE = "BARCODE";
    public static final String INTERFACE_TYPE_ORDERQUERY = "ORDERQUERY";
    public static final String INTERFACE_TYPE_REFUNDQUERY = "REFUNDQUERY";
    public static final String INTERFACE_TYPE_T0 = "T0";
    public static final String INTERFACE_TYPE_T0QUERY = "T0QUERY";
    /* 接口类型：下载对账单 */
    public static final String INTERFACE_TYPE_DLORDERS = "DLORDERS";


    /* 接口通道：*/
    public static final String INTERFACE_CHANNEL_WECHAT = "WECHAT";
    public static final String INTERFACE_CHANNEL_ALIPAY = "ALIPAY";
    /**
     * 美团订单状态
     */
    /** * 交易新建*/
    public static final String ORDER_NEW = "ORDER_NEW";
    /** 交易失败 */
    public static final String ORDER_FAILED = "ORDER_FAILED";
    /**交易冲正中*/
    public static final String ORDER_REVERSALING = "ORDER_REVERSALING";
    /**交易冲正*/
    public static final String ORDER_REVERSAL = "ORDER_REVERSAL";
    /**交易成功*/
    public static final String ORDER_SUCCESS = "ORDER_SUCCESS";
    /**交易半退*/
    public static final String ORDER_PART_REFUND = "ORDER_PART_REFUND";
    /**交易全退*/
    public static final String ORDER_ALL_REFUND = "ORDER_ALL_REFUND";
    /**退款中*/
    public static final String ORDER_REFUNDING = "ORDER_REFUNDING";
    /**交易失败*/
    public static final String ORDER_CLOSE = "ORDER_CLOSE";
    /**新建退款*/
    public static final String TRADE_NEW = "TRADE_NEW";
    /**退款中*/
    public static final String TRADE_REFUNDING = "TRADE_REFUNDING";
    /**退款失败*/
    public static final String TRADE_FAILED = "TRADE_FAILED";
    /**退款成功*/
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
}
