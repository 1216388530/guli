import request from '@/utils/request'
export default{
    //生成统计数据
    createStaData(day){
        return request({
            url: `/staService/sta/registerCount/${day}`,
            method: 'post',
          })
    },
    getDataSta(searchObj){
        return request({
            url: `/staService/sta/showData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method: 'get',
          })
    }
}