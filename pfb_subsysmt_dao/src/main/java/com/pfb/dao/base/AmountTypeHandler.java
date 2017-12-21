package com.pfb.dao.base;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.pfb.common.Amount;

/**
 * 自定义金额TypeHandler
 * @author 于增佳
 * @since 2013-6-17 上午10:35:05
 */
public class AmountTypeHandler implements TypeHandler<Object> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter,
			JdbcType jdbcType) throws SQLException {
		if (parameter != null) {
			Amount amount = (Amount) parameter;
			ps.setBigDecimal(i, amount.getValue());
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
		Amount amount = new Amount(number);
		return amount;
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
