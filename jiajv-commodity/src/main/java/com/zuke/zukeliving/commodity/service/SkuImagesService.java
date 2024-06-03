package com.zuke.zukeliving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuke.common.utils.PageUtils;
import com.zuke.zukeliving.commodity.entity.SkuImagesEntity;

import java.util.Map;

/**
 * sku 图片
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-06-01 15:43:45
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

