package com.bili.eduOrder.controller;

import com.bili.commonUtils.R;
import com.bili.eduOrder.service.PayLogService;
import com.bili.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/eduOrder/payLog")

public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //生成微信支付二维码
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        Map map=payLogService.createNative(orderNo);
        System.out.println("*****返回二维码map集合："+map);
        return R.ok().data(map);
    }
    //查询订单支付状态，支付这个动作就是直接外包给微信了，只能从微信那里获取是否已经付钱
    //参数是订单号，根据订单号查询支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("*****查询订单状态map集合："+map);
        if(map==null)
            throw new GuliException(20001,"支付出错");
        if(map.get("trade_state").equals("SUCCESS")){//支付成功
            //添加记录到支付表中，更新订单表订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        //利用拦截器使其前端没有响应
        return R.ok().message("支付中").code(25000);
    }

}

