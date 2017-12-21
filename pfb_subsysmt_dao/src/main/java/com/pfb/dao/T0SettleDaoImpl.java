package com.pfb.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.pfb.dao.base.SimpleBaseDao;
import com.pfb.entity.T0SettleEntity;


@Repository
public class T0SettleDaoImpl extends SimpleBaseDao<T0SettleEntity> {

	public List<T0SettleEntity> queryUserRefundingOrders() {
		return this.getSqlQuerySession().selectList(T0SettleEntity.class.getName()+".queryUserRefundingOrders");
	}

}
