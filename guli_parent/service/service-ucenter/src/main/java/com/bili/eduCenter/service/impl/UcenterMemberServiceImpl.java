package com.bili.eduCenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bili.commonUtils.JwtUtils;
import com.bili.commonUtils.MD5;
import com.bili.eduCenter.entity.UcenterMember;
import com.bili.eduCenter.entity.vo.RegisterVo;
import com.bili.eduCenter.mapper.UcenterMemberMapper;
import com.bili.eduCenter.service.UcenterMemberService;
import com.bili.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.security.krb5.internal.crypto.Aes128;


/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-09
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private
    RedisTemplate<String,String> redisTemplate;
    //登录方法
    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号和密码非空的判断
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
        //手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if(mobileMember==null)
            throw new GuliException(20001,"登录失败,没有这个用户");
        //密码
        //密码的问题：加密
        //先将password加密，然后比较，用 MD5(只能加密，不能解密)
        if(!MD5.encrypt(password).equals(mobileMember.getPassword()))
            throw new GuliException(20001,"登录失败,密码错误");
        //判断用户是否禁用
        if(mobileMember.getIsDisabled())
            throw new GuliException(20001,"登录失败,用户禁用");
        //用jwt生成token
        String token = JwtUtils.getJwtToken(mobileMember.getId(),mobileMember.getNickname());

        return token;
    }

    //注册
    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)
                ||StringUtils.isEmpty(code)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
        //判断该验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode))
            throw new GuliException(20001,"验证码错误");
        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer integer = baseMapper.selectCount(wrapper);
        if(integer>=1)
            throw new GuliException(20001,"注册失败");
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setAvatar("https://edu-1216388530-0101.oss-cn-beijing.aliyuncs.com/2020/06/18/8822f56c29174940bc79b9ce5bf379fd.png");//默认头像
        baseMapper.insert(member);

    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
