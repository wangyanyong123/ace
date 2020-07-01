package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizFeedbackBiz;
import com.github.wxiaoqi.security.app.vo.feedback.SaveFeedback;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 意见反馈
 *
 * @author zxl
 * @Date 2019-01-08 11:00:06
 */
@RestController
@RequestMapping("bizFeedback")
@CheckClientToken
@CheckUserToken
@Api(tags = "意见反馈")
public class BizFeedbackController {

    @Autowired
    private BizFeedbackBiz bizFeedbackBiz;



    /**
     * 提交反馈
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveFeedback" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "提交反馈---APP端", notes = "提交反馈---APP端",httpMethod = "POST")
    public ObjectRestResponse saveFeedback(@RequestBody @ApiParam SaveFeedback param){
        return  bizFeedbackBiz.saveFeedback(param);
    }


}