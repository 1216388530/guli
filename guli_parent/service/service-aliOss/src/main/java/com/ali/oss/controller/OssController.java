package com.ali.oss.controller;

import com.ali.oss.service.OssService;
import com.bili.commonUtils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduOss/fileOss")
public class OssController {
    @Autowired
    private OssService ossService;
    //上传头像的方法
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        //获取上传文件
        //返回aliyun中的路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }

    @PostMapping("removeOssFile")
    public R removeOssFile(@RequestBody String url){
        ossService.deleteByUrl(url);
        return R.ok();
    }

}
