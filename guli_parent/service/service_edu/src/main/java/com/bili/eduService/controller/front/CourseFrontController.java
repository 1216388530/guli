package com.bili.eduService.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bili.commonUtils.JwtUtils;
import com.bili.commonUtils.R;
import com.bili.commonUtils.orderVo.CourseWebVoOrder;
import com.bili.eduService.client.OrderClient;
import com.bili.eduService.entity.EduCourse;
import com.bili.eduService.entity.EduTeacher;
import com.bili.eduService.entity.frontVo.CourseFrontVo;
import com.bili.eduService.entity.frontVo.CourseWebVo;
import com.bili.eduService.entity.vo.ChapterVo;
import com.bili.eduService.service.EduChapterService;
import com.bili.eduService.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/courseFront")

public class CourseFrontController {
    @Autowired
    EduCourseService courseService;
    @Autowired
    EduChapterService chapterService;
    @Autowired
    OrderClient orderClient;

    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit
                ,@RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map =  courseService.getCourseFrontList(pageTeacher,courseFrontVo);
        return R.ok().data(map);
    }
    
    //课程详情
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id，编写sql语句查询出课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id,查询章节和小节部分
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        //根据课程id和用户id去查询当前这个课程是否已经被支付过了
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId))
            return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList)
                    .data("isBuy",false);
        boolean flag=orderClient.isBuyCourse(courseWebVo.getId(),memberId);
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList)
                .data("isBuy",flag);
    }

    //根据课程id查询课程的信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder  getCourseInfoOrder(@PathVariable("id") String id){
        CourseWebVo baseCourseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(baseCourseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
