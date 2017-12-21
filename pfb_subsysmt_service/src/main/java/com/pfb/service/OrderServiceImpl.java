package com.pfb.service;

import com.pfb.common.page.PageQueryInterface;
import com.pfb.common.page.PageQueryParam;
import com.pfb.dao.OrderDaoImpl;
import com.pfb.entity.OrderEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements PageQueryInterface<OrderEntity> {

    @Resource
    OrderDaoImpl orderDaoImpl;


    @Override
    public OrderEntity add(OrderEntity orderEntity) {
        return orderDaoImpl.add(orderEntity);
    }

    @Override
    public int delById(Long id) {
        return orderDaoImpl.delById(id);
    }

    @Override
    public OrderEntity getById(Long id) {
        return orderDaoImpl.getById(id);
    }

    @Override
    public int getRecordCount(PageQueryParam<OrderEntity> pageQueryParam) {
        return orderDaoImpl.getRecordCount(pageQueryParam);
    }

    @Override
    public List<OrderEntity> queryListByParam(OrderEntity queryParam) {
        return orderDaoImpl.queryListByParam(queryParam);
    }

    @Override
    public List<OrderEntity> queryListForPage(PageQueryParam<OrderEntity> pageQueryParam) {
        return orderDaoImpl.queryListForPage(pageQueryParam);
    }

    @Override
    public int update(OrderEntity orderEntity) {
        return orderDaoImpl.update(orderEntity);
    }

	public int updateFinalStatus(OrderEntity order) {
		// TODO Auto-generated method stub
		return orderDaoImpl.updateFinalStatus(order);
	}

	public List<OrderEntity> getUserPayingOrders() {
		// TODO Auto-generated method stub
		return orderDaoImpl.queryUserPayingOrders();
	}


}
