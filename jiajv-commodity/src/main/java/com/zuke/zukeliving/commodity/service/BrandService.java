package com.zuke.zukeliving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuke.common.utils.PageUtils;
import com.zuke.zukeliving.commodity.entity.BrandEntity;

import java.util.Map;

/**
 * 家居品牌
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-21 13:01:58
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

