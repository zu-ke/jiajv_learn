package com.zuke.zukeliving.commodity.controller;

import java.util.Arrays;
import java.util.Map;

import com.zuke.zukeliving.commodity.entity.AttrAttrgroupRelationEntity;
import com.zuke.zukeliving.commodity.service.AttrAttrgroupRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zuke.zukeliving.commodity.entity.AttrEntity;
import com.zuke.zukeliving.commodity.service.AttrService;
import com.zuke.common.utils.PageUtils;
import com.zuke.common.utils.R;



/**
 * 商品属性表
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-29 12:04:30
 */
@RestController
@RequestMapping("commodity/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/base/list/{categoryId}")
    //@RequiresPermissions("commodity:attrgroup:list")
    public R baseList(@RequestParam Map<String, Object> params, @PathVariable("categoryId") Long categoryId) {
        PageUtils page = attrService.queryBaseAttrPage(params, categoryId);

        return R.ok().put("page", page);
    }

    @RequestMapping("/sale/list/{categoryId}")
    //@RequiresPermissions("commodity:attrgroup:list")
    public R saleList(@RequestParam Map<String, Object> params, @PathVariable("categoryId") Long categoryId) {
        PageUtils page = attrService.querySaleAttrPage(params, categoryId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("commodity:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
		AttrEntity attr = attrService.getByIdNew(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:attr:save")
    public R save(@RequestBody AttrEntity attr){
		attrService.saveAttrAndRelation(attr);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:attr:update")
    public R update(@RequestBody AttrEntity attr){
		attrService.updateById(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
