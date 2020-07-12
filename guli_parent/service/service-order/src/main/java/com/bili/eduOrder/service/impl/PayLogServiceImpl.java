package com.bili.eduOrder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.bili.eduOrder.entity.Order;
import com.bili.eduOrder.entity.PayLog;
import com.bili.eduOrder.mapper.PayLogMapper;
import com.bili.eduOrder.service.OrderService;
import com.bili.eduOrder.service.PayLogService;
import com.bili.eduOrder.utils.HttpClient;
import com.bili.exception.GuliException;
import com.github.wxpay.sdk.WXPayUtil;
import org.omg.PortableInterceptor.ObjectReferenceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    OrderService orderService;

    //根据订单号生成二维码接口
    @Override
    public Map createNative(String orderNo) {
        try{
            //1 根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            Order order = orderService.getOne(wrapper);
            //2 使用map设置生成二维码需要的信息
            Map m = new HashMap();
            m.put("appid","wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");//商户号
            m.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
            m.put("body", order.getCourseTitle()); //课程标题
            m.put("out_trade_no", orderNo); //订单号，二维码的唯一标识
            //订单价格，因为order.getTotalFee()是BigDecimal类型的，所以需要这样写
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");//项目的ip地址
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");//回调地址
            m.put("trade_type", "NATIVE");
            //3 发送httpclient请求，传递参数xml格式
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");//固定的请求地址
            //设置xml参数       这里利用商户的key对map进行加密
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            //执行请求发送
            client.post();
            //4 得到发送请求返回的结果
            //这个返回值是xml格式的
            String xml = client.getContent();
            //把xml转换为map
            Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);
            //最终返回数据的封装

            Map map = new HashMap();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));        //二维码地址
            return map;
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"二维码生成失败");
        }
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
            try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2 发送httpclient
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3 得到请求返回内容
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map再返回
            return resultMap;
        }catch(Exception e) {
            return null;
        }
    }
    //map中有("out_trade_no", orderNo)
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //从map中获取订单号
        String orderNo = map.get("out_trade_no");
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);

        //更新状态 0为未支付，1为支付
        if(order.getStatus().intValue()==1)return;
        order.setStatus(1);
        orderService.updateById(order);

        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);  //订单号
        payLog.setPayTime(new Date()); //订单完成时间
        payLog.setPayType(1);//支付类型 1微信
        payLog.setTotalFee(order.getTotalFee());//总金额(分)

        payLog.setTradeState(map.get("trade_state"));//支付状态,这个是微信的
        payLog.setTransactionId(map.get("transaction_id")); //流水号,这个是微信的
        payLog.setAttr(JSONObject.toJSONString(map));//其他属性
        //向支付表中添加记录
        baseMapper.insert(payLog);

    }
}
