<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-steps :active="step" finish-status="success">
          <el-step title="基本信息"></el-step>
          <el-step title="基本属性/规格参数"></el-step>
          <el-step title="销售属性"></el-step>
          <el-step title="SKU信息"></el-step>
          <el-step title="保存完成"></el-step>
        </el-steps>
      </el-col>
      <el-col :span="24" v-show="step==0">
        <el-card class="box-card" style="width:80%;margin:20px auto">
          <el-form ref="spuBaseForm" :model="spu" label-width="120px" :rules="spuBaseInfoRules">
            <el-form-item label="商品名称" prop="spuName">
              <el-input v-model="spu.spuName"></el-input>
            </el-form-item>
            <el-form-item label="商品描述" prop="spuDescription">
              <el-input v-model="spu.spuDescription"></el-input>
            </el-form-item>
            <el-form-item label="选择分类" prop="catalogId">
              <!--              <category-cascader></category-cascader>-->
              <!-- 这里我们加入分类的Cascader 级联选择器, 前面我们使用过 -->
              <el-cascader :cascadedCategoryId.sync="cascadedCategoryId" v-model="cascadedCategoryId" :options="categorys" :props="props"></el-cascader>
            </el-form-item>
            <el-form-item label="选择品牌" prop="brandId">
              <!--              <brand-select></brand-select>-->
              <el-select placeholder="请选择" :brandId.sync="brandId" v-model="brandId" filterable clearable>
                <el-option
                  v-for="item in brands"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="商品重量(Kg)" prop="weight">
              <el-input-number v-model.number="spu.weight" :min="0" :precision="3" :step="0.1"></el-input-number>
            </el-form-item>
            <el-form-item label="设置积分" prop="bounds">
              <label>金币</label>
              <el-input-number
                style="width:130px"
                placeholder="金币"
                v-model="spu.bounds.buyBounds"
                :min="0"
                controls-position="right"
              ></el-input-number>
              <label style="margin-left:15px">成长值</label>
              <el-input-number
                style="width:130px"
                placeholder="成长值"
                v-model="spu.bounds.growBounds"
                :min="0"
                controls-position="right"
              >
                <template slot="prepend">成长值</template>
              </el-input-number>
            </el-form-item>
            <el-form-item label="商品介绍" prop="decript">
              <multi-upload v-model="spu.decript"></multi-upload>
            </el-form-item>

            <el-form-item label="商品图集" prop="images">
              <multi-upload v-model="spu.images"></multi-upload>
            </el-form-item>
            <el-form-item>

              <el-button type="success" @click="collectSpuBaseInfo">下一步：设置基本参数</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="24" v-show="step==1">
        <el-card class="box-card" style="width:80%;margin:20px auto">
          <el-tabs tab-position="left" style="width:98%">
            <el-tab-pane
              :label="group.name"
              v-for="(group,gidx) in dataResp.attrGroups"
              :key="group.attrGroupId"
            >
              <!-- 遍历属性,每个tab-pane对应一个表单，每个属性是一个表单项  spu.baseAttrs[0] = [{attrId:xx,val:}]-->
              <el-form ref="form" :model="spu">
                <el-form-item
                  :label="attr.attrName"
                  v-for="(attr,aidx) in group.attrs"
                  :key="attr.attrId"
                >
                  <el-input
                    v-model="dataResp.baseAttrs[gidx][aidx].attrId"
                    type="hidden"
                    v-show="false"
                  ></el-input>
                  <el-select
                    v-model="dataResp.baseAttrs[gidx][aidx].attrValues"
                    :multiple="attr.valueType == 1"
                    filterable
                    allow-create
                    default-first-option
                    placeholder="请选择或输入值"
                  >
                    <el-option
                      v-for="(val,vidx) in attr.valueSelect.split(',')"
                      :key="vidx"
                      :label="val"
                      :value="val"
                    ></el-option>
                  </el-select>
                  <el-checkbox
                    v-model="dataResp.baseAttrs[gidx][aidx].showDesc"
                    :true-label="1"
                    :false-label="0"
                  >快速展示
                  </el-checkbox>
                </el-form-item>
              </el-form>
            </el-tab-pane>
          </el-tabs>
          <div style="margin:auto">
            <el-button type="primary" @click="step = 0">上一步</el-button>
            <el-button type="success" @click="generateSaleAttrs">下一步：设置销售属性</el-button>
          </div>
        </el-card>
      </el-col>
      <el-col :span="24" v-show="step==2">
        <el-card class="box-card" style="width:80%;margin:20px auto">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>选择销售属性</span>
              <el-form ref="saleform" :model="spu">
                <el-form-item
                  :label="attr.attrName"
                  v-for="(attr,aidx) in dataResp.saleAttrs"
                  :key="attr.attrId"
                >
                  <el-input
                    v-model="dataResp.tempSaleAttrs[aidx].attrId"
                    type="hidden"
                    v-show="false"
                  ></el-input>
                  <el-checkbox-group v-model="dataResp.tempSaleAttrs[aidx].attrValues">
                    <el-checkbox
                      v-if="dataResp.saleAttrs[aidx].valueSelect != ''"
                      :label="val"
                      v-for="val in dataResp.saleAttrs[aidx].valueSelect.split(',')"
                      :key="val"
                    ></el-checkbox>
                    <div style="margin-left:20px;display:inline">
                      <el-button
                        v-show="!inputVisible[aidx].view"
                        class="button-new-tag"
                        size="mini"
                        @click="showInput(aidx)"
                      >+自定义
                      </el-button>
                      <el-input
                        v-show="inputVisible[aidx].view"
                        v-model="inputValue[aidx].val"
                        :ref="'saveTagInput'+aidx"
                        size="mini"
                        style="width:150px"
                        @keyup.enter.native="handleInputConfirm(aidx)"
                        @blur="handleInputConfirm(aidx)"
                      ></el-input>
                    </div>
                  </el-checkbox-group>
                </el-form-item>
              </el-form>
            </div>
            <el-button type="primary" @click="step = 1">上一步</el-button>
            <el-button type="success" @click="generateSkus">下一步：设置SKU信息</el-button>
          </el-card>
        </el-card>
      </el-col>
      <el-col :span="24" v-show="step==3">
        <el-card class="box-card" style="width:80%;margin:20px auto">
          <el-table :data="spu.skus" style="width: 100%">
            <el-table-column label="属性组合">
              <el-table-column
                :label="item.attrName"
                v-for="(item,index) in dataResp.tableAttrColumn"
                :key="item.attrId"
              >
                <template slot-scope="scope">
                  <span style="margin-left: 10px">{{ scope.row.attr[index].attrValue }}</span>
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column label="商品名称" prop="skuName">
              <template slot-scope="scope">
                <el-input v-model="scope.row.skuName"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="标题" prop="skuTitle">
              <template slot-scope="scope">
                <el-input v-model="scope.row.skuTitle"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="副标题" prop="skuSubtitle">
              <template slot-scope="scope">
                <el-input v-model="scope.row.skuSubtitle"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="价格" prop="price">
              <template slot-scope="scope">
                <el-input v-model="scope.row.price"></el-input>
              </template>
            </el-table-column>
            <el-table-column type="expand">
              <template slot-scope="scope">
                <el-row>
                  <el-col :span="24">
                    <label style="display:block;float:left">选择图集 或</label>
                    <multi-upload
                      style="float:left;margin-left:10px;"
                      :showFile="false"
                      :listType="'text'"
                      v-model="uploadImages"
                    ></multi-upload>
                  </el-col>
                  <el-col :span="24">
                    <el-divider></el-divider>
                  </el-col>
                  <el-col :span="24">
                    <el-card
                      style="width:170px;float:left;margin-left:15px;margin-top:15px;"
                      :body-style="{ padding: '0px' }"
                      v-for="(img,index) in spu.images"
                      :key="index"
                    >
                      <img :src="img" style="width:160px;height:120px"/>
                      <div style="padding: 14px;">
                        <el-row>
                          <el-col :span="12">
                            <el-checkbox
                              v-model="scope.row.images[index].imgUrl"
                              :true-label="img"
                              false-label
                            ></el-checkbox>
                          </el-col>
                          <el-col :span="12">
                            <el-tag v-if="scope.row.images[index].defaultImg == 1">
                              <input
                                type="radio"
                                checked
                                :name="scope.row.skuName"
                                @change="checkDefaultImg(scope.row,index,img)"
                              />设为默认
                            </el-tag>
                            <el-tag v-else>
                              <input
                                type="radio"
                                :name="scope.row.skuName"
                                @change="checkDefaultImg(scope.row,index,img)"
                              />设为默认
                            </el-tag>
                          </el-col>
                        </el-row>
                      </div>
                    </el-card>
                  </el-col>
                </el-row>
                <!-- 折扣，满减，会员价 -->
                <el-form :model="scope.row">
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="设置折扣">
                        <label>满</label>
                        <el-input-number
                          style="width:160px"
                          :min="0"
                          controls-position="right"
                          v-model="scope.row.fullCount"
                        ></el-input-number>
                        <label>件</label>

                        <label style="margin-left:15px;">打</label>
                        <el-input-number
                          style="width:160px"
                          v-model="scope.row.discount"
                          :precision="2"
                          :max="1"
                          :min="0"
                          :step="0.01"
                          controls-position="right"
                        ></el-input-number>
                        <label>折</label>
                        <el-checkbox
                          v-model="scope.row.countStatus"
                          :true-label="1"
                          :false-label="0"
                        >可叠加优惠
                        </el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="设置满减">
                        <label>满</label>
                        <el-input-number
                          style="width:160px"
                          v-model="scope.row.fullPrice"
                          :step="100"
                          :min="0"
                          controls-position="right"
                        ></el-input-number>
                        <label>元</label>
                        <label style="margin-left:15px;">减</label>
                        <el-input-number
                          style="width:160px"
                          v-model="scope.row.reducePrice"
                          :step="10"
                          :min="0"
                          controls-position="right"
                        ></el-input-number>
                        <label>元</label>
                        <el-checkbox
                          v-model="scope.row.priceStatus"
                          :true-label="1"
                          :false-label="0"
                        >可叠加优惠
                        </el-checkbox>
                      </el-form-item>
                    </el-col>

                    <el-col :span="24">
                      <el-form-item label="设置会员价" v-if="scope.row.memberPrice.length>0">
                        <br/>
                        <!--   @change="handlePriceChange(scope,mpidx,$event)" -->
                        <el-form-item v-for="(mp,mpidx) in scope.row.memberPrice" :key="mp.id">
                          {{ mp.name }}
                          <el-input-number
                            style="width:160px"
                            v-model="scope.row.memberPrice[mpidx].price"
                            :precision="2"
                            :min="0"
                            controls-position="right"
                          ></el-input-number>
                        </el-form-item>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-form>
              </template>
            </el-table-column>
          </el-table>
          <el-button type="primary" @click="step = 2">上一步</el-button>
          <el-button type="success" @click="submitSkus">下一步：保存商品信息</el-button>
        </el-card>
      </el-col>
      <el-col :span="24" v-show="step==4">
        <el-card class="box-card" style="width:80%;margin:20px auto">
          <h1>保存成功</h1>
          <el-button type="primary" @click="addAgian">继续添加</el-button>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

