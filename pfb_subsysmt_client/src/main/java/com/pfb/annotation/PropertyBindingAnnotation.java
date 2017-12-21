package com.pfb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyBindingAnnotation {

	/**
	 * 映射对应的属性名称
	 * String
	 * @author:yu.zengjia@pufubao.net
	 * @since:Oct 25, 20173:47:54 PM
	 */
	public String propertyName() default "";
	
	public boolean autoWired() default false;
	/**
	 * 映射属性来源
	 * String
	 * @author:yu.zengjia@pufubao.net
	 * @since:Oct 25, 20174:10:08 PM
	 */
	public String[] propertyFrom() default {""};
	
	/**
	 * 映射属性目标
	 * String
	 * @author:yu.zengjia@pufubao.net
	 * @since:Oct 25, 20174:10:08 PM
	 */
	public String[] propertyTarget() default {""};
}
