package com.bili.eduStatistics.controller;

import com.bili.commonUtils.R;
import com.bili.eduStatistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

@RequestMapping("/staService/sta")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;
    //统计某一天的注册人数,统计某天注册人数生成统计数据
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        staService.registerCount(day);
        return R.ok();
    }
    //图表显示，返回两部分数据，日期的json数组，数量的json数组
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map =  staService.getShowData(type,begin,end);
        return R.ok().data(map);
    }

}
