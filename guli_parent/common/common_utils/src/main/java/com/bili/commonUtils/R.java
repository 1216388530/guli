package com.bili.commonUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一结果返回类
 */
@Data
public class R {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String,Object> data = new HashMap<>();

    private R(){}

    public static R ok(){
        R r = new R();
        r.success = true;
        r.code = ResultCode.SUCCESS;
        r.message = "成功";
        return r;
    }
    public static R error(){
        R r = new R();
        r.success = false;
        r.code = ResultCode.ERROR;
        r.message = "失败";
        return r;
    }

    /***
     * 这个返回是为了链式编程
     * r.ok().success().message()...
     * @return
     */
    public R success(Boolean success){
        this.setSuccess(success);
        //返回当前对象
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
