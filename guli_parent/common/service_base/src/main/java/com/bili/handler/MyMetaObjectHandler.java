package com.bili.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.fillStrategy(metaObject,"gmtCreate", LocalDateTime.now());
        this.fillStrategy(metaObject,"gmtModified", LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.fillStrategy(metaObject,"gmtModified", LocalDateTime.now());
    }
}
