package com.pfb.biz.service;

import com.pfb.entity.OrderDetailEntity;
import com.pfb.entity.OrderEntity;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author zhanghui
 * @version 1.0
 * @date: 26/09/2017 17:32
 */
public interface IOrderBiz {
    /**
     * 根据id查询订单
     *
     * @param id
     * @return
     */
    OrderEntity getOrderById(long id);

    /**
     * 根据单号查询订单
     *
     * @param orderNum
     * @return
     */
    OrderEntity getOrderByOrderNum(String orderNum);

    /**
     * 根据上游单号查询订单
     *
     * @param channelOrderNum
     * @return
     */
    OrderEntity getOrderByChannelOrderNum(String channelOrderNum);

    /**
     * 查询订单列表
     *
     * @param orderEntity
     * @param isPage
     * @param curPage
     * @param pageSize
     * @return
     */
    List<OrderEntity> getOrderList(OrderEntity orderEntity, boolean isPage, int curPage, int pageSize);

    /**
     * 新增订单
     *
     * @param orderEntity
     */
    void addOrder(OrderEntity orderEntity);


    /**
     * 更新订单
     *
     * @param orderEntity
     */
    void updateOrder(OrderEntity orderEntity);

    /*============OrderDetail============*/

    /**
     * 根据订单号查询订单详情
     *
     * @param orderNum
     * @return
     */
    OrderDetailEntity getOrderDetailByOrderNum(String orderNum);

    /**
     * @param orderDetailEntity
     * @return
     */
    List<OrderDetailEntity> getOrderDetailList(OrderDetailEntity orderDetailEntity);

    /**
     * 新增订单详情
     *
     * @param orderDetailEntity
     */
    void addOrderDetail(OrderDetailEntity orderDetailEntity);

    /**
     * 更新订单详情
     *
     * @param orderDetailEntity
     */
    void updateOrderDetail(OrderDetailEntity orderDetailEntity);

    /*============union============*/

    /**
     * @param orderNum
     * @return
     */
    OrderEntity getOrderInfoByOrderNum(String orderNum);

	boolean updateOrderFinalStatus(OrderEntity order);
	/**
	 * 查询前 3到 15分的支付中订单
	 * List<OrderEntity>
	 * @author:yu.zengjia@pufubao.net
	 * @since:Nov 3, 201711:04:10 AM
	 */
	List<OrderEntity> getUserPayingOrders();

}
