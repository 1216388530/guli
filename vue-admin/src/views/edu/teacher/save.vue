<template>
  <div class="app-container">
     <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
  <el-form-item label="讲师头像">

    <!-- 头衔缩略图 -->
    <pan-thumb :image="teacher.avatar"/>
    <!-- 文件上传按钮 -->
    <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
    </el-button>

    <!--
      v-show：是否显示上传组件
      :key：类似于id，如果一个页面多个图片上传控件，可以做区分
      :url：后台上传的url地址
      @close：关闭上传组件
      @crop-upload-success：上传成功后的回调
      v-show 就是根据imagecropperShow来了表示显示与否
      -->
          <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="'/eduOss/fileOss'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
          />
      </el-form-item>
      <!--:disabled="saveBtnDisabled"
          利用变量使其不能重复点击-->
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teacherApi from "@/api/edu/teacher"
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
export default {
  components: { ImageCropper, PanThumb },
  data(){
    return{
      teacher:{
        name: '',
        sort: 0,
        level: 1,
        career: '',
        intro: '',
        avatar: ''
      },//封装值
      saveBtnDisabled:false,//按钮是否禁用
      imagecropperShow:false,//上传弹框的组件能否显示
      imagecropperKey:0,//上传组件key值
    }
  },
  created(){
    this.init()
    // console.log("1111");
    // console.log("process"+process);
    
    
  },
    watch: {//监听路由变化的方式
      $route(to, from) {//当路由发生变化，这个方法就会执行
        console.log('watch $route')
        this.init()
      }
    },
  methods:{
    close(){//关闭上传弹框
      this.imagecropperShow=false;
      console.log(BASE_API);
      //上传组件初始化
      this.imagecropperKey = this.imagecropperKey+1
    },
    cropSuccess(data){//上传成功
      this.imagecropperShow=false;
      //上传后，接口返回地址
      this.teacher.avatar = data.url
      this.imagecropperKey = this.imagecropperKey+1
    },
    init(){
      //固定写法：表示得到路由里面的参数
      console.log('created')
    //id是参数名称
      if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id
      this.getInfo(id)
      }else{//没有id值
      //清空，不清空会有bug
      this.teacher ={}
      }
    },
    //根据讲师id查询的方法
    getInfo(id){
      teacherApi.getTeacherInfo(id)
      .then(response =>{
        this.teacher = response.data.teacher
      })
    },
    saveOrUpdate(){
      //判断是修改还是添加
      //根据teacher中是否有id进行判断
      if(!this.teacher.id){
        console.log("saveTeacher");
        this.saveTeacher()
      }else{
        console.log("updateTeacher");
        this.updateTeacher()
      }

    },
    saveTeacher(){
      teacherApi.addTeacher(this.teacher)
      .then(response=>{
        //提示
        this.$message({
              type: 'success',
              message: '添加成功!'
            });
        //回到列表页面，路由跳转(其实就是重定向)
        this.$router.push({path:'/teacher/table'})
      })     
    },
    //修改方法
    updateTeacher(){
      teacherApi.updateTeacherInfo(this.teacher).then(response=>{
        //提示
        this.$message({
              type: 'success',
              message: '修改成功!'
            });
        //回到列表页面，路由跳转(其实就是重定向)
        this.$router.push({path:'/teacher/table'})
      })
    }
  }
}
</script>