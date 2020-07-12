package com.bili.eduService.entity.vo;

import lombok.Data;

@Data
public class CourseQueryVo {
    //课程id
    private String id;
    //课程名称
    private String courseTitle;
    //课程状态
    private String status;
    //讲师名称
    private String teacherId;
    //二级分类
    private String subjectId;
    //一级分类
    private String subjectParentId;
}
