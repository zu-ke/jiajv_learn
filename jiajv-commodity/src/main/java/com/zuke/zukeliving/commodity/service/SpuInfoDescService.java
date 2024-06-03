package com.zuke.zukeliving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuke.common.utils.PageUtils;
import com.zuke.zukeliving.commodity.entity.SpuInfoDescEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品 spu 信息介绍
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-06-01 10:34:10
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //保存spu的介绍图片
    void saveSpuInfoDesc(SpuInfoDescEntity spuInfoDesc);
}

