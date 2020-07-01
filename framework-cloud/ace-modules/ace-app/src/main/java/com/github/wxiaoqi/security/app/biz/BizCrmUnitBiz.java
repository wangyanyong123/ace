package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.entity.BizCrmUnit;
import com.github.wxiaoqi.security.app.mapper.BizCrmUnitMapper;
import com.github.wxiaoqi.security.app.vo.city.out.UnitInfoVo;
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
 * 单元表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:11
 */
@Service
@Slf4j
public class BizCrmUnitBiz extends BusinessBiz<BizCrmUnitMapper,BizCrmUnit> {
	@Autowired
	private BizCrmUnitMapper crmUnitMapper;

	public void updateUnit(List<BizCrmUnit> unitList) {
		if(null != unitList && unitList.size() > 0){
			List<String> unitIds = unitList.stream().map(bizCrmUnit -> bizCrmUnit.getUnitId()).distinct().collect(toList());
			List<String> needUpdateUnitIds = new ArrayList<>();
			List<BizCrmUnit> updateUnitList = new ArrayList<>();
			List<BizCrmUnit> addUnitList = new ArrayList<>();
			List<BizCrmUnit> crmUnitList = crmUnitMapper.getByIds(unitIds);
			if(null != crmUnitList && crmUnitList.size() > 0){
				List<String> oldUnitIds = crmUnitList.stream().map(bizCrmUnit -> bizCrmUnit.getUnitId()).distinct().collect(toList());
				needUpdateUnitIds = unitIds.stream().filter(item -> oldUnitIds.contains(item)).collect(toList());
			}

			if(null != needUpdateUnitIds && needUpdateUnitIds.size() > 0){
				Iterator<BizCrmUnit> it = unitList.iterator();
				while(it.hasNext()){
					BizCrmUnit x = it.next();
					if(needUpdateUnitIds.contains(x.getUnitId())){
						updateUnitList.add(x);
						it.remove();
					}else {
						addUnitList.add(x);
					}
				}
			}else {
				addUnitList = unitList;
			}
			if(null != updateUnitList && updateUnitList.size() > 0){
				log.info("批量更新单元信息"+updateUnitList.size()+"条");
				int num = 0;
				for (BizCrmUnit updateUnit:updateUnitList) {
					updateUnit.setModifyTime(new Date());
					if(crmUnitMapper.updateByPrimaryKeySelective(updateUnit)>0){
						num++;
					}
				}
				log.info("成功更新单元信息"+num+"条");
			}
			if(null != addUnitList && addUnitList.size() > 0){
				log.info("批量插入单元信息"+updateUnitList.size()+"条");
				int num = 0;
				for (BizCrmUnit addUnit:addUnitList) {
					addUnit.setCreateTime(new Date());
					addUnit.setStatus("1");
					if(crmUnitMapper.insertSelective(addUnit)>0){
						num++;
					}
				}
				log.info("成功插入单元信息"+num+"条");
			}
		}

	}

	public ObjectRestResponse<List<UnitInfoVo>> getUnitInfoListByBuildId(String buildId, int type) {
		ObjectRestResponse<List<UnitInfoVo>> restResponse = new ObjectRestResponse<>();
		if(StringUtils.isEmpty(buildId)){
			restResponse.setStatus(501);
			restResponse.setMessage("buildId不能为空");
			return restResponse;
		}
		List<UnitInfoVo> unitInfoVos = crmUnitMapper.getUnitInfoListByBuildId(buildId,type);
		restResponse.setData(unitInfoVos);
		return restResponse;
	}
}