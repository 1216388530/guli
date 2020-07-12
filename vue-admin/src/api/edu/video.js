import request from '@/utils/request'
export default{
    addVideo(video){
        return request({
            url: `/eduService/video/addVideo`,
            method: 'post',
            data:video
          })
    }

    ,updateVideo(video){
        return request({
            url: `/eduService/video/updateVideo`,
            method: 'post',
            data:video
          })
    }
    ,deleteVideo(videoId){
        return request({
            url: `/eduService/video/${videoId}`,
            method: 'delete',
          })
    }
    ,getVideoInfo(videoId){
        return request({
            url: `/eduService/video/getVideoInfo/${videoId}`,
            method: 'get',
        })
    }
    ,deleteAliVideo(id){
        return request({
         url: `/eduVod/video/removeAliVideo/${id}`,
        method: 'delete',
        })
    }

}