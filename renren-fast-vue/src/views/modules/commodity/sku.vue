<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form :inline="true" :model="dataForm">
        <el-form-item label="分类">
          <!--          <category-cascader :catalogPath.sync="catalogPath"></category-cascader>-->
          <!-- 这里我们加入分类的Cascader 级联选择器, 前面我们使用过 -->
          <el-cascader :cascadedCategoryId.sync="cascadedCategoryId" v-model="cascadedCategoryId" :options="categorys"
                       :props="props"></el-cascader>
        </el-form-item>
        <el-form-item label="品牌">
          <!--          <brand-select style="width:160px"></brand-select>-->
          <el-select placeholder="请选择" :brandId.sync="brandId" v-model="brandId" filterable clearable>
            <el-option
              v-for="item in brands"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格">
          <el-input-number style="width:160px" v-model="dataForm.price.min" :min="0"></el-input-number>
          -
          <el-input-number style="width:160px" v-model="dataForm.price.max" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="检索">
          <el-input style="width:160px" v-model="dataForm.key" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchSkuInfo">查询</el-button>
        </el-form-item>
      </el-form>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;"
      @expand-change="getSkuDetails"
    >
      <el-table-column type="expand">
        <template slot-scope="scope">
          商品标题：{{ scope.row.skuTitle }}
          <br/>
          商品副标题：{{ scope.row.skuSubtitle }}
          <br/>
          商品描述：{{ scope.row.skuDesc }}
          <br/>
          分类ID：{{ scope.row.catalogId }}
          <br/>
          SpuID：{{ scope.row.spuId }}
          <br/>
          品牌ID：{{ scope.row.brandId }}
          <br/>
        </template>
      </el-table-column>
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="skuId" header-align="center" align="center" label="skuId"></el-table-column>
      <el-table-column prop="skuName" header-align="center" align="center" label="名称"></el-table-column>
      <el-table-column prop="skuDefaultImg" header-align="center" align="center" label="默认图片">
        <template slot-scope="scope">
          <img :src="scope.row.skuDefaultImg" style="width:80px;"/>
        </template>
      </el-table-column>
      <el-table-column prop="price" header-align="center" align="center" label="价格"></el-table-column>
      <el-table-column prop="saleCount" header-align="center" align="center" label="销量"></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="previewHandle(scope.row.skuId)">预览</el-button>
          <el-button type="text" size="small" @click="commentHandle(scope.row.skuId)">评论</el-button>
          <el-dropdown
            @command="handleCommand(scope.row,$event)"
            size="small"
            split-button
            type="text"
          >
            更多
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="uploadImages">上传图片</el-dropdown-item>
              <el-dropdown-item command="seckillSettings">参与秒杀</el-dropdown-item>
              <el-dropdown-item command="reductionSettings">满减设置</el-dropdown-item>
              <el-dropdown-item command="discountSettings">折扣设置</el-dropdown-item>
              <el-dropdown-item command="memberPriceSettings">会员价格</el-dropdown-item>
              <el-dropdown-item command="stockSettings">库存管理</el-dropdown-item>
              <el-dropdown-item command="couponSettings">优惠劵</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>
  </div>
</template>

<script>
export default {
  data () {
    return {
      //品牌选择下拉相关数据变量 start
      categoryId: 0,
      brands: [
        {
          label: 'a',
          value: 1
        }
      ],

      brandId: '',
      subscribe: null,
      //品牌选择下拉相关数据变量 end

      //集成分类层级控件， 相关数据
      cascadedCategoryId: [],
      props: {      //显示返回的家居分类的哪些字段/信息
        value: 'id',
        label: 'name',
        children: 'childrenCategories'
      },
      categorys: [], //所有的家居分类
      catPathSub: null,
      brandIdSub: null,
      dataForm: {
        key: '',
        brandId: 0,
        catalogId: 0,
        price: {
          min: 0,
          max: 0
        }
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      catalogPath: []
    }
  },
  components: {},
  activated () {
    this.getDataList()
  },
  watch: {
    //如果三级分类变化，就做处理
    cascadedCategoryId (path) {
      console.log('三级分类变化了~', path)
      this.categoryId = path[path.length - 1]
      //把当前的分类id，也赋给this.dataForm.catalogId , 后面在请求检索有用
      this.dataForm.catalogId = this.categoryId
      this.getCatBrands()
    },

    //监控品牌id的变化, 如果变化就赋给this.dataForm, 后面检索需要
    brandId (val) {
      this.brandId = val
      this.dataForm.catalogId = this.categoryId
      this.dataForm.brandId = val
      console.log('brandid=', this.dataForm.brandId)
      console.log('catalogId=', this.dataForm.catalogId)
    }
  },
  //生命周期-创建完成（可以访问当前this实例）
  created () {
    this.getCategorys()
  },
  methods: {
    //获取到某个分类的品牌
    getCatBrands () {
      this.$http({
        //url: "http://localhost:9090/commodity/categorybrandrelation/brands/list",
        url: this.$http.adornUrl('/commodity/categorybrandrelation/brands/list'),
        method: 'get',
        params: this.$http.adornParams({
          catId: this.categoryId
        })
      }).then(({data}) => {
        this.brands = data.data
      })
    },

    //显示关联品牌和分类的相关方法
    getCategorys () {
      this.$http({
        //url: "http://localhost:9090/commodity/category/list/tree",
        url: this.$http.adornUrl('/commodity/category/list/tree'),
        method: 'get'
      }).then(({data}) => {
        this.categorys = data.data
      })
    },
    getSkuDetails (row, expand) {
      //sku详情查询
      console.log('展开某行...', row, expand)
    },
    //处理更多指令
    handleCommand (row, command) {
      if ('stockSettings' == command) {
        this.$router.push({path: '/ware-sku', query: {skuId: row.skuId}})
      }
    },
    searchSkuInfo () {
      this.getDataList()
    },
    // 获取数据列表
    getDataList () {
      this.dataListLoading = true
      this.$http({
        //url: "http://localhost:9090/commodity/skuinfo/list",
        url: this.$http.adornUrl('/commodity/skuinfo/list'),
        method: 'get',
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          key: this.dataForm.key,
          catalogId: this.dataForm.catalogId,
          brandId: this.dataForm.brandId,
          min: this.dataForm.price.min,
          max: this.dataForm.price.max
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
    }
  },
  mounted () {
    // this.catPathSub = PubSub.subscribe("catPath", (msg, val) => {
    //   this.dataForm.catalogId = val[val.length - 1];
    // });
    // this.brandIdSub = PubSub.subscribe("brandId", (msg, val) => {
    //   this.dataForm.brandId = val;
    // });
  },
  beforeDestroy () {
    PubSub.unsubscribe(this.catPathSub)
    PubSub.unsubscribe(this.brandIdSub)
  } //生命周期-销毁之前
}
</script>