import MultiUpload from "@/components/upload/multiUpload";

export default {
  //import引入的组件需要注入到对象中才能使用
  components: {MultiUpload},
  props: {},
  data() {
    return {

      //品牌选择下拉相关数据变量 start
      categoryId: 0,
      brands: [
        {
          label: "a",
          value: 1
        }
      ],

      brandId: "",
      subscribe: null,
      //品牌选择下拉相关数据变量 end

      //集成分类层级控件， 相关数据
      cascadedCategoryId: [],
      props: {      //显示返回的家居分类的哪些字段/信息
        value: "id",
        label: "name",
        children: "childrenCategories"
      },
      categorys: [], //所有的家居分类


      catPathSub: null,
      brandIdSub: null,
      uploadDialogVisible: false,
      uploadImages: [],
      step: 0,
      //spu_name  spu_description  catalog_id  brand_id  weight  publish_status
      spu: {
        //要提交的数据
        spuName: "",
        spuDescription: "",
        catalogId: 0,
        brandId: "",
        weight: "",
        publishStatus: 0,
        decript: [], //商品详情
        images: [], //商品图集，最后sku也可以新增
        bounds: {
          //积分
          buyBounds: 0,
          growBounds: 0
        },
        baseAttrs: [], //基本属性
        skus: [] //所有sku信息
      },
      spuBaseInfoRules: {
        // spuName: [
        //   {required: true, message: "请输入商品名字", trigger: "blur"}
        // ],
        // spuDescription: [
        //   {required: true, message: "请编写一个简单描述", trigger: "blur"}
        // ],
        // catalogId: [
        //   {required: true, message: "请选择一个分类", trigger: "blur"}
        // ],
        // brandId: [
        //   {required: true, message: "请选择一个品牌", trigger: "blur"}
        // ],
        // decript: [
        //   {required: true, message: "请上传商品详情图集", trigger: "blur"}
        // ],
        // images: [
        //   {required: true, message: "请上传商品图片集", trigger: "blur"}
        // ],
        // weight: [
        //   {
        //     type: "number",
        //     required: true,
        //     message: "请填写正确的重量值",
        //     trigger: "blur"
        //   }
        // ]
      },
      dataResp: {
        //后台返回的所有数据
        attrGroups: [],
        baseAttrs: [],
        saleAttrs: [],
        tempSaleAttrs: [],
        tableAttrColumn: [],
        memberLevels: [],
        steped: [false, false, false, false, false]
      },
      inputVisible: [],
      inputValue: []
    };
  },
  //计算属性类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {

    //如果三级分类变化，就做处理
    cascadedCategoryId(path) {
      console.log("三级分类变化了~", path);
      this.categoryId = path[path.length - 1];
      //把当前的分类id，也赋给this.spu.catalogId , 后面在请求分类下的属性组和属性有用
      this.spu.catalogId = this.categoryId;
      this.getCatBrands();
    },

    //监控品牌id的变化, 如果变化就赋给spu的brandId
    brandId(val) {
      this.spu.brandId = val;
      //this.PubSub.publish("brandId", val);
    },

    // uploadImages(val) {
    //   //扩展每个skus里面的imgs选项
    //   let imgArr = Array.from(new Set(this.spu.images.concat(val)));
    //
    //   //{imgUrl:"",defaultImg:0} 由于concat每次迭代上次，有很多重复。所以我们必须得到上次+这次的总长
    //
    //   this.spu.skus.forEach((item, index) => {
    //     let len = imgArr.length - this.spu.skus[index].images.length; //还差这么多
    //     if (len > 0) {
    //       let imgs = new Array(len);
    //       imgs = imgs.fill({imgUrl: "", defaultImg: 0});
    //       this.spu.skus[index].images = item.images.concat(imgs);
    //     }
    //   });
    //
    //   this.spu.images = imgArr; //去重
    //   console.log("this.spu.skus", this.spu.skus);
    //}
  },
  //方法集合
  methods: {
    //获取到某个分类的品牌
    getCatBrands() {
      this.$http({
        //url: "http://localhost:9090/commodity/categorybrandrelation/brands/list",
        url: this.$http.adornUrl("/commodity/categorybrandrelation/brands/list"),
        method: "get",
        params: this.$http.adornParams({
          catId: this.categoryId
        })
      }).then(({data}) => {
        this.brands = data.data;

      });
    },

    //显示关联品牌和分类的相关方法
    getCategorys() {
      this.$http({
        //url: "http://localhost:9090/commodity/category/list/tree",
        url: this.$http.adornUrl("/commodity/category/list/tree"),
        method: "get"
      }).then(({data}) => {
        this.categorys = data.data;
      });
    },
    addAgian() {
      this.step = 0;
      this.resetSpuData();
    },
    resetSpuData() {
      this.spu = {
        spuName: "",
        spuDescription: "",
        catalogId: 0,
        brandId: "",
        weight: "",
        publishStatus: 0,
        decript: [],
        images: [],
        bounds: {
          buyBounds: 0,
          growBounds: 0
        },
        baseAttrs: [],
        skus: []
      };
    },
    handlePriceChange(scope, mpidx, e) {
      this.spu.skus[scope.$index].memberPrice[mpidx].price = e;
    },
    getMemberLevels() {
      this.$http({
        url: this.$http.adornUrl("/member/memberlevel/list"),
        method: "get",
        params: this.$http.adornParams({
          page: 1,
          limit: 500
        })
      })
        .then(({data}) => {
          this.dataResp.memberLevels = data.page.list;
        })
        .catch(e => {
          console.log(e);
        });
    },
    showInput(idx) {
      this.inputVisible[idx].view = true;
      // this.$refs['saveTagInput'+idx].$refs.input.focus();
    },
    checkDefaultImg(row, index, img) {
      console.log("默认图片", row, index);
      //这个图片被选中了，
      row.images[index].imgUrl = img; //默认选中
      row.images[index].defaultImg = 1; //修改标志位;
      //修改其他人的标志位
      row.images.forEach((item, idx) => {
        if (idx != index) {
          row.images[idx].defaultImg = 0;
        }
      });
    },
    handleInputConfirm(idx) {
      let inputValue = this.inputValue[idx].val;
      if (inputValue) {
        // this.dynamicTags.push(inputValue);
        if (this.dataResp.saleAttrs[idx].valueSelect == "") {
          this.dataResp.saleAttrs[idx].valueSelect = inputValue;
        } else {
          this.dataResp.saleAttrs[idx].valueSelect += ";" + inputValue;
        }
      }
      this.inputVisible[idx].view = false;
      this.inputValue[idx].val = "";
    },
    //得到当前分类的基本属性
    collectSpuBaseInfo() {
      this.$refs.spuBaseForm.validate(valid => {
        if (valid) {
          this.step = 1;
          this.showBaseAttrs();
        } else {
          return false;
        }
      });
    },
    generateSaleAttrs() {
      //把页面绑定的所有attr处理到spu里面,这一步都要做
      this.spu.baseAttrs = [];
      this.dataResp.baseAttrs.forEach(item => {
        item.forEach(attr => {
          let {attrId, attrValues, showDesc} = attr;
          //跳过没有录入值的属性
          if (attrValues != "") {
            if (attrValues instanceof Array) {
              //多个值用;隔开
              attrValues = attrValues.join(";");
            }
            this.spu.baseAttrs.push({attrId, attrValues, showDesc});
          }
        });
      });
      console.log("baseAttrs", this.spu.baseAttrs);
      this.step = 2;
      this.getShowSaleAttr();
    },
    generateSkus() {
      this.step = 3;

      //根据笛卡尔积运算进行生成sku
      let selectValues = [];
      this.dataResp.tableAttrColumn = [];
      this.dataResp.tempSaleAttrs.forEach(item => {
        if (item.attrValues.length > 0) {
          selectValues.push(item.attrValues);
          this.dataResp.tableAttrColumn.push(item);
        }
      });

      let descartes = this.descartes(selectValues);
      console.log("生成的组合", JSON.stringify(descartes));
      //有多少descartes就有多少sku
      let skus = [];

      descartes.forEach((descar, descaridx) => {
        let attrArray = []; //sku属性组
        descar.forEach((de, index) => {
          //构造saleAttr信息
          let saleAttrItem = {
            attrId: this.dataResp.tableAttrColumn[index].attrId,
            attrName: this.dataResp.tableAttrColumn[index].attrName,
            attrValue: de
          };
          attrArray.push(saleAttrItem);
        });
        //先初始化几个images，后面的上传还要加
        let imgs = [];
        this.spu.images.forEach((img, idx) => {
          imgs.push({imgUrl: "", defaultImg: 0});
        });

        //会员价，也必须在循环里面生成，否则会导致数据绑定问题
        let memberPrices = [];
        if (this.dataResp.memberLevels.length > 0) {
          for (let i = 0; i < this.dataResp.memberLevels.length; i++) {
            if (this.dataResp.memberLevels[i].priviledgeMemberPrice == 1) {
              memberPrices.push({
                id: this.dataResp.memberLevels[i].id,
                name: this.dataResp.memberLevels[i].name,
                price: 0
              });
            }
          }
        }
        //;descaridx，判断如果之前有就用之前的值;
        let res = this.hasAndReturnSku(this.spu.skus, descar);
        if (res === null) {
          skus.push({
            attr: attrArray,
            skuName: this.spu.spuName + " " + descar.join(" "),
            price: 0,
            skuTitle: this.spu.spuName + " " + descar.join(" "),
            skuSubtitle: "",
            images: imgs,
            descar: descar,
            fullCount: 0,
            discount: 0,
            countStatus: 0,
            fullPrice: 0.0,
            reducePrice: 0.0,
            priceStatus: 0,
            memberPrice: new Array().concat(memberPrices)
          });
        } else {
          skus.push(res);
        }
      });
      this.spu.skus = skus;
      console.log("结果!!!", this.spu.skus, this.dataResp.tableAttrColumn);
    },
    //判断如果包含之前的sku的descar组合，就返回这个sku的详细信息；
    hasAndReturnSku(skus, descar) {
      let res = null;
      if (skus.length > 0) {
        for (let i = 0; i < skus.length; i++) {
          if (skus[i].descar.join(" ") == descar.join(" ")) {
            res = skus[i];
          }
        }
      }
      return res;
    },
    getShowSaleAttr() {
      console.log("this.spu.catalogId=", this.spu.catalogId)
      //获取当前分类可以使用的销售属性
      if (!this.dataResp.steped[1]) {
        this.$http({
          //url: `http://localhost:9090/commodity/attr/sale/list/${this.spu.catalogId}`,
          url: this.$http.adornUrl(`/commodity/attr/sale/list/${this.spu.catalogId}`),
          method: "get",
          params: this.$http.adornParams({
            page: 1,
            limit: 500
          })
        }).then(({data}) => {
          this.dataResp.saleAttrs = data.page.list;
          data.page.list.forEach(item => {
            this.dataResp.tempSaleAttrs.push({
              attrId: item.attrId,
              attrValues: [],
              attrName: item.attrName
            });
            this.inputVisible.push({view: false});
            this.inputValue.push({val: ""});
          });
          this.dataResp.steped[1] = true;
        });
      }
    },
    showBaseAttrs() {//得到当前分类的基本属性
      if (!this.dataResp.steped[0]) {
        this.$http({
          //url: `http://localhost:9090/commodity/attrgroup/${this.spu.catalogId}/withattr`,
          url: this.$http.adornUrl(`/commodity/attrgroup/${this.spu.catalogId}/withattr`),
          method: "get",
          params: this.$http.adornParams({})
        }).then(({data}) => {
          //先对表单的baseAttrs进行初始化
          data.data.forEach(item => {
            let attrArray = [];
            item.attrs.forEach(attr => {
              attrArray.push({
                attrId: attr.attrId,
                attrValues: "",
                showDesc: attr.showDesc
              });
            });

            this.dataResp.baseAttrs.push(attrArray);
          });
          this.dataResp.steped[0] = 0;
          this.dataResp.attrGroups = data.data;
        });
      }
    },

    submitSkus() {
      console.log("要提交保存的商品信息(spu=) " , JSON.stringify(this.spu))
      this.$confirm("将要提交商品数据，需要一小段时间，是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http({
            //url: "http://localhost:9090/commodity/spuinfo/save",
            url: this.$http.adornUrl("/commodity/spuinfo/save"),
            method: "post",
            data: this.$http.adornData(this.spu, false)
          }).then(({data}) => {
            this.spu = {}
            if (data.code == 0) {
              this.$message({
                type: "success",
                message: "新增商品成功!"
              });
              this.step = 4;
            } else {
              this.$message({
                type: "error",
                message: "保存失败，原因【" + data.msg + "】"
              });
            }

          });
        })
        .catch(e => {
          console.log(e);
          this.$message({
            type: "info",
            message: "已取消"
          });
        });


    },
    //笛卡尔积运算
    descartes(list) {
      //parent上一级索引;count指针计数
      var point = {};

      var result = [];
      var pIndex = null;
      var tempCount = 0;
      var temp = [];

      //根据参数列生成指针对象
      for (var index in list) {
        if (typeof list[index] == "object") {
          point[index] = {parent: pIndex, count: 0};
          pIndex = index;
        }
      }

      //单维度数据结构直接返回
      if (pIndex == null) {
        return list;
      }

      //动态生成笛卡尔积
      while (true) {
        for (var index in list) {
          tempCount = point[index]["count"];
          temp.push(list[index][tempCount]);
        }

        //压入结果数组
        result.push(temp);
        temp = [];

        //检查指针最大值问题
        while (true) {
          if (point[index]["count"] + 1 >= list[index].length) {
            point[index]["count"] = 0;
            pIndex = point[index]["parent"];
            if (pIndex == null) {
              return result;
            }

            //赋值parent进行再次检查
            index = pIndex;
          } else {
            point[index]["count"]++;
            break;
          }
        }
      }
    }
  },
  //生命周期-创建完成（可以访问当前this实例）
  created() {
    this.getCategorys();
  },
  //生命周期-挂载完成（可以访问DOM元素）
  mounted() {

    //监听三级分类消息的变化, 就相应的改变对应的品牌
    // this.subscribe = PubSub.subscribe("cascadedCategoryId", (msg, val) => {
    //   console.log("PubSub.subscribe...")
    //   this.catId = val[val.length - 1];
    //   this.getCatBrands();
    // });
    //
    // this.catPathSub = PubSub.subscribe("cascadedCategoryId", (msg, val) => {
    //   this.spu.catalogId = val[val.length - 1];
    // });
    // this.brandIdSub = PubSub.subscribe("brandId", (msg, val) => {
    //   this.spu.brandId = val;
    // });
    //获取会员级别，暂时注销
    //this.getMemberLevels();
  },
  beforeCreate() {
  }, //生命周期-创建之前
  beforeMount() {
  }, //生命周期-挂载之前
  beforeUpdate() {
  }, //生命周期-更新之前
  updated() {
  }, //生命周期-更新之后
  beforeDestroy() {
    // PubSub.unsubscribe(this.subscribe); //销毁订阅
    // PubSub.unsubscribe(this.catPathSub);
    // PubSub.unsubscribe(this.brandIdSub);
  }, //生命周期-销毁之前
  destroyed() {
  }, //生命周期-销毁完成
  activated() {
  } //如果页面有keep-alive缓存功能，这个函数会触发
};
</script>
<style scoped>
</style>
