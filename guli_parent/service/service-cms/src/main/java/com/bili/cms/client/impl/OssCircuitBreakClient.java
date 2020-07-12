package com.bili.cms.client.impl;

import com.bili.cms.client.OssClient;
import com.bili.commonUtils.R;
import org.springframework.stereotype.Component;

@Component
public class OssCircuitBreakClient implements OssClient {
    @Override
    public R removeOssFile(String url) {
        return R.error().message("Oss熔断");
    }
}
