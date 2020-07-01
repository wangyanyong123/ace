package com.github.wxiaoqi.security.app.fegin;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ace
 * @create 2018/2/1.
 */
@FeignClient(value = "ace-tool", configuration = FeignApplyConfiguration.class)
public interface SmsFeign {

    /**
     * 发送短信
     * @param mobilePhone
     * @param num
     * @param lostSecond
     * @param bizType
     * @param msgTheme
     * @return
     */
    @RequestMapping(value = "/smsService/sendMsg", method = RequestMethod.GET)
    public ObjectRestResponse sendMsg(@RequestParam("mobilePhone") String mobilePhone, @RequestParam(value = "num", required = false) Integer num,
                                      @RequestParam(value = "lostSecond", required = false) Integer lostSecond, @RequestParam("bizType") String bizType,
                                      @RequestParam("msgTheme") String msgTheme, @RequestParam("msgParam") String param);

    @RequestMapping(value = "/oss/upload", method = RequestMethod.GET)
    public ObjectRestResponse<String> upload(@ApiParam(value = "上传的文件", required = true) @RequestParam("file") MultipartFile file, @RequestParam("type") String type,
                                             @RequestParam("fileDesc") String fileDesc, @RequestParam("system") String system);
}
