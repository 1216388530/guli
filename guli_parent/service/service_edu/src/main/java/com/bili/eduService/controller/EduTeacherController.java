package com.bili.eduService.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bili.commonUtils.R;
import com.bili.eduService.entity.EduTeacher;
import com.bili.eduService.entity.vo.TeacherQuery;
import com.bili.eduService.service.EduTeacherService;
import com.bili.exception.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-31
 */
//在swagger上添加中文提示
@Api("讲师管理")
@RestController
@RequestMapping("/eduService/teacher")

public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //查询讲师表中的所有数据
    //在swagger上添加中文提示
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher(){
          return R.ok().data("items",teacherService.list(null));
    }

    //逻辑删除
    @ApiOperation(value = "按id逻辑删除讲师")
    @DeleteMapping("/{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag)
        return R.ok();
        return R.error();
    }

    /**
     * 分页查询讲师的方法
     * @param current 当前页
     * @param limit 单页记录数
     * @return
     */
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @PathVariable long current,
            @PathVariable long limit){
        Page<EduTeacher> page = new Page<>(current,limit);

//        try {
//            int i = 10/0;
//        }catch (Exception e){
//            throw new GuliException(20001,"执行了自定义异常处理。。。。");
//        }

        //调用方法后会将所有的数据和相关信息都封装到page中
        teacherService.page(page,null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "条件分页查询讲师")
    //使用json，就只能用PostMapping
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit
             //表明 teacherQuery可以为空，就是在没有任何条件时
            ,@RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> page=new Page<>(current,limit);
        QueryWrapper <EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();;
        //StringUtils:spring带的工具
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        //做个排序
        wrapper.orderByDesc("gmt_create");

        teacherService.page(page,wrapper);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if(save)
            return R.ok();
        return R.error();
    }

    @ApiOperation(value = "根据讲师id进行查询")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable Long id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }
    @ApiOperation("讲师修改")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag)
            return R.ok();
        return R.error();
    }
}

