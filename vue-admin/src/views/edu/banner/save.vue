<template>
  <div class="app-container">
     <el-form label-width="120px">
      <el-form-item label="banner标题">
        <el-input v-model="banner.title"/>
      </el-form-item>
            <el-form-item label="banner链接">
        <el-input v-model="banner.linkUrl"/>
      </el-form-item>
      <el-form-item label="banner排序">
        <el-input-number v-model="banner.sort" controls-position="right"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
  <el-form-item label="轮播图">
    <!-- 头衔缩略图 -->
    <pan-thumb :image="banner.imageUrl"/>
    <!-- 文件上传按钮 -->
    <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换轮播图
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
import bannerApi from "@/api/edu/banner"
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
export default {
  components: { ImageCropper, PanThumb },
  data(){
    return{
      banner:{
        title: '',
        sort: 0,
        imageUrl: ''
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
      this.banner.imageUrl = data.url
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
      this.banner ={}
      }
    },
    //根据banner id查询的方法
    getInfo(id){
      bannerApi.getBannerInfo(id)
      .then(response =>{
        this.banner = response.data.item
      })
    },
    saveOrUpdate(){
      //判断是修改还是添加
      //根据teacher中是否有id进行判断
      if(!this.banner.id){
        console.log("saveBanner");
        this.saveBanner()
      }else{
        console.log("updateBanner");
        this.updateBanner()
      }

    },
    saveBanner(){
      bannerApi.addBanner(this.banner)
      .then(response=>{
        //提示
        this.$message({
              type: 'success',
              message: '添加成功!'
            });
        //回到列表页面，路由跳转(其实就是重定向)
        this.$router.push({path:'/banner/table'})
      })     
    },
    //修改方法
    updateBanner(){
      bannerApi.updateBannerInfo(this.banner).then(response=>{
        //提示
        this.$message({
              type: 'success',
              message: '修改成功!'
            });
        //回到列表页面，路由跳转(其实就是重定向)
        this.$router.push({path:'/banner/table'})
      })
    }
  }
}
</script>