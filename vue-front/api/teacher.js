import request from '@/utils/request'
import cookie from 'js-cookie'
export default {
  //分页讲师查询
  getTeacherList(page,limit) {
    return request({
      url: `/eduService/teacherFront/getTeacherFrontList/${page}/${limit}`,
      method: 'post'
    })
  },
  getTeacherInfo(id){
    return request({
      url: `/eduService/teacherFront/getTeacherFrontInfo/${id}`,
      method: 'get'
    })
  }

}