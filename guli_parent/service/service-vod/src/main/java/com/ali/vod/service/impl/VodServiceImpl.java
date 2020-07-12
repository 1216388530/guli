package com.ali.vod.service.impl;

import com.ali.vod.service.VodService;
import com.ali.vod.utils.ConstantVodUtil;
import com.ali.vod.utils.InitVodClient;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.bili.exception.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideoAli(MultipartFile file) {
        try {
        //title：上传后显示名称
        //fileName：上传文件的原始名称
        //inputStream:输入流
        String fileName = file.getOriginalFilename();
        String title = UUID.randomUUID() +fileName.substring(0,fileName.lastIndexOf("."));
        InputStream inputStream = file.getInputStream();

        UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET,
                title,fileName ,inputStream );

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        String videoId = null;
        if(response.isSuccess()) {
            videoId= response.getVideoId() ;
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            videoId= response.getVideoId() ;
        }
        return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeMoreAliVideo(List<String> videoIdList) {

        try{
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET);
            //创建一个删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //将数组变成   id,id,id,...
            String videoIds = StringUtils.join(videoIdList.toArray(),",");
            request.setVideoIds(videoIds);
            client.getAcsResponse(request);
        }catch(Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }
}
