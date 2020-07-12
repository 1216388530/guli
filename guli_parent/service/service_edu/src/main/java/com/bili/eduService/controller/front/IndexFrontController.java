package com.bili.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bili.commonUtils.R;
import com.bili.eduService.entity.EduCourse;
import com.bili.eduService.entity.EduTeacher;
import com.bili.eduService.service.EduCourseService;
import com.bili.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduService/indexFront")

public class IndexFrontController {
    @Autowired
    EduCourseService courseService;
    @Autowired
    EduTeacherService teacherService;
    //查询前8条热门课程，前四个名师
    @Cacheable(value="index",key="'courseAndTeacher'")
    @GetMapping("index")
    public R index(){
        QueryWrapper<EduCourse>  wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        List<EduCourse> courses = courseService.list(wrapperCourse);

        QueryWrapper<EduTeacher>  wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teachers = teacherService.list(wrapperTeacher);

        return R.ok().data("courses",courses).data("teachers",teachers);
    }
}
