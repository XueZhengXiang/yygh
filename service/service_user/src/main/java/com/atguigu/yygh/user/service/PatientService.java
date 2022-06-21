package com.atguigu.yygh.user.service;

import com.atguigu.yygh.model.user.Patient;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author lijian
 * @create 2021-05-02 16:00
 */
public interface PatientService extends IService<Patient> {
    //根据登录用户id获取就诊列表
    List<Patient> findAllUserId(Long userId);

    //根据就诊人id获取就诊人信息
    Patient getPatientById(Long id);
}
