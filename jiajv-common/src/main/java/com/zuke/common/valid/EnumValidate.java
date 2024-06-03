package com.zuke.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// 自定义注解
// @Constraint(validatedBy = {EnumConstraintValidator.class})可以指定该自定义注解
//      和com.zuke.common.valid.EnumConstraintValidator校验器关联
// String message() default "{?}";指定校验时返回的信息
// Class<?>[] groups() default { };支持指定组

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {EnumConstraintValidator.class})
public @interface EnumValidate {

    String message() default "{com.zuke.common.valid.EnumValidate.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // 增加values属性，可以传入数值
    int[] values() default {};
}
