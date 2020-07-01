package com.github.wxiaoqi.security.im.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.vo.SmsNoticeVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ：wangjl
 * @date ：Created in 2019/9/19 17:46
 * @description：
 * @modified By：
 * @version: $
 */
@FeignClient(value = "ace-app", configuration = FeignApplyConfiguration.class)
public interface SysMsgFeign {

    @RequestMapping(value = "/sysMsgNotice/saveSmsNotice", method = {RequestMethod.POST})
    ObjectRestResponse saveSmsNotice(@RequestBody SmsNoticeVo smsNoticeVo) ;

}
