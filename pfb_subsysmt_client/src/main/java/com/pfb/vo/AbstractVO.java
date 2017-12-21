package com.pfb.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @ClassName: AbstractVO
 * @Description: 抽象类-重写String方法
 * @author wang.shuran@pufubao.net
 * @version  1.0
 * @date 2016年6月29日 上午11:43:00
 */
@SuppressWarnings("serial")
public abstract class AbstractVO implements Serializable {

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
