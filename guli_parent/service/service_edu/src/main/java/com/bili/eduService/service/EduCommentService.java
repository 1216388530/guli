package com.bili.eduService.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bili.eduService.entity.EduComment;
import com.bili.eduService.entity.EduVideo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
public interface EduCommentService extends IService<EduComment> {

    void addComment(String memberId, EduComment comment);
}
