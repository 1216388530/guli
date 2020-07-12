package com.bili.exceptionHandler;

import com.bili.commonUtils.ExceptionUtil;
import com.bili.commonUtils.R;
import com.bili.exception.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

//统一异常处理
@ControllerAdvice
//表示会用到logback日志
@Slf4j
public class GlobalExceptionHandler {

    //全局异常处理
    //指定处理哪些异常
    @ExceptionHandler(Exception.class)
    //返回json
    @ResponseBody
    public R error(Exception e){

        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了算术异常处理");
    }

    //自定义异常处理
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e){
        //将异常信息写入到log_error文件中，如果想写详细，可以写更多的东西
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
