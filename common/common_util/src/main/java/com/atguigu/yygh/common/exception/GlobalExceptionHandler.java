package com.atguigu.yygh.common.exception;

import com.atguigu.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lijian
 * @create 2021-04-22 21:07
 */

/**
 * 全局异常处理
 */
@ControllerAdvice
@ResponseBody  //将结果用json输出
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(YyghException.class)
    public Result error(YyghException e){
        e.printStackTrace();
        return Result.fail();
    }
}
