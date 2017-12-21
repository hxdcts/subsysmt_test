package com.pfb.dao.base.intercepter;

/**
 * DB2分页方言，主要是用于把分页的SQL拼接出来
 * 不使用IBATIS本身的分页，使用DB2的物理分页可以加快查询效率
 * 
 * 在XML中使用select * from XXX 的时候可能 会出现问题，尽量统一使用如下 方法来
 * select x.* from XXX x
 * 
 * 
 * @author 于增佳
 *
 */
public class MysqlDialect extends Dialect{

	@Override
	public boolean supportsLimit() {
		return true;
	}
	
	@Override
	public boolean supportsLimitOffset(){
		return true;
	}
	
	@SuppressWarnings("unused")
	private static String getRowNumber(String sql) {
		StringBuffer rownumber = new StringBuffer(50)
			.append("rownumber() over(");

		int orderByIndex = sql.toLowerCase().indexOf("order by");

		if ( orderByIndex>0 && !hasDistinct(sql) ) {
			rownumber.append( sql.substring(orderByIndex) );
		}

		rownumber.append(") as rownumber_,");

		return rownumber.toString();
	}
	
	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct")>=0;
	}

	@Override
	public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
		int startOfSelect = sql.toLowerCase().indexOf("select");
       String pagingSelects="";
		StringBuffer pagingSelect = new StringBuffer( sql.length()+100 );

		if ( hasDistinct(sql) ) {
			pagingSelect.append("select row_.* from ( ") 
				.append( sql.substring(startOfSelect) ) 
				.append(" ) as row_"); 
		}
		else {
			pagingSelect.append( sql.substring(startOfSelect) ); 
		}


		if (limit > 0) {
			pagingSelect.append(" limit "+offset+", "+limit);
		}
		
		pagingSelects=pagingSelect.toString();
//          if(pagingSelects.indexOf("with ur")!=-1){
//        	  pagingSelects= pagingSelects.replaceAll("with ur", " ");
//        	  pagingSelects=pagingSelects+" with ur";
//          }
		return pagingSelects;
	}
}