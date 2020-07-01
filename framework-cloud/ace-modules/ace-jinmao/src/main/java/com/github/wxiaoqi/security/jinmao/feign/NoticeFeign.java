package com.github.wxiaoqi.security.jinmao.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.jinmao.entity.BizSubscribe;
import com.github.wxiaoqi.security.jinmao.vo.face.UserFaceInfo;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ace-app", configuration = FeignApplyConfiguration.class)
public interface NoticeFeign {


    @RequestMapping(value = "/clientUser/syncPropertyToCrm", method = RequestMethod.POST)
    public ObjectRestResponse<BizSubscribe> syncPropertyToCrm(@RequestBody @ApiParam BizSubscribe bizSubscribe);

    @RequestMapping(value = "/face/deleteFaceByUser", method = RequestMethod.POST)
    ObjectRestResponse deleteFaceByUser(@RequestBody @ApiParam UserFaceInfo userFaceInfo);
}
