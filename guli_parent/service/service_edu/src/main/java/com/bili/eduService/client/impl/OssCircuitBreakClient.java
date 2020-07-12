package com.bili.eduService.client.impl;

import com.bili.commonUtils.R;
import com.bili.eduService.client.OssClient;

public class OssCircuitBreakClient implements OssClient {
    @Override
    public R removeOssFile(String url) {
        return R.error().message("Oss熔断");
    }
}
