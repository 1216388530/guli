package com.bili.eduService.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduComment对象", description="课程评论")
public class EduComment {
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;
    private String courseId;
    private String teacherId;
    //用户的信息
    private String memberId;
    private String nickname;
    private String avatar;
    //评论内容
    private String content;
    @TableLogic
    private Integer isDeleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;
}
