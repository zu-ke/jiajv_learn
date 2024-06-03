package com.zuke.zukeliving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuke.common.utils.PageUtils;
import com.zuke.zukeliving.commodity.entity.AttrEntity;
import com.zuke.zukeliving.commodity.entity.AttrgroupEntity;
import com.zuke.zukeliving.commodity.vo.AttrGroupWithAttsVo;

import java.util.List;
import java.util.Map;

/**
 * 家居商品属性分组
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-27 15:46:55
 */
public interface AttrgroupService extends IService<AttrgroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //根据分类（第三级）+查询条件[key、categoryId]+分页的API接口
    PageUtils queryPage(Map<String, Object> params, Long categoryId);

    List<AttrgroupEntity> getByCategoryId(Long id);

    //根据分类id返回该分类关联的属性组和这些属性组关联的基本属性
    List<AttrGroupWithAttsVo> getAttrGroupWithAttrsByCategory(Long categoryId);


}

