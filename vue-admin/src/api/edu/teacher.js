import request from '@/utils/request'
export default{
    //讲师列表
    //传3个参数
    getTeacherListPage(current,limit,teacherQuery){
        return request({
            url: `/eduService/teacher/pageTeacherCondition/${current}/${limit}`,
            method: 'post',
            //teacherQuery，后端使用RequestBody获取数据,需要这么写
            //data 将对象json化
            data:teacherQuery
          })
    },
    //根据id删除讲师
    //传3个参数
    deleteTeacherById(id){
        return request({
            url: `/eduService/teacher/${id}`,
            method: 'delete'
          })
    },
    addTeacher(teacher){
        return request({
            url: `/eduService/teacher/addTeacher`,
            method: 'post',
            data:teacher
          })
    },//根据id查询讲师
    getTeacherInfo(id){
        return request({
            url: `/eduService/teacher/getTeacher/${id}`,
            method: 'get'
          })
    },//修改讲师
    updateTeacherInfo(teacher){
        return request({
            url: `/eduService/teacher/updateTeacher`,
            method: 'post',
            data:teacher
          })
    }


}
