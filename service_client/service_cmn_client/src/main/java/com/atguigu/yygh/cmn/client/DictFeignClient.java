package com.atguigu.yygh.cmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lijian
 * @create 2021-04-27 18:25
 */
@FeignClient("service-cmn")
@Repository
public interface DictFeignClient {
    //根据dictcode和value查询名称
    @GetMapping("/admin/cmn/dict/getName/{dictCode}/{value}")  //路径写完整
    public String getName(@PathVariable("dictCode") String dictCode,  //远程调用过程中注解括号里面加上值
                          @PathVariable("value") String value);
    //根据value查询查询名称
    @GetMapping("/admin/cmn/dict/getName/{value}")
    public String getName(@PathVariable("value") String value);
}
