package com.zuke.zukeliving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuke.common.utils.PageUtils;
import com.zuke.zukeliving.commodity.entity.SkuSaleAttrValueEntity;

import java.util.Map;

/**
 * sku 的销售属性/值表
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-06-01 16:23:38
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

