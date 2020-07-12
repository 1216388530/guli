package com.bili.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bili.eduService.client.VodClient;
import com.bili.eduService.entity.EduVideo;
import com.bili.eduService.mapper.EduVideoMapper;
import com.bili.eduService.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodClient vodClient;
    //根据课程id删小节
    //删除视频 todo
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapperVideo=new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> videoList =  baseMapper.selectList(wrapperVideo);
        List<String> list = new ArrayList<>();
        for(EduVideo video:videoList){
            String videoId = video.getVideoSourceId();
            if(!StringUtils.isEmpty(videoId))
            list.add(videoId);
        }
        if(list.size()>0)
        vodClient.deleteBatch(list);
        QueryWrapper<EduVideo> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
