package com.github.wxiaoqi.security.jinmao.feign;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:08 2018/11/21
 * @Modified By:
 */

import com.github.wxiaoqi.security.api.vo.order.in.FileListVo;
import com.github.wxiaoqi.security.api.vo.to.AliRefundTO;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "ace-tool", configuration = FeignApplyConfiguration.class)
public interface ToolFegin {

	/**
	 * 获取验证码
	 * @return
	 */
	@RequestMapping(value = "/smsService/sendMsg", method = RequestMethod.GET)
	ObjectRestResponse getCode(@RequestParam("mobilePhone") String mobilePhone, @RequestParam("num") Integer num,
                               @RequestParam("lostSecond") Integer lostSecond, @RequestParam("bizType") String bizType,
                               @RequestParam("msgTheme") String msgTheme, @RequestParam("msgParam") String msgParam);

	/**
	 * 验证码认证
	 * @return
	 */
	@GetMapping("/smsService/checkCode")
	ObjectRestResponse checkCode(@RequestParam("mobilePhone") String mobilePhone, @RequestParam("volidCode") String volidCode);

	/**
	 * 验证码认证
	 * @return
	 */
	@GetMapping("/smsService/codeIsTrue")
	ObjectRestResponse codeIsTrue(@RequestParam("mobilePhone") String mobilePhone, @RequestParam("volidCode") String volidCode);

	@GetMapping("/config/getOssConfig")
	ObjectRestResponse getOssConfig(@RequestParam("isPub") String isPub);

	@GetMapping("/config/getSTSToken")
	ObjectRestResponse getSTSToken();


	@GetMapping("/oss/movePath")
	ObjectRestResponse<String> movePath(@RequestParam("url") String url, @RequestParam("dirPath") String dirPath);

	@GetMapping("/oss/movePaths")
	ObjectRestResponse<List<String>> movePaths(@RequestParam("urls") String urls, @RequestParam("dirPath") String dirPath);

	/**
	 * 移动多个文件到指定路径
	 * @param urls  多个文件用逗号(,)隔开
	 * @param dirPath 移动目前文件路径
	 * @return 返回移动后文件,多个文件用逗号(,)隔开
	 */
    @GetMapping("/oss/moveUrlPaths")
    ObjectRestResponse<String> moveUrlPaths(@RequestParam("urls") String urls, @RequestParam("dirPath") String dirPath) ;

	/**
	 * 移动多个文件到指定路径
	 * @param urls  多个文件用逗号(,)隔开
	 * @param dirPath 移动目前文件路径
	 * @return 返回移动后文件,多个文件用逗号(,)隔开
	 */
	@GetMapping("/oss/moveAppUploadUrlPaths")
	ObjectRestResponse<String> moveAppUploadUrlPaths(@RequestParam("urls") String urls, @RequestParam("dirPath") String dirPath) ;


	@RequestMapping(value = "/oss/uploadBase64Images",method = RequestMethod.POST)
	public ObjectRestResponse uploadBase64Images(@RequestBody @ApiParam FileListVo fileListVo) ;


		/**
         * 用户手机信息更新
         * @return
         */
	@RequestMapping(value = "/smsService/updateMobileInfo")
	public ObjectRestResponse updateMobileInfo(@RequestParam("cid") String cid, @RequestParam("userId") String userId,
                                               @RequestParam("clientType") String clientType, @RequestParam("os") String os,
                                               @RequestParam("osVersion") String osVersion, @RequestParam("version") String version,
                                               @RequestParam("macId") String macId) ;


	@RequestMapping(value ="/smsService/sendMsg",method = RequestMethod.GET)
	public ObjectRestResponse sendMsg(@RequestParam(value = "mobilePhone", required = false) String mobilePhone,
                                      @RequestParam(value = "num", required = false) Integer num,
                                      @RequestParam(value = "lostSecond", required = false) Integer lostSecond,
                                      @RequestParam(value = "bizType", required = false, defaultValue = "2") String bizType,
                                      @RequestParam(value = "email", required = false) String email,
                                      @RequestParam(value = "userId", required = false) String userId,
                                      @RequestParam("msgTheme") String msgTheme,
                                      @RequestParam(value = "msgParam", required = false) String msgParam);

	@RequestMapping(value = "/aliPay/refund",method = RequestMethod.POST)
	ObjectRestResponse refund(@RequestBody @ApiParam AliRefundTO aliRefundTO) ;

}
