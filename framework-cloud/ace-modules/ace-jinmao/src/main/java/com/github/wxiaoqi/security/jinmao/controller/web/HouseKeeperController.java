package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BaseAppHousekeeperAreaBiz;
import com.github.wxiaoqi.security.jinmao.biz.BaseAppServerUserBiz;
import com.github.wxiaoqi.security.jinmao.vo.houseKeeper.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:14 2018/12/7
 * @Modified By:
 */
@RestController
@RequestMapping("web/houseKeeper")
@CheckClientToken
@CheckUserToken
@Api(tags="管家管理")
public class HouseKeeperController {
		@Autowired
		private BaseAppServerUserBiz baseAppServerUserBiz;

		@Autowired
		private BaseAppHousekeeperAreaBiz housekeeperAreaBiz;

		@RequestMapping(value = "/addHouseKeeper",method = RequestMethod.POST)
		@ApiOperation(value = "添加管家", notes = "添加管家",httpMethod = "POST")
		public ObjectRestResponse addHouseKeeper(@RequestBody @ApiParam HouseKeeperInVo houseKeeperInVo){
			return baseAppServerUserBiz.addHouseKeeper(houseKeeperInVo);
		}

		@RequestMapping(value = "/getHouseKeeperInfoByPhone/{mobilePhone}", method = RequestMethod.GET)
		@ApiOperation(value = "通过手机号码查询管家信息", notes = "通过手机号码查询管家信息",httpMethod = "GET")
		public ObjectRestResponse<HouseKeeperVo> getHouseKeeperInfoByPhone(@PathVariable String mobilePhone){
			return baseAppServerUserBiz.getHouseKeeperInfoByPhone(mobilePhone);
		}

		@RequestMapping(value = "/getHouseKeeperList",method = RequestMethod.GET)
		@ApiOperation(value = "查询管家管理列表", notes = "查询管家管理列表",httpMethod = "GET")
		@ApiImplicitParams({
				@ApiImplicitParam(name="searchVal",value="根据姓名,手机号码模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
				@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
				@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
		})
		public TableResultResponse<HouseKeeper> getHouseKeeperList(String searchVal, Integer page, Integer limit){
			List<HouseKeeper> houseKeeperList = housekeeperAreaBiz.getHouseKeeperList(searchVal, page, limit);
			int total = housekeeperAreaBiz.getHouseKeeperCount(searchVal);
			return new TableResultResponse<HouseKeeper>(total, houseKeeperList);
		}

		@RequestMapping(value = "/getHouseKeeperInfo/{id}", method = RequestMethod.GET)
		@ApiOperation(value = "查询管家详情", notes = "查询管家详情",httpMethod = "GET")
		public ObjectRestResponse<HouseKeeperInfoVo> getHouseKeeperInfo(@PathVariable String id){
			return baseAppServerUserBiz.getHouseKeeperInfo(id);
		}

		@RequestMapping(value = "/delHouseKeeper/{id}",method = RequestMethod.GET)
		@ApiOperation(value = "删除管家", notes = "删除管家",httpMethod = "GET")
		public ObjectRestResponse delHouseKeeper(@PathVariable String id){
			return housekeeperAreaBiz.delHouseKeeper(id);
		}

		@RequestMapping(value = "/updateHouseKeeper",method = RequestMethod.POST)
		@ApiOperation(value = "编辑管家", notes = "编辑管家",httpMethod = "POST")
		public ObjectRestResponse updateHouseKeeper(@RequestBody @ApiParam HouseKeeperInfoVo houseKeeperInfoVo){
			return baseAppServerUserBiz.updateHouseKeeper(houseKeeperInfoVo);
		}

		@RequestMapping(value = "/delBuild",method = RequestMethod.GET)
		@ApiOperation(value = "删除楼栋", notes = "删除楼栋",httpMethod = "GET")
		@ApiImplicitParams({
				@ApiImplicitParam(name="userId",value="用户id",dataType="Integer",required = true, paramType = "query",example="4"),
				@ApiImplicitParam(name="buildIds",value="楼栋id，多个以逗号隔开",dataType="String", required = true,paramType = "query",example="1sdsgsfdghsfdgsd")
		})
		public ObjectRestResponse delBuild(String userId,String buildIds){
			return housekeeperAreaBiz.delBuild(userId, buildIds);
		}

		@RequestMapping(value = "/addBuild",method = RequestMethod.GET)
		@ApiOperation(value = "添加楼栋", notes = "添加楼栋",httpMethod = "GET")
		@ApiImplicitParams({
				@ApiImplicitParam(name="userId",value="用户id",dataType="Integer",required = true, paramType = "query",example="4"),
				@ApiImplicitParam(name="buildIds",value="楼栋id，多个以逗号隔开",dataType="String", required = true,paramType = "query",example="1sdsgsfdghsfdgsd")
		})
		public ObjectRestResponse addBuild(String userId,String buildIds){
			return housekeeperAreaBiz.addBuild(userId, buildIds);
		}

		@RequestMapping(value = "/getAllBuilds",method = RequestMethod.GET)
		@ApiOperation(value = "获取所有的楼栋", notes = "获取所有的楼栋",httpMethod = "GET")
		public ObjectRestResponse<List<BuildInfoVo>> getAllBuilds(){
			ObjectRestResponse<List<BuildInfoVo>> response = new ObjectRestResponse<>();
			response.setData(housekeeperAreaBiz.getAllBuilds());
			return response;
		}

		@RequestMapping(value = "/getBuildsByUserId",method = RequestMethod.GET)
		@ApiOperation(value = "获取用户负责的楼栋", notes = "获取用户负责的楼栋",httpMethod = "GET")
		@ApiImplicitParam(name="userId",value="用户id",dataType="String",required = true,paramType = "query",example="1sdsgsfdghsfdgsd")
		public ObjectRestResponse<List<BuildInfoVo>> getBuildsByUserId(String userId,Integer page, Integer limit){
			ObjectRestResponse<List<BuildInfoVo>> response = new ObjectRestResponse<>();
			if(StringUtils.isEmpty(userId)){
				response.setStatus(501);
				response.setMessage("userId不能为空！");
				return response;
			}
			response.setData(housekeeperAreaBiz.getBuildsByUserId(userId));
			return response;
		}

		@RequestMapping(value = "/getAllIsChooseBuilds",method = RequestMethod.GET)
		@ApiOperation(value = "获取所有被选楼栋id", notes = "获取所有被选楼栋id",httpMethod = "GET")
		public ObjectRestResponse<List<String>> getAllIsChooseBuilds(){
			ObjectRestResponse<List<String>> response = new ObjectRestResponse<>();
			response.setData(housekeeperAreaBiz.getAllIsChooseBuilds());
			return response;
		}
}
