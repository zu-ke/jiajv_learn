<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="100px">
      <el-form-item label="组名" prop="name">
        <el-input v-model="dataForm.name" placeholder="组名"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
      </el-form-item>
      <el-form-item label="说明" prop="description">
        <el-input v-model="dataForm.description" placeholder="说明"></el-input>
      </el-form-item>
      <el-form-item label="组图标" prop="icon">
        <!--<el-input v-model="dataForm.icon" placeholder="组图标"></el-input>-->
        <SingleUpload v-model="dataForm.icon"></SingleUpload>
      </el-form-item>
      <el-form-item label="所属分类 id" prop="categoryId">
        <!--<el-input v-model="dataForm.categoryId" placeholder="所属分类 id"></el-input>-->
        <el-cascader
          filterable
          placeholder="搜索"
          v-model="cascadedCategoryId"
          :options="categories"
          :props="props"
          @change="handleChange"></el-cascader>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>

import SingleUpload from '../../../components/upload/singleUpload.vue'

export default {
  components: {SingleUpload},
  data () {
    return {
      props: {
        value: 'id',
        label: 'name',
        children: 'childrenCategories'
      },
      cascadedCategoryId: [],
      categories: [],
      visible: false,
      dataForm: {
        id: 0,
        name: '',
        sort: '',
        description: '',
        icon: '',
        categoryId: ''
      },
      dataRule: {
        name: [
          {required: true, message: '组名不能为空', trigger: 'blur'}
        ],
        sort: [
          {required: true, message: '排序不能为空', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '说明不能为空', trigger: 'blur'}
        ],
        icon: [
          {required: true, message: '组图标不能为空', trigger: 'blur'}
        ],
        categoryId: [
          {required: true, message: '所属分类 id不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  created () {
    this.getCategoriesTree()
  },
  methods: {
    handleChange(){
      this.dataForm.categoryId = this.cascadedCategoryId[this.cascadedCategoryId.length - 1]
    },

    // 获取tree数据列表
    getCategoriesTree () {
      this.$http({
        // url: 'http://localhost:9090/commodity/category/list/tree',
        url: this.$http.adornUrl(`/commodity/category/list/tree`),
        method: 'get'
      }).then((data) => {
        this.categories = data.data.data
      })
    },

    init (id) {
      this.dataForm.id = id || 0
      this.cascadedCategoryId = []
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/commodity/attrgroup/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.attrgroup.name
              this.dataForm.sort = data.attrgroup.sort
              this.dataForm.description = data.attrgroup.description
              this.dataForm.icon = data.attrgroup.icon
              this.dataForm.categoryId = data.attrgroup.categoryId
              //实现每次修改，都能获取到正确的分类层级
              this.cascadedCategoryId = data.attrgroup.cascadedCategoryId
            }
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/commodity/attrgroup/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'name': this.dataForm.name,
              'sort': this.dataForm.sort,
              'description': this.dataForm.description,
              'icon': this.dataForm.icon,
              'categoryId': this.cascadedCategoryId[this.cascadedCategoryId.length - 1]
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.visible = false
                  this.$emit('refreshDataList')
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
    }
  }
}
</script>
