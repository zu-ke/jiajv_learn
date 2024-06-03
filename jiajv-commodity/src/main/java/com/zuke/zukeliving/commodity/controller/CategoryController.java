package com.zuke.zukeliving.commodity.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zuke.zukeliving.commodity.entity.CategoryEntity;
import com.zuke.zukeliving.commodity.service.CategoryService;
import com.zuke.common.utils.PageUtils;
import com.zuke.common.utils.R;



/**
 * 商品分类表
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-17 07:08:47
 */
@RestController
@RequestMapping("commodity/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //查询所有分类及其子类，带有层级关系-即树形
    @RequestMapping("/list/tree")
    public R listTree(){
        List<CategoryEntity> categoryEntities = categoryService.listTree();
        return R.ok().put("data",categoryEntities);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //这个是shiro注解，我们不使用，注销即可
    //@RequiresPermissions("commodity:category:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:category:info")
    public R info(@PathVariable("id") Long id){
		CategoryEntity category = categoryService.getById(id);
        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:category:save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:category:update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:category:delete")
    public R delete(@RequestBody Long[] ids){
		categoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
