package com.bili.eduService.controller;


import com.bili.commonUtils.R;
import com.bili.eduService.client.VodClient;
import com.bili.eduService.entity.EduVideo;
import com.bili.eduService.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@RestController
@RequestMapping("/eduService/video")
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    //注入vodClient
    @Autowired
    private VodClient vodClient;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo video){
        videoService.save(video);
        return R.ok();
    }
    @DeleteMapping("{id}")
    //todo:需要完善，删除小节时，需要同时删除视频
    //ok: 微服务实现
    public R deleteVideo(@PathVariable String id){
        //根据小节id得到视频id
        EduVideo video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        System.out.println("video:"+videoSourceId);
        if(!StringUtils.isEmpty(videoSourceId)){
            R r = vodClient.removeAliVideo(videoSourceId);
            System.out.println(r.getMessage());
        }


        videoService.removeById(id);
        return R.ok();
    }

    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo video){
        videoService.updateById(video);
        return R.ok();
    }

    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId){
        EduVideo video = videoService.getById(videoId);
        return R.ok().data("video",video);
    }
}

