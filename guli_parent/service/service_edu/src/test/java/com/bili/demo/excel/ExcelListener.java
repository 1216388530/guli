package com.bili.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelListener extends AnalysisEventListener<ReadData> {
    List<ReadData> list = new ArrayList<>();
   //一行一行的去读取excel的内容
    @Override
    public void invoke(ReadData readData, AnalysisContext analysisContext) {
        System.out.println("***"+readData);
        list.add(readData);
    }
    //读取表头的内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }
    //读取完执行的方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取完成");
    }


}
