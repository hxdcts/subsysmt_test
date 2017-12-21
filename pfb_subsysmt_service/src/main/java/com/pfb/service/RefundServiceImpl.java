package com.pfb.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pfb.common.page.PageQueryInterface;
import com.pfb.common.page.PageQueryParam;
import com.pfb.dao.RefundDaoImpl;
import com.pfb.entity.OrderEntity;
import com.pfb.entity.RefundEntity;

@Service
public class RefundServiceImpl implements PageQueryInterface<RefundEntity> {
	
	@Resource
	RefundDaoImpl refundDaoImpl;

	@Override
	public RefundEntity add(RefundEntity refundEntity) {
		return refundDaoImpl.add(refundEntity);
	}

	@Override
	public RefundEntity getById(Long id) {
		return refundDaoImpl.getById(id);
	}

	@Override
	public int delById(Long id) {
		return refundDaoImpl.delById(id);
	}

	@Override
	public int update(RefundEntity refundEntity) {
		return refundDaoImpl.update(refundEntity);
	}

	@Override
	public int getRecordCount(PageQueryParam<RefundEntity> pageQueryParam) {
		return refundDaoImpl.getRecordCount(pageQueryParam);
	}

	@Override
	public List<RefundEntity> queryListForPage(PageQueryParam<RefundEntity> pageQueryParam) {
		return refundDaoImpl.queryListForPage(pageQueryParam);
	}

	@Override
	public List<RefundEntity> queryListByParam(RefundEntity queryParam) {
		return refundDaoImpl.queryListByParam(queryParam);
	}
	
	public List<RefundEntity> queryUserRefundingOrders() {
		return refundDaoImpl.queryUserRefundingOrders();
	}
}
