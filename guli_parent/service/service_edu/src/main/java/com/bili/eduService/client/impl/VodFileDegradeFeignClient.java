package com.bili.eduService.client.impl;

import com.bili.commonUtils.R;
import com.bili.eduService.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 这种实现类的作用就是当服务熔断的时候，会调用这里的方法来报告错误
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAliVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
