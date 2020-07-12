<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="'/static/cage.xlsx'">点击下载模版</a>
        </el-tag>

      </el-form-item>

      <el-form-item label="选择Excel">
          <!--
          ref：唯一标识
          :auto-upload=是否自动上传
          :on-success=上传成功方法
          :on-error=上传失败方法
          :disabled="importBtnDisabled"
          :limit="1"每次只能传一个文件
          :action="BASE_API+'/admin/edu/subject/
          name="file"
          accept="application/vnd.ms-excel">传微软excel

          -->
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API+'/eduService/subject/addSubject'"
          name="file"
          accept='application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel'>
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            :loading="loading"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload">上传到服务器</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
    data(){
        return{
        BASE_API: process.env.BASE_API, // 接口API地址
        importBtnDisabled: false, // 按钮是否禁用,
        loading: false
        }
    },
    created(){

    },
    methods:{
        //上传文件到接口，只是表单提交
        submitUpload(){
          
            this.fileUploadBtnText = '正在上传'
            this.importBtnDisabled = true
            this.loading = true
            //ref=upload:document.getElementById("upload").submit()
            this.$refs.upload.submit()
        },
        //上传成功后
        fileUploadSuccess(){
            this.loading = false
            this.$message({
                type: 'success',
                message: '添加课程分类成功'
            })
            //跳转到课程分类list
            //路由跳转
            this.$router.push({path:'/subject/list'})
        },
        //上传失败后
        fileUploadError(){
            this.loading = false
            this.$message({
            type: 'error',
            message: '添加课程分类失败'
            })
        }

    }
}
</script>