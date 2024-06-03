package com.zuke.zukeliving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuke.common.utils.PageUtils;
import com.zuke.zukeliving.commodity.entity.CategoryEntity;
import com.zuke.zukeliving.commodity.vo.Catalog2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品分类表
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-17 07:08:47
 */
public interface CategoryService extends IService<CategoryEntity> {

    //返回所有分类及其子分类，带有其子分类-即树形
    List<CategoryEntity> listTree();

    PageUtils queryPage(Map<String, Object> params);

    Long[] getCascadedCategoryId(Long id);

    //获取一级分类
    List<CategoryEntity> getLevel1Categories();

    //返回二级分类（3级分类）
    Map<String,List<Catalog2Vo>> getCatalogJson();
}
