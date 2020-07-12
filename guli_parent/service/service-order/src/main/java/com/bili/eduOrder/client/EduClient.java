package com.bili.eduOrder.client;

import com.bili.commonUtils.orderVo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name="service-edu")
public interface EduClient {
    //根据课程id获取课程的标题，头像，教师名称，价格
    @PostMapping("/eduService/courseFront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);

}
