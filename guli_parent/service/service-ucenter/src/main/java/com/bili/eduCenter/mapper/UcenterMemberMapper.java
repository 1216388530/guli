package com.bili.eduCenter.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bili.eduCenter.entity.UcenterMember;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-03-09
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegisterDay(String day);
}
