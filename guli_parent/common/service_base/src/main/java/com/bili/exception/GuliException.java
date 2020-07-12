package com.bili.exception;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.management.RuntimeMXBean;
@Data
@AllArgsConstructor //全参数构造器
@NoArgsConstructor//无参数构造器
public class GuliException extends RuntimeException {
    private Integer code;
    private String msg;
}
