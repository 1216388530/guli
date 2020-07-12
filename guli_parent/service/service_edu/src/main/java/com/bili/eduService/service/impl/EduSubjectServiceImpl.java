package com.bili.eduService.service.impl;

import com.alibaba.excel.EasyExcel;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bili.eduService.entity.EduSubject;
import com.bili.eduService.entity.excel.SubjectData;
import com.bili.eduService.entity.subject.OneSubject;
import com.bili.eduService.entity.subject.TwoSubject;
import com.bili.eduService.listener.SubjectExcelListener;
import com.bili.eduService.mapper.EduSubjectMapper;
import com.bili.eduService.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-02-29
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne =new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjects = this.list(wrapperOne);

        //查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo =new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjects = this.list(wrapperTwo);

        List<OneSubject> finalSubjectList = new ArrayList<>();

        for(EduSubject one:oneSubjects){
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(one,oneSubject);
            oneSubject.setChildren(new ArrayList<>());
            for(EduSubject two:twoSubjects){
                if(two.getParentId().equals(one.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(two,twoSubject);
                    oneSubject.getChildren().add(twoSubject);
                }
            }
            finalSubjectList.add(oneSubject);
        }
        return finalSubjectList;
    }
}
