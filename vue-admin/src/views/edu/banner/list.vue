<template>
  <div class="app-container">
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

      <el-table-column prop="title" label="标题" width="80" />
      <el-table-column prop="linkUrl" label="链接" width="160" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <!--用了router-link
          使用了路由（去加路由去）的方式跳转到对应页面-->
          <router-link :to="'/banner/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
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
//引入调用teacher.js文件
import bannerApi  from '@/api/edu/banner'
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
        }
    },
    created(){//页面渲染前执行
        this.getList()
    },
    methods:{//创建具体的方法，调用teacher.js定义的方法
    //讲师列表的方法
    getList(page = 1){
        //默认为1，传入
        //实现页码的切换
        this.page = page;
        bannerApi.getBannerList(this.page,this.limit)
        .then(response=>{
           // console.log(response);
           this.list = response.data.items
           this.total = response.data.total
        })
        .catch(error=>{
            console.log(error)
        })
    },
    removeDataById(id){
      this.$confirm('此操作将永久删除banner记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {//点击确定，执行方法
          bannerApi.deleteBannerById(id)//删除
          .then(response => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.getList()
          })
        })
      
      // .catch(error => {

      // })
    }
  }
}
</script>