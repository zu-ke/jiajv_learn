package com.zuke.zukeliving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuke.common.utils.PageUtils;
import com.zuke.zukeliving.commodity.entity.AttrgroupEntity;
import com.zuke.zukeliving.commodity.entity.BrandEntity;
import com.zuke.zukeliving.commodity.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联表
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-28 16:26:33
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryBrandRelationEntity> getByBrandId(Long id);

    //根据categoryId返回该分类关联的品牌信息
    List<BrandEntity> getBrandsByCategoryId(Long categoryId);
}

