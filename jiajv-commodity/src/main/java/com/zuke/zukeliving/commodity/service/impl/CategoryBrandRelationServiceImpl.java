package com.zuke.zukeliving.commodity.service.impl;

import com.zuke.zukeliving.commodity.dao.BrandDao;
import com.zuke.zukeliving.commodity.entity.AttrgroupEntity;
import com.zuke.zukeliving.commodity.entity.BrandEntity;
import com.zuke.zukeliving.commodity.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuke.common.utils.PageUtils;
import com.zuke.common.utils.Query;

import com.zuke.zukeliving.commodity.dao.CategoryBrandRelationDao;
import com.zuke.zukeliving.commodity.entity.CategoryBrandRelationEntity;
import com.zuke.zukeliving.commodity.service.CategoryBrandRelationService;

import javax.annotation.Resource;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Resource
    private BrandDao brandDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryBrandRelationEntity> getByBrandId(Long id) {
        QueryWrapper<CategoryBrandRelationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("brand_id", id.longValue());
        return this.list(wrapper);
    }

    @Override
    public List<BrandEntity> getBrandsByCategoryId(Long categoryId) {

        List<CategoryBrandRelationEntity> brandRelationEntities = this.list(new QueryWrapper<CategoryBrandRelationEntity>().eq("category_id", categoryId));
        List<Long> brandList = brandRelationEntities.stream().map(i -> {
            return i.getBrandId();
        }).collect(Collectors.toList());
        if (brandList.size() == 0 || brandList == null) {
            return null;
        }
        List<BrandEntity> brandEntities = brandDao.selectBatchIds(brandList);
        return brandEntities;
    }

}
