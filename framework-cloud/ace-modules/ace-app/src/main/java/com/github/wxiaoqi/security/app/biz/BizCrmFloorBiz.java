package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.entity.BizCrmFloor;
import com.github.wxiaoqi.security.app.mapper.BizCrmFloorMapper;
import com.github.wxiaoqi.security.app.vo.city.out.FloorInfoVo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 楼层表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@Service
@Slf4j
public class BizCrmFloorBiz extends BusinessBiz<BizCrmFloorMapper,BizCrmFloor> {
	@Autowired
	private BizCrmFloorMapper crmFloorMapper;

	public void updateFloor(List<BizCrmFloor> floorList) {
		if(null != floorList && floorList.size() > 0){
			List<String> floorIds = floorList.stream().map(bizCrmFloor -> bizCrmFloor.getFloorId()).distinct().collect(toList());
			List<String> needUpdateFloorIds = new ArrayList<>();
			List<BizCrmFloor> updateFloorList = new ArrayList<>();
			List<BizCrmFloor> addFloorList = new ArrayList<>();
			List<BizCrmFloor> crmFloorList = crmFloorMapper.getByIds(floorIds);
			if(null != crmFloorList && crmFloorList.size() > 0){
				List<String> oldFloorIds = crmFloorList.stream().map(bizCrmFloor -> bizCrmFloor.getFloorId()).distinct().collect(toList());
				needUpdateFloorIds = floorIds.stream().filter(item -> oldFloorIds.contains(item)).collect(toList());
			}

			if(null != needUpdateFloorIds && needUpdateFloorIds.size() > 0){
				Iterator<BizCrmFloor> it = floorList.iterator();
				while(it.hasNext()){
					BizCrmFloor x = it.next();
					if(needUpdateFloorIds.contains(x.getFloorId())){
						updateFloorList.add(x);
						it.remove();
					}else {
						addFloorList.add(x);
					}
				}
			}else {
				addFloorList = floorList;
			}
			if(null != updateFloorList && updateFloorList.size() > 0){
				log.info("批量更新楼层信息"+updateFloorList.size()+"条");
				int num = 0;
				for (BizCrmFloor updateFloor:updateFloorList) {
					updateFloor.setModifyTime(new Date());
					if(crmFloorMapper.updateByPrimaryKeySelective(updateFloor)>0){
						num++;
					}
				}
				log.info("成功更新楼层信息"+num+"条");
			}
			if(null != addFloorList && addFloorList.size() > 0){
				log.info("批量插入楼层信息"+updateFloorList.size()+"条");
				int num = 0;
				for (BizCrmFloor addFloor:addFloorList) {
					addFloor.setCreateTime(new Date());
					addFloor.setStatus("1");
					if(crmFloorMapper.insertSelective(addFloor)>0){
						num++;
					}
				}
				log.info("成功插入楼层信息"+num+"条");
			}
		}
	}

	public ObjectRestResponse<List<FloorInfoVo>> getFloorInfoListByUnitId(String unitId, int type) {
		ObjectRestResponse<List<FloorInfoVo>> restResponse = new ObjectRestResponse<>();
		if(StringUtils.isEmpty(unitId)){
			restResponse.setStatus(501);
			restResponse.setMessage("unitId不能为空");
			return restResponse;
		}
		List<FloorInfoVo> floorInfoVos = crmFloorMapper.getFloorInfoListByUnitId(unitId,type);
		restResponse.setData(floorInfoVos);
		return restResponse;
	}
}