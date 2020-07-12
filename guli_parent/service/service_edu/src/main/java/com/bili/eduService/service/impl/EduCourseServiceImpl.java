package com.bili.eduService.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bili.eduService.entity.EduCourse;
import com.bili.eduService.entity.EduCourseDescription;
import com.bili.eduService.entity.frontVo.CourseFrontVo;
import com.bili.eduService.entity.frontVo.CourseWebVo;
import com.bili.eduService.entity.vo.CourseInfoVo;
import com.bili.eduService.entity.vo.CoursePublishVo;
import com.bili.eduService.mapper.EduCourseMapper;
import com.bili.eduService.service.EduChapterService;
import com.bili.eduService.service.EduCourseDescriptionService;
import com.bili.eduService.service.EduCourseService;
import com.bili.eduService.service.EduVideoService;
import com.bili.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述注入
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表中加课程基本信息
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        boolean save = this.save(eduCourse);
        if(!save)
            throw new GuliException(20001,"添加课程失败");

        //向课程简介表添加课程简介
        String id = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(id);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.save(eduCourseDescription);
        return id;
    }


    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse course =this.getById(courseId);
        EduCourseDescription courseDescription= courseDescriptionService.getById(courseId);
        CourseInfoVo courseInfoVo =new CourseInfoVo();
        System.out.println(course);
        BeanUtils.copyProperties(course,courseInfoVo);
        if(courseDescription!=null)
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public String updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,course);
        //修改课程表
        boolean flag = this.updateById(course);
        if(!flag)
            throw new GuliException(20001,"修改课程信息失败");
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,courseDescription);
        courseDescriptionService.updateById(courseDescription);
        return course.getId();
    }
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用Mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }
    //删除课程
    @Override
    public void removeCourse(String courseId) {
        //1.删小节
        videoService.removeVideoByCourseId(courseId);
        //2.删章节
        chapterService.removeChapterByCourseId(courseId);
        //3.删描述
        courseDescriptionService.removeById(courseId);
        //4.删课程本身
        int result = baseMapper.deleteById(courseId);
        if(result==0)
            throw new GuliException(20001,"删除失败");
    }

    //
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageTeacher, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){//一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){//一级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){//销量
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageTeacher,wrapper);

        List<EduCourse> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
