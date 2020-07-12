package com.bili.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bili.commonUtils.R;
import com.bili.eduService.client.UcenterClient;
import com.bili.eduService.entity.EduComment;
import com.bili.eduService.mapper.EduCommentMapper;
import com.bili.eduService.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    UcenterClient ucenterClient;
    @Override
    public void addComment(String memberId, EduComment comment) {
        Map<String,Object> map = ucenterClient.getMember(memberId).getData();
        String avatar = (String) map.get("avatar");
        String nickname = (String) map.get("nickname");
        comment.setMemberId(memberId);
        comment.setAvatar(avatar);
        comment.setNickname(nickname);
        baseMapper.insert(comment);
    }
}
