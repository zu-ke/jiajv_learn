package com.zuke.common.valid;

import javax.sound.midi.Track;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

// 校验器
// EnumConstraintValidator：真正的校验器，校验的逻辑写在这里
// 校验器需要实现接口ConstraintValidator
// <EnumValidate, Integer>标识该校验器是针对com.zuke.common.valid.EnumValidate注解传入的integer类型数据进行校验
public class EnumConstraintValidator implements ConstraintValidator<EnumValidate, Integer> {

    //用于收集传入的values值
    private Set<Integer> set = new HashSet<>();

    @Override
    public void initialize(EnumValidate constraintAnnotation) {
        //下面是程序员在属性上写的values值
        int[] values = constraintAnnotation.values();
        for (int value : values) {
            //System.out.println(value);
            set.add(value);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        //value表单传进来的值
        //System.out.println("isValid==>" + value);
        return set.contains(value);
    }
}
