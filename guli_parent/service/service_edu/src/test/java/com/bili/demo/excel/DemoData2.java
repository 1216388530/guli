package com.bili.demo.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DemoData2 {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "D:\\java-developer\\ioSpace\\write.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, ReadData.class, new ExcelListener()).sheet().doRead();

    }
}
