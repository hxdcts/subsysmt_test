package com.pfb.biz.service.impl;

import com.pfb.biz.service.IOrderBiz;
import com.pfb.common.page.PageQueryParam;
import com.pfb.entity.OrderDetailEntity;
import com.pfb.entity.OrderEntity;
import com.pfb.service.OrderDetailServiceImpl;
import com.pfb.service.OrderServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 订单服务
 *
 * @author zhanghui
 * @version 1.0
 * @date: 26/09/2017 17:33
 */
@Service
public class OrderBizImpl implements IOrderBiz {

    @Resource
    private OrderServiceImpl orderServiceImpl;
    @Resource
    private OrderDetailServiceImpl orderDetailServiceImpl;

    @Override
    public OrderEntity getOrderById(long id) {
        return orderServiceImpl.getById(id);
    }

    @Override
    public OrderEntity getOrderByOrderNum(String orderNum) {
        OrderEntity queryParam = new OrderEntity();
        queryParam.setOrderNum(orderNum);
        PageQueryParam<OrderEntity> pageQueryParam = new PageQueryParam<>();
        pageQueryParam.setPageEnable(false);
        pageQueryParam.setQueryParam(queryParam);
        List<OrderEntity> orderList = orderServiceImpl.queryListForPage(pageQueryParam);
        if (orderList == null || orderList.size() == 0) {
            return null;
        }
        return orderList.get(0);
    }

    @Override
    public OrderEntity getOrderByChannelOrderNum(String channelOrderNum) {
        OrderEntity queryParam = new OrderEntity();
        queryParam.setChannelOrderNum(channelOrderNum);
        PageQueryParam<OrderEntity> pageQueryParam = new PageQueryParam<>();
        pageQueryParam.setPageEnable(false);
        pageQueryParam.setQueryParam(queryParam);
        List<OrderEntity> orderList = orderServiceImpl.queryListForPage(pageQueryParam);
        if (orderList == null || orderList.size() == 0) {
            return null;
        }
        return orderList.get(0);
    }

    @Override
    public List<OrderEntity> getOrderList(OrderEntity orderEntity, boolean isPage, int curPage, int pageSize) {
        PageQueryParam<OrderEntity> pageQueryParam = new PageQueryParam<>();
        pageQueryParam.setQueryParam(orderEntity);
        if (isPage) {
            pageQueryParam.setPageEnable(isPage);
            pageQueryParam.setCurPage(curPage);
            pageQueryParam.setPageSize(pageSize);
        }
        List<OrderEntity> orderList = orderServiceImpl.queryListForPage(pageQueryParam);
        if (orderList == null || orderList.size() == 0) {
            return Collections.emptyList();
        }
        return orderList;
    }

    @Override
    public void addOrder(OrderEntity orderEntity) {
        orderServiceImpl.add(orderEntity);
    }

    @Override
    public void updateOrder(OrderEntity orderEntity) {
        orderServiceImpl.update(orderEntity);
    }

    @Override
    public OrderDetailEntity getOrderDetailByOrderNum(String orderNum) {
        OrderDetailEntity queryParam = new OrderDetailEntity();
        queryParam.setOrderNum(orderNum);
        PageQueryParam<OrderDetailEntity> pageQueryParam = new PageQueryParam<>();
        pageQueryParam.setPageEnable(false);
        pageQueryParam.setQueryParam(queryParam);
        List<OrderDetailEntity> orderDetailList = orderDetailServiceImpl.queryListForPage(pageQueryParam);
        if (orderDetailList == null || orderDetailList.size() == 0) {
            return null;
        }
        return orderDetailList.get(0);
    }

    @Override
    public List<OrderDetailEntity> getOrderDetailList(OrderDetailEntity orderDetailEntity) {
        PageQueryParam<OrderDetailEntity> pageQueryParam = new PageQueryParam<>();
        pageQueryParam.setPageEnable(false);
        pageQueryParam.setQueryParam(orderDetailEntity);
        List<OrderDetailEntity> orderDetailList = orderDetailServiceImpl.queryListForPage(pageQueryParam);
        if (orderDetailList == null || orderDetailList.size() == 0) {
            return Collections.emptyList();
        }
        return orderDetailList;
    }

    @Override
    public void addOrderDetail(OrderDetailEntity orderDetailEntity) {
        orderDetailServiceImpl.add(orderDetailEntity);
    }

    @Override
    public void updateOrderDetail(OrderDetailEntity orderDetailEntity) {
        orderDetailServiceImpl.update(orderDetailEntity);
    }

    @Override
    public OrderEntity getOrderInfoByOrderNum(String orderNum) {
        OrderEntity queryParam = new OrderEntity();
        queryParam.setOrderNum(orderNum);
        PageQueryParam<OrderEntity> pageQueryParam = new PageQueryParam<>();
        pageQueryParam.setPageEnable(false);
        pageQueryParam.setQueryParam(queryParam);
        List<OrderEntity> orderList = orderServiceImpl.queryListForPage(pageQueryParam);
        if (orderList == null || orderList.size() == 0) {
            return null;
        }
        OrderEntity orderEntity = orderList.get(0);

        OrderDetailEntity queryParamDetail = new OrderDetailEntity();
        queryParamDetail.setOrderNum(orderNum);
        PageQueryParam<OrderDetailEntity> pageQueryParamDetail = new PageQueryParam<>();
        pageQueryParamDetail.setPageEnable(false);
        pageQueryParamDetail.setQueryParam(queryParamDetail);
        List<OrderDetailEntity> orderDetailList = orderDetailServiceImpl.queryListForPage(pageQueryParamDetail);
        if (orderDetailList != null || orderDetailList.size() != 0) {
            orderEntity.setOrderDetailEntity(orderDetailList.get(0));
        }

        return orderList.get(0);
    }

	@Override
	public boolean updateOrderFinalStatus(OrderEntity order) {
		int rows = orderServiceImpl.updateFinalStatus(order);
		return rows>0;
	}

	@Override
	public List<OrderEntity> getUserPayingOrders() {
		return orderServiceImpl.getUserPayingOrders();
	}
}
