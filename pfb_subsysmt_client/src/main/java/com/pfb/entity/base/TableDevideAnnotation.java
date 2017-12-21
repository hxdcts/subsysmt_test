package com.pfb.entity.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)

public @interface TableDevideAnnotation {

	/**
	 * 表名
	 * TODO
	 * @author yu.zengjia@pufubao.net
	 * @since 2017 2017年1月3日 下午5:12:20
	 */
	public String tableName();
	
	/**
	 * 分表类型 MOD模 DATE日期 
	 * TODO
	 * @author yu.zengjia@pufubao.net
	 * @since 2017 2017年1月3日 下午5:08:45
	 */
	public String sharedType();
	
	public int devided() default 1;
	/**
	 * 根据哪个字段分表
	 * TODO
	 * @author yu.zengjia@pufubao.net
	 * @since 2017 2017年1月3日 下午5:08:52
	 */
	public String sharedBy();
}
