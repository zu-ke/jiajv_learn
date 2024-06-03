package com.zuke.zukeliving.commodity.exception;

import com.zuke.common.exception.JiajvCodeEnum;
import com.zuke.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

// 全局异常处理器，处理controller包中的类对于entity属性的校验（JS303）
@ResponseBody
@Slf4j
@ControllerAdvice(basePackages = "com.zuke.zukeliving.commodity.controller")
public class JiajvControllerExceptionAdvice {

    //@ExceptionHandler : 写明要处理那些异常
    //MethodArgumentNotValidException.class : 如果controller包里面的类抛出了数据校验异常，就交由handleValidException()方法处理
    // 方法里面记得加上注解@Validated ： public R save(@Validated @RequestBody BrandEntity brand){}
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public R handleValidException(MethodArgumentNotValidException ex) {
        // 输出日志，观察异常的信息
        //log.error("数据校验出现问题{} 异常类型是{}", ex.getMessage(), ex.getClass());

        // 得到BindingResult
        BindingResult bindingResult = ex.getBindingResult();
        //创建一个map，用于收集校验错误
        Map<String, String> errorMap = new HashMap();
        //遍历result收集错误信息
        bindingResult.getFieldErrors().forEach((i) -> {
            //得到field
            String field = i.getField();
            //得到校验的错误信息
            String message = i.getDefaultMessage();
            //收集
            errorMap.put(field, message);
        });
        return R.error(JiajvCodeEnum.INVALID_EXCEPTION.getCode(), JiajvCodeEnum.INVALID_EXCEPTION.getMsg()).put("data", errorMap);
    }

    // 处理没有精确匹配到的异常/错误，返回一个同意的信息，方便前端处理
    // Throwable.class下由很多异常，大部分异常可以被捕获
    // 说明：如果按照业务逻辑，需要去精确匹配处理异常/错误，可以再写方法处理
    @ExceptionHandler({Throwable.class})
    public R handleException(Throwable throwable) {

        // 使用公共模块的枚举类com.zuke.common.exception.JiajvCodeEnum
        return R.error(JiajvCodeEnum.INVALID_EXCEPTION.getCode(), JiajvCodeEnum.INVALID_EXCEPTION.getMsg());
    }
}
