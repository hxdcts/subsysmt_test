package com.pfb.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pfb.dao.base.SimpleBaseDao;
import com.pfb.entity.OrderEntity;


@Repository
public class OrderDaoImpl extends SimpleBaseDao<OrderEntity> {
	/**
	 * 订单状态更新（终态）
	 * int
	 * @author:yu.zengjia@pufubao.net
	 * @since:Nov 1, 20172:08:04 PM
	 */
	public int updateFinalStatus(OrderEntity order) {
		return this.getSqlSession().update(OrderEntity.class.getName()+".updateFinalStatus", order);
	}

	public List<OrderEntity> queryUserPayingOrders() {
		return this.getSqlQuerySession().selectList(OrderEntity.class.getName()+".queryUserPayingOrders");
	}

}
