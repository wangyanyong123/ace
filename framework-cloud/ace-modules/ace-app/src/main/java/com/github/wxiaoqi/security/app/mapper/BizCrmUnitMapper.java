package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizCrmUnit;
import com.github.wxiaoqi.security.app.vo.city.out.UnitInfoVo;
import com.github.wxiaoqi.security.app.vo.face.Unit;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 单元表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:11
 */
public interface BizCrmUnitMapper extends CommonMapper<BizCrmUnit> {

	List<BizCrmUnit> getByIds(@Param("ids")List<String> unitIds);

	List<UnitInfoVo> getUnitInfoListByBuildId(@Param("buildId") String buildId,@Param("type") int type);

	List<com.github.wxiaoqi.security.api.vo.face.UnitInfoVo> getUnitInfoListByProjectId(@Param("projectId") String projectId);

	List<UnitInfoVo> getUnitInfo(@Param("projectId") String projectId);

	List<Unit> getUnitByUser(@Param("userId") String userId,@Param("projectId")String projectId);

	List<Map<String,String>> getUnitInfoList();
}
