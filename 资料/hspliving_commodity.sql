/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35)
 Source Host           : localhost:3306
 Source Schema         : hspliving_commodity

 Target Server Type    : MySQL
 Target Server Version : 80035 (8.0.35)
 File Encoding         : 65001

 Date: 03/06/2024 20:00:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for commodity_attr
-- ----------------------------
DROP TABLE IF EXISTS `commodity_attr`;
CREATE TABLE `commodity_attr`  (
  `attr_id` bigint NOT NULL AUTO_INCREMENT COMMENT '属性 id',
  `attr_name` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '属性名',
  `search_type` tinyint NULL DEFAULT NULL COMMENT '是否需要检索[0-不需要，1-需要]',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `value_select` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '可选值列表[用逗号分隔]',
  `attr_type` tinyint NULL DEFAULT NULL COMMENT '属性类型[0-销售属性，1-基本属性]',
  `ENABLE` bigint NULL DEFAULT NULL COMMENT '启用状态[0 - 禁用，1 - 启用]',
  `category_id` bigint NULL DEFAULT NULL COMMENT '所属分类',
  `show_desc` tinyint NULL DEFAULT NULL COMMENT '快速展示【是否展示在介绍上；0-否 1-是】',
  PRIMARY KEY (`attr_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品属性表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_attr
-- ----------------------------
INSERT INTO `commodity_attr` VALUES (1, '是否触摸屏', 0, 'icon', '非触摸屏,触摸屏', 1, 1, 301, 1);
INSERT INTO `commodity_attr` VALUES (2, '显示类型', 0, 'icon', 'led', 1, 1, 301, 1);
INSERT INTO `commodity_attr` VALUES (3, '型号', 0, 'icon', '55IV', 1, 1, 301, 1);
INSERT INTO `commodity_attr` VALUES (4, '面积', NULL, 'icon', '100*100,200*200', 0, 1, 301, NULL);
INSERT INTO `commodity_attr` VALUES (5, 'xx', 0, 'xx', 'xx', 1, 1, 301, 1);
INSERT INTO `commodity_attr` VALUES (6, 'zz', 0, 'zz', 'zz', 1, 1, 301, 1);
INSERT INTO `commodity_attr` VALUES (7, 'tt', 0, 'tt', 't', 1, 1, 602, 1);
INSERT INTO `commodity_attr` VALUES (8, '颜色', NULL, 'logo', '黑色,红色', 0, 1, 301, NULL);

-- ----------------------------
-- Table structure for commodity_attr_attrgroup_relation
-- ----------------------------
DROP TABLE IF EXISTS `commodity_attr_attrgroup_relation`;
CREATE TABLE `commodity_attr_attrgroup_relation`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `attr_id` bigint NULL DEFAULT NULL COMMENT '属性 id',
  `attr_group_id` bigint NULL DEFAULT NULL COMMENT '属性分组 id',
  `attr_sort` int NULL DEFAULT NULL COMMENT '属性组内排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品属性和商品属性组的关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_attr_attrgroup_relation
-- ----------------------------
INSERT INTO `commodity_attr_attrgroup_relation` VALUES (1, 1, 1, NULL);
INSERT INTO `commodity_attr_attrgroup_relation` VALUES (9, 2, 2, NULL);
INSERT INTO `commodity_attr_attrgroup_relation` VALUES (10, 3, 3, NULL);
INSERT INTO `commodity_attr_attrgroup_relation` VALUES (11, 7, 5, NULL);

-- ----------------------------
-- Table structure for commodity_attrgroup
-- ----------------------------
DROP TABLE IF EXISTS `commodity_attrgroup`;
CREATE TABLE `commodity_attrgroup`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组名',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '说明',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组图标',
  `category_id` bigint NULL DEFAULT NULL COMMENT '所属分类 id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '家居商品属性分组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_attrgroup
-- ----------------------------
INSERT INTO `commodity_attrgroup` VALUES (1, '主体', 0, '主体说明', 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-29/300c36da-4996-4a6a-80b7-5c4ba92acea3_touxiang.jpg', 301);
INSERT INTO `commodity_attrgroup` VALUES (2, '规格', 0, '规格说明', 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-29/b809dc6c-f05c-43a6-8bef-c80997e0488a_touxiang.jpg', 301);
INSERT INTO `commodity_attrgroup` VALUES (3, '功能', 0, '功能说明', 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-29/6777c15b-6ea9-4273-8cef-18962d4ac084_touxiang.jpg', 301);
INSERT INTO `commodity_attrgroup` VALUES (5, '舒适性能', 0, '舒适性能', 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-29/38f5fa97-508f-4602-84e0-a5cffd84b398_touxiang.jpg', 602);

-- ----------------------------
-- Table structure for commodity_brand
-- ----------------------------
DROP TABLE IF EXISTS `commodity_brand`;
CREATE TABLE `commodity_brand`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌名',
  `logo` varchar(1200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'logo',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '说明',
  `isShow` tinyint NULL DEFAULT NULL COMMENT '显示',
  `first_letter` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '检索首字母',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '家居品牌' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_brand
-- ----------------------------
INSERT INTO `commodity_brand` VALUES (1, '海信', 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/e820b602-b8fb-4c18-8428-cba768c31f35_touxiang.jpg', 'haixin', 1, 'h', 2);
INSERT INTO `commodity_brand` VALUES (5, '小米', 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/713890a4-0c4b-4604-86e5-b5a7594c3168_touxiang.jpg', 'xiaomi', 1, 'x', 1);
INSERT INTO `commodity_brand` VALUES (6, '红米', 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/a792c99e-3d17-4c4f-8b62-183fe19e9eaf_touxiang.jpg', 'redmi', 1, 'r', 0);

-- ----------------------------
-- Table structure for commodity_category
-- ----------------------------
DROP TABLE IF EXISTS `commodity_category`;
CREATE TABLE `commodity_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `parent_id` bigint NOT NULL COMMENT '父分类 id',
  `cat_level` int NOT NULL COMMENT '层级',
  `is_show` tinyint NOT NULL COMMENT '0 不显示，1 显示',
  `sort` int NOT NULL COMMENT '排序',
  `icon` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图标',
  `pro_unit` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '统计单位',
  `pro_count` int NOT NULL COMMENT '商品数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 658 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_category
-- ----------------------------
INSERT INTO `commodity_category` VALUES (1, '家用电器', 0, 1, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (2, '家居家装', 0, 1, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (21, '大家电', 1, 2, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (22, '厨卫大电', 1, 2, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (41, '家纺', 2, 2, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (42, '灯具', 2, 2, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (201, '燃气灶', 22, 3, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (202, '油烟机', 22, 3, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (301, '平板电视', 21, 3, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (601, '桌布/罩件', 41, 3, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (602, '地毯地垫', 41, 3, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (651, '台灯', 42, 3, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (652, '节能灯', 42, 3, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (653, 'zukeLight', 42, 3, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (654, '租客小台灯1', 42, 3, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (655, '租客小台灯2', 42, 3, 1, 0, '~', '~', 0);
INSERT INTO `commodity_category` VALUES (656, '租客的小台灯3', 42, 3, 1, 0, '', '', 0);
INSERT INTO `commodity_category` VALUES (657, '租客的小台灯4', 652, 3, 1, 0, '', '', 0);

-- ----------------------------
-- Table structure for commodity_category_brand_relation
-- ----------------------------
DROP TABLE IF EXISTS `commodity_category_brand_relation`;
CREATE TABLE `commodity_category_brand_relation`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brand_id` bigint NULL DEFAULT NULL COMMENT '品牌 id',
  `category_id` bigint NULL DEFAULT NULL COMMENT '分类 id',
  `brand_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌名称',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '品牌分类关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_category_brand_relation
-- ----------------------------
INSERT INTO `commodity_category_brand_relation` VALUES (1, 1, 301, '海信', '平板电视');
INSERT INTO `commodity_category_brand_relation` VALUES (3, 5, 301, '小米', '平板电视');

-- ----------------------------
-- Table structure for commodity_product_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `commodity_product_attr_value`;
CREATE TABLE `commodity_product_attr_value`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `spu_id` bigint NULL DEFAULT NULL COMMENT '商品 id',
  `attr_id` bigint NULL DEFAULT NULL COMMENT '属性 id',
  `attr_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '属性名',
  `attr_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '属性值',
  `attr_sort` int NULL DEFAULT NULL COMMENT '顺序',
  `quick_show` tinyint NULL DEFAULT NULL COMMENT '快速展示【是否展示在介绍上；0-否 1-是】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'spu 基本属性值' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_product_attr_value
-- ----------------------------
INSERT INTO `commodity_product_attr_value` VALUES (1, 10, NULL, '是否触摸屏', '触摸屏', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (2, 10, NULL, '显示类型', 'led', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (3, 10, NULL, '型号', '55IV', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (4, 11, NULL, '是否触摸屏', '非触摸屏', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (5, 11, NULL, '显示类型', 'led', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (6, 11, NULL, '型号', '55IV', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (7, 12, NULL, '是否触摸屏', '触摸屏', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (8, 12, NULL, '显示类型', 'led', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (9, 12, NULL, '型号', '55IV', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (10, 16, NULL, '是否触摸屏', '触摸屏', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (11, 16, NULL, '显示类型', 'led', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (12, 16, NULL, '型号', '55IV', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (13, 17, NULL, '是否触摸屏', '非触摸屏', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (14, 17, NULL, '显示类型', 'led', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (15, 17, NULL, '型号', '55IV', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (16, 18, NULL, '是否触摸屏', '触摸屏', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (17, 18, NULL, '显示类型', 'led', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (18, 18, NULL, '型号', '55IV', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (19, 19, NULL, '是否触摸屏', '触摸屏', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (20, 19, NULL, '显示类型', 'led', 0, 1);
INSERT INTO `commodity_product_attr_value` VALUES (21, 19, NULL, '型号', '55IV', 0, 1);

-- ----------------------------
-- Table structure for commodity_sku_images
-- ----------------------------
DROP TABLE IF EXISTS `commodity_sku_images`;
CREATE TABLE `commodity_sku_images`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint NULL DEFAULT NULL COMMENT 'sku_id',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片地址',
  `img_sort` int NULL DEFAULT NULL COMMENT '排序',
  `default_img` int NULL DEFAULT NULL COMMENT '默认图[0 - 不是默认图，1 - 是默认图]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'sku 图片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_sku_images
-- ----------------------------
INSERT INTO `commodity_sku_images` VALUES (1, 13, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/49830ff3-c503-4376-8325-7e75096088ca_1.png', 0, 1);
INSERT INTO `commodity_sku_images` VALUES (2, 13, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (3, 13, '', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (4, 13, '', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (5, 14, '', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (6, 14, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', 0, 1);
INSERT INTO `commodity_sku_images` VALUES (7, 14, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/66846fe9-e697-4730-879e-2e73016c8135_598673.jpg', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (8, 14, '', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (9, 15, '', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (10, 15, '', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (11, 15, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/66846fe9-e697-4730-879e-2e73016c8135_598673.jpg', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (12, 15, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/bd134e14-f49b-478f-826f-daa091f4311b_1345266.png', 0, 1);
INSERT INTO `commodity_sku_images` VALUES (13, 16, '', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (14, 16, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', 0, 1);
INSERT INTO `commodity_sku_images` VALUES (15, 16, '', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (16, 16, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/bd134e14-f49b-478f-826f-daa091f4311b_1345266.png', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (17, 17, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/d630215c-3b0f-461e-862a-38102af8961b_1.png', 0, 1);
INSERT INTO `commodity_sku_images` VALUES (18, 17, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/253e4f2b-31de-4f87-89d7-92c5b9318cf9_2.png', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (19, 18, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/253e4f2b-31de-4f87-89d7-92c5b9318cf9_2.png', 0, 1);
INSERT INTO `commodity_sku_images` VALUES (20, 18, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/8ab597cc-a7b8-4ba8-8f70-7eab1e8f3440_598673.jpg', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (21, 19, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/8ab597cc-a7b8-4ba8-8f70-7eab1e8f3440_598673.jpg', 0, 1);
INSERT INTO `commodity_sku_images` VALUES (22, 19, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/ec4566a4-ca78-4124-816a-1c3761c0ed78_1357899.png', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (23, 20, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/253e4f2b-31de-4f87-89d7-92c5b9318cf9_2.png', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (24, 20, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/ec4566a4-ca78-4124-816a-1c3761c0ed78_1357899.png', 0, 1);
INSERT INTO `commodity_sku_images` VALUES (25, 21, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/11ec0703-679b-49eb-8bcc-19ea3524c045_2.png', 0, 1);
INSERT INTO `commodity_sku_images` VALUES (26, 21, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/3b8cfc23-a228-4e6f-85f6-4dded333805b_598673.jpg', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (27, 22, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/11ec0703-679b-49eb-8bcc-19ea3524c045_2.png', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (28, 22, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/3b8cfc23-a228-4e6f-85f6-4dded333805b_598673.jpg', 0, 1);
INSERT INTO `commodity_sku_images` VALUES (29, 23, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/11ec0703-679b-49eb-8bcc-19ea3524c045_2.png', 0, 0);
INSERT INTO `commodity_sku_images` VALUES (30, 23, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/3b8cfc23-a228-4e6f-85f6-4dded333805b_598673.jpg', 0, 1);
INSERT INTO `commodity_sku_images` VALUES (31, 24, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/e3d2488d-8608-4d91-8c37-c74ea9291dfd_1.png', 0, 1);
INSERT INTO `commodity_sku_images` VALUES (32, 24, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/11ec0703-679b-49eb-8bcc-19ea3524c045_2.png', 0, 0);

-- ----------------------------
-- Table structure for commodity_sku_info
-- ----------------------------
DROP TABLE IF EXISTS `commodity_sku_info`;
CREATE TABLE `commodity_sku_info`  (
  `sku_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'skuId',
  `spu_id` bigint NULL DEFAULT NULL COMMENT 'spuId',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'sku 名称',
  `sku_desc` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'sku 介绍描述',
  `catalog_id` bigint NULL DEFAULT NULL COMMENT '所属分类 id',
  `brand_id` bigint NULL DEFAULT NULL COMMENT '品牌 id',
  `sku_default_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '默认图片',
  `sku_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `sku_subtitle` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '副标题',
  `price` decimal(18, 4) NULL DEFAULT NULL COMMENT '价格',
  `sale_count` bigint NULL DEFAULT NULL COMMENT '销量',
  PRIMARY KEY (`sku_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'sku 信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_sku_info
-- ----------------------------
INSERT INTO `commodity_sku_info` VALUES (1, 11, '海信电视-06 100*100 黑色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', NULL, '海信电视-06 x', 100.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (2, 11, '海信电视-06 100*100 红色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', NULL, '海信电视-06 x', 200.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (3, 11, '海信电视-06 200*200 黑色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', NULL, '海信电视-06 x', 300.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (4, 11, '海信电视-06 200*200 红色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', NULL, '海信电视-06 x', 0.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (5, 12, '海信电视-07 100*100 黑色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-07 100*100 黑色', '海信电视-07 x', 200.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (6, 12, '海信电视-07 100*100 红色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-07 100*100 红色', '海信电视-07 x', 400.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (7, 12, '海信电视-07 200*200 黑色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-07 200*200 黑色', '海信电视-07 x', 100.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (8, 12, '海信电视-07 200*200 红色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-07 200*200 红色', '海信电视-07 x', 500.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (9, 16, '海信电视-08 100*100 红色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-08 100*100 红色', '海信电视-08 x', 12.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (10, 16, '海信电视-08 100*100 黑色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-08 100*100 黑色', '海信电视-08 x', 12.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (11, 16, '海信电视-08 200*200 红色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-08 200*200 红色', '海信电视-08 x', 12.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (12, 16, '海信电视-08 200*200 黑色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-08 200*200 黑色', '海信电视-08 x', 12.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (13, 17, '海信电视-09 200*200 黑色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-09 200*200 黑色', '海信电视-09 x', 400.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (14, 17, '海信电视-09 200*200 红色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-09 200*200 红色', '海信电视-09 x', 300.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (15, 17, '海信电视-09 100*100 黑色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-09 100*100 黑色', '海信电视-09 x', 200.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (16, 17, '海信电视-09 100*100 红色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-09 100*100 红色', '海信电视-09 x', 100.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (17, 18, '海信电视-10 200*200 黑色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-10 200*200 黑色', '海信电视-10 x', 4.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (18, 18, '海信电视-10 200*200 红色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-10 200*200 红色', '海信电视-10 x', 3.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (19, 18, '海信电视-10 100*100 黑色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-10 100*100 黑色', '海信电视-10 x', 2.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (20, 18, '海信电视-10 100*100 红色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-10 100*100 红色', '海信电视-10 x', 1.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (21, 19, '海信电视-11 100*100 黑色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-11 100*100 黑色', '海信电视-11 x', 66.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (22, 19, '海信电视-11 100*100 红色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-11 100*100 红色', '海信电视-11 x', 88.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (23, 19, '海信电视-11 200*200 黑色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-11 200*200 黑色', '海信电视-11 x', 77.0000, 0);
INSERT INTO `commodity_sku_info` VALUES (24, 19, '海信电视-11 200*200 红色', NULL, 301, 1, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', '海信电视-11 200*200 红色', '海信电视-11 x', 55.0000, 0);

-- ----------------------------
-- Table structure for commodity_sku_sale_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `commodity_sku_sale_attr_value`;
CREATE TABLE `commodity_sku_sale_attr_value`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint NULL DEFAULT NULL COMMENT 'sku_id',
  `attr_id` bigint NULL DEFAULT NULL COMMENT 'attr_id',
  `attr_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '销售属性名',
  `attr_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '销售属性值',
  `attr_sort` int NULL DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'sku 的销售属性/值表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_sku_sale_attr_value
-- ----------------------------
INSERT INTO `commodity_sku_sale_attr_value` VALUES (1, 21, 4, '面积', '100*100', 0);
INSERT INTO `commodity_sku_sale_attr_value` VALUES (2, 21, 8, '颜色', '黑色', 0);
INSERT INTO `commodity_sku_sale_attr_value` VALUES (3, 22, 4, '面积', '100*100', 0);
INSERT INTO `commodity_sku_sale_attr_value` VALUES (4, 22, 8, '颜色', '红色', 0);
INSERT INTO `commodity_sku_sale_attr_value` VALUES (5, 23, 4, '面积', '200*200', 0);
INSERT INTO `commodity_sku_sale_attr_value` VALUES (6, 23, 8, '颜色', '黑色', 0);
INSERT INTO `commodity_sku_sale_attr_value` VALUES (7, 24, 4, '面积', '200*200', 0);
INSERT INTO `commodity_sku_sale_attr_value` VALUES (8, 24, 8, '颜色', '红色', 0);

-- ----------------------------
-- Table structure for commodity_spu_images
-- ----------------------------
DROP TABLE IF EXISTS `commodity_spu_images`;
CREATE TABLE `commodity_spu_images`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `spu_id` bigint NULL DEFAULT NULL COMMENT 'spu_id',
  `img_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片名',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片地址',
  `img_sort` int NULL DEFAULT NULL COMMENT '顺序',
  `default_img` tinyint NULL DEFAULT NULL COMMENT '是否默认图',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'spu 图片集' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_spu_images
-- ----------------------------
INSERT INTO `commodity_spu_images` VALUES (1, 4, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/23f14fe7-13cc-4696-8ea5-713689df4126_touxiang.jpg', NULL, NULL);
INSERT INTO `commodity_spu_images` VALUES (2, 4, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/15617d2e-f9d6-4608-817f-20dfb1449fed_1329169.png', NULL, NULL);
INSERT INTO `commodity_spu_images` VALUES (8, 10, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/e820b602-b8fb-4c18-8428-cba768c31f35_touxiang.jpg', NULL, 0);
INSERT INTO `commodity_spu_images` VALUES (9, 11, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/e820b602-b8fb-4c18-8428-cba768c31f35_touxiang.jpg', NULL, 0);
INSERT INTO `commodity_spu_images` VALUES (10, 12, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/e820b602-b8fb-4c18-8428-cba768c31f35_touxiang.jpg', NULL, 0);
INSERT INTO `commodity_spu_images` VALUES (11, 16, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/e820b602-b8fb-4c18-8428-cba768c31f35_touxiang.jpg', NULL, 0);
INSERT INTO `commodity_spu_images` VALUES (12, 17, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/49830ff3-c503-4376-8325-7e75096088ca_1.png', NULL, NULL);
INSERT INTO `commodity_spu_images` VALUES (13, 17, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/83149df1-ca11-41b0-86c1-ace7e17f7a99_2.png', NULL, NULL);
INSERT INTO `commodity_spu_images` VALUES (14, 17, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/66846fe9-e697-4730-879e-2e73016c8135_598673.jpg', NULL, NULL);
INSERT INTO `commodity_spu_images` VALUES (15, 17, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/bd134e14-f49b-478f-826f-daa091f4311b_1345266.png', NULL, NULL);
INSERT INTO `commodity_spu_images` VALUES (16, 18, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/d630215c-3b0f-461e-862a-38102af8961b_1.png', NULL, NULL);
INSERT INTO `commodity_spu_images` VALUES (17, 18, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/253e4f2b-31de-4f87-89d7-92c5b9318cf9_2.png', NULL, NULL);
INSERT INTO `commodity_spu_images` VALUES (18, 18, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/8ab597cc-a7b8-4ba8-8f70-7eab1e8f3440_598673.jpg', NULL, NULL);
INSERT INTO `commodity_spu_images` VALUES (19, 18, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/ec4566a4-ca78-4124-816a-1c3761c0ed78_1357899.png', NULL, NULL);
INSERT INTO `commodity_spu_images` VALUES (20, 19, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/e3d2488d-8608-4d91-8c37-c74ea9291dfd_1.png', NULL, NULL);
INSERT INTO `commodity_spu_images` VALUES (21, 19, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/11ec0703-679b-49eb-8bcc-19ea3524c045_2.png', NULL, NULL);
INSERT INTO `commodity_spu_images` VALUES (22, 19, NULL, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/3b8cfc23-a228-4e6f-85f6-4dded333805b_598673.jpg', NULL, NULL);

-- ----------------------------
-- Table structure for commodity_spu_info
-- ----------------------------
DROP TABLE IF EXISTS `commodity_spu_info`;
CREATE TABLE `commodity_spu_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品 id',
  `spu_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `spu_description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品描述',
  `catalog_id` bigint NULL DEFAULT NULL COMMENT '所属分类 id',
  `brand_id` bigint NULL DEFAULT NULL COMMENT '品牌 id',
  `weight` decimal(18, 4) NULL DEFAULT NULL,
  `publish_status` tinyint NULL DEFAULT NULL COMMENT '上架状态[0 - 新建，1 - 上架，2 - 下架]',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品 spu 信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_spu_info
-- ----------------------------
INSERT INTO `commodity_spu_info` VALUES (1, '海信电视-01', '海信电视-01-描述', 301, 1, 10.0000, 1, '2024-06-01 10:31:40', '2024-06-01 10:31:40');
INSERT INTO `commodity_spu_info` VALUES (2, '海信电视-02', '海信电视-02-描述', 301, 1, 10.0000, 1, '2024-06-01 10:53:24', '2024-06-03 14:02:20');
INSERT INTO `commodity_spu_info` VALUES (3, '海信电视-03', '海信电视-03-描述', 301, NULL, 30.0000, 1, '2024-06-01 10:54:55', '2024-06-03 14:02:22');
INSERT INTO `commodity_spu_info` VALUES (4, '海信电视-04', '海信电视-04-描述', 301, 1, 10.0000, 1, '2024-06-01 11:13:51', '2024-06-03 14:02:25');
INSERT INTO `commodity_spu_info` VALUES (10, '海信电视-05', '海信电视-05-描述', 301, 1, 20.0000, 0, '2024-06-01 13:05:23', '2024-06-01 13:05:23');
INSERT INTO `commodity_spu_info` VALUES (11, '海信电视-06', '海信电视-06', 301, 1, 20.0000, 0, '2024-06-01 13:37:20', '2024-06-01 13:37:20');
INSERT INTO `commodity_spu_info` VALUES (12, '海信电视-07', '海信电视-07-描述', 301, NULL, 10.0000, 0, '2024-06-01 13:42:36', '2024-06-01 13:42:36');
INSERT INTO `commodity_spu_info` VALUES (16, '海信电视-08', '海信电视-08-描述', 301, 1, 20.0000, 0, '2024-06-01 13:48:17', '2024-06-01 13:48:17');
INSERT INTO `commodity_spu_info` VALUES (17, '海信电视-09', '海信电视-09-描述', 301, 1, 10.0000, 0, '2024-06-01 16:03:36', '2024-06-01 16:03:36');
INSERT INTO `commodity_spu_info` VALUES (18, '海信电视-10', '海信电视-10-描述', 301, 1, 20.0000, 0, '2024-06-01 16:19:16', '2024-06-01 16:19:16');
INSERT INTO `commodity_spu_info` VALUES (19, '海信电视-11', '海信电视-11-描述', 301, 1, 10.0000, 0, '2024-06-01 16:32:58', '2024-06-01 16:32:58');

-- ----------------------------
-- Table structure for commodity_spu_info_desc
-- ----------------------------
DROP TABLE IF EXISTS `commodity_spu_info_desc`;
CREATE TABLE `commodity_spu_info_desc`  (
  `spu_id` bigint NOT NULL COMMENT '商品 id',
  `decript` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品介绍图片',
  PRIMARY KEY (`spu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品 spu 信息介绍' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_spu_info_desc
-- ----------------------------
INSERT INTO `commodity_spu_info_desc` VALUES (2, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/e820b602-b8fb-4c18-8428-cba768c31f35_touxiang.jpg');
INSERT INTO `commodity_spu_info_desc` VALUES (3, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/d480a3fa-db77-461d-8a45-c2216613a261_1329169.png');
INSERT INTO `commodity_spu_info_desc` VALUES (4, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/1f86c15e-3949-496e-8df3-fb8a140a3b33_mobile.jpg,https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/9f536071-f6b1-4708-83d1-fc9ca3dfd910_1339755.jpeg');
INSERT INTO `commodity_spu_info_desc` VALUES (10, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/e820b602-b8fb-4c18-8428-cba768c31f35_touxiang.jpg');
INSERT INTO `commodity_spu_info_desc` VALUES (11, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/e820b602-b8fb-4c18-8428-cba768c31f35_touxiang.jpg');
INSERT INTO `commodity_spu_info_desc` VALUES (12, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/e820b602-b8fb-4c18-8428-cba768c31f35_touxiang.jpg');
INSERT INTO `commodity_spu_info_desc` VALUES (16, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/e820b602-b8fb-4c18-8428-cba768c31f35_touxiang.jpg');
INSERT INTO `commodity_spu_info_desc` VALUES (17, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/22c49309-3420-426c-8869-8045e147d3b9_touxiang.jpg');
INSERT INTO `commodity_spu_info_desc` VALUES (18, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/0cfec391-6197-48a8-8323-1590132104de_d2d6d1ba9404be2ef34cc97e04b03fb.png');
INSERT INTO `commodity_spu_info_desc` VALUES (19, 'https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-06-01/7432576f-32c6-4980-8bbf-17b33093cb5d_c12b5621e1a6230508d283aa6a6ba44.jpg');

SET FOREIGN_KEY_CHECKS = 1;
