package com.bili.eduCenter.controller;



import com.bili.commonUtils.JwtUtils;
import com.bili.commonUtils.R;
import com.bili.commonUtils.orderVo.UcenterMemberOrder;
import com.bili.eduCenter.entity.UcenterMember;
import com.bili.eduCenter.entity.vo.RegisterVo;
import com.bili.eduCenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/eduCenter/member")

public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;
    //登录
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member){
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getUserInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt，根据request获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //调用数据库
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    @GetMapping("getMember/{memberId}")
    public R getMember(@PathVariable("memberId") String memberId){
        //调用数据库
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("memberId",member.getId()).data("avatar",member.getAvatar())
                .data("nickname",member.getNickname());
    }

    //根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id){
        //调用数据库
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }
    //查询某一天的注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }

}

