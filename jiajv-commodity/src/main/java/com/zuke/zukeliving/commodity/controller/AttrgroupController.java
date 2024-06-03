package com.zuke.zukeliving.commodity.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.zuke.zukeliving.commodity.entity.AttrAttrgroupRelationEntity;
import com.zuke.zukeliving.commodity.entity.AttrEntity;
import com.zuke.zukeliving.commodity.entity.BrandEntity;
import com.zuke.zukeliving.commodity.service.AttrAttrgroupRelationService;
import com.zuke.zukeliving.commodity.service.AttrService;
import com.zuke.zukeliving.commodity.service.CategoryService;
import com.zuke.zukeliving.commodity.vo.AttrGroupWithAttsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zuke.zukeliving.commodity.entity.AttrgroupEntity;
import com.zuke.zukeliving.commodity.service.AttrgroupService;
import com.zuke.common.utils.PageUtils;
import com.zuke.common.utils.R;


/**
 * 家居商品属性分组
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-27 15:46:55
 */
@RestController
@RequestMapping("commodity/attrgroup")
public class AttrgroupController {
    @Autowired
    private AttrgroupService attrgroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @RequestMapping("/{catalogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catalogId") Long categoryId) {
        List<AttrGroupWithAttsVo> list = attrgroupService.getAttrGroupWithAttrsByCategory(categoryId);
        return R.ok().put("data", list);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrgroupService.queryPage(params);

        return R.ok().put("page", page);
    }

    //据分类（第三级）+查询条件[key、categoryId]+分页的API接口
    @RequestMapping("/list/{categoryId}")
    //@RequiresPermissions("commodity:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("categoryId") Long categoryId) {
        PageUtils page = attrgroupService.queryPage(params, categoryId);

        return R.ok().put("page", page);
    }

    @RequestMapping("/{attrGroupId}/noattr/relation")
    //@RequiresPermissions("commodity:attrgroup:list")
    public R getAllowRelationAttr(@RequestParam Map<String, Object> params, @PathVariable("attrGroupId") Long attrGroupId) {
        PageUtils page = attrService.getAllowRelationAttr(params, attrGroupId);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:attrgroup:info")
    public R info(@PathVariable("id") Long id) {
        AttrgroupEntity attrgroup = attrgroupService.getById(id);
        Long[] cascadedCategoryId = categoryService.getCascadedCategoryId(attrgroup.getCategoryId());
        attrgroup.setCascadedCategoryId(cascadedCategoryId);
        return R.ok().put("attrgroup", attrgroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:attrgroup:save")
    public R save(@RequestBody AttrgroupEntity attrgroup) {
        attrgroupService.save(attrgroup);

        return R.ok();
    }

    @RequestMapping("/attr/relation")
    //@RequiresPermissions("commodity:attrgroup:save")
    public R saveRelatio(@RequestBody List<AttrAttrgroupRelationEntity> list) {
        attrAttrgroupRelationService.saveBatch(list);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:attrgroup:update")
    public R update(@RequestBody AttrgroupEntity attrgroup) {
        attrgroupService.updateById(attrgroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/attr/relation/delete")
    //@RequiresPermissions("commodity:attrgroup:delete")
    public R delete(@RequestBody AttrAttrgroupRelationEntity[] attrAttrgroupRelationEntities) {
        attrService.deleteRelation(attrAttrgroupRelationEntities);

        return R.ok();
    }

    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:attrgroup:delete")
    public R deleteRelation(@RequestBody Long[] ids) {
        attrgroupService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    //根据分类id获取其属性组
    @RequestMapping("/getByCategoryId/{id}")
    //@RequiresPermissions("commodity:attrgroup:list")
    public R getByCategoryId(@PathVariable("id") Long id) {
        List<AttrgroupEntity> list = attrgroupService.getByCategoryId(id);

        return R.ok().put("data", list);
    }

    @RequestMapping("/{attrGroupId}/attr/relation")
    //@RequiresPermissions("commodity:attrgroup:list")
    public R attrRelation(@PathVariable("attrGroupId") Long attrGroupId) {
        List<AttrEntity> list = attrService.getRelationAttr(attrGroupId);
        return R.ok().put("data", list);
    }

}
