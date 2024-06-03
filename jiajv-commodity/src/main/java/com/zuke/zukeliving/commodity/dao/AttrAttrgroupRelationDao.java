package com.zuke.zukeliving.commodity.dao;

import com.zuke.zukeliving.commodity.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性和商品属性组的关联表
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-29 16:22:42
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    //增加批量删除属性组和属性的关联关系
    void deleteBatchRelation(@Param("entities")List<AttrAttrgroupRelationEntity> entities);
}
