package com.bili.eduService.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bili.commonUtils.R;
import com.bili.eduService.entity.EduCourse;
import com.bili.eduService.entity.EduTeacher;
import com.bili.eduService.service.EduCourseService;
import com.bili.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/teacherFront")

public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    //1 分页查询讲师的方法
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(page,limit) ;
        Map<String,Object> map=teacherService.getTeacherFrontList(pageTeacher);

        //返回分页的所有数据
        return R.ok().data(map);
    }
    //讲师详情
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable  String teacherId){
        EduTeacher teacher = teacherService.getById(teacherId);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> eduList = courseService.list(wrapper);

        return R.ok().data("teacher",teacher).data("courseList",eduList);
    }
}
