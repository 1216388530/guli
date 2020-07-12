package com.bili.eduService.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bili.eduService.entity.EduVideo;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByCourseId(String courseId);
}
