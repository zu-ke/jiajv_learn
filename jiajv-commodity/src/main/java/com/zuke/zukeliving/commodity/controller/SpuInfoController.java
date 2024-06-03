package com.zuke.zukeliving.commodity.controller;

import java.util.Arrays;
import java.util.Map;

import com.zuke.zukeliving.commodity.vo.SpuSaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zuke.zukeliving.commodity.entity.SpuInfoEntity;
import com.zuke.zukeliving.commodity.service.SpuInfoService;
import com.zuke.common.utils.PageUtils;
import com.zuke.common.utils.R;



/**
 * 商品 spu 信息
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-31 14:49:25
 */
@RestController
@RequestMapping("commodity/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:spuinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuInfoService.queryPageByCondition(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:spuinfo:info")
    public R info(@PathVariable("id") Long id){
		SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:spuinfo:save")
    public R save(@RequestBody SpuSaveVO spuSaveVO){
        spuInfoService.saveSpuInfo(spuSaveVO);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:spuinfo:update")
    public R update(@RequestBody SpuInfoEntity spuInfo){
		spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    //上架
    @RequestMapping("/{id}/up")
    //@RequiresPermissions("commodity:spuinfo:update")
    public R up(@PathVariable("id") Long id){
        spuInfoService.up(id);

        return R.ok();
    }

    //下架
    @RequestMapping("/{id}/down")
    //@RequiresPermissions("commodity:spuinfo:update")
    public R down(@PathVariable("id") Long id){
        spuInfoService.down(id);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:spuinfo:delete")
    public R delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
