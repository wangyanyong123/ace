package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.app.entity.BizCrmBlock;
import com.github.wxiaoqi.security.app.mapper.BizCrmBlockMapper;
import com.github.wxiaoqi.security.app.mapper.BizCrmBuildingMapper;
import com.github.wxiaoqi.security.app.vo.city.out.*;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 地块表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@Service
@Slf4j
public class BizCrmBlockBiz extends BusinessBiz<BizCrmBlockMapper,BizCrmBlock> {
	@Autowired
	private BizCrmBlockMapper crmBlockMapper;

	@Autowired
	private BizCrmBuildingMapper crmBuildingMapper;

	public void updateBlock(List<BizCrmBlock> blockList) {
		if(null != blockList && blockList.size() > 0){
			List<String> blockIds = blockList.stream().map(bizCrmBlock -> bizCrmBlock.getBlockId()).distinct().collect(toList());
			List<String> needUpdateBlockIds = new ArrayList<>();
			List<BizCrmBlock> updateBlockList = new ArrayList<>();
			List<BizCrmBlock> addBlockList = new ArrayList<>();
			List<BizCrmBlock> crmBlockList = crmBlockMapper.getByIds(blockIds);
			if(null != crmBlockList && crmBlockList.size() > 0){
				List<String> oldBlockIds = crmBlockList.stream().map(bizCrmBlock -> bizCrmBlock.getBlockId()).distinct().collect(toList());
				needUpdateBlockIds = blockIds.stream().filter(item -> oldBlockIds.contains(item)).collect(toList());
			}

			if(null != needUpdateBlockIds && needUpdateBlockIds.size() > 0){
				Iterator<BizCrmBlock> it = blockList.iterator();
				while(it.hasNext()){
					BizCrmBlock x = it.next();
					if(needUpdateBlockIds.contains(x.getBlockId())){
						updateBlockList.add(x);
						it.remove();
					}else {
						addBlockList.add(x);
					}
				}
			}else {
				addBlockList = blockList;
			}
			if(null != updateBlockList && updateBlockList.size() > 0){
				log.info("批量更新地块信息"+updateBlockList.size()+"条");
				int num = 0;
				for (BizCrmBlock updateBlock:updateBlockList) {
					updateBlock.setModifyTime(new Date());
					if(crmBlockMapper.updateByPrimaryKeySelective(updateBlock)>0){
						num++;
					}
				}
				log.info("成功更新地块信息"+num+"条");
			}
			if(null != addBlockList && addBlockList.size() > 0){
				log.info("批量插入地块信息"+updateBlockList.size()+"条");
				int num = 0;
				for (BizCrmBlock addBlock:addBlockList) {
					addBlock.setCreateTime(new Date());
					addBlock.setStatus("1");
					if(crmBlockMapper.insertSelective(addBlock)>0){
						num++;
					}
				}
				log.info("成功插入地块信息"+num+"条");
			}
		}
	}

	public ObjectRestResponse<List<BlockInfoVo>> getBlockAndBuildInfoListByProjectId(String projectId, int type) {
		ObjectRestResponse<List<BlockInfoVo>> restResponse = new ObjectRestResponse<>();
		if(StringUtils.isEmpty(projectId)){
			restResponse.setStatus(501);
			restResponse.setMessage("projectId不能为空");
			return restResponse;
		}
		List<BlockInfoVo> results = new ArrayList<>();

		if(results == null || results.size() < 1){
			List<BlockInfoVo> blockInfoVos = crmBlockMapper.getBlockInfoListByProjectId(projectId,type);
			for (BlockInfoVo blockInfoVo:blockInfoVos) {
				List<BuildInfoVo> buildInfoVos = crmBuildingMapper.getBuilInfoListByBlockId(blockInfoVo.getBlockId());
				blockInfoVo.setBuildInfoVos(buildInfoVos);
			}
			restResponse.setData(blockInfoVos);
		}
		return restResponse;
	}
}