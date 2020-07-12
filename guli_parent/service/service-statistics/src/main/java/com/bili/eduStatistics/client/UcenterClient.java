package com.bili.eduStatistics.client;

import com.bili.commonUtils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="service-ucenter")
@Component
public interface UcenterClient {
    @GetMapping("/eduCenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);

}
