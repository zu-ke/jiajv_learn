package com.zuke.zukeliving.commodity.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuke.common.utils.PageUtils;
import com.zuke.common.utils.Query;

import com.zuke.zukeliving.commodity.dao.SpuImagesDao;
import com.zuke.zukeliving.commodity.entity.SpuImagesEntity;
import com.zuke.zukeliving.commodity.service.SpuImagesService;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

    //��������spu�Ľ���ͼƬ
    @Override
    public void saveImages(Long id, List<String> images) {
        if (images.size() == 0 || images == null) { //û��ͼƬ��������Ĭ��ͼƬ��
            SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
            spuImagesEntity.setSpuId(id);
            spuImagesEntity.setImgUrl("https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/e820b602-b8fb-4c18-8428-cba768c31f35_touxiang.jpg");
            spuImagesEntity.setDefaultImg(0);
            this.save(spuImagesEntity);
        } else { //��������������
            List<SpuImagesEntity> spuImagesEntities = images.stream().map(i -> {
                SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
                spuImagesEntity.setSpuId(id);
                spuImagesEntity.setImgUrl(i);
                return spuImagesEntity;
            }).collect(Collectors.toList());
            this.saveBatch(spuImagesEntities);
        }
    }


}
