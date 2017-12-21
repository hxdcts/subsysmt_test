package com.pfb.dao.utils;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.stereotype.Repository;

import com.pfb.dao.base.SimpleBaseDao;

@Repository
public class CommonDaoImpl extends SimpleBaseDao<T> {

	/**
	 * 插入数据
	 * 
	 * @param paras
	 * @return
	 * @author wangsr
	 * @date 2016年6月2日 上午10:18:01
	 */
	public int insertTable(String paras) {
		return this.getSqlQuerySession().insert("CommonEntity.insertRecord",
				paras);
	}

	/**
	 * 更新表数据
	 * 
	 * @param paras
	 * @return
	 * @author wangsr
	 * @date 2016年6月2日 上午10:17:45
	 */
	public int updateTable(String paras) {
		return this.getSqlQuerySession().update("CommonEntity.updateRecord",
				paras);
	}

	/**
	 * 删除表数据
	 * 
	 * @param paras
	 * @return
	 * @author wangsr
	 * @date 2016年6月2日 上午10:17:45
	 */
	public int deleteTable(String paras) {
		return this.getSqlQuerySession().delete("CommonEntity.deleteRecord",
				paras);
	}
}
