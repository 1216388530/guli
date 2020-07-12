package com.bili.eduService.client;

import com.bili.commonUtils.R;
import com.bili.eduService.client.impl.OssCircuitBreakClient;
import com.bili.eduService.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="service-oss",fallback = OssCircuitBreakClient.class) //引入服务，根据服务名
@Component
public interface OssClient {
    @PostMapping("delete")
    public R removeOssFile(@RequestBody String url);
}
