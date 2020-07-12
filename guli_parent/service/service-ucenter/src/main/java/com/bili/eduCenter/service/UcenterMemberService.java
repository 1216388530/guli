package com.bili.eduCenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bili.eduCenter.entity.UcenterMember;
import com.bili.eduCenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-09
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegisterDay(String day);
}
