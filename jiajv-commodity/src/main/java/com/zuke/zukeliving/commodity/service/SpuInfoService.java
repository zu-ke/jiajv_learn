package com.zuke.zukeliving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuke.common.utils.PageUtils;
import com.zuke.zukeliving.commodity.entity.SpuInfoEntity;
import com.zuke.zukeliving.commodity.vo.SpuSaveVO;

import java.util.Map;

/**
 * 商品 spu 信息
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-31 14:49:25
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCondition(Map<String, Object> params);

    //保存SpuSaveVO对象/数据，根据业务，将数据分别保存到对应表
    void saveSpuInfo(SpuSaveVO spuSaveVO);

    //保存spu基本信息
    void saveBaseSpuInfo(SpuInfoEntity spuInfo);

    void up(Long id);
    void down(Long id);


}

