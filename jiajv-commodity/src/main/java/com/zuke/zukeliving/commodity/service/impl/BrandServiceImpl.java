package com.zuke.zukeliving.commodity.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuke.common.utils.PageUtils;
import com.zuke.common.utils.Query;

import com.zuke.zukeliving.commodity.dao.BrandDao;
import com.zuke.zukeliving.commodity.entity.BrandEntity;
import com.zuke.zukeliving.commodity.service.BrandService;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 默认renren-fast生成的代码，条件查询中没有携带参数，可以如下修改实现，等后面有更好的实现方案，这里先注释掉
        //QueryWrapper<BrandEntity> brandEntityQueryWrapper = new QueryWrapper<>();
        //brandEntityQueryWrapper.like("name", params.get("name"));
        //IPage<BrandEntity> page = this.page(
        //        new Query<BrandEntity>().getPage(params),
        //        brandEntityQueryWrapper
        //);

        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}
