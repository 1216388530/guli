package com.bili.eduCenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bili.commonUtils.JwtUtils;
import com.bili.eduCenter.entity.UcenterMember;
import com.bili.eduCenter.service.UcenterMemberService;
import com.bili.eduCenter.utils.ConstantWxUtils;
import com.bili.eduCenter.utils.HttpClientUtils;
import com.bili.exception.GuliException;

import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService memberService;
    //生成二维码
    @GetMapping("login")
    public String getWxCode(){
        //请求微信地址
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }

        String url = String.format(baseUrl, ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl
                ,"atguigu");
        //重定向到
        return "redirect:"+url;
    }

    //获取扫码人的信息，加入数据库
    @GetMapping("callback")
    //code:类似与手机的验证码
    //state:原样传递的
    public String callback(String code,String state){
        try {
            System.out.println("code:" + code + " state:" + state);
            //1 获取code值，临时票据，类似与验证码
            //2 拿着code取请求微信里的固定地址，得到两个值  access_token和openid
            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantWxUtils.WX_OPEN_APP_ID
                    , ConstantWxUtils.WX_OPEN_APP_SECRET
                    , code);
            //请求这个拼接好的地址，得到返回俩个值access_token和openid
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo:"+accessTokenInfo);

            //从accessTokenInfo中获取出两值
            //使用gson将string变成object
            Gson gson =new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");
            System.out.println("openid:"+openid);
            //先查表
            UcenterMember member = memberService.getOpenIdMember(openid);
            if(member==null){//表中无数据
                //3.查找access_token和openid请求固定地址
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println(userInfo);//json字符串
                //获取扫码人的信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            //使用jwt生成token字符串
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token="+token;
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"登录失败");
        }

    }
}
