package com.zuke.zukeliving.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 商品分类表
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-17 07:08:47
 */
@Data
@TableName("commodity_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 父分类 id
	 */
	private Long parentId;
	/**
	 * 层级
	 */
	private Integer catLevel;
	/**
	 * 0 不显示，1 显示
	 */
	//说明：
	//如果没有在application.yml文件中配置，则可以使用 @TableLogic(value = "1", delval = "0")
	//如果配置了，就直接如下即可
	@TableLogic
	private Integer isShow;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 统计单位
	 */
	private String proUnit;
	/**
	 * 商品数量
	 */
	private Integer proCount;

	//1. 增加属性 childrenCategories 便于list查询时层级结构显示
	//2. 由于没有对应表字段，所以增加下面注解，表示本字段不对应表字段
	@TableField(exist = false)
	//如果childrenCategories值为空数组，就不返回（响应前端）
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<CategoryEntity> childrenCategories;


}
