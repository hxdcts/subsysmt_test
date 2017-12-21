package com.pfb.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pfb.common.page.PageQueryInterface;
import com.pfb.common.page.PageQueryParam;
import com.pfb.dao.OrderDetailDaoImpl;
import com.pfb.entity.OrderDetailEntity;

@Service
public class OrderDetailServiceImpl implements PageQueryInterface<OrderDetailEntity> {

	@Resource
	OrderDetailDaoImpl orderDetailDaoImpl;
	
	
	@Override
	public OrderDetailEntity add(OrderDetailEntity arg0) {
		return orderDetailDaoImpl.add(arg0);
	}

	@Override
	public int delById(Long id) {
		return orderDetailDaoImpl.delById(id);
	}

	@Override
	public OrderDetailEntity getById(Long id) {
		return orderDetailDaoImpl.getById(id);
	}

	@Override
	public int getRecordCount(PageQueryParam<OrderDetailEntity> pageQueryParam) {
		return orderDetailDaoImpl.getRecordCount(pageQueryParam);
	}

	@Override
	public List<OrderDetailEntity> queryListByParam(OrderDetailEntity queryParam) {
		return orderDetailDaoImpl.queryListByParam(queryParam);
	}

	@Override
	public List<OrderDetailEntity> queryListForPage(PageQueryParam<OrderDetailEntity> pageQueryParam) {
		return orderDetailDaoImpl.queryListForPage(pageQueryParam);
	}

	@Override
	public int update(OrderDetailEntity arg0) {
		return orderDetailDaoImpl.update(arg0);
	}

	

	
	
	
	

}
