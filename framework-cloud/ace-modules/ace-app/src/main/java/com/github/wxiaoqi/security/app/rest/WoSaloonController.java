package com.github.wxiaoqi.security.app.rest;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.buffer.OperateConstants;
import com.github.wxiaoqi.security.api.vo.order.in.DoOperateByTypeVo;
import com.github.wxiaoqi.security.api.vo.order.in.SearchWoSaloon;
import com.github.wxiaoqi.security.api.vo.order.in.TransactionLogBean;
import com.github.wxiaoqi.security.api.vo.order.in.TrunWoInVo;
import com.github.wxiaoqi.security.api.vo.order.out.WoListVo;
import com.github.wxiaoqi.security.api.vo.order.out.WoVo;
import com.github.wxiaoqi.security.api.vo.user.ServerUserInfo;
import com.github.wxiaoqi.security.app.biz.*;
import com.github.wxiaoqi.security.app.entity.BizSubscribeWo;
import com.github.wxiaoqi.security.app.fegin.SmsUtilsFegin;
import com.github.wxiaoqi.security.app.mapper.BaseAppServerUserMapper;
import com.github.wxiaoqi.security.app.mapper.BizSubscribeWoMapper;
import com.github.wxiaoqi.security.app.mapper.BizWoMapper;
import com.github.wxiaoqi.security.app.vo.clientuser.out.UserVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * 工单
 *
 * @author huangxl
 * @Date 2018-11-27 14:57:49
 */
@RestController
@RequestMapping("woService")
@CheckClientToken
@CheckUserToken
@Slf4j
@Api(tags="服务端工单模块")
public class WoSaloonController {

    @Autowired
    private BizWoBiz bizWoBiz;
    @Autowired
    private BizWoMapper bizWoMapper;
    @Autowired
    private BizSubscribeWoBiz bizSubscribeWoBiz;
    @Autowired
    private BizSubscribeWoMapper bizSubscribeWoMapper;
    @Autowired
    private BaseAppServerUserBiz appServerUserBiz;
    @Autowired
    private BaseAppServerUserMapper appServerUserMapper;
    @Autowired
    private BizTransactionLogBiz bizTransactionLogBiz;
    @Autowired
    private BaseAppClientUserBiz baseAppClientUserBiz;
    @Autowired
    private SmsUtilsFegin smsUtilsFegin;


    @PostMapping("woSaloon")
    @ResponseBody
    @ApiOperation(value = "工单大厅列表查询", notes = "服务端APP工单模块---工单大厅列表查询",httpMethod = "POST")
    public ObjectRestResponse<WoListVo> woSaloon(@RequestBody @ApiParam SearchWoSaloon searchWoSaloon) throws Exception {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        if(StringUtils.isEmpty(userId)){
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("用户未登陆，请登陆系统");
            return objectRestResponse;
        }
        String woStatus = searchWoSaloon.getWoStatus()==null ? "" : searchWoSaloon.getWoStatus();
        if(StringUtils.isEmpty(woStatus)){
            objectRestResponse.setStatus(102);
            objectRestResponse.setMessage("参数为空，请输入正确的参数");
            return objectRestResponse;
        }
        if(!("01".equals(woStatus))){
            objectRestResponse.setStatus(102);
            objectRestResponse.setMessage("参数不正确，请输入正确的参数");
            return objectRestResponse;
        }
        List<WoListVo> woList = bizWoBiz.getWaitWoList(userId,searchWoSaloon);
        if(woList==null){
            woList = new ArrayList<WoListVo>();
        }
        objectRestResponse.setData(woList);
        return objectRestResponse;
    }

