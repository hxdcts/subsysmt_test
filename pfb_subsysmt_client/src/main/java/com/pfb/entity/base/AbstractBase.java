package com.pfb.entity.base;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 抽象基类
 * 
 * @author zhang.mingmeng@pufubao.net
 * @date 2017年9月26日 下午4:49:53
 * @version v1.0
 */
@SuppressWarnings("serial")
public class AbstractBase implements Serializable {

	/**
	 * 重写toString()方法
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
