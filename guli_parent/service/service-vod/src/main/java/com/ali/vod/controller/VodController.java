package com.ali.vod.controller;

import com.ali.vod.service.VodService;
import com.ali.vod.utils.ConstantVodUtil;
import com.ali.vod.utils.InitVodClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.bili.commonUtils.R;
import com.bili.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Path;
import java.util.List;

@RestController
@RequestMapping("/eduVod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    //上传到阿里云
    @PostMapping("uploadAliVideo")
    public R uploadAliVideo(MultipartFile file){
        String videoId = vodService.uploadVideoAli(file);
        System.out.println(videoId);
        return R.ok().data("videoId",videoId);
    }
    //9.35
    //根据视频id删除阿里云视频
    @DeleteMapping("removeAliVideo/{id}")
    public R removeAliVideo(@PathVariable("id") String id){
        try{
            System.out.println(id);
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET);
            //创建一个删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
        }catch(Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
        return R.ok();
    }

    //删除多个阿里云中视频的方法
    //参数是多个视频id
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreAliVideo(videoIdList);
        return R.ok();
    }

    //根据视频的id获取视频的播放凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try {
            //根据视频ip获取视频的播放凭证
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtil.ACCESS_KEY_ID
                    , ConstantVodUtil.ACCESS_KEY_SECRET);
            //不一样
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            request.setVideoId(id);

            response = client.getAcsResponse(request);

            System.out.println("PlayAuth:"+response.getPlayAuth());
            return R.ok().data("playAuth",response.getPlayAuth());
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"获取凭证失败");
        }

    }
}
