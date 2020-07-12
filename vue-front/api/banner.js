import request from '@/utils/request'
export default {
    //查询前两条banner信息
  getListBanner() {
    return request({
      url: `/eduCms/bannerFront/getAllBanner`,
      method: 'get'
    })
  }
}