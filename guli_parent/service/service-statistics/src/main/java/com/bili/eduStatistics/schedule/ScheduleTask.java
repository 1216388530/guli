package com.bili.eduStatistics.schedule;

import com.bili.eduStatistics.service.StatisticsDailyService;
import com.bili.eduStatistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTask {
    @Autowired
    StatisticsDailyService staService;
    //每隔5秒执行一次该方法
    //@Scheduled(cron = "0/5 * * * * ?")
    public void test1(){
        System.out.println("。。。。。。。。。test1执行了");
    }
    /**
     * 建议网上查语句生成器
     * cron表达式（七子表达式，七域表达式）
     * 7个关键词，标识  秒（0到59）  分钟（0到59）  小时（0到23）  日（1到31）  月（1到12）  周（1到7）  年（默认当前年，不写）
     *     *标识任意
     *     ？表示任意周几
     * spring中只能写到周，默认为当前年
     */
    //在每天凌晨一点，将前一天的数据查询添加
    @Scheduled(cron = "0 0 1 * * ?")
    public void registerCountDailyTask(){
        System.out.println("。。。。。。。。。task1执行了");
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
