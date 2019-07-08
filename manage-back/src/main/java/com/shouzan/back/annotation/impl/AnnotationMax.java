package com.shouzan.back.annotation.impl;

import com.shouzan.back.annotation.MaxValue;
import com.shouzan.back.annotation.MinValue;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;
import scala.annotation.meta.field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: com.shouzan.back.annotation.impl.AnnotationMax
 * @Author: bin.yang
 * @Date: 2019/5/24 14:22
 * @Description: TODO  参数最大限制
 */
@Slf4j
public class AnnotationMax implements ConstraintValidator<MaxValue, Object> {

    private String deMessage;

    private Double deValue;

    /**
     * @Description: 初始化运行
     * @param constraintAnnotation
     * @[param] [constraintAnnotation]
     * @return void
     * @author:  bin.yang
     * @date:  2019/6/18 4:13 PM
     */
    @Override
    public void initialize(MaxValue constraintAnnotation) {
        deMessage = constraintAnnotation.message();
        deValue = constraintAnnotation.value();
    }

    /**
     * @Description: 注解逻辑效验
     * @param value
     * @param context
     * @[param] [value, context]
     * @return boolean
     * @author:  bin.yang
     * @date:  2019/6/18 4:13 PM
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (Double.valueOf(value.toString()) > deValue) {
            return false;
        }
        return true;
    }
}
