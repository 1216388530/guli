package com.bili.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bili.commonUtils.JwtUtils;
import com.bili.commonUtils.R;
import com.bili.eduService.client.UcenterClient;
import com.bili.eduService.client.VodClient;
import com.bili.eduService.entity.EduComment;
import com.bili.eduService.entity.EduVideo;
import com.bili.eduService.service.EduCommentService;
import com.bili.eduService.service.EduVideoService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@RestController
@RequestMapping("/eduService/comment")

public class EduCommentController {
    @Autowired
    EduCommentService commentService;
    //根据指定用户id查看评论，分页
    @GetMapping("getPageComments/{page}/{limit}")
    public R GetPageComments(@PathVariable long page,
                             @PathVariable long limit,String courseId){
        Page<EduComment> pageComment = new Page<>(page,limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        commentService.page(pageComment,wrapper);
        List<EduComment> commentList = pageComment.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageComment.getCurrent());
        map.put("pages", pageComment.getPages());
        map.put("size", pageComment.getSize());
        map.put("total", pageComment.getTotal());
        map.put("hasNext", pageComment.hasNext());
        map.put("hasPrevious", pageComment.hasPrevious());
        return R.ok().data(map);
    }
    //根据传来的token来获取用户信息
    @PostMapping("insertComment")
    public R insertComment(HttpServletRequest request,@RequestBody EduComment comment){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        //request不可用序列化
        commentService.addComment(memberId,comment);
        return R.ok();
    }

}

