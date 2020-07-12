package com.bili.eduService.client;

import com.bili.commonUtils.R;
import com.bili.eduService.client.impl.UcenterErrorClient;
import com.bili.eduService.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

//指定出错后执行的实现类方法
@FeignClient(name="service-ucenter",fallback = UcenterErrorClient.class) //引入服务，根据服务名
@Component
public interface UcenterClient {
///eduCenter/member/getMember/{memberId}
    @GetMapping("/eduCenter/member/getMember/{memberId}")
    public R getMember(@PathVariable("memberId")String  memberId);
}
