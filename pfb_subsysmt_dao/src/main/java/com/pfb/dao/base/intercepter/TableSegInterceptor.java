package com.pfb.dao.base.intercepter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.type.SimpleTypeRegistry;

import com.pfb.entity.base.TableDevideAnnotation;
import com.pfb.common.util.DateUtil;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class TableSegInterceptor implements Interceptor {

	private static final String tag = TableSegInterceptor.class.getName();
	    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	    @Override
	    public Object intercept(Invocation invocation) throws Throwable {
	        StatementHandler statementHandler = (StatementHandler) invocation
	                .getTarget();
	        MetaObject metaStatementHandler = MetaObject.forObject(
	                statementHandler, DEFAULT_OBJECT_FACTORY,
	                DEFAULT_OBJECT_WRAPPER_FACTORY);
	        String originalSql = (String) metaStatementHandler
	                .getValue("delegate.boundSql.sql");
	        BoundSql boundSql = (BoundSql) metaStatementHandler
	                .getValue("delegate.boundSql");
	        //Configuration configuration = (Configuration) metaStatementHandler
	                //.getValue("delegate.configuration");
//	        Object parameterObject = metaStatementHandler
//	                .getValue("delegate.boundSql.parameterObject");
	        if (originalSql!=null&&!originalSql.equals("")) {
	            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler
	                    .getValue("delegate.mappedStatement");
	            String id = mappedStatement.getId();
	            String className = id.substring(0, id.lastIndexOf("."));
	            Class<?>  classObj = Class.forName(className);
	            //根据配置自动生成分表SQL
	            TableDevideAnnotation tableDevide = classObj.getAnnotation(TableDevideAnnotation.class);
	            if(tableDevide!=null){
	            	/**
	            	 * 根据哪列分表
	            	 */
	            	String sharedBy = tableDevide.sharedBy();
	            	if(null != sharedBy && !"".equals(sharedBy)){
	            		List<ParameterMapping> mappings = boundSql.getParameterMappings();
		            	boolean hasSharedColumn = false;
		            	if(mappings!=null&&mappings.size()>0){
		            		for(ParameterMapping pm : mappings){
		            			if(sharedBy.equals(pm.getProperty())){
		            				hasSharedColumn = true;
		            				break;
		            			}
		            		}
		            	}
		            	if(hasSharedColumn){
		            		Object parameter = boundSql.getParameterObject();
		            		Object val = null;
			            	if(!SimpleTypeRegistry.isSimpleType(parameter.getClass())){
			            		//对象类型，获取该属性值
			            		String sharedByProperties = "";
			            		String[] sharedStrArray = sharedBy.split("_");
			            		if(sharedStrArray.length == 1){
			            			sharedByProperties = sharedStrArray[0];
			            		}else{
			            			StringBuilder builder = new StringBuilder();
			            			for(int i=0;i<sharedStrArray.length;i++){
			            				if(i==0){
			            					builder.append(sharedStrArray[i]);
			            				}else{
			            					builder.append(sharedStrArray[i].substring(0, 1).toUpperCase() + sharedStrArray[i].substring(1));
			            				}
			            			}
			            			sharedByProperties = builder.toString();
			            		}
			            		String methodName = "get"+sharedByProperties.substring(0, 1).toUpperCase()+sharedByProperties.substring(1);
			            		Method sharedByMethod = classObj.getMethod(methodName);
			            		val = sharedByMethod.invoke(parameter);
			            	}else{
			            		val = parameter;
			            	}
			            	if(val != null){
			            		String tableName = tableDevide.tableName().toUpperCase();
			            		String newTableName = tableName;
				            	String sharedType = tableDevide.sharedType();
				            	String newSql = originalSql.toUpperCase();
				            	if("MOD".equals(sharedType)){
				            		int devided = tableDevide.devided();
				            		int m = Math.abs(val.hashCode()%devided);
				            		newTableName = newTableName+"_"+m;
				            	}else if("DATE".equals(sharedType)&&!SimpleTypeRegistry.isSimpleType(classObj)){
				            		String formatDate = DateUtil.format((Date)val,"yyyyMMdd");
				            		newTableName = newTableName+"_"+formatDate;
				            	}else{
				            		return invocation.proceed();
				            	}
				            	newSql = newSql.replace(tableName, newTableName);
				                if(newSql!=null){
//				                    LogUtil.d(tag,"分表后SQL =====>"+ newSql);
				                	System.out.println("分表后SQL =====>"+ newSql);
				                    metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
				                }
			            	}
		            	}
	            	}
	            }
	        }
	        // 传递给下一个拦截器处理
	        return invocation.proceed();
	    }
	 
	    
	    @Override
	    public Object plugin(Object target) {
	        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的
	        // 次数
	        if (target instanceof StatementHandler) {
	            return Plugin.wrap(target, this);
	        } else {
	            return target;
	        }
	    }
	 
	    @Override
	    public void setProperties(Properties properties) {
	        // TODO Auto-generated method stub
	 
	    }


}
