<template>
  <div class="app-container">
    <!--表单-->
    <el-form :inline="true" class="demo-form-inline">
<!--文件 =>  首选项 => 用户代码片段 => 新建全局代码片段/或文件夹代码片段：vue-html.code-snippets-->
      <el-form-item label="日期">
        <el-date-picker
          v-model="day"
          type="date"
          placeholder="选择要统计的日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>

      <el-button
        :disabled="btnDisabled"
        type="primary"
        @click="create()">生成</el-button>
    </el-form>

  </div>
</template>
<script>
import staApi from '@/api/sta'
export default {
    data(){
        return{
            day:'',
            btnDisabled:false
        }
    },
    created(){

    },
    methods:{
        create(){
            staApi.createStaData(this.day)
            .then(response=>{
                this.$message({
                    type:'success',
                    message:'生成数据成功'
                })
                //跳转到图表显示页面
                this.$router.push({path:'/sta/show'})
            })
        }  
    }
}
</script>