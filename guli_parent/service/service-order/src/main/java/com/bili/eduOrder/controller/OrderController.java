package com.bili.eduOrder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bili.commonUtils.JwtUtils;
import com.bili.commonUtils.R;
import com.bili.eduOrder.entity.Order;
import com.bili.eduOrder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/eduOrder/order")

public class OrderController {

    @Autowired
    private OrderService orderService;

    //1 生成订单的方法
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        //创建订单，返回订单号
        String orderNo =                  //课程id   课程id
                orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }

    //2 根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }
    /**
     * 生成订单后，需要跳转到指定页面。所以就利用订单号查询到生成的订单
     */

    //根据课程id和用户id，判定课程是否已经被购买
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId
                         ,@PathVariable("memberId") String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if(count>0)return true;
        return false;
    }

}

