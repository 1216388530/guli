import request from '@/utils/request'
export default{
//课程分类列表
    getSubjectList(){
        return request({
            url: `/eduService/subject/getAllSubject`,
            method: 'get'
          })
    }
}