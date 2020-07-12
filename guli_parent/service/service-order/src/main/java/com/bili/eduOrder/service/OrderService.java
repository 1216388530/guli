package com.bili.eduOrder.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bili.eduOrder.entity.Order;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
public interface OrderService extends IService<Order> {
    String createOrders(String courseId, String memberIdByJwtToken);
}
