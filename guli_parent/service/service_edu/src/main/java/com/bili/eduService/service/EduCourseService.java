package com.bili.eduService.service;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bili.eduService.entity.EduCourse;
import com.bili.eduService.entity.frontVo.CourseFrontVo;
import com.bili.eduService.entity.frontVo.CourseWebVo;
import com.bili.eduService.entity.vo.CourseInfoVo;
import com.bili.eduService.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
public interface EduCourseService extends IService<EduCourse> {
    //添加课程基本信息的方法
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    String updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageTeacher, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
