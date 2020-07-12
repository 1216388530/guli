import request from '@/utils/request'
import cookie from 'js-cookie'
export default {
  //登录
  submitLogin(userInfo) {
    return request({
      url: `/eduCenter/member/login`,
      method: 'post',
      data: userInfo
    })
  },
  //根据token获取用户信息
  getLoginInfo() {
    return request({
      url: `/eduCenter/member/getUserInfo`,
      method: 'get',
      headers: {'token': cookie.get('guli_token')}
    })
  }
}