package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.entity.BizCrmBuilding;
import com.github.wxiaoqi.security.app.mapper.BizCrmBuildingMapper;
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
 * 楼栋表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@Service
@Slf4j
public class BizCrmBuildingBiz extends BusinessBiz<BizCrmBuildingMapper,BizCrmBuilding> {
	@Autowired
	private BizCrmBuildingMapper crmBuildingMapper;

	public void updateBuilding(List<BizCrmBuilding> buildingList) {
		if(null != buildingList && buildingList.size() > 0){
			List<String> housingResourcesIds = buildingList.stream().map(bizCrmBuilding -> bizCrmBuilding.getHousingResourcesId()).distinct().collect(toList());
			List<String> needUpdateHousingResourcesIds = new ArrayList<>();
			List<BizCrmBuilding> updateBuildingList = new ArrayList<>();
			List<BizCrmBuilding> addBuildingList = new ArrayList<>();
			List<BizCrmBuilding> crmBuildingList = crmBuildingMapper.getByIds(housingResourcesIds);
			if(null != crmBuildingList && crmBuildingList.size() > 0){
				List<String> oldHousingResourcesIds = crmBuildingList.stream().map(bizCrmBuilding -> bizCrmBuilding.getHousingResourcesId()).distinct().collect(toList());
				needUpdateHousingResourcesIds = housingResourcesIds.stream().filter(item -> oldHousingResourcesIds.contains(item)).collect(toList());
			}

			if(null != needUpdateHousingResourcesIds && needUpdateHousingResourcesIds.size() > 0){
				Iterator<BizCrmBuilding> it = buildingList.iterator();
				while(it.hasNext()){
					BizCrmBuilding x = it.next();
					if(needUpdateHousingResourcesIds.contains(x.getHousingResourcesId())){
						updateBuildingList.add(x);
						it.remove();
					}else {
						addBuildingList.add(x);
					}
				}
			}else {
				addBuildingList = buildingList;
			}
			if(null != updateBuildingList && updateBuildingList.size() > 0){
				log.info("批量更新楼栋信息"+updateBuildingList.size()+"条");
				int num = 0;
				for (BizCrmBuilding updateBuilding:updateBuildingList) {
					updateBuilding.setModifyTime(new Date());
					if(crmBuildingMapper.updateByPrimaryKeySelective(updateBuilding)>0){
						num++;
					}
				}
				log.info("成功更新楼栋信息"+num+"条");
			}
			if(null != addBuildingList && addBuildingList.size() > 0){
				log.info("批量插入楼栋信息"+updateBuildingList.size()+"条");
				int num = 0;
				for (BizCrmBuilding addBuilding:addBuildingList) {
					addBuilding.setCreateTime(new Date());
					addBuilding.setStatus("1");
					if(crmBuildingMapper.insertSelective(addBuilding)>0){
						num++;
					}
				}
				log.info("成功插入楼栋信息"+num+"条");
			}
		}
	}
}