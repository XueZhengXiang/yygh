package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;

/**
 * @author lijian
 * @create 2021-04-25 9:54
 */
public class TestRead {
    public static void main(String[] args) {
        //读取的文件路径和名称
        String fileName = "F:\\excel\\01.xlsx";

        //调用方法实现读取操作
        EasyExcel.read(fileName,UserData.class,new ExcelListener()).sheet().doRead();
    }
}
