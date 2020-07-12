import request from '@/utils/request'
export default{
    //查询所有的讲师
    //根据课程id获取章节和小节的数据列表
    getAllChapterVideo(courseId){
        return request({
            url: `/eduService/chapter/getChapterVideo/`+courseId,
            method: 'get'
          })
    }
    ,addChapter(chapter){
        return request({
            url: `/eduService/chapter/addChapter`,
            method: 'post',
            data:chapter
          })
    }
    ,getChapter(chapterId){
        return request({
            url: `/eduService/chapter/getChapterInfo/${chapterId}`,
            method: 'get',
          })
    }
    ,updateChapter(chapter){
        return request({
            url: `/eduService/chapter/updateChapter`,
            method: 'post',
            data:chapter
          })
    }
    ,deleteChapter(chapterId){
        return request({
            url: `/eduService/chapter/${chapterId}`,
            method: 'delete',
          })
    }
    //-------------
    ,addVideo(video){
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
}