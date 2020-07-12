package com.bili.eduStatistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bili.eduStatistics.entity.StatisticsDaily;

import java.util.Map;

public interface StatisticsDailyService extends IService<StatisticsDaily> {
    void registerCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
