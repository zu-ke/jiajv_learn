package com.zuke.zukeliving.commodity.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.zuke.common.valid.SaveGroup;
import com.zuke.common.valid.UpdateGroup;
import com.zuke.common.valid.UpdateIsShowGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zuke.zukeliving.commodity.entity.BrandEntity;
import com.zuke.zukeliving.commodity.service.BrandService;
import com.zuke.common.utils.PageUtils;
import com.zuke.common.utils.R;


/**
 * 家居品牌
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-21 13:01:58
 */
@RestController
@RequestMapping("commodity/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:brand:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:brand:info")
    public R info(@PathVariable("id") Long id) {
        BrandEntity brand = brandService.getById(id);

        return R.ok().put("brand", brand);
    }

    /**
     * @param brand
     * param result：springboot会将校验的错误放在这
     * @return
     * @Validated作用：启用BrandEntity属性校验
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:brand:save")

    // @Validated({SaveGroup.class}) : 调用save方法时，进行参数校验，使用的是SaveGroup这个组的校验规则
    public R save(@Validated({SaveGroup.class}) @RequestBody BrandEntity brand/*, BindingResult result*/) {

        //if (result.hasErrors()) {
        //    //创建一个map，用于收集校验错误
        //    Map<String, String> map = new HashMap();
        //    //遍历result收集错误信息
        //    result.getFieldErrors().forEach((i) -> {
        //        //得到field
        //        String field = i.getField();
        //        //得到校验的错误信息
        //        String message = i.getDefaultMessage();
        //        //收集
        //        map.put(field, message);
        //    });
        //    return R.error(400, "品牌表单数据不合法").put("data", map);
        //}
        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:brand:update")

    // @Validated({UpdateGroup.class}) : 调用update方法时，进行参数校验，使用的是UpdateGroup这个组的校验规则
    public R update(@Validated({UpdateGroup.class}) @RequestBody BrandEntity brand) {
        System.out.println(brand);
        brandService.updateById(brand);

        return R.ok();
    }

    @RequestMapping("/update/isshow")
    //@RequiresPermissions("commodity:brand:update")

    // @Validated({UpdateGroup.class}) : 调用update方法时，进行参数校验，使用的是UpdateGroup这个组的校验规则
    public R updateIsShow(@Validated({UpdateIsShowGroup.class}) @RequestBody BrandEntity brand) {
        System.out.println(brand);
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:brand:delete")
    public R delete(@RequestBody Long[] ids) {
        brandService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }



}
