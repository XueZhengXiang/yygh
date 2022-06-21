package com.atguigu.yygh.msm.service;

import com.atguigu.yygh.vo.msm.MsmVo;

/**
 * @author lijian
 * @create 2021-04-30 23:13
 */
public interface MsmService {
    //通过整合短信服务进行发送
    boolean send(String phone, String code);

    //使用mq发送短信
    boolean send(MsmVo msmVo);
}
