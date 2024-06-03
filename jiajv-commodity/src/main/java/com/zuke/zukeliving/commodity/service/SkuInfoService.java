package com.zuke.zukeliving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuke.common.utils.PageUtils;
import com.zuke.zukeliving.commodity.entity.SkuInfoEntity;
import com.zuke.zukeliving.commodity.vo.SearchResult;

import java.util.Map;

/**
 * sku 信息
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-06-01 13:07:45
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPageByCondition(Map<String, Object> params);

//    保存sku的基本信息
    void saveSkuInfo(SkuInfoEntity skuInfo);

    //返回购买用户检索的结果 PageUtils -> SearchResult
    SearchResult querySearchPageByCondition(Map<String, Object> params);
}

