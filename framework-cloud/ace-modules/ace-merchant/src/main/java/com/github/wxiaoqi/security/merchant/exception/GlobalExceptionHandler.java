package com.github.wxiaoqi.security.merchant.exception;

import com.github.wxiaoqi.security.merchant.constants.ResponseCodeEnum;
import com.github.wxiaoqi.security.merchant.response.MerchantObjectResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 全局异常处理类
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	@ExceptionHandler(value=MethodArgumentNotValidException.class)
	public MerchantObjectResponse MethodArgumentNotValidHandler(MethodArgumentNotValidException exception){
		//取出参数校验信息
		BindingResult bindingResult = exception.getBindingResult();
		List<FieldError> errors = bindingResult.getFieldErrors();
		String message = errors.get(0).getDefaultMessage();
		return MerchantObjectResponse.error(ResponseCodeEnum.PARAMETER_EXCEPTION,message);

	}
}