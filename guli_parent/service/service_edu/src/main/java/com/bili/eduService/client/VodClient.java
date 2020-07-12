package com.bili.eduService.client;

import com.bili.commonUtils.R;
import com.bili.eduService.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//指定出错后执行的实现类方法
@FeignClient(name="service-vod",fallback = VodFileDegradeFeignClient.class) //引入服务，根据服务名
@Component
public interface VodClient {
    //定义要调用的方法的路径
    @DeleteMapping("/eduVod/video/removeAliVideo/{id}")
    //@PathVariable内一定要求写值，不写会有问题
    public R removeAliVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduVod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}