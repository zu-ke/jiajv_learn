package com.zuke.zukeliving.commodity.service.impl;

import com.zuke.zukeliving.commodity.dao.AttrAttrgroupRelationDao;
import com.zuke.zukeliving.commodity.dao.AttrgroupDao;
import com.zuke.zukeliving.commodity.entity.AttrAttrgroupRelationEntity;
import com.zuke.zukeliving.commodity.entity.AttrgroupEntity;
import com.zuke.zukeliving.commodity.service.AttrgroupService;
import com.zuke.zukeliving.commodity.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuke.common.utils.PageUtils;
import com.zuke.common.utils.Query;

import com.zuke.zukeliving.commodity.dao.AttrDao;
import com.zuke.zukeliving.commodity.entity.AttrEntity;
import com.zuke.zukeliving.commodity.service.AttrService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Resource
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Resource
    private CategoryService categoryService;

    @Resource
    private AttrgroupDao attrgroupDao;

    @Resource
    private AttrgroupService attrgroupService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long categoryId) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type", 1);

        if (categoryId.longValue() != 0) {
            queryWrapper.eq("category_id", categoryId);
        }

        String key = (String) params.get("key");

        if (StringUtils.isNotBlank(key)) {
            queryWrapper.eq("attr_id", key).or().like("attr_name", key);
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils querySaleAttrPage(Map<String, Object> params, Long categoryId) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type", 0);

        if (categoryId.longValue() != 0) {
            queryWrapper.eq("category_id", categoryId);
        }

        String key = (String) params.get("key");

        if (StringUtils.isNotBlank(key)) {
            queryWrapper.eq("attr_id", key).or().like("attr_name", key);
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);

    }

    //�漰���ű�����������������
    @Transactional
    @Override
    public void saveAttrAndRelation(AttrEntity attrEntity) {
        //1.�����������
        this.save(attrEntity);

        //2.����������Ժ�������Ĺ�����ϵ
        if (attrEntity.getAttrType().intValue() == 1 && attrEntity.getAttrGroupId() != null) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationEntity.setAttrGroupId(attrEntity.getAttrGroupId());
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }
    }

    @Override
    public AttrEntity getByIdNew(Long id) {

        AttrEntity attrEntity = this.getById(id);
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectById(attrEntity.getAttrId());
        if (attrAttrgroupRelationEntity != null) {
            attrEntity.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
            attrEntity.setAttrGroupName(attrgroupService.getById(attrAttrgroupRelationEntity.getAttrGroupId()).getName());

        }
        attrEntity.setCascadedCategoryId(categoryService.getCascadedCategoryId(attrEntity.getCategoryId()));
        return attrEntity;
    }

    @Override
    public List<AttrEntity> getRelationAttr(Long attrGroupId) {
        List<AttrAttrgroupRelationEntity> entities = attrAttrgroupRelationDao
                .selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId));
        List<Long> attrIds = entities.stream().map(entity -> {
            return entity.getAttrId();
        }).collect(Collectors.toList());

        if (attrIds.size() == 0 || attrIds == null) {
            return null;
        }

        Collection<AttrEntity> attrEntities = this.listByIds(attrIds);
        return (List<AttrEntity>) attrEntities;
    }

    @Override
    public void deleteRelation(AttrAttrgroupRelationEntity[] attrAttrgroupRelationEntities) {
        attrAttrgroupRelationDao.deleteBatchRelation(Arrays.asList(attrAttrgroupRelationEntities));
    }

    @Override
    public PageUtils getAllowRelationAttr(Map<String, Object> params, Long attrGroupId) {

        // С���ע�⣺ ��������漰�������Ľ������.. ͨ����ʽ����stream API

        //1. ͨ�����յ� ������id attrgroupId, �õ���Ӧ��categoryId
        AttrgroupEntity attrgroupEntity = attrgroupDao.selectById(attrGroupId);
        Long categoryId = attrgroupEntity.getCategoryId();

        // ----- ����ҵ�������ų��Ѿ������Ļ�������------

        //(1) �ȵõ���ǰcategoryId �����з��� - commodity_attrgroup��
        List<AttrgroupEntity> group =
                attrgroupDao.selectList(new QueryWrapper<AttrgroupEntity>().eq("category_id", categoryId));
        //�ռ�������õ�group�Ķ�Ӧ��������id->jdk8 ��ʽ���� stream API
        List<Long> attrGroupIds = group.stream().map((item) -> {
            return item.getId();
        }).collect(Collectors.toList());


        //(2) �ٵ�commodity_attr_attrgroup_relation�У�ȥ��������Щ���������Ѿ�����һ���õ��������������
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities =
                attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", attrGroupIds));
        //�ռ�������õ� attrAttrgroupRelationEntities��Ӧattr_id,���뵽����->jdk8 ��ʽ����
        List<Long> attrIds = attrAttrgroupRelationEntities.stream().map((item) -> {
            return item.getAttrId();
        }).collect(Collectors.toList());

        //2. ͨ���õ� categoryId, ��ȡ����Ӧ�Ļ�������
        QueryWrapper<AttrEntity> wrapper =
                new QueryWrapper<AttrEntity>().eq("category_id", categoryId).eq("attr_type", 1);

        //(3) ����һ���ų���ǰ���Ѿ��������Ļ������Լ���.

        if (attrIds != null && attrIds.size() != 0) {
            wrapper.notIn("attr_id", attrIds);
        }

        //3. ��Ϊ����֧��������ѯ�����Կ���Я���ļ�������.
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) { //���key������
            wrapper.and((obj) -> {
                obj.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        //���з�ҳ��ѯ
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);

    }

}
