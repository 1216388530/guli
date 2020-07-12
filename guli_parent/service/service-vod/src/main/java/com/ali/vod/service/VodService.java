package com.ali.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideoAli(MultipartFile file);

    void removeMoreAliVideo(List<String> videoIdList);
}
