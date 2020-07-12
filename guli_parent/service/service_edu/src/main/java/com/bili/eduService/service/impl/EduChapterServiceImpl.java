package com.bili.eduService.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bili.eduService.entity.EduChapter;
import com.bili.eduService.entity.EduVideo;
import com.bili.eduService.entity.vo.ChapterVo;
import com.bili.eduService.entity.vo.VideoVo;
import com.bili.eduService.mapper.EduChapterMapper;
import com.bili.eduService.service.EduChapterService;
import com.bili.eduService.service.EduVideoService;
import com.bili.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = this.list(wrapperChapter);

        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        List<ChapterVo> chapterList = new ArrayList<>();
        for(EduChapter chapter:eduChapterList){
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);

            List<VideoVo> videoVoList = new ArrayList<>();
            for(EduVideo video:eduVideoList){
                if(video.getChapterId().equals(chapterVo.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
            chapterList.add(chapterVo);
        }
        return chapterList;
    }

    //删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        boolean result;
        if(count>0){
            throw new GuliException(20001,"不能删除");
        }else{
            result = this.removeById(chapterId);
        }
        return result;
    }

    //根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
