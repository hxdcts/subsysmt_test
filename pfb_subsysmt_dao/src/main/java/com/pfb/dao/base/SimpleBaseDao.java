package com.pfb.dao.base;

import java.lang.reflect.ParameterizedType;

public class SimpleBaseDao<T> extends BaseDao<T> {
	@SuppressWarnings("unchecked")
	public SimpleBaseDao(){
		@SuppressWarnings({ "rawtypes" })
		Class clazz = (Class<T>)(
                (ParameterizedType)getClass()    
                .getGenericSuperclass())        
                .getActualTypeArguments()[0];  
		this.setMapper(clazz);
	}
}
