package com.atguigu.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author lijian
 * @create 2021-04-25 9:39
 */

@Data
public class UserData {
    @ExcelProperty(value = "用户编号" ,index = 0)
    private int id;
    @ExcelProperty(value = "用户名称",index = 1)
    private String userName;
}
