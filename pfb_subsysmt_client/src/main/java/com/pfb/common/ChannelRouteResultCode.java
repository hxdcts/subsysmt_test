package com.pfb.common;

public enum ChannelRouteResultCode {
	
	/** 成功 */
	s_000000("000000", "操作成功"),
	
	/** 失败 */
	e_999999("999999", "操作失败"),
	
	/** 失败 */
	SIGN000001("SIGN000001", "校验签名失败"),
	
	/** 业务暂停**/
	e_888888("888888", "商户进件服务维护中...请联系客服人员！"),
	
	
	/** 自动路由 业务码 */
	CR400000("400000", "交易类型不能为空"),
	CR400001("400001", "商户编号不能为空"),
	CR400002("400002", "无可用通道"),
	CR400003("400003", "标示和产品编号不能同时为空");
	
	

	private final String val;
	private final String error;

	private ChannelRouteResultCode(String val, String error) {
		this.val = val;
		this.error = error;
	}

	public String val() {
		return val;
	}

	public String error() {
		return error;
	}

	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";
	public static final String OK = "OK";
	public static final String Y = "Y";
	public static final String N = "N";
}