    @PostMapping("selectMyWoList")
    @ResponseBody
    @ApiOperation(value = "获取我的工单列表", notes = "服务端APP工单模块---获取我的工单列表",httpMethod = "POST")
    public ObjectRestResponse<WoListVo> selectMyWoList(@RequestBody @ApiParam SearchWoSaloon searchWoSaloon) throws Exception {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        if(StringUtils.isEmpty(userId)){
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("用户未登陆，请登陆系统");
            return objectRestResponse;
        }
        String woStatus = searchWoSaloon.getWoStatus()==null ? "" : searchWoSaloon.getWoStatus();
        if(StringUtils.isEmpty(woStatus)){
            objectRestResponse.setStatus(102);
            objectRestResponse.setMessage("参数为空，请输入正确的参数");
            return objectRestResponse;
        }
        if(!("03".equals(woStatus) || "05".equals(woStatus) || "04".equals(woStatus) || "07".equals(woStatus) || "99".equals(woStatus))){
            objectRestResponse.setStatus(102);
            objectRestResponse.setMessage("参数不正确，请输入正确的参数");
            return objectRestResponse;
        }
        List<WoListVo> woList = bizWoBiz.selectMyWoList(userId,searchWoSaloon);
        if(woList==null){
            woList = new ArrayList<WoListVo>();
        }
        objectRestResponse.setData(woList);
        return objectRestResponse;
    }

    /**
     * 接受工单
     * @param trunWoInVo 参数
     * @return
     */
    @PostMapping("doAccpet")
    @ResponseBody
    @ApiOperation(value = "接受工单", notes = "服务端工单模块---接受工单",httpMethod = "POST")
    public ObjectRestResponse doAccpet(@RequestBody @ApiParam TrunWoInVo trunWoInVo){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        String woId = trunWoInVo.getWoId();
        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(woId)){
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("参数不能为空");
            return objectRestResponse;
        }

