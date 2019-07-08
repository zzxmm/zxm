package com.shouzan.back.annotation;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: com.shouzan.back.annotation.ParameterException
 * @Author: bin.yang
 * @Date: 2019/6/14 14:55
 * @Description: TODO  注解效验参数异常
 */
@Data
public class ParameterException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 6985986496754341584L;

    protected String message;

    public ParameterException(String message) {
        this.message = message;
    }

    public ParameterException() {
        super();
    }
}
