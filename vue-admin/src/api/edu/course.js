import request from '@/utils/request'
export default{
//添加课程信息功能
    addCourseInfo(courseInfo){
        return request({
            url: `/eduService/course/addCourseInfo`,
            method: 'post',
            data:courseInfo
          })
    },
    //查询所有的讲师
    getListTeacher(){
        return request({
            url: `/eduService/teacher/findAll`,
            method: 'get'
          })
    },
    //根据课程id查询课程的基本信息
    getCourseInfo(id){
        return request({
            url: `/eduService/course/getCourseInfo/${id}`,
            method: 'get'
          })
    },
    //修改课程信息
    updateCourseInfo(courseInfo){
        return request({
            url: `/eduService/course/updateCourseInfo`,
            method: 'post',
            data:courseInfo
          })
    },
    //课程确认信息
    //根据课程id查询课程的基本信息
    getPublishCourseInfo(id){
        return request({
            url: `/eduService/course/getPublishCourseInfo/${id}`,
            method: 'get'
          })
    },
    publishCourse(id){
        return request({
            url: `/eduService/course/publishCourse/${id}/Normal`,
            method: 'get'
          })
    },
    //分页条件查询
    pageCourseCondition(current,limit,courseQuery){
        return request({
            url: `/eduService/course/pageCourseCondition/${current}/${limit}`,
            method: 'post',
            data:courseQuery
        })
    },
    removeCourse(courseId){
        return request({
            url: `/eduService/course/${courseId}`,
            method: 'delete'
        })
    }

}