package com.zuke.zukeliving.commodity.service.impl;

import com.zuke.zukeliving.commodity.dao.AttrAttrgroupRelationDao;
import com.zuke.zukeliving.commodity.dao.AttrDao;
import com.zuke.zukeliving.commodity.entity.AttrAttrgroupRelationEntity;
import com.zuke.zukeliving.commodity.entity.AttrEntity;
import com.zuke.zukeliving.commodity.service.AttrAttrgroupRelationService;
import com.zuke.zukeliving.commodity.service.AttrService;
import com.zuke.zukeliving.commodity.vo.AttrGroupWithAttsVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuke.common.utils.PageUtils;
import com.zuke.common.utils.Query;

import com.zuke.zukeliving.commodity.dao.AttrgroupDao;
import com.zuke.zukeliving.commodity.entity.AttrgroupEntity;
import com.zuke.zukeliving.commodity.service.AttrgroupService;

import javax.annotation.Resource;


@Service("attrgroupService")
public class AttrgroupServiceImpl extends ServiceImpl<AttrgroupDao, AttrgroupEntity> implements AttrgroupService {

    @Resource
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Resource
    private AttrDao attrDao;

    @Resource
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrgroupEntity> page = this.page(
                new Query<AttrgroupEntity>().getPage(params),
                new QueryWrapper<AttrgroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
        //��ȡ����Ĺؼ���
        String key = (String) params.get("key");

        //����ʵ�������װ��ѯ������QueryWrapper
        QueryWrapper<AttrgroupEntity> queryWrapper = new QueryWrapper<>();

        //�ж�key�Ƿ����Я���˲�ѯ����
        if (StringUtils.isNotBlank(key)) {
            //queryWrapper.eq("id", key).or().like("name", key);
            queryWrapper.and((obj) -> {
                obj.eq("id", key).or().like("name", key);
            });
        }

        //�����Ƿ���Ҫ��װcategoryId
        //����ҵ��������categoryId=0����������������������������ֵ�ͼ��������������Ҫǰ�˴�����ϣ�
        if (!(categoryId.longValue() == 0) && StringUtils.isNotBlank(key)) {
            queryWrapper.eq("category_id", categoryId);
        }

        IPage<AttrgroupEntity> page = this.page(
                new Query<AttrgroupEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);

    }

    @Override
    public List<AttrgroupEntity> getByCategoryId(Long id) {

        QueryWrapper<AttrgroupEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", id);
        return this.list(wrapper);
    }

    @Override
    public List<AttrGroupWithAttsVo> getAttrGroupWithAttrsByCategory(Long categoryId) {
        List<AttrgroupEntity> attrgroupEntityList = this.list(new QueryWrapper<AttrgroupEntity>().eq("category_id", categoryId));
        List<AttrGroupWithAttsVo> list = attrgroupEntityList.stream().map(i -> {
            AttrGroupWithAttsVo attrGroupWithAttsVo = new AttrGroupWithAttsVo();
            //attrGroupWithAttsVo.setId(i.getId());
            //attrGroupWithAttsVo.setIcon(i.getIcon());
            //attrGroupWithAttsVo.setSort(i.getSort());
            //attrGroupWithAttsVo.setName(i.getName());
            //attrGroupWithAttsVo.setDescription(i.getDescription());
            //attrGroupWithAttsVo.setCategoryId(i.getCategoryId());

            //����ע�͵Ĵ���������Դ��Ĺ��������
            BeanUtils.copyProperties(i, attrGroupWithAttsVo);

            List<AttrEntity> attrEntityList = attrService.getRelationAttr(i.getId());
            attrGroupWithAttsVo.setAttrs(attrEntityList);
            return attrGroupWithAttsVo;
        }).collect(Collectors.toList());
        return list;
    }


}
