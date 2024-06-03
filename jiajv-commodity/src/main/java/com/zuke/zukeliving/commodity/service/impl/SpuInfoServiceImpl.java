package com.zuke.zukeliving.commodity.service.impl;

import com.zuke.zukeliving.commodity.entity.*;
import com.zuke.zukeliving.commodity.service.*;
import com.zuke.zukeliving.commodity.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuke.common.utils.PageUtils;
import com.zuke.common.utils.Query;

import com.zuke.zukeliving.commodity.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Resource
    private SpuInfoDescService spuInfoDescService;

    @Resource
    private SpuImagesService spuImagesService;

    @Resource
    private ProductAttrValueService productAttrValueService;

    @Resource
    private AttrService attrService;

    @Resource
    private SkuInfoService skuInfoService;

    @Resource
    private SkuImagesService skuImagesService;

    @Resource
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {

        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) {
            wrapper.and(w -> {
                w.eq("id", key).or().like("spu_name", key);
            });
        }

        String status = (String) params.get("status");
        if (StringUtils.isNotBlank(status)) {
            wrapper.and(w -> {
                w.eq("publish_status", status);
            });
        }

        String brandId = (String) params.get("brandId");
        if (StringUtils.isNotBlank(brandId) && !"0".equals(brandId)) {
            wrapper.and(w -> {
                w.eq("brand_id", brandId);
            });
        }

        String catalogId = (String) params.get("catalogId");
        if (StringUtils.isNotBlank(catalogId) && !"0".equals(catalogId)) {
            wrapper.and(w -> {
                w.eq("catalog_id", catalogId);
            });
        }

        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    //����SpuSaveVO����/���ݣ�����ҵ�񣬽����ݷֱ𱣴浽��Ӧ��
    @Transactional //�������
    @Override
    public void saveSpuInfo(SpuSaveVO spuSaveVO) {
        //1.����spu��Ϣ��commodity_spu_info��
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        //2.��spuSaveVO���������ֵ������spuInfoEntity������������Ҫ�ж�Ӧ��ϵ��
        BeanUtils.copyProperties(spuSaveVO, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());

        //3.��spuInfoEntity���浽commodity_spu_info������Ϊ�˿ɶ��Ժ���չ�ԣ�����дһ������ ��saveBaseSpuInfo(SpuInfoEntity spuInfo)��
        this.saveBaseSpuInfo(spuInfoEntity);

        //4.����spu�Ľ���ͼƬ����Ϣcommodity_spu_info_desc
        List<String> decriptImg = spuSaveVO.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());

        //5.�ж���ͼƬ���ö��ż��
        if (decriptImg.size() == 0) { //��Ĭ��ͼƬ������һ��Ĭ��ͼƬ
            spuInfoDescEntity.setDecript("https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/e820b602-b8fb-4c18-8428-cba768c31f35_touxiang.jpg");
        } else { //�ж�ӦͼƬ
            spuInfoDescEntity.setDecript(String.join(",", decriptImg));
        }

        //6.����
        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);

        //7.����ͼƬ��
        spuImagesService.saveImages(spuInfoEntity.getId(), spuSaveVO.getImages());

        //8.�������spu��������
        List<BaseAttrs> baseAttrs = spuSaveVO.getBaseAttrs();
        List<ProductAttrValueEntity> productAttrValueEntities = baseAttrs.stream().map(i -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            productAttrValueEntity.setSpuId(spuInfoEntity.getId());
            productAttrValueEntity.setQuickShow(i.getShowDesc());
            productAttrValueEntity.setAttrValue(i.getAttrValues());
            productAttrValueEntity.setAttrSort(0); //Ĭ��ֵ0
            //��������id��ѯ�������ƣ�ȥ��commodity_attr��ѯ
            //======С����======
            /* mybatisPlus��ôʵ��������sql��䣺 select `name` from `user` where `id`=1 */
            //======С����======
            //QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
            //queryWrapper.select("attr_name").eq("id",i.getAttrId());
            productAttrValueEntity.setAttrName(attrService.getById(i.getAttrId()).getAttrName());
            return productAttrValueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveProductAttr(productAttrValueEntities);

        //����sku�Ļ�����Ϣ
        List<Skus> skusList = spuSaveVO.getSkus();
        if (skusList.size() != 0 || skusList != null) {
            skusList.forEach(i -> {
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                skuInfoEntity.setSpuId(spuInfoEntity.getId());

                //����Ĭ��ͼƬ
                String defaultImg = "https://hsp-jiajv-100.oss-cn-chengdu.aliyuncs.com/2024-05-22/e820b602-b8fb-4c18-8428-cba768c31f35_touxiang.jpg";
                List<Images> images = i.getImages();
                for (Images image : images) {
                    if (image.getDefaultImg() == 0) {
                        defaultImg = image.getImgUrl();
                    }
                }

                //����Ĭ����0
                skuInfoEntity.setSaleCount(0l);

                skuInfoEntity.setCatalogId(spuSaveVO.getCatalogId());
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setPrice(i.getPrice());
                skuInfoEntity.setSkuName(i.getSkuName());
                skuInfoEntity.setSkuSubtitle(i.getSkuSubtitle());
                skuInfoEntity.setSkuTitle(i.getSkuTitle());

                //����sku������Ϣ����commodity_sku_info
                skuInfoService.saveSkuInfo(skuInfoEntity);

                //����sku��ͼƬ����Ϣ��commodity_sku_images
                List<Images> list = i.getImages();
                List<SkuImagesEntity> skuImagesEntities = list.stream().filter(j -> {
                    return StringUtils.isNotBlank(j.getImgUrl());
                }).map(item -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setImgSort(0);
                    skuImagesEntity.setDefaultImg(item.getDefaultImg());
                    skuImagesEntity.setImgUrl(item.getImgUrl());
                    skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());
                    return skuImagesEntity;
                }).collect(Collectors.toList());

                //�������
                skuImagesService.saveBatch(skuImagesEntities);

                //����sku����������-��Ӧ��commodity_sku_sale_attr_value
                List<Attr> attrList = i.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attrList.stream().map(attr -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    skuSaleAttrValueEntity.setSkuId(skuInfoEntity.getSkuId());
                    skuSaleAttrValueEntity.setAttrSort(0);
                    skuSaleAttrValueEntity.setAttrName(attr.getAttrName());
                    skuSaleAttrValueEntity.setAttrValue(attr.getAttrValue());
                    skuSaleAttrValueEntity.setAttrId(attr.getAttrId());
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

            });
        }


    }

    //����spu������Ϣ
    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfo) {
        this.save(spuInfo);
    }

    @Override
    public void up(Long id) {
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        spuInfoEntity.setId(id);
        spuInfoEntity.setPublishStatus(1);
        spuInfoEntity.setUpdateTime(new Date());
        this.baseMapper.updateById(spuInfoEntity);
    }

    @Override
    public void down(Long id) {
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        spuInfoEntity.setId(id);
        spuInfoEntity.setPublishStatus(2);
        spuInfoEntity.setUpdateTime(new Date());
        this.baseMapper.updateById(spuInfoEntity);
    }

}
