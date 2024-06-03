package com.zuke.zukeliving.commodity.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.zuke.zukeliving.commodity.entity.BrandEntity;
import com.zuke.zukeliving.commodity.service.BrandService;
import com.zuke.zukeliving.commodity.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zuke.zukeliving.commodity.entity.CategoryBrandRelationEntity;
import com.zuke.zukeliving.commodity.service.CategoryBrandRelationService;
import com.zuke.common.utils.PageUtils;
import com.zuke.common.utils.R;


/**
 * 品牌分类关联表
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-28 16:26:33
 */
@RestController
@RequestMapping("commodity/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    //根据categoryId返回该分类关联的品牌信息
    @RequestMapping("/brands/list")
    public R relationBrandList(@RequestParam(value = "catId", required = true) Long categoryId) {
        List<BrandEntity> brands = categoryBrandRelationService.getBrandsByCategoryId(categoryId);
        return R.ok().put("data", brands);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id) {
        CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelation.setBrandName(brandService.getById(categoryBrandRelation.getBrandId()).getName());
        categoryBrandRelation.setCategoryName(categoryService.getById(categoryBrandRelation.getCategoryId()).getName());
        categoryBrandRelationService.save(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    //根据brandId查询数据
    @RequestMapping("/brand/list")
    //@RequiresPermissions("commodity:categorybrandrelation:list")
    public R getByBrandId(@RequestParam("brandId") Long brandId) {
        List<CategoryBrandRelationEntity> list = categoryBrandRelationService.getByBrandId(brandId);

        return R.ok().put("data", list);
    }

}
