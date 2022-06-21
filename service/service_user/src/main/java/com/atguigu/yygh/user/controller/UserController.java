package com.atguigu.yygh.user.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.model.user.UserInfo;
import com.atguigu.yygh.user.service.UserInfoService;
import com.atguigu.yygh.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author lijian
 * @create 2021-05-02 18:23
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    //用户列表（条件查询带分页）
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable("page") long page,
                       @PathVariable("limit") long limit,
                       UserInfoQueryVo userInfoQueryVo){
        Page<UserInfo> pageParam = new Page<>(page,limit);
        IPage<UserInfo> pageModel = userInfoService.selectPage(pageParam,userInfoQueryVo);
        return Result.ok(pageModel);
    }

    //用户锁定功能
    @GetMapping("lock/{userId}/{status}")
    public Result lock(@PathVariable("userId") long userId,
                       @PathVariable("status") Integer status){
        userInfoService.lock(userId,status);
        return Result.ok();
    }

    //用户详情功能
    @GetMapping("show/{userId}")
    public Result show(@PathVariable("userId") long userId){
        Map<String,Object> map = userInfoService.show(userId);
        return Result.ok(map);
    }

    //认证审批接口
    @GetMapping("approval/{userId}/{authStatus}")
    public Result approval(@PathVariable("userId") long userId,
                           @PathVariable("authStatus") Integer authStatus){
        userInfoService.approval(userId,authStatus);
        return Result.ok();
    }

}
