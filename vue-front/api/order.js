import request from '@/utils/request'
export default {
    //生成订单接口
  createOrders(courseId) {
    return request({
      url: `/eduOrder/order/createOrder/${courseId}`,
      method: 'post'
    })
  },
  //根据id查询订单接口
  getOrdersInfo(orderId) {
    return request({
      url: `/eduOrder/order/getOrderInfo/${orderId}`,
      method: 'get'
    })
  },
  //生成二维码
  createNative(orderNo) {
    return request({
      url: `/eduOrder/payLog/createNative/${orderNo}`,
      method: 'get'
    })
  },
  //查询订单状态
  queryPayStatus(orderNo) {
    return request({
      url: `/eduOrder/payLog/queryPayStatus/${orderNo}`,
      method: 'get'
    })
  }
}