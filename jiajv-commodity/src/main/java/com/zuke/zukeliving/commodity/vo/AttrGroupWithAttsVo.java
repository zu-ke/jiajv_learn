package com.zuke.zukeliving.commodity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.zuke.zukeliving.commodity.entity.AttrEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//如果返回的数据，是单独放回当前的实体类或对象不能满足前端需求
//解决方案：增加vo类，根据前端需求进行组合（增加字段、删除字段，动态调整）
//这里演示前端需要AttrgroupEntity和AttrEntity组合返回
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttrGroupWithAttsVo {
    /**
     * id
     */
    private Long id;
    /**
     * 组名
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 说明
     */
    private String description;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类 id
     */
    private Long categoryId;

    private List<AttrEntity> attrs;

}
