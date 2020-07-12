package com.bili.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        //实现excel写操作
        //地址和名称
        String fileName = "D:\\java-developer\\ioSpace\\write.xlsx";

        //调用easyExcel的方法 文件路径，对应输入的值的列名（class）
        EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(getData());


    }

    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("aa"+i);
            list.add(data);
        }
        return list;
    }
}
