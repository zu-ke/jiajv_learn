package com.zuke.zukeliving.commodity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品 spu 信息介绍
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-06-01 10:34:10
 */
@Data
@TableName("commodity_spu_info_desc")
public class SpuInfoDescEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品 id
	 */

	//因为表中的字段spuId不是自增长的，是insert时，程序员为其配置，所以加上type = IdType.INPUT
	@TableId(type = IdType.INPUT)
	private Long spuId;
	/**
	 * 商品介绍图片
	 */
	private String decript;

}
