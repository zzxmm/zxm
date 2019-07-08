package com.shouzan.back.annotation.impl;

import com.shouzan.back.annotation.MinValue;
import com.shouzan.back.annotation.ParameterException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: com.shouzan.back.annotation.impl.AnnotationMin
 * @Author: bin.yang
 * @Date: 2019/5/24 14:22
 * @Description: TODO  最小参数限制
 */
@Slf4j
public class AnnotationMin implements ConstraintValidator<MinValue, Object> {

    private String deMessage;

    private Double deValue;

    /**
     * @Description: 初始化运行
     * @param constraint
     * @[param] [constraint]
     * @return void
     * @author:  bin.yang
     * @date:  2019/6/18 4:13 PM
     */
    @Override
    public void initialize(MinValue constraint) {
        deMessage = constraint.message();
        deValue = constraint.value();
    }

    /**
     * @Description: 注解逻辑效验
     * @param value
     * @param context
     * @[param] [value, context]
     * @return boolean
     * @author:  bin.yang
     * @date:  2019/6/18 4:14 PM
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            throw new ParameterException(deMessage);
        }
        if (Double.valueOf(value.toString()) < deValue) {
            throw new ParameterException(deMessage);
        }
        return true;
    }


}
