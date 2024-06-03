<template>
  <div>
    <el-button type="danger" size="small" @click="batchDelete">删除</el-button>
    <el-tree :data="data"
             :props="defaultProps"
             ref="categoryTree"
             show-checkbox
             :default-expanded-keys="expandedKey"
             node-key="id"
             :expand-on-click-node="false">
          <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button
            v-if="node.level <= 2"
            type="text"
            size="mini"
            @click="() => append(data)">
            Append
          </el-button>
          <!--引入修改按钮-->
          <el-button
            type="text"
            size="mini"
            @click="() => edit(data)">
            edit
          </el-button>
          <el-button
            v-if="node.childNodes.length == 0"
            type="text"
            size="mini"
            @click="() => remove(node, data)">
            Delete
          </el-button>
        </span>
      </span>
    </el-tree>

    <!--引入了对话框-->
    <!--
        1. :visible.sync="dialogFormVisible" 动态绑定dialogFormVisible，为true则显示对话框
        2. :model="category" 动态绑定数据对象
    -->
    <el-dialog title="添加分类" :visible.sync="dialogVisible" width="30%">
      <el-form :model="category">
        <el-form-item label="分类名">
          <el-input v-model="category.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="category.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="单位">
          <el-input v-model="category.proUnit" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCategory">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Log from '../sys/log.vue'

export default {
  data () {
    return {
      category: {},  //分类对象数据
      dialogVisible: false, //对话框显示
      data: [],
      expandedKey: [],
      defaultProps: {
        children: 'childrenCategories',
        label: 'name'
      }
    }
  },
  created () {
    this.getCategoriesTree()
  },
  methods: {
    //处理批量删除
    batchDelete(){
      //通过ref="categoryTree"获取勾选的分类信息
      /**
       * 解读 this.$refs.categoryTree.getCheckedNodes(leafOnly, includeHalfChecked)
       * 1. this.$refs.categoryTree : 代表 el-tree 控件
       * 2. getCheckedNodes(leafOnly, includeHalfChecked)
       * 2.1 若节点可被选择（即 show-checkbox 为 true），则返回目前被选中的节点所组成的数组
       * 2.2 (leafOnly, includeHalfChecked) 接收两个 boolean 类型的参数，
       * (1). 是否只是叶子节点，默认值为 false (2). 是否包含半选节点，默认值为 false
       * 2.3 老师没有传入参数 , 等价getCheckedNodes(false, false), 即返回选中的分类节点(不包括半选的)
       * 2.4 半选就是某个分类的子分类, 只选择了部分，没有全选，是 - 符号
       */
      var checkedNodes = this.$refs.categoryTree.getCheckedNodes()
      // 收集选中的id和选中的分类名
      var ids = []
      var categoryNames = []
      for (var i = 0; i < checkedNodes.length; i++){
        ids.push(checkedNodes[i].id)
        categoryNames.push(checkedNodes[i].name)
      }
      // console.log(ids)
      // console.log(categoryNames)

      // 给出提示，让用户确定，逻辑删除
      this.$confirm(`是否批量删除【${categoryNames}】菜单`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //如果点击确认，就发出请求批量删除
        this.$http({
          // url: 'http://localhost:9090/commodity/category/delete',
          url: this.$http.adornUrl(`/commodity/category/delete`),
          method: 'post',
          data: this.$http.adornData(ids, false) //发出请求时，携带的数据
        }).then((data) => {
          this.$message({
            message: '批量删除成功',
            type: 'success',
          })
          this.getCategoriesTree()
        })
      }).catch(() => {
        //如果点击取消，就不删除
      })
    },

    //根据id查询分类信息
    // 1. 打开对话框
    // 2. 修改分类信息--回显
    edit (data) {
      // console.log(data)
      this.$http({
        // url: `http://localhost:9090/commodity/category/info/${data.id}`,
        url: this.$http.adornUrl(`/commodity/category/info/${data.id}`),
        method: 'get',
      }).then((data) => {
        // console.log("data==>",data.data.category)
        this.category = data.data.category //也可以有选择性的绑定
        this.dialogVisible = true
      })
    },

    //处理点击添加分类的对话框的确定按钮时发送请求添加数据
    //处理修改对象时发送数据修改数据
    addCategory () {
      //如果点击确认，就发出请求删除
      if (this.category.id){
        //修改
        this.$http({
          // url: 'http://localhost:9090/commodity/category/update',
          url: this.$http.adornUrl(`/commodity/category/update`),
          method: 'post',
          data: this.$http.adornData(this.category, false) //发出请求时，携带的数据
        }).then((data) => {
          this.$message({
            message: '修改成功',
            type: 'success',
          })
          this.dialogVisible = false
          this.getCategoriesTree()
          this.expandedKey = [this.category.parentId]
        })

      }else {
        //添加
        this.$http({
          // url: 'http://localhost:9090/commodity/category/save',
          url: this.$http.adornUrl(`/commodity/category/save`),
          method: 'post',
          data: this.$http.adornData(this.category, false) //发出请求时，携带的数据
        }).then((data) => {
          this.$message({
            message: '新增成功',
            type: 'success',
          })
          this.dialogVisible = false
          this.getCategoriesTree()
          this.expandedKey = [this.category.parentId]
        })
      }

    },

    //处理点击删除分类
    remove (node, data) {
      // console.log("node",node);
      // console.log("data",data);
      var ids = [data.id]
      this.$confirm(`是否删除【${data.name}】菜单`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //如果点击确认，就发出请求删除
        this.$http({
          // url: 'http://localhost:9090/commodity/category/delete',
          url: this.$http.adornUrl(`/commodity/category/delete`),
          method: 'post',
          data: this.$http.adornData(ids, false) //发出请求时，携带的数据
        }).then((data) => {
          this.$message({
            message: '操作成功',
            type: 'success',
          })
          this.getCategoriesTree()
          this.expandedKey = [node.parent.data.id]
        })
      }).catch(() => {
        //如果点击取消，就不删除
      })
    },

    //处理点击显示添加分类的对话框
    append (data) {
      this.category = { //分类对象数据
        id: null,
        name: '',
        parentId: data.id,
        catLevel: data.catLevel * 1 + 1, //添加分类，为其父类的层级加1，这里 * 1 是为了将字符串转成数值
        isShow: 1,
        sort: 0,
        icon: '',
        proUnit: '',
        proCount: 0
      }, //初始化表单的对象数据
      this.dialogVisible = true
    },

    // 获取tree数据列表
    getCategoriesTree () {
      this.$http({
        // url: 'http://localhost:9090/commodity/category/list/tree',
        url: this.$http.adornUrl(`/commodity/category/list/tree`),
        method: 'get'
      }).then((data) => {
        this.data = data.data.data
      })
    },

  }
}
</script>

<style scoped lang="scss">

</style>
