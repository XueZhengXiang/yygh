package com.atguigu.yygh.order.service;

import com.atguigu.yygh.model.order.OrderInfo;
import com.atguigu.yygh.model.order.PaymentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author lijian
 * @create 2021-05-05 9:10
 */
public interface PaymentService extends IService<PaymentInfo> {
    //向支付记录表里面添加信息
    void savePaymentInfo(OrderInfo order, Integer paymentType);

    //更改订单状态
    void paySuccess(String out_trade_no, Integer status, Map<String, String> resultMap);

    //获取支付记录
    PaymentInfo getPaymentInfo(Long orderId, Integer paymentType);
}
