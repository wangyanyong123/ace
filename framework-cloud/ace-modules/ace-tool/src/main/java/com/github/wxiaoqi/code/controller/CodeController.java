package com.github.wxiaoqi.code.controller;

import com.github.wxiaoqi.code.service.CodeUtilService;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:16 2018/11/12
 * @Modified By:
 */
@RestController
@RequestMapping("/code")
@Api(tags="获取系统唯一编码")
public class CodeController {
    @Autowired
    private CodeUtilService codeUtilService;
    /**
     * 上传文件
     */
    @RequestMapping(value = "/getCode",method = {RequestMethod.GET})
    public ObjectRestResponse<String> upload(@RequestParam("searchKey") String searchKey, @RequestParam("prefixCode")String prefixCode,
                                             @RequestParam("codeSize")String codeSize, @RequestParam("sequenceType")String sequenceType) throws Exception{
        if (StringUtils.isBlank(searchKey)) {
            throw new BusinessException("searchKey不能为空");
        }
        if (StringUtils.isBlank(prefixCode)) {
            throw new BusinessException("prefixCode不能为空");
        }
        if (StringUtils.isBlank(codeSize)) {
            throw new BusinessException("codeSize不能为空");
        }
        if (StringUtils.isBlank(sequenceType)) {
            throw new BusinessException("sequenceType不能为空");
        }
        return new ObjectRestResponse<>().data(codeUtilService.getAutoCode(searchKey, prefixCode, codeSize, sequenceType));
    }
}
