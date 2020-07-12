package com.bili.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bili.commonUtils.R;
import com.bili.eduService.entity.EduCourse;
import com.bili.eduService.entity.EduTeacher;
import com.bili.eduService.entity.vo.CourseInfoVo;
import com.bili.eduService.entity.vo.CoursePublishVo;
import com.bili.eduService.entity.vo.CourseQueryVo;
import com.bili.eduService.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@RestController
@RequestMapping("/eduService/course")

public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //课程列表接口 todo
    //条件分页查询
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody CourseQueryVo courseQuery){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String id = courseQuery.getId();
        String courseTitle = courseQuery.getCourseTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectId = courseQuery.getSubjectId();
        String subjectPatentId = courseQuery.getSubjectParentId();
        String status = courseQuery.getStatus();
        if(!StringUtils.isEmpty(id)){
            wrapper.eq("id",id);
        }
        if(!StringUtils.isEmpty(courseTitle)){
            wrapper.like("title",courseTitle);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectPatentId)){
            wrapper.eq("subject_parent_id",subjectPatentId);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        Page<EduCourse> page = new Page<>(current,limit);
        courseService.page(page,wrapper);
        long total = page.getTotal();
        List<EduCourse> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }
    //删除课程，把视频，小节，章节，描述，都删除
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }





    //添加课程基本信息
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //需要返回课程id
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }
    //根据课程id查询课程的基本信息
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo=courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfo",courseInfoVo);
    }
    //课程的修改
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = courseService.updateCourseInfo(courseInfoVo);

        return R.ok().data("courseId",id);
    }
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo =  courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }
    //课程最终发布
    @GetMapping("publishCourse/{id}/{status}")
    public R publishCourse(@PathVariable String id,@PathVariable String status){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus(status);
        courseService.updateById(eduCourse);
        return R.ok();
    }
}

