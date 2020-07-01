package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizCrmBuilding;
import com.github.wxiaoqi.security.app.vo.city.out.BuildInfoVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 楼栋表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
public interface BizCrmBuildingMapper extends CommonMapper<BizCrmBuilding> {

	List<BizCrmBuilding> getByIds(@Param("ids")List<String> housingResourcesIds);

	List<BuildInfoVo> getBuilInfoListByProjectId(@Param("projectId")String projectId,@Param("type") int type);

	List<BuildInfoVo> getBuilInfoListByBlockId(@Param("blockId")String blockId);
}
