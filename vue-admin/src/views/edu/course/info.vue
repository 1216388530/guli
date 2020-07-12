<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>
<el-form label-width="120px">

  <el-form-item label="课程标题">
    <el-input v-model="courseInfo.title" placeholder=""/>
  </el-form-item>

  <!-- 所属分类 TODO -->
<el-form-item label="课程分类">
  <el-select
    v-model="courseInfo.subjectParentId"
    placeholder="一级分类" @change="subjectLevelOneChanged">
    <el-option
      v-for="subject in subjectOneList"
      :key="subject.id"
      :label="subject.title"
      :value="subject.id"/>
  </el-select>
  <!-- 二级分类 ,当一级分类触发onchange事件就获取一次-->
<el-select v-model="courseInfo.subjectId" placeholder="请选择">
  <el-option
    v-for="subject in subjectTwoList"
    :key="subject.id"
    :label="subject.title"
    :value="subject.id"/>
</el-select>
</el-form-item>

  <!-- 课程讲师 TODO -->
<!-- 课程讲师 -->
<el-form-item label="课程讲师">
  <el-select
    v-model="courseInfo.teacherId"
    placeholder="请选择">
    <el-option
      v-for="teacher in teacherList"
      :key="teacher.id"
      :label="teacher.name"
      :value="teacher.id"/>
  </el-select>
</el-form-item>

  <el-form-item label="总课时">
    <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder=""/>
  </el-form-item>

    <!-- 课程简介 TODO -->
    <!-- 课程简介-->
    <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description"/>
    </el-form-item>
    <!-- 课程封面 TODO -->
  <!-- 课程封面-->
  <el-form-item label="课程封面">

    <el-upload
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
      :action="BASE_API+'/eduOss/fileOss'"
      class="avatar-uploader">
      <img :src="courseInfo.cover">
    </el-upload>

  </el-form-item>
    <el-form-item label="课程价格">
      <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
    </el-form-item>

    <el-form-item>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
    </el-form-item>
</el-form>
  </div>
</template>
<script>
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
import courseApi  from '@/api/edu/course'
import subjectApi  from '@/api/edu/subject'
import Tinymce from '@/components/Tinymce'
export default {
   components: { Tinymce },
    data(){
        return{
            saveBtnDisabled:false,
            courseInfo:{
              title: '',
              subjectId: '',//二级分类
              subjectParentId:'',//一级分类
              teacherId: '',
              lessonNum: 0,
              description: '',
              cover: 'https://edu-1216388530-0101.oss-cn-beijing.aliyuncs.com/5607710d7aa898e408e484705d506056.jpg',
              price: 0
            },
            teacherList:[]//所有讲师的数据
            ,subjectOneList:[]
            ,subjectTwoList:[]
            ,courseId:''
            ,BASE_API: process.env.BASE_API // 接口API地址
        }
    },
    created(){
      this.init()
    },
    watch: {//监听路由变化的方式
      $route(to, from) {//当路由发生变化，这个方法就会执行
        this.init()
      }
    },
    methods:{
      init(){
        if (this.$route.params && this.$route.params.id){//修改操作
        this.courseId = this.$route.params.id
        //调用根据id获取课程的方法
        console.log("回显："+this.courseId);
        this.getInfo()
        }else{
          //添加操作
          this.getListTeacher()
          this.getOneSubject();
          this.courseInfo={
              title: '',
              subjectId: '',//二级分类
              subjectParentId:'',//一级分类
              teacherId: '',
              lessonNum: 0,
              description: '',
              cover: 'https://edu-1216388530-0101.oss-cn-beijing.aliyuncs.com/5607710d7aa898e408e484705d506056.jpg',
              price: 0
            }
            this.courseId=''
        }
      },
      getInfo(){
        courseApi.getCourseInfo(this.courseId).then(response=>{
          console.log(response.data.courseInfo);
          
          this.courseInfo = response.data.courseInfo

          //查询
          courseApi.getListTeacher()
          .then(response=>{
             this.teacherList= response.data.items
          })

          subjectApi.getSubjectList()
          .then(response=>{
          this.subjectOneList = response.data.list
          for (let i = 0; i < this.subjectOneList.length; i++) {
          if (this.subjectOneList[i].id === this.courseInfo.subjectParentId) {
            this.subjectTwoList = this.subjectOneList[i].children
            }
          }
          })
        })
      },

      handleAvatarSuccess(res,file){//上传成功
        this.courseInfo.cover = res.data.url
      },
      beforeAvatarUpload(file){//上传之前调用的方法
         const isJPG = file.type === 'image/jpeg'
          const isLt2M = file.size / 1024 / 1024 < 2

          if (!isJPG) {
            this.$message.error('上传头像图片只能是 JPG 格式!')
          }
          if (!isLt2M) {
            this.$message.error('上传头像图片大小不能超过 2MB!')
          }
          return isJPG && isLt2M
      },
      //当一级分类改变，就会触发此方法,显示对应的二级分类
      subjectLevelOneChanged(value){
        //value就是一级分类的id值，会自动传回
        for (let i = 0; i < this.subjectOneList.length; i++) {
        if (this.subjectOneList[i].id === value) {
            this.subjectTwoList = this.subjectOneList[i].children
            this.courseInfo.subjectId = '';
        }
      }
      },
      //查询所有一级分类
      getOneSubject(){
        subjectApi.getSubjectList()
        .then(response=>{
          this.subjectOneList = response.data.list
          this.courseInfo.subjectId = '';
        })
       
      },
      //查询所有的讲师
      getListTeacher(){
        courseApi.getListTeacher()
        .then(response=>{
             this.teacherList= response.data.items
        })
      },
        saveOrUpdate(){
          if(this.$route.params && this.$route.params.id){
            this.saveCourseInfo()
          }else{
            this.updateCourseInfo()
          }
        },

        saveCourseInfo(){
            courseApi.updateCourseInfo(this.courseInfo)
          .then(response=>{
            //提示信息
             this.$message({
                type: 'success',
                message: '修改课程信息成功'
            })
             //跳转到第二步
            this.$router.push({path:'/course/chapter/'+response.data.courseId})
          })
        },
        
        updateCourseInfo(){
            courseApi.addCourseInfo(this.courseInfo)
          .then(response=>{
            //提示信息
             this.$message({
                type: 'success',
                message: '添加课程信息成功'
            })
             //跳转到第二步
            this.$router.push({path:'/course/chapter/'+response.data.courseId})
          })
        }
    }
}
</script>
<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>