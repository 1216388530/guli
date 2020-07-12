<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />

    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>
<script>
import subjectApi  from '@/api/edu/subject'
export default {

  data() {
    return {
      filterText: '',
      data2: [],//返回所有分类数据
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },
  created(){
    this.getAllSubject() 
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },

  methods: {
    getAllSubject(){
      subjectApi.getSubjectList()
      .then(response =>{
        console.log(response.data);
        this.data2 = response.data.list;  
      })
    },
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value) !== -1
    }
  }
}
</script>

