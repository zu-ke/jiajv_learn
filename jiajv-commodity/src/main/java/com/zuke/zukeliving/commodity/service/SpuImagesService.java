package com.zuke.zukeliving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuke.common.utils.PageUtils;
import com.zuke.zukeliving.commodity.entity.SpuImagesEntity;
import com.zuke.zukeliving.commodity.entity.SpuInfoDescEntity;

import java.util.List;
import java.util.Map;

/**
 * spu 图片集
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-06-01 10:56:48
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //批量保存spu的介绍图片
    void saveImages(Long id, List<String> images);
}

