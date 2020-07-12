<template>
  <div class="app-container">
      <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <!--课程名-->
      <el-form-item>
        <el-input v-model="courseQuery.courseTitle" placeholder="课程名"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
          <el-option value='Draft'  label="未发布"/>
          <el-option value='Normal'  label="已发布"/>
        </el-select>
      </el-form-item>

    <el-form-item label="课程讲师">
        <el-select
            v-model="courseQuery.teacherId"
            placeholder="请选择">
            <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
    </el-form-item>

     <!-- 所属分类 TODO -->
    <el-form-item label="课程分类">
    <el-select
        v-model="courseQuery.subjectParentId"
        placeholder="一级分类" @change="subjectLevelOneChanged">
        <el-option
        v-for="subject in subjectOneList"
        :key="subject.id"
        :label="subject.title"
        :value="subject.id"/>
    </el-select>
    <!-- 二级分类 ,当一级分类触发onchange事件就获取一次-->
    <el-select v-model="courseQuery.subjectId" placeholder="请选择">
    <el-option
        v-for="subject in subjectTwoList"
        :key="subject.id"
        :label="subject.title"
        :value="subject.id"/>
    </el-select>

    </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

      <!--v-loading="listLoading"
      element-loading-text="数据加载中"
      显示正在加载的图标和文字-->
       <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="课程名称" width="150" />

      <el-table-column label="课程状态" width="80">
        <template slot-scope="scope">

          {{ scope.row.status=='Draft'?'未发布':'已发布' }}
        </template>
      </el-table-column>

      <el-table-column prop="lessonNum" label="课时数" />

      <el-table-column prop="gmtCreate" label="添加时间" width="80"/>

      <el-table-column prop="viewCount" label="浏览数量" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <!--用了router-link
          使用了路由（去加路由去）的方式跳转到对应页面-->
          <router-link :to="'/course/save/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改课程</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除课程</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--这个组件会在调用getList()方法时
        自动的传参 page-->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>
<script>
//引入调用course.js文件
//获取course和teacher信息
import courseApi  from '@/api/edu/course'
//获取分类信息
import subjectApi  from '@/api/edu/subject'
export default {
    //写核心代码的位置
    // data:{

    // },
    data(){//定义变量和初始值
        return{
            list:null,//查询后接口返回集合
            total:null,
            page:1,//当前页
            limit:5,//每页记录
            courseQuery:{//现在表示为课程的查询信息
            },//添加封装对象
            teacherList:[],
            subjectOneList:[],
            subjectTwoList:[]

        }
    },
    created(){//页面渲染前执行
        this.init()
    },
    methods:{
    init(){
        this.courseQuery={}
        this.getList()
        this.getOneSubject()
        this.getListTeacher()
    },
     //当一级分类改变，就会触发此方法,显示对应的二级分类
      subjectLevelOneChanged(value){
        //value就是一级分类的id值，会自动传回
        for (let i = 0; i < this.subjectOneList.length; i++) {
        if (this.subjectOneList[i].id === value) {
            this.subjectTwoList = this.subjectOneList[i].children
        }
      }
      },
      //查询所有一级分类
      getOneSubject(){
        subjectApi.getSubjectList()
        .then(response=>{
          this.subjectOneList = response.data.list
        })
       
      },
      //查询所有的讲师
      getListTeacher(){
        courseApi.getListTeacher()
        .then(response=>{
             this.teacherList= response.data.items
        })
      },    
    //创建具体的方法，调用teacher.js定义的方法
    //讲师列表的方法
    getList(page = 1){
        //默认为1，传入
        //实现页码的切换
        this.page = page;
        courseApi.pageCourseCondition(this.page,this.limit,this.courseQuery)
        .then(response=>{
           // console.log(response);
           this.list = response.data.rows
           this.total = response.data.total
        })
        .catch(error=>{
            console.log(this.courseQuery);
            console.log(this.page);
            console.log(this.limit);
        })
    },
    resetData(){
      //清空表单输入项
      this.courseQuery={}
      //重新查询
      this.getList()
    }
    ,
    removeDataById(id){
      this.$confirm('此操作将永久删除课程记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {//点击确定，执行方法
          courseApi.removeCourse(id)//删除
          .then(response => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.getList()
          })
        })
    }
  }
}
</script>