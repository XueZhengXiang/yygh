package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author lijian
 * @create 2021-04-27 10:05
 */
public interface DepartmentService {
    //上传科室接口
    void save(Map<String, Object> parampMap);

    //查询科室
    Page<Department> finPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    //删除科室
    void remove(String hoscode, String depcode);

    //根据医院编号，查询所有科室列表
    List<DepartmentVo> findDepTree(String hoscode);

    //根据医院编号和科室编号查询科室名称
    String getDepName(String hoscode, String depcode);

    //根据医院编号和科室编号查询科室
    Department getDepartment(String hoscode, String depcode);
}
