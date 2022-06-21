package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.order.SignInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author lijian
 * @create 2021-04-22 15:53
 */
public interface HospitalSetService extends IService<HospitalSet> {
    //根据传递过来的医院编码，查询数据库，查询签名
    String getSignKey(String hospcode);

    //获取医院签名信息
    SignInfoVo getSignInfoVo(String hoscode);
}
