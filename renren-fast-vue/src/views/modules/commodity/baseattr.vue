<template>

  <el-row :gutter="20">
    <el-col :span="5">
      <!--
      1.
      分类树形占 5
      个单元宽度,
      保留 ref, :data, :props, node-key
      基本显示的属性
      2. node-click
      实际是 el-tree
      中规定好的实际，会传递三个参数:
      参考 elmentui
      官网
      elementui-Tree
      树形控件-Events
      -->
      <el-tree ref="categoryTree" :data="data" :props="defaultProps" node-key="id" @node-click="nodeClick">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
      </span>
      </el-tree>
    </el-col>
    <el-col :span="19">
      <!--
      说明 原家居分类属性组的表单放在这里 带 div.
      -->
      <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
          <el-form-item>
            <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="getDataList()">查询</el-button>
            <el-button v-if="isAuth('commodity:attr:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
            <el-button v-if="isAuth('commodity:attr:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
          </el-form-item>
        </el-form>
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          @selection-change="selectionChangeHandle"
          style="width: 100%;">
          <el-table-column
            type="selection"
            header-align="center"
            align="center"
            width="50">
          </el-table-column>
          <el-table-column
            prop="attrId"
            header-align="center"
            align="center"
            label="属性 id">
          </el-table-column>
          <el-table-column
            prop="attrName"
            header-align="center"
            align="center"
            label="属性名">
          </el-table-column>
          <el-table-column
            prop="searchType"
            header-align="center"
            align="center"
            label="是否需要检索">
          </el-table-column>
          <el-table-column
            prop="icon"
            header-align="center"
            align="center"
            label="图标">
          </el-table-column>
          <el-table-column
            prop="valueSelect"
            header-align="center"
            align="center"
            label="可选值列表">
          </el-table-column>
          <el-table-column
            prop="attrType"
            header-align="center"
            align="center"
            label="属性类型">
          </el-table-column>
          <el-table-column
            prop="enable"
            header-align="center"
            align="center"
            label="启用状态">
          </el-table-column>
          <el-table-column
            prop="categoryId"
            header-align="center"
            align="center"
            label="所属分类">
          </el-table-column>
          <el-table-column
            prop="showDesc"
            header-align="center"
            align="center"
            label="快速展示">
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="150"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.attrId)">修改</el-button>
              <el-button type="text" size="small" @click="deleteHandle(scope.row.attrId)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
        <!-- 弹窗, 新增 / 修改 -->
        <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
      </div>
    </el-col>
  </el-row>

</template>

<script>
  import AddOrUpdate from './attr-add-or-update'
  export default {
    data () {
      return {
        data: [],
        defaultProps: {
          children: 'childrenCategories',
          label: 'name'
        },
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    created () {
      this.getCategoriesTree()
    },
    methods: {
      // 响应用户点击树形控件的第三级菜单
      nodeClick (data) {
        if (data.catLevel === 3) {
          this.dataList.categoryId = data.id
          this.getDataList()
        }
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

      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl(`/commodity/attr/base/list/${this.dataList.categoryId ? this.dataList.categoryId : 0}`),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.attrId
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/commodity/attr/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>
