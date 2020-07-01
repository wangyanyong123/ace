package com.github.wxiaoqi.store.rest;

import com.github.wxiaoqi.constants.ResponseCodeEnum;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.store.biz.ProductStoreBiz;
import com.github.wxiaoqi.store.entity.BizStore;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 库存管理
 */
@RestController
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class BaseStoreController extends BaseController<ProductStoreBiz,BizStore,String> {

    /**
     * 参数异常处理
     * @param ex 参数异常类
     * @return 异常描述信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ObjectRestResponse handleException(MethodArgumentNotValidException ex) {
        //从异常中取出参数校验不通过的信息
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> errors = bindingResult.getFieldErrors();
        String errorMessage = errors.get(0).getDefaultMessage();
        ObjectRestResponse result = new ObjectRestResponse();
        result.setStatus(ResponseCodeEnum.PARAMETER_EXCEPTION.getKey());
        result.setMessage(errorMessage);
        return result;
    }

}