package com.github.wxiaoqi.security.app.axb;

import com.github.wxiaoqi.security.api.vo.pns.out.AXBResult;
import com.github.wxiaoqi.security.app.axb.bean.*;
import com.github.wxiaoqi.security.api.vo.pns.in.AXBBindingDTO;
import com.github.wxiaoqi.security.api.vo.constant.AXBResponseCodeEnum;
import com.github.wxiaoqi.security.app.axb.constants.BindingFlagConstant;
import com.github.wxiaoqi.security.app.axb.constants.BindingTypeConstant;
import com.github.wxiaoqi.security.app.axb.service.AXBService;
import com.github.wxiaoqi.security.app.entity.BizPnsCallLog;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("axb")
@CheckClientToken
@CheckUserToken
@Api(tags = "号码隐私服务")
public class AXBController {

    @Autowired
    private AXBService axbService;

    /**
     * 绑定
     * @return
     */
    @RequestMapping(value = "/binding",method = RequestMethod.POST)
    @ApiOperation(value = "号码绑定--APP", notes = "号码绑定---APP",httpMethod = "POST")
    public ObjectRestResponse axbBinding(@RequestBody @ApiParam @Valid AXBBindingDTO axbBindingDTO){
        return axbService.axbBinding(axbBindingDTO);
    }

    /**
     * 绑定（内部调用）
     * @return
     */
    @RequestMapping(value = "/binding/inner",method = RequestMethod.POST)
    @ApiOperation(value = "号码绑定--APP", notes = "号码绑定---APP",httpMethod = "POST")
    public AXBResult axbBindingInner(@RequestBody @ApiParam @Valid AXBBindingDTO axbBindingDTO){
        return axbService.axbBindingInner(axbBindingDTO);
    }



    /**
     *  解绑
     * @param bindId 邦定ID
     * @return 结果
     */
    @RequestMapping(value = "/unbinding",method = RequestMethod.GET)
    @ApiOperation(value = "号码解绑--APP", notes = "号码解绑---APP",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="bindId",value="邦定ID",dataType="String",paramType = "query")
    })
    public ObjectRestResponse axbUnbinding(String bindId){
        return axbService.axbUnbinding(bindId);
    }

    /**
     *  解绑（内部调用）
     * @param bindId 邦定ID
     * @return 结果
     */
    @RequestMapping(value = "/unbinding/inner",method = RequestMethod.GET)
    @ApiOperation(value = "号码解绑--APP", notes = "号码解绑---APP",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="bindId",value="邦定ID",dataType="String",paramType = "query")
    })
    public AXBResult axbUnbindingInner(String bindId){
        return axbService.axbUnbindingInner(bindId);
    }

    /**
     * 外呼转接设置
     * @param axbOuttransferSet 设置参数
     * @return
     */
    @RequestMapping(value = "/outtransfer/Set",method = RequestMethod.POST)
    @ApiOperation(value = "外呼转接设置--APP", notes = "外呼转接设置---APP",httpMethod = "POST")
    public String axbOuttransferSet(@RequestBody @ApiParam AXBOuttransferSet axbOuttransferSet){
        return axbService.axbOuttransferSet(axbOuttransferSet);
    }

    /**
     * 解绑失败再次添加任务去解绑
     * @param bindId
     */
    @RequestMapping(value = "/add/timeout/job",method = RequestMethod.GET)
    @ApiOperation(value = "解绑失败再次添加任务去解绑", notes = "解绑失败再次添加任务去解绑",httpMethod = "GET")
    public void addBindTimeoutJob(String bindId){
        axbService.addBindTimeoutJob(bindId);
    }

    /**
     * 回调地址接收话单
     * @param axbCallbackCall 话单参数
     * @return
     */
    @RequestMapping(value = "/callback",method = RequestMethod.POST)
    @ApiOperation(value = "接收话单--APP", notes = "接收话单---APP",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public AXBResult callbackURL(@RequestBody @ApiParam AXBCallbackCall axbCallbackCall){
        return axbService.callbackURL(axbCallbackCall);
    }

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
        String messsage = errors.get(0).getDefaultMessage();
        ObjectRestResponse result = new ObjectRestResponse();
        result.setStatus(AXBResponseCodeEnum.PARAMETER_EXCEPTION.getKey());
        result.setMessage(messsage);
        return result;

    }

}
