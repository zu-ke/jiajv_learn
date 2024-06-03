package com.zuke.zukeliving.commodity.dao;

import com.zuke.zukeliving.commodity.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品分类表
 * 
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-17 07:08:47
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
