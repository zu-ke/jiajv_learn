package com.zuke.zukeliving.commodity.dao;

import com.zuke.zukeliving.commodity.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品牌分类关联表
 * 
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-28 16:26:33
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {
	
}
