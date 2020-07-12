import request from '@/utils/request'
export default{
    //分页查询
    getBannerList(current,limit){
        return request({
            url: `/eduCms/bannerAdmin/pageBanner/${current}/${limit}`,
            method: 'get',
          })
    },
    //根据id删除讲师
    //传3个参数
    deleteBannerById(id){
        return request({
            url: `/eduCms/bannerAdmin/remove/${id}`,
            method: 'delete'
          })
    },
    addBanner(banner){
        return request({
            url: `/eduCms/bannerAdmin/addBanner`,
            method: 'post',
            data:banner
          })
    },//根据id查询讲师
    getBannerInfo(id){
        return request({
            url: `/eduCms/bannerAdmin/get/${id}`,
            method: 'get'
          })
    },//修改讲师
    updateBannerInfo(banner){
        return request({
            url: `/eduCms/bannerAdmin/update`,
            method: 'post',
            data:banner
          })
    }
}
