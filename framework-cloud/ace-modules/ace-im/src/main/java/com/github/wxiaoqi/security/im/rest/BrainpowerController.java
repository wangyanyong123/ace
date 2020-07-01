package com.github.wxiaoqi.security.im.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.ToolUtil;
import com.github.wxiaoqi.security.im.biz.BizHousekeeperUserBiz;
import com.github.wxiaoqi.security.im.entity.BizChatMessage;
import com.github.wxiaoqi.security.im.mapper.BizChatMessageMapper;
import com.github.wxiaoqi.security.im.mapper.BrainpowerMapper;
import com.github.wxiaoqi.security.im.tio.msg.BrainMessageIn;
import com.github.wxiaoqi.security.im.tio.msg.BrainMessageOut;
import com.github.wxiaoqi.security.im.vo.brainpower.in.ChangeBrainpowerInVo;
import com.github.wxiaoqi.security.im.vo.brainpower.in.GuessBrainpowerInVo;
import com.github.wxiaoqi.security.im.vo.brainpower.in.OperateVo;
import com.github.wxiaoqi.security.im.vo.brainpower.out.BrainpowerFunctionVo;
import com.github.wxiaoqi.security.im.vo.brainpower.out.BrainpowerQuestionVo;
import com.github.wxiaoqi.security.im.vo.userchat.out.HouseKeeperVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tio.utils.json.Json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 
 *
 * @author huangxl
 * @Date 2019-4-17 13:32:27
 */
@RestController
@RequestMapping("brainpower")
@CheckClientToken
@CheckUserToken
@Api(tags="人工智能")
public class BrainpowerController {

	@Autowired
	private BrainpowerMapper brainpowerMapper;
	@Autowired
	private BizChatMessageMapper bizChatMessageMapper;

	@PostMapping("getGuessQuestList")
	@ApiOperation(value = "获取猜你想问接口", notes = "获取猜你想问接口",httpMethod = "POST")
	public ObjectRestResponse<List<BrainpowerQuestionVo>> getGuessQuestList(@RequestBody @ApiParam GuessBrainpowerInVo guessBrainpowerInVo){
		ObjectRestResponse restResponse = new ObjectRestResponse();
		List<BrainpowerQuestionVo> brainpowerQuestionList = brainpowerMapper.getBrainpowerQuestionList(guessBrainpowerInVo.getFunctionIdList());
		if(brainpowerQuestionList==null){
			brainpowerQuestionList = new ArrayList<BrainpowerQuestionVo>();
		}
		brainpowerQuestionList = ToolUtil.getSubStringByRadom(brainpowerQuestionList,5);
		restResponse.setData(brainpowerQuestionList);
		return restResponse;
	}

	@GetMapping("getBottomBrainpowerFunctionList")
	@ApiOperation(value = "获取置低功能接口", notes = "获取置低功能接口",httpMethod = "GET")
	public ObjectRestResponse<List<BrainpowerFunctionVo>> getBottomBrainpowerFunctionList(){
		ObjectRestResponse restResponse = new ObjectRestResponse();
		List<BrainpowerFunctionVo> brainpowerFunctionList= brainpowerMapper.getBottomBrainpowerFunctionList();
		if(brainpowerFunctionList==null){
			brainpowerFunctionList = new ArrayList<BrainpowerFunctionVo>();
		}
		restResponse.setData(brainpowerFunctionList);
		return restResponse;
	}

	@PostMapping("evaluateOperate")
	@ApiOperation(value = "评价是否已解决问题", notes = "评价是否已解决问题",httpMethod = "POST")
	public ObjectRestResponse evaluateOperate(@RequestBody @ApiParam OperateVo operateVo) {
		ObjectRestResponse restResponse = new ObjectRestResponse();
		if(operateVo!=null && StringUtils.isNotEmpty(operateVo.getMsgId()) && StringUtils.isNotEmpty(operateVo.getOperateType())){
			BizChatMessage bizChatMessage = bizChatMessageMapper.selectByPrimaryKey(operateVo.getMsgId());
			if(bizChatMessage!=null){
				String message = bizChatMessage.getMessage();
				BrainMessageOut brainMessageOut = Json.toBean(message, BrainMessageOut.class);
				if(brainMessageOut!=null && brainMessageOut.getIsSolve()==null){
					brainMessageOut.setIsSolve(operateVo.getOperateType());

					bizChatMessage.setMessage(Json.toJson(brainMessageOut));
					bizChatMessageMapper.updateByPrimaryKeySelective(bizChatMessage);
					if("1".equals(operateVo.getOperateType())){
						bizChatMessageMapper.addSolve(brainMessageOut.getId());
					}else if ("2".equals(operateVo.getOperateType())){
						bizChatMessageMapper.addUnSolve(brainMessageOut.getId());
					}
				}else{
					restResponse.setStatus(103);
					restResponse.setMessage("该消息已评价过");
				}
			}else{
				restResponse.setStatus(102);
				restResponse.setMessage("未找到评价的消息");
			}
		}else{
			restResponse.setStatus(101);
			restResponse.setMessage("参数为空");
		}
		return restResponse;
	}

	@PostMapping("changeBrainpowerQuestion")
	@ApiOperation(value = "子问题的换一批", notes = "子问题的换一批",httpMethod = "POST")
	public ObjectRestResponse<List<BrainpowerQuestionVo>> changeBrainpowerQuestion(@RequestBody @ApiParam ChangeBrainpowerInVo changeBrainpowerInVo) {
		ObjectRestResponse restResponse = new ObjectRestResponse();
		List<BrainpowerQuestionVo> brainpowerQuestionList = brainpowerMapper.changeBrainpowerQuestion(changeBrainpowerInVo);
		if(brainpowerQuestionList==null){
			brainpowerQuestionList = new ArrayList<BrainpowerQuestionVo>();
		}
		brainpowerQuestionList = ToolUtil.getSubStringByRadom(brainpowerQuestionList,5);
		restResponse.setData(brainpowerQuestionList);
		return restResponse;
	}


}