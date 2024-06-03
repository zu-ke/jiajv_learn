package com.zuke.zukeliving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuke.common.utils.PageUtils;
import com.zuke.zukeliving.commodity.entity.AttrAttrgroupRelationEntity;
import com.zuke.zukeliving.commodity.entity.AttrEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品属性表
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-29 12:04:30
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long categoryId);

    PageUtils querySaleAttrPage(Map<String, Object> params, Long categoryId);

    void saveAttrAndRelation(AttrEntity attrEntity);

    AttrEntity getByIdNew(Long id);

    List<AttrEntity> getRelationAttr(Long attrGroupId);

    //批量删除属性组和属性的关联关系
    void deleteRelation(AttrAttrgroupRelationEntity[] attrAttrgroupRelationEntities);

    //获取某个属性组可以关联的基本属性
    //如果某个基本属性已经和某个属性组关联了，它就不能再次关联
    //某个属性组可以关联的基本属性，必须是同一个分类
    PageUtils getAllowRelationAttr(Map<String, Object> params, Long attrGroupId);
}

