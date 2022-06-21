package com.atguigu.yygh.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author lijian
 * @create 2021-05-02 9:53
 */
public interface FileService {
    //获取上传文件
    String upload(MultipartFile file);
}
