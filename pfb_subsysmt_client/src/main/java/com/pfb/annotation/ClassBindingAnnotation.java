package com.pfb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassBindingAnnotation {

	/**
	 * 映射的实体类名
	 * String
	 * @author:yu.zengjia@pufubao.net
	 * @since:Oct 25, 20173:45:46 PM
	 */
	public String className() default "com.pfb.notice.vo.trade.TradePayNoticeVo";
}
