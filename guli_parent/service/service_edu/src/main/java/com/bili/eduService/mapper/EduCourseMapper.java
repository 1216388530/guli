package com.bili.eduService.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bili.eduService.entity.EduCourse;
import com.bili.eduService.entity.frontVo.CourseWebVo;
import com.bili.eduService.entity.vo.CoursePublishVo;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@Repository
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //根据课程id查询课程信息
     CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
