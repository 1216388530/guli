package com.bili.eduStatistics.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bili.eduStatistics.client.UcenterClient;
import com.bili.eduStatistics.entity.StatisticsDaily;
import com.bili.eduStatistics.mapper.StatisticsDailyMapper;
import com.bili.eduStatistics.service.StatisticsDailyService;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService   {
    @Autowired
    UcenterClient ucenterClient;
    @Override
    public void registerCount(String day) {
        //先删除相同日期的记录
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        Map<String,Object> map=ucenterClient.countRegister(day).getData();
        Integer countRegister = (Integer) map.get("countRegister");
        //将数据添加入表中
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(countRegister);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    //根据类型，日期查询不同的统计数据
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //先查询
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        //只要日期和指定类型的数据
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        //前端要求用json数组，就是java的list
        List<String> dateList = new ArrayList<>();
        List<Integer> numDataList= new ArrayList<>();
        for(StatisticsDaily s:staList){
            dateList.add(s.getDateCalculated());
            switch (type) {
                case "register_num":
                    numDataList.add(s.getRegisterNum());
                    break;
                case "login_num":
                    numDataList.add(s.getLoginNum());
                    break;
                case "video_view_num":
                    numDataList.add(s.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(s.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String,Object> map =new HashMap<>();
        map.put("dateList",dateList);
        map.put("numDataList",numDataList);
        return map;
    }
}
