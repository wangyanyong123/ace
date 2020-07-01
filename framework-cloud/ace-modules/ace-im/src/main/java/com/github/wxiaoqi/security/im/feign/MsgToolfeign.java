package com.github.wxiaoqi.security.im.feign;

import com.github.wxiaoqi.security.api.vo.search.IndexObject;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:58 2019/1/26
 * @Modified By:
 */
@FeignClient(value = "ace-tool")
public interface  MsgToolfeign {
	@RequestMapping(value ="/smsService/sendMsg",method = RequestMethod.GET)
	ObjectRestResponse sendMsg(@RequestParam(value ="mobilePhone",required = false) String mobilePhone,
									  @RequestParam(value = "num" ,required = false) Integer num,
									  @RequestParam(value = "lostSecond" ,required = false) Integer lostSecond,
									  @RequestParam(value = "bizType",required = false ,defaultValue = "2") String bizType,
									  @RequestParam(value = "email",required = false) String email,
									  @RequestParam(value = "userId",required = false) String userId,
									  @RequestParam("msgTheme") String msgTheme,
									  @RequestParam(value = "msgParam",required = false) String msgParam);

	@RequestMapping(value = "/search", method = {RequestMethod.POST})
	public TableResultResponse<IndexObject> search(@RequestParam String word, @RequestParam(defaultValue = "1") Integer pageNumber, @RequestParam(defaultValue = "15") Integer pageSize) ;

}
