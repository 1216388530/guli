package com.ali.msm.controller;

import com.ali.msm.service.MsmService;
import com.ali.msm.utils.RandomUtil;
import com.bili.commonUtils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/eduMsm/msm")
public class MsmController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;



    @Autowired
    private MsmService msmService;
    //发送短信的方法
    @GetMapping("send/{phone}")
    public R  sendMsm(@PathVariable String phone){
        //1.从redis中取验证码，能获取就返回
        String code = redisTemplate.opsForValue().get(phone);
        //2.如果redis获取不到，进行阿里云的发送
        if(!StringUtils.isEmpty(code)){
            return R.ok();//已经有验证码了
        }


        //随机验证码需要自己生成，传给阿里云进行发送
         code = RandomUtil.getFourBitRandom();
        //最好用map传
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        //调用service中的短信发送方法
        Boolean isSend =  msmService.send(param,phone);
        if(isSend){
            //如果发送成功。将成功的验证码放入redis中
            //设置有效时间5分钟
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }
        return R.error().message("短信发送失败");
    }

}