        DoOperateByTypeVo doOperateByTypeVo = new DoOperateByTypeVo();
        doOperateByTypeVo.setId(woId);
        doOperateByTypeVo.setHandleBy(BaseContextHandler.getUserID());
        doOperateByTypeVo.setOperateType(OperateConstants.OperateType.WOGRAB.toString());
        return bizSubscribeWoBiz.doOperateByType(doOperateByTypeVo);
    }

    /**
     * 指派工单
     * @param trunWoInVo
     * @return
     */
    @PostMapping("doAssign")
    @ResponseBody
    @ApiOperation(value = "指派工单", notes = "服务端工单模块---指派工单",httpMethod = "POST")
    public ObjectRestResponse doAssign(@RequestBody @ApiParam TrunWoInVo trunWoInVo) throws Exception {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        String woId = trunWoInVo.getWoId();
        String handleBy = trunWoInVo.getHandleBy();

        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(woId) || StringUtils.isEmpty(handleBy)){
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("参数不能为空");
            return objectRestResponse;
        }

        WoVo woVo = bizSubscribeWoMapper.getWoDetail(woId);
        if(woVo!=null && StringUtils.isNotEmpty(woVo.getHandleBy())){
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("工单已在处理中，您不能指派");
            return objectRestResponse;
        }

        DoOperateByTypeVo doOperateByTypeVo = new DoOperateByTypeVo();
        doOperateByTypeVo.setId(woId);
        doOperateByTypeVo.setOperateType(OperateConstants.OperateType.WOGRAB.toString());
        doOperateByTypeVo.setHandleBy(handleBy);
        doOperateByTypeVo.setUserId(userId);
        doOperateByTypeVo.setDescription("指派");
        objectRestResponse = bizSubscribeWoBiz.doOperateByType(doOperateByTypeVo);

        //2.转单给对方成功后消息提醒
        UserVo userVo = baseAppClientUserBiz.getUserNameById(handleBy,"");
        if(userVo!=null){
            userId = userVo.getId();
            String mobilePhone = userVo.getMobilePhone();
            String msgTheme = "SRS_TURN_WO";
            String msgParam = "";

            userVo = baseAppClientUserBiz.getUserNameById(BaseContextHandler.getUserID(),"");
            if(userVo!=null){
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("turnBy",userVo.getName());
                msgParam = JSONObject.toJSON(paramMap).toString();
            }
            ObjectRestResponse restResponse = smsUtilsFegin.sendMsg(mobilePhone, null, null, null, null, userId, msgTheme, msgParam);
            log.info("发送消息结果："+restResponse.getStatus()+"-"+restResponse.getMessage());
        }
        return objectRestResponse;
    }

    /**
     * 转单
     * @param trunWoInVo 参数
     * @return
     */
    @PostMapping("doTurnWo")
    @ResponseBody
    @ApiOperation(value = "转单", notes = "服务端工单模块---转单",httpMethod = "POST")
    public ObjectRestResponse doTurnWo(@RequestBody @ApiParam TrunWoInVo trunWoInVo) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        String woId = trunWoInVo.getWoId();
        String handleBy = trunWoInVo.getHandleBy();

        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(woId) || StringUtils.isEmpty(handleBy)){
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("参数不能为空");
            return objectRestResponse;
        }
        WoVo woVo = bizSubscribeWoMapper.getWoDetail(woId);
        if(woVo==null || woVo.getHandleBy()==null || !userId.equals(woVo.getHandleBy())){
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("工单已在被别人处理中，您不能转单");
            return objectRestResponse;
        }

        //1.修改工单表
        BizSubscribeWo bizSubscribeWo = new BizSubscribeWo();
        bizSubscribeWo.setId(woId);
        bizSubscribeWo.setModifyBy(userId);
        bizSubscribeWo.setModifyTime(new Date());
        if(StringUtils.isNotEmpty(handleBy)){
            bizSubscribeWo.setHandleBy(handleBy);
            bizSubscribeWo.setLastHandleBy(userId);
        }
        bizSubscribeWoMapper.updateByPrimaryKeySelective(bizSubscribeWo);

        //3.记录工单操作日志
        TransactionLogBean transactionLogBean = new TransactionLogBean();
        transactionLogBean.setCurrStep("转单");
        transactionLogBean.setImgIds(trunWoInVo.getImgId());
        UserVo userVo = baseAppClientUserBiz.getUserNameById(handleBy,"");
        transactionLogBean.setDesc("已接单，处理中\r\n处理人："+userVo.getName()+"\r\n转单事由："+trunWoInVo.getDescription());
        transactionLogBean.setConName(userVo.getName());
        transactionLogBean.setConTel(userVo.getMobilePhone());
        bizTransactionLogBiz.insertTransactionLog(woId,transactionLogBean);

        //2.转单给对方成功后消息提醒
        if(userVo!=null) {
            userId = userVo.getId();
            String mobilePhone = userVo.getMobilePhone();
            String msgTheme = "SRS_TURN_WO";
            String msgParam = "";

            userVo = baseAppClientUserBiz.getUserNameById(BaseContextHandler.getUserID(),"");
            if(userVo!=null){
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("turnBy",userVo.getName());
                msgParam = JSONObject.toJSON(paramMap).toString();
            }

            ObjectRestResponse restResponse = smsUtilsFegin.sendMsg(mobilePhone, null, null, null, null, userId, msgTheme, msgParam);
            log.info("发送消息结果：" + restResponse.getStatus() + "-" + restResponse.getMessage());
        }

        return objectRestResponse;
    }

    /**
     * 获取可指派/转单人员列表
     * @param woId 参数
     * @return
     */
    @GetMapping("getAccpetWoUserList")
    @ResponseBody
    @ApiOperation(value = "获取可指派/转单人员列表", notes = "服务端工单模块---获取可指派/转单人员列表",httpMethod = "GET")
    @ApiImplicitParam(name="woId",value="工单ID",dataType="String",required = true ,paramType = "query",example="")
    public ObjectRestResponse getAccpetWoUserList(String woId) {
        return bizSubscribeWoBiz.getAccpetWoUserList(woId);
    }

    /**
     * 获取可指派/转单人员列表不需要token
     * @param woId 参数
     * @return
     */
    @GetMapping("getAccpetWoUserListNoToken")
    @ResponseBody
    @ApiOperation(value = "获取可指派/转单人员列表", notes = "服务端工单模块---获取可指派/转单人员列表不需要token",httpMethod = "GET")
    @ApiImplicitParam(name="woId",value="工单ID",dataType="String",required = true ,paramType = "query",example="")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse<List<Map<String,String>>> getAccpetWoUserListNoToken(String woId) {
        ObjectRestResponse result = new ObjectRestResponse();
        List<Map<String,String>> serverUserListResult = new ArrayList<>();
        ObjectRestResponse restResponse = bizSubscribeWoBiz.getAccpetWoUserList(woId);
        if(restResponse!=null && restResponse.getStatus()==200){
            List<ServerUserInfo> serverUserList = new ArrayList<>();
            serverUserList = restResponse.getData()==null ? null : (List<ServerUserInfo>)restResponse.getData();
            if(serverUserList!=null && serverUserList.size()>0){
                Map<String,String> userMap = null;
                for (ServerUserInfo serverUserInfo : serverUserList){
                    userMap = new HashMap<>();
                    userMap.put("userId",serverUserInfo.getUserId());
                    userMap.put("phone",serverUserInfo.getPhone());
                    userMap.put("userName",serverUserInfo.getUserName());
                    serverUserListResult.add(userMap);
                }
            }
        }
        result.setData(serverUserListResult);
        return result;
    }

    /**
     * 按工单类型统计我的工单列表
     * @return
     */
    @GetMapping("selectMyWoListCountByWoType")
    @ResponseBody
    @ApiOperation(value = "按工单类型统计我的工单列表", notes = "服务端工单模块---按工单类型统计我的工单列表",httpMethod = "GET")
    public ObjectRestResponse selectMyWoListCountByWoType(String projectId) throws Exception {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();

        Map<String, String> map = new HashMap<>();
        map.put("handleBy",userId);
        map.put("projectId",projectId);
        List<Map<String,Integer>> countList = bizWoMapper.selectMyWoListCountByWoType(map);
        if(countList==null || countList.size()==0){
            countList = new ArrayList<>();
        }
        if(countList.size()==1){
            Map<String,Integer> countMap = countList.get(0)==null ? null : (Map<String,Integer>)countList.get(0);
            if(countMap==null || countMap.get("woType") == null || "".equals(countMap.get("woType"))){
                countList = new ArrayList<>();
            }
        }
        objectRestResponse.setData(countList);
        return objectRestResponse;
    }

    /**
     * 获取用户的完成工单数和接单数
     * @return
     */
    @GetMapping("getWoSumByUserId")
    @ResponseBody
    @ApiOperation(value = "获取用户的完成工单数和接单数", notes = "服务端工单模块---获取用户的完成工单数和接单数",httpMethod = "GET")
    public ObjectRestResponse<Map<String,Object>> getWoSumByUserId(String projectId) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();

        if(StringUtils.isEmpty(userId)){
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("参数不能为空");
            return objectRestResponse;
        }
        Map<String,Integer> woSum = bizSubscribeWoMapper.getWoSumByUserId(userId,projectId);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("finishWoSum",woSum.get("finishWoSum"));
        resultMap.put("acceptWoSum",woSum.get("acceptWoSum"));
        Double appraisalVal = bizSubscribeWoMapper.getAppraisalValByUserId(userId);
        if(appraisalVal!=null) {
            BigDecimal b = new BigDecimal(appraisalVal);
            double appraisalValTem = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            resultMap.put("appraisalVal", appraisalValTem);
        }
        objectRestResponse.setData(resultMap);
        return objectRestResponse;
    }

    @GetMapping("getServerUserProject")
    @ApiOperation(value = "获取物业人员项目", notes = "获取物业人员项目",httpMethod = "GET")
    public ObjectRestResponse getServerUserProject() {
        return appServerUserBiz.getServerUserProject();
    }
}