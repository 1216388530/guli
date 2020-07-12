import request from '@/utils/request'

export default {

  getCommentList(page, limit, courseId) {
    return request({
      url: `/eduService/comment/getPageComments/${page}/${limit}`,
      method: 'get',
      params: {courseId}
    })
  },
  addComment(comment) {
    return request({
      url: `/eduService/comment/insertComment`,
      method: 'post',
      data: comment
    })
  }
}