package com.zuke.zukeliving.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.zuke.common.valid.EnumValidate;
import com.zuke.common.valid.SaveGroup;
import com.zuke.common.valid.UpdateGroup;
import com.zuke.common.valid.UpdateIsShowGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 家居品牌
 * JS303校验
 * 分组校验，区分添加和修改操作对参数的要求
 *
 * @author zukw
 * @email zuke@163.com
 * @date 2024-05-21 13:01:58
 */
@Data
@TableName("commodity_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    // 分组校验，区分添加和修改操作对参数的要求
    @TableId
    @NotNull(message = "修改操作要求指定id", groups = {UpdateGroup.class, UpdateIsShowGroup.class})
    @Null(message = "添加操作不能携带id", groups = {SaveGroup.class})
    private Long id;
    /**
     * 品牌名
     */
    // 不能为空
    // The annotated element must not be null and must contain at least one non-whitespace character. Accepts CharSequence.
    @NotBlank(message = "品牌名不能为空", groups = {SaveGroup.class, UpdateGroup.class})
    private String name;
    /**
     * logo
     */
    // The annotated element must not be null and must contain at least one non-whitespace character. Accepts CharSequence.
    @NotBlank(message = "logo不能为空", groups = {SaveGroup.class})
    // Validates the annotated string is an URL.
    @URL(message = "logo不是一个合法的url", groups = {SaveGroup.class, UpdateGroup.class})
    private String logo;
    /**
     * 说明
     */
    private String description;
    /**
     * 显示
     */
    // The annotated element must not be null. Accepts any type
    @NotNull(message = "显示状态不能为空", groups = {SaveGroup.class, UpdateIsShowGroup.class, UpdateIsShowGroup.class})
    // 只能是0或者1，如果是String属性，可以通过@Pattern来校验。此时我们通过后面自定义校验器来解决
    // ========分割线========
    // 下面是自定义校验器解决
    @EnumValidate(values = {0, 1}, message = "显示状态的值必须是0或1", groups = {SaveGroup.class, UpdateGroup.class, UpdateIsShowGroup.class})
    private Integer isshow;
    /**
     * 检索首字母
     */
    // The annotated element must not be null and must contain at least one non-whitespace character. Accepts CharSequence.
    @NotBlank(message = "检索首字母不能为空", groups = {SaveGroup.class})
    // a_z 或 A_Z
    @Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是a_z 或 A_Z", groups = {SaveGroup.class, UpdateGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    // The annotated element must not be null. Accepts any type
    @NotNull(message = "排序值不能为空", groups = {SaveGroup.class})
    // 排序值大于等于0的整数
    //The annotated element must be a number whose value must be higher or equal to the specified minimum.
    //Supported types are:
    //BigDecimal、BigInteger、byte, short, int, long, and their respective wrappers
    //Note that double and float are not supported due to rounding errors (some providers might provide some approximative support).
    //null elements are considered valid.
    @Min(value = 0, message = "排序值必须是大于等于0的整数", groups = {SaveGroup.class, UpdateGroup.class})
    private Integer sort;

}
