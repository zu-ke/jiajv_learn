package com.zuke.zukeliving.commodity.service.impl;

import com.zuke.zukeliving.commodity.vo.Catalog2Vo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuke.common.utils.PageUtils;
import com.zuke.common.utils.Query;

import com.zuke.zukeliving.commodity.dao.CategoryDao;
import com.zuke.zukeliving.commodity.entity.CategoryEntity;
import com.zuke.zukeliving.commodity.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    //返回所有分类及其子分类，带有其子分类-即树形
    //这里会使用到Java8的流式计算以及递归操作
    @Override
    public List<CategoryEntity> listTree() {
        //思路分析
        //1.先查询出所有分类数据
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);

        //2.组装成层级树形结构【使用到Java8的stream api + 递归操作】

        List<CategoryEntity> categoryTrees = categoryEntities.stream().filter(categoryEntity -> {
            //2.1 过滤，返回一级分类
            return categoryEntity.getParentId() == 0;
        }).map(categoryEntity -> {
            //2.2 进行map映射操作，给每个一级分类设置对应的子分类（这个过程会用到递归）-- 重要
            List<CategoryEntity> childrenCategories = getChildrenCategories(categoryEntity, categoryEntities);
            categoryEntity.setChildrenCategories(childrenCategories);
            return categoryEntity;
        }).sorted((c1, c2) -> {
            //2.3 进行排序sorted操作，按照sort升序排列
            return (c1.getSort() == null ? 0 : c1.getSort()) - (c2.getSort() == null ? 0 : c2.getSort());
        }).collect(Collectors.toList()); //2.4 将处理好的数据收集到集合中

        //3. 返回处理好的树形数据
        return categoryTrees;
    }

    //递归查询所有分类的子分类（此方法查询tree，比如某个一级分类的二级分类，二级分类的三级分类）
    //root即一级分类
    //all即所有数据
    private List<CategoryEntity> getChildrenCategories(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> list = all.stream().filter(categoryEntity -> {

            // 错误方式
            //return categoryEntity.getParentId() == root.getId();
            // 解决方式一
            return categoryEntity.getParentId().longValue() == root.getId().longValue();
            // 解决方式二
            //return categoryEntity.getParentId().equals(root.getId().longValue());
        }).map(categoryEntity -> {
            //1. 找到子分类并设置，递归  (寻找二级分类的子分类)
            categoryEntity.setChildrenCategories(getChildrenCategories(categoryEntity, all));
            return categoryEntity;
        }).sorted((c1, c2) -> {
            //进行排序sorted操作，按照sort升序排列
            return (c1.getSort() == null ? 0 : c1.getSort()) - (c2.getSort() == null ? 0 : c2.getSort());
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Long[] getCascadedCategoryId(Long id) {
        //1.创建一个集合，把层级关系收集到集合中
        List<Long> cascadedCategoryId = new ArrayList<>();

        //2.调用方法进行处理--递归方法
        getParentCategoryId(id, cascadedCategoryId);

        //3.将集合进行翻转
        Collections.reverse(cascadedCategoryId);
        return cascadedCategoryId.toArray(new Long[cascadedCategoryId.size()]);
    }

    @Override
    public List<CategoryEntity> getLevel1Categories() {

        List<CategoryEntity> list = this.list(new QueryWrapper<CategoryEntity>().eq("parent_id", 0));
        return list;
    }

    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {

        List<CategoryEntity> selectList = this.baseMapper.selectList(null);
        List<CategoryEntity> level1 = getParentCid(selectList, 0L);
        Map<String, List<Catalog2Vo>> collect = level1.stream().collect(Collectors.toMap(k -> {
            return k.getId().toString();
        }, v -> {
            List<Catalog2Vo> catalog2Vos = new ArrayList<>();
            List<CategoryEntity> level2 = getParentCid(selectList, v.getId());
            if (level2.size() > 0 && level2 != null) {
                catalog2Vos = level2.stream().map(i -> {
                    Catalog2Vo catalog2Vo = new Catalog2Vo(v.getId().toString(), null, i.getId().toString(), i.getName());

                    List<CategoryEntity> level3 = getParentCid(selectList, i.getId());

                    if (level3 != null && level2.size() > 0) {
                        List<Catalog2Vo.Catalog3Vo> list = level3.stream().map(l3 -> {
                            Catalog2Vo.Catalog3Vo catalog3Vo = new Catalog2Vo.Catalog3Vo(i.getId().toString(), l3.getId().toString(), l3.getName());
                            return catalog3Vo;
                        }).collect(Collectors.toList());
                        catalog2Vo.setCatalog3List(list);
                    }
                    return catalog2Vo;
                }).collect(Collectors.toList());
            }
            return catalog2Vos;
        }));
        return collect;
    }

    private List<CategoryEntity> getParentCid(List<CategoryEntity> selectList, Long parentCId) {

        List<CategoryEntity> list = selectList.stream().filter(i -> {
            return i.getParentId().equals(parentCId);
        }).collect(Collectors.toList());
        return list;
    }

    //递归
    private void getParentCategoryId(Long categoryId, List<Long> cascadedCategoryId) {
        //1.先把categoryId放进cascadedCategoryId
        cascadedCategoryId.add(categoryId);

        //2.根据categoryId先得到它的CategoryEntity
        CategoryEntity categoryEntity = this.getById(categoryId);
        //递归处理
        if (categoryEntity.getParentId().longValue() != 0) {
            getParentCategoryId(categoryEntity.getParentId().longValue(), cascadedCategoryId);
        }
    }

}
