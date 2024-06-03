package com.zuke.zukeliving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuke.common.utils.PageUtils;
import com.zuke.zukeliving.commodity.entity.ProductAttrValueEntity;
import com.zuke.zukeliving.commodity.vo.BaseAttrs;

import java.util.List;
import java.util.Map;

/**
 * spu 基本属性值
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-06-01 11:16:39
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //批量添加spu基本属性
    void saveProductAttr(List<ProductAttrValueEntity> productAttrValueEntities);
}

