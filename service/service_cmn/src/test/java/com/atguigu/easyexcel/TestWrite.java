package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijian
 * @create 2021-04-25 9:41
 */
public class TestWrite {
    public static void main(String[] args) {
        //构建list集合
        List<UserData> list = new ArrayList<>();
        for(int i = 0;i <= 10;i++){
            UserData userData = new UserData();
            userData.setId(i);
            userData.setUserName("lucy" + i);
            list.add(userData);
        }

        //设置excel文件路径和名称
        String fileName = "F:\\excel\\01.xlsx";

        //调用方法实现写操作
        EasyExcel.write(fileName,UserData.class).sheet("用户信息")
                .doWrite(list);
    }
}
