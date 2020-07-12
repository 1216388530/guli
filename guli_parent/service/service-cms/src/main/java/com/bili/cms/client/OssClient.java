package com.bili.cms.client;

import com.bili.cms.client.impl.OssCircuitBreakClient;
import com.bili.commonUtils.R;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="service-oss",fallback = OssCircuitBreakClient.class) //引入服务，根据服务名
@Component
public interface OssClient {
    @PostMapping("/eduOss/fileOss/removeOssFile")
    public R removeOssFile(@RequestBody String url);
}
