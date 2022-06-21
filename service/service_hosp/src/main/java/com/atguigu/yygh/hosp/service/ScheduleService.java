package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.ScheduleOrderVo;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author lijian
 * @create 2021-04-27 15:52
 */
public interface ScheduleService extends IService<Schedule> {
    //上传排班
    void save(Map<String, Object> parampMap);

    //查询科室
    Page<Schedule> finPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    //删除排班
    void remove(String hoscode, String hosScheduleId);

    //根据医院编号和科室编号，查询排班规则数据
    Map<String, Object> getReleSchedule(Long page, Long limit, String hoscode, String depcode);

    //根据医院编号、科室编号和工作日期，查询排班详情信息
    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);

    //获取可预约排班数据
    Map<String,Object> getBookingScheduleRule(Integer page, Integer limit, String hoscode, String depcode);

    //根据排班id获取排班数据
    Schedule getScheduleId(String scheduleId);

    //根据排班id获取预约下单数据
    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    //更新排班，用于mq
    void update(Schedule schedule);
}
