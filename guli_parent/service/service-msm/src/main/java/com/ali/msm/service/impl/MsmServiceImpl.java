package com.ali.msm.service.impl;

import com.ali.msm.service.MsmService;
import com.ali.msm.utils.ConstantPropertiesUtils;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {


    //发送短信的方法
    @Override
    public Boolean send(Map<String, Object> param, String phone) {

        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        //设置参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //发送相关的参数
        request.putQueryParameter("PhoneNumbers",phone);//设置发送的手机号
        request.putQueryParameter(
                "SignName","小白在线教育系统");//设置阿里云的签名名称
        request.putQueryParameter(
                "TemplateCode","SMS_193249731");//设置阿里云的模板的code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//验证码,需要传入像json格式

        //使用redis解决验证码有效时间问题

        //最终发送
        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
