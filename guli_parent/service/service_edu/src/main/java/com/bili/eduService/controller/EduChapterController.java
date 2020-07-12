package com.bili.eduService.controller;


import com.bili.commonUtils.R;
import com.bili.eduService.entity.EduChapter;
import com.bili.eduService.entity.vo.ChapterVo;
import com.bili.eduService.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/eduService/chapter")

public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;
    //课程大纲列表
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list =  chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    //添加课程
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }
    //修改课程
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.ok();
    }
    //删除课程,处理其下的小节
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){

        //方法一：将其下的小节全部删除
        //方法二：当其下有小节时，就不能删除此章节
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag)
        return R.ok();
        return R.error();
    }

    //根据id获取课程
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter eduChapter=chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }
}

