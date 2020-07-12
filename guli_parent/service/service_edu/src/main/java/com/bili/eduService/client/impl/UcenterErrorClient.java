package com.bili.eduService.client.impl;

import com.bili.commonUtils.R;
import com.bili.eduService.client.UcenterClient;
import com.bili.exception.GuliException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UcenterErrorClient implements UcenterClient {
    @Override
    public R getMember(String memberId) {
        throw new GuliException(20001,"用户模块熔断");
    }
}
