package com.github.wxiaoqi.security.jinmao.feign;

import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value = "ace-tool", configuration = FeignApplyConfiguration.class)
public interface OssExcelFeign {

    @RequestMapping(value = "/oss/uploadExcel" ,method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ObjectRestResponse uploadExcel(@RequestBody @ApiParam ExcelInfoVo excelInfoVo);

}
