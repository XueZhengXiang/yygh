package com.atguigu.yygh.hosp.controller.api;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * @author lijian
 * @create 2021-04-26 18:19
 */

@RestController
@RequestMapping("/api/hosp")
public class ApiController {
    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    //上传医院接口
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request){
        //获取医院传递过来的信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        //为避免后面遍历，将map中的String[]转换成Object
        Map<String, Object> parampMap = HttpRequestHelper.switchMap(parameterMap);
        //核验签名是否一致
            //1.获取医院系统传递过来的签名
        String hospSign = (String) parampMap.get("sign");
            //2.根据传递过来的医院编码，查询数据库，查询签名
        String hoscode = (String) parampMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
            //3.把查询出来的签名进行MD5加密
        String signKeyMD5 = MD5.encrypt(signKey);
            //4.判断签名是否一致
        if(!hospSign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //图片数据采取base64工具类传输，在传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoData = (String) parampMap.get("logoData");
        logoData = logoData.replace(" ","+");
        parampMap.put("logoData",logoData);

        //调用service的方法
        hospitalService.save(parampMap);
        return Result.ok();
    }

    //查询医院接口
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request){
        //获取医院传递过来的信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        //为避免后面遍历，将map中的String[]转换成Object
        Map<String, Object> parampMap = HttpRequestHelper.switchMap(parameterMap);
        //获取传递过来的医院编号
        String hoscode = (String) parampMap.get("hoscode");
        //签名校验
        String hospSign = (String) parampMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = MD5.encrypt(signKey);
        if(!hospSign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //调用service方法
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }

    //上传科室接口
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        //获取传递过来的科室信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> parampMap = HttpRequestHelper.switchMap(parameterMap);
        //核验签名是否一致
        String hospSign = (String) parampMap.get("sign");
        String hoscode = (String) parampMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = MD5.encrypt(signKey);
        if(!hospSign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //调用service方法
        departmentService.save(parampMap);
        return Result.ok();
    }

    //科室查询接口
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request){
        //获取传递过来的科室信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> parampMap = HttpRequestHelper.switchMap(parameterMap);
        //医院编号
        String hoscode = (String) parampMap.get("hoscode");
        //当前页
        int page = Integer.parseInt((String) parampMap.get("page"));
        if(StringUtils.isEmpty(page)){
            page = 1;
        }
        //每页显示记录数
        int limit = Integer.parseInt((String) parampMap.get("limit"));
        if(StringUtils.isEmpty(limit)){
            limit = 1;
        }
        //签名校验
        String hospSign = (String) parampMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = MD5.encrypt(signKey);
        if(!hospSign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //调用service查询
            //查询条件的值封装到departmentQueryVo中
        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        Page<Department> pageModel = departmentService.finPageDepartment(page,limit,departmentQueryVo);
        return Result.ok(pageModel);
    }

    //删除科室接口
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request){
        //获取传递过来的科室信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> parampMap = HttpRequestHelper.switchMap(parameterMap);
        //获取医院编号和科室编号
        String hoscode = (String) parampMap.get("hoscode");
        String depcode = (String) parampMap.get("depcode");
        //签名校验
        String hospSign = (String) parampMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = MD5.encrypt(signKey);
        if(!hospSign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        departmentService.remove(hoscode,depcode);
        return Result.ok();
    }

    //上传排班
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        //获取传递过来的科室信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> parampMap = HttpRequestHelper.switchMap(parameterMap);

        //签名校验
        String hospSign = (String) parampMap.get("sign");
        String hoscode = (String) parampMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = MD5.encrypt(signKey);
        if(!hospSign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //调用service方法
        scheduleService.save(parampMap);
        return Result.ok();
    }

    //查询排班
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request) {
        //获取传递过来的科室信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> parampMap = HttpRequestHelper.switchMap(parameterMap);
        //医院编号
        String hoscode = (String) parampMap.get("hoscode");
        //科室编号
        String depcode = (String) parampMap.get("depcode");

        //当前页
        int page = Integer.parseInt((String) parampMap.get("page"));
        if (StringUtils.isEmpty(page)) {
            page = 1;
        }
        //每页显示记录数
        int limit = Integer.parseInt((String) parampMap.get("limit"));
        if (StringUtils.isEmpty(limit)) {
            limit = 1;
        }
        //签名校验
        String hospSign = (String) parampMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = MD5.encrypt(signKey);
        if (!hospSign.equals(signKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //调用service查询
            //查询条件的值封装到departmentQueryVo中
        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        Page<Schedule> pageModel = scheduleService.finPageSchedule(page, limit, scheduleQueryVo);
        return Result.ok(pageModel);
    }

    //删除排班
    @PostMapping("schedule/remove")
    public Result remove(HttpServletRequest request){
        //获取传递过来的科室信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> parampMap = HttpRequestHelper.switchMap(parameterMap);
        //获取医院编号和排班编号
        String hoscode = (String) parampMap.get("hoscode");
        String hosScheduleId = (String) parampMap.get("hosScheduleId");
        //签名校验
        String hospSign = (String) parampMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = MD5.encrypt(signKey);
        if(!hospSign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }
}
