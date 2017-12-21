package com.pfb.dao.base;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.pfb.common.Percent;

/**
 * 自定义百分数TypeHandler
 * @author 于增佳
 * @since 2013-6-17 上午9:41:59
 */
public class PercentTypeHandler implements TypeHandler<Object> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter,
			JdbcType jdbcType) throws SQLException {
		if (parameter != null) {
			Percent percent = (Percent) parameter;
			ps.setBigDecimal(i, percent.getValue());
		} else {
			ps.setNull(i, Types.DECIMAL);
		}
	}

	@Override
	public Object getResult(ResultSet rs, String columnName)
			throws SQLException {
		BigDecimal number = rs.getBigDecimal(columnName);
		if (number == null) {
			return null;
		}
		Percent percent = new Percent(number);
		return percent;
	}

	@Override
	public Object getResult(CallableStatement arg0, int arg1)
			throws SQLException {
		return null;
	}

	@Override
	public Object getResult(ResultSet arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
