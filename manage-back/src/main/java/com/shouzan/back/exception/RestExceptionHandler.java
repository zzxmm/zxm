package com.shouzan.back.exception;

import com.shouzan.back.annotation.ParameterException;
import com.shouzan.back.constant.Response;
import com.shouzan.common.msg.ObjectRestResponse;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;

/**   
 * @ClassName:  RestExceptionHandler   
 * @Description:异常处理器
 * @author:
 * @date:   2017年12月11日 下午2:16:58   
 *    
 * @Copyright:2017
 *
 */
@ControllerAdvice()
@SuppressWarnings("rawtypes")
public class RestExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);
	
	@ExceptionHandler(BindException.class)
	@ResponseBody
	private ObjectRestResponse illegalPostParamsExceptionHandler(BindException e) {
		List<FieldError> errors = e.getBindingResult().getFieldErrors();
		StringBuffer sb = new StringBuffer();
		errors.forEach(item -> sb.append(item.getDefaultMessage()).append(","));
		log.error("入参检查Exception:{}", sb.substring(0, sb.length() - 1), e);
		return new ObjectRestResponse().rel(Response.FAILURE).msg(sb.substring(0, sb.length() - 1));
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	private ObjectRestResponse illegalPostParamsExceptionHandler(MethodArgumentNotValidException e) {
		List<FieldError> errors = e.getBindingResult().getFieldErrors();
		StringBuffer sb = new StringBuffer();
		errors.forEach(item -> sb.append(item.getDefaultMessage()).append(","));
		log.error("入参检查Exception:{}", sb.substring(0, sb.length() - 1), e);
		return new ObjectRestResponse().rel(Response.FAILURE).msg(sb.substring(0, sb.length() - 1));
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	private ObjectRestResponse illegalGetParamsExceptionHandler(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> set = e.getConstraintViolations();
		StringBuffer sb = new StringBuffer();
		set.forEach(item -> sb.append(item.getMessageTemplate()).append(","));
		log.error("入参检查Exception:{}", sb.substring(0, sb.length() - 1), e);
		return new ObjectRestResponse().rel(Response.FAILURE).msg(sb.substring(0, sb.length() - 1));
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	private ObjectRestResponse ParameterException(ValidationException e) {
        Throwable cause = e.getCause();
        log.error(cause.getMessage());
        return new ObjectRestResponse().rel(Response.FAILURE).msg(cause.getMessage());
	}

    @ExceptionHandler(Exception.class)
    @ResponseBody
    private ObjectRestResponse ExceptionHandlerAll(Exception e) {
        e.printStackTrace();
        return new ObjectRestResponse().rel(Response.FAILURE).msg("服务器内部异常");
    }

    @ExceptionHandler(MyBatisSystemException.class)
    @ResponseBody
    private ObjectRestResponse ExceptionHandlerAll(MyBatisSystemException e) {
        e.printStackTrace();
        return new ObjectRestResponse().rel(Response.FAILURE).msg(" 请求错误 ! 注意请求方式 与 格式 .");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    private ObjectRestResponse ExceptionHandlerAll(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        return new ObjectRestResponse().rel(Response.FAILURE).msg(" 请求错误 ! 注意请求方式 与 格式 .");
    }

}
