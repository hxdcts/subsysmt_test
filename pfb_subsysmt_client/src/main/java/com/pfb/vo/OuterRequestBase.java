package com.pfb.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 下游上送参数基类
 * 
 * @author zhanghaixia
 */
@SuppressWarnings("serial")
public class OuterRequestBase extends AbstractVO {

	/**
	 * 重写toString()方法
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
