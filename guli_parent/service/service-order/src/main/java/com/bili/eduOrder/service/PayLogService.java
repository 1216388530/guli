package com.bili.eduOrder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bili.eduOrder.entity.PayLog;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
public interface PayLogService extends IService<PayLog> {
    //生成微信支付二维码
    Map createNative(String orderNo);
    //根据订单号查询订单支付状态
    Map<String, String> queryPayStatus(String orderNo);
    //向支付表添加记录，更新订单状态
    void updateOrderStatus(Map<String, String> map);
}
