package com.atguigu.yygh.oss.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lijian
 * @create 2021-05-02 9:50
 */
@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {
    @Autowired
    private FileService fileService;
    //上传文件到阿里云
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file){  //通过MultipartFile可以得到上传文件
        //获取上传文件
        String url = fileService.upload(file);  //上传后得到路径
        return Result.ok(url);
    }
}
