package com.pfb.enums;

/**
 * 客户端回应码
 * 
 * @author wangsr
 *
 */
public enum CommonCodeEnum {

	/**
	 * 成功（000000）
	 */
	s_000000("000000", "操作成功"),

	/**
	 * 失败（999999）
	 */
	e_999999("999999", "操作失败"),
	FAIL("999999", "操作失败"),
	/**
	 * 参数不能为空
	 */
	PARAM_REQUIRED("400102","参数不能为空"),
	/**
	 * 格式错误
	 */
	FORMAT_ERROR("99991001","格式错误"),
	/**
	 * 缺少参数
	 */
	PARAME_REQUIRED("99991002","缺少参数"),
	/**
	 * 签名错误
	 */
	SIGN_ERROR("99991003", "签名错误"),
	/**
	 * 参数转换错误
	 */
	PARAM_TRANS_ERROR("99991004","参数转换错误"),
	/**
	 * 获取报文体长度出错（e_100001）
	 */
	e_000001("GATEPRE_000001", "获取报文体长度出错"),
	/**
	 * 商户报备失败
	 */
	UPPER_CUSTOMER_FAIL("400105","商户报备失败"),
	/**
	 * 商户报备信息不存在
	 */
	UPPER_CUSTOMER_NOT_EXIST("400105","商户报备信息不存在"),
	/**
	 * 订单信息不存在
	 */
	ORDER_NOT_EXIST("400106","订单信息不存在"),
	UPPER_QUERY_ERROR("400107","查询失败");
	

	private final String val;
	private final String error;

	private CommonCodeEnum(String val, String error) {
		this.val = val;
		this.error = error;
	}

	public String val() {
		return val;
	}

	public String error() {
		return error;
	}

}
