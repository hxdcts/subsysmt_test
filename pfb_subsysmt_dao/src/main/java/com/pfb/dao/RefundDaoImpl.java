package com.pfb.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.pfb.dao.base.SimpleBaseDao;
import com.pfb.entity.RefundEntity;


@Repository
public class RefundDaoImpl extends SimpleBaseDao<RefundEntity> {

	public List<RefundEntity> queryUserRefundingOrders() {
		return this.getSqlQuerySession().selectList(RefundEntity.class.getName()+".queryUserRefundingOrders");
	}

}
