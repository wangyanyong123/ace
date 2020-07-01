package com.github.wxiaoqi.security.app.job;

import com.github.wxiaoqi.security.app.axb.constants.BindingFlagConstant;
import com.github.wxiaoqi.security.app.axb.service.AXBService;
import com.github.wxiaoqi.security.app.entity.BizPnsCall;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * XxlJob开发示例（Bean模式）
 *
 * 开发步骤：
 * 1、在Spring Bean实例中，开发Job方法，方式格式要求为 "public ReturnT<String> execute(String param)"
 * 2、为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法",
 * destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@Slf4j
@Component
public class AXBBindTimeOutInserverJobHandler {

    @Resource
    private AXBService axbService;

    /**
     * 1、订单超时（Bean模式）
     */
    @XxlJob("axbBindTimeOutInserverJobHandler")
    public ReturnT<String> orderPayTimeOutJobHandler(String param) {
        XxlJobLogger.log("XXL-JOB, handler={}, param={}",
                "productOrderPayTimeOutJobHandler",param);
        if(StringUtils.isEmpty(param)){
            XxlJobLogger.log("参数为空");
            return ReturnT.FAIL;
        }

        BizPnsCall pnsCall = axbService.queryBindRecord(param);
        if(pnsCall == null){
            XxlJobLogger.log("绑定关系不存在，param={}",param);
            return ReturnT.FAIL;
        }

        if(BindingFlagConstant.BINGDING == pnsCall.getBindingFlag()){
            ObjectRestResponse restResponse = axbService.overTimeHandle(param);
            try {
                XxlJobLogger.log("自动解绑结果："+ JacksonJsonUtil.beanToJson(restResponse));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(!restResponse.success()){
                XxlJobLogger.log("隐私号解绑失败，param={}，message={}",param,restResponse.getMessage());
                return ReturnT.FAIL;
            }
        }else{
            XxlJobLogger.log("隐私号已解绑，是否绑定={}"
                    ,BindingFlagConstant.BINGDING == pnsCall.getBindingFlag());
        }
        return ReturnT.SUCCESS;
    }

}
