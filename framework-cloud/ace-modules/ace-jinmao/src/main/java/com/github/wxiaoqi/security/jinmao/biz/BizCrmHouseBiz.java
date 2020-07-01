package com.github.wxiaoqi.security.jinmao.biz;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.github.wxiaoqi.security.jinmao.entity.BizCrmHouse;
import com.github.wxiaoqi.security.jinmao.entity.BizCrmProject;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BizCrmHouseMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizCrmProjectMapper;
import com.github.wxiaoqi.security.jinmao.vo.house.HouseInfoTree;
import com.github.wxiaoqi.security.jinmao.vo.house.HouseTreeList;
import com.github.wxiaoqi.security.jinmao.vo.house.UserRegistryVo;
import com.github.wxiaoqi.security.jinmao.vo.wo.woaging.WoAgingCount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 房屋表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@Service
@Slf4j
public class BizCrmHouseBiz extends BusinessBiz<BizCrmHouseMapper, BizCrmHouse> {
	@Autowired
	private BizCrmHouseMapper crmHouseMapper;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private OssExcelFeign excelFeign;
	@Autowired
	private BizCrmProjectMapper projectMapper;

	public TableResultResponse<HouseInfoTree> getHouseInfoTree(String projectId, int type) {
		TableResultResponse<HouseInfoTree> restResponse = new TableResultResponse<>();
		if(StringUtils.isEmpty(projectId)){
			restResponse.setStatus(501);
			restResponse.setMessage("projectId不能为空");
			return restResponse;
		}
		List<HouseInfoTree> houseInfoTrees = new ArrayList<>();
		houseInfoTrees = JSON.parseArray(redisTemplate.opsForValue().get("GetHouseTreeWeb:" + projectId + ":" + type), HouseInfoTree.class);
		if(houseInfoTrees == null || houseInfoTrees.size() < 1){
			List<HouseTreeList> houseTrees = crmHouseMapper.getHouseInfoTree(projectId,type);
			if (houseTrees != null) {
				List<HouseInfoTree> trees = new ArrayList<>();
				houseTrees.forEach(houseTree -> {
					trees.add(new HouseInfoTree(houseTree.getId(), houseTree.getParentId(), houseTree.getName(),houseTree.getNameStr(), houseTree.getCode(),houseTree.getType()));
				});
				long time = System.currentTimeMillis();
				System.out.println("start====>"+time);
	//			houseInfoTrees = TreeUtil.bulid(trees, "1", null);
				houseInfoTrees = TreeUtil.buildByRecursive(trees, projectId);
				System.out.println("end====>"+(System.currentTimeMillis()-time));
			}
			redisTemplate.opsForValue().set("GetHouseTreeWeb:"+projectId + ":" + type,JSON.toJSONString(houseInfoTrees),30, TimeUnit.DAYS);
		}
		return new TableResultResponse<HouseInfoTree>(houseInfoTrees.size(),houseInfoTrees);
	}


    public ObjectRestResponse getRegistryByProject(String projectId) {
		ObjectRestResponse response = new ObjectRestResponse();
		BizCrmProject bizCrmProject = projectMapper.selectByPrimaryKey(projectId);
		if (bizCrmProject == null) {
			response.setMessage("错误的项目");
			response.setStatus(101);
			return response;
		}
		List<UserRegistryVo> registryList =  crmHouseMapper.getRegistryByProject(projectId);
		List<Map<String, Object>> dataList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		if (registryList != null && registryList.size() > 0) {
			for (int i = 0; i < registryList.size(); i++) {
				UserRegistryVo temp = mapper.convertValue(registryList.get(i), UserRegistryVo.class);
				Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);
				dataList.add(dataMap);
			}
		}
		String[] titles = {"项目","地块","楼栋","单元","楼层","房间","是否注册"};
		String[] keys = {"projectName","blockName","buildingName","unitName","floorName","houseName","isRegistry"};
		String fileName = "项目注册状态统计.xlsx";

		ExcelInfoVo excelInfoVo = new ExcelInfoVo();
		excelInfoVo.setTitles(titles);
		excelInfoVo.setKeys(keys);
		excelInfoVo.setDataList(dataList);
		excelInfoVo.setFileName(fileName);
		response = excelFeign.uploadExcel(excelInfoVo);
		return response;
    }

	public Integer getProjectHouseCount(String projectId) {
		return this.mapper.getProjectHouseCount(projectId);
	}
}