package com.shouzan.back.annotation;

import com.shouzan.back.annotation.impl.AnnotationMin;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @ClassName: com.shouzan.back.annotation.MinValue
 * @Author: bin.yang
 * @Date: 2019/5/24 11:03
 * @Description: TODO  最小值效验注解
 */

@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = AnnotationMin.class)
public @interface MinValue {

    String message() default " ERROR message : 入参错误[参数不符合规则] ";

    double value() default 0.01;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
