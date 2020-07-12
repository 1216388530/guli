package com.bili.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ReadData {
    //设置列对应的参数
    @ExcelProperty(index = 0)
    private int id;
    @ExcelProperty(index = 1)
    private String name;
}
