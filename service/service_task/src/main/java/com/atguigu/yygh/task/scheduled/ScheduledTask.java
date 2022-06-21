package com.atguigu.yygh.task.scheduled;

import com.atguigu.common.rabbit.constant.MqConst;
import com.atguigu.common.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lijian
 * @create 2021-05-05 17:03
 */

@Component
@EnableScheduling  //开启定时任务操作
public class ScheduledTask {
    @Autowired
    private RabbitService rabbitService;

    //每天8点执行 提醒就诊
    //cron表达式，设置执行的时间  网上搜cron表达式在线生成即可
    //@Scheduled(cron = "0 0 1 * * ?")  表示每天8点发送
    @Scheduled(cron = "0/30 * * * * ?")  //表示每隔30s发送，为测试方便
    public void taskPatient() {
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK, MqConst.ROUTING_TASK_8, "");
    }
}

