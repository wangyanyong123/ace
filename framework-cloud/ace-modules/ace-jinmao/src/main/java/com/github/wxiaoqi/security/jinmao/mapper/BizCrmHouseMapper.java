package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizCrmHouse;
import com.github.wxiaoqi.security.jinmao.vo.house.HouseTreeList;
import com.github.wxiaoqi.security.jinmao.vo.house.UserRegistryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 房屋表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
public interface BizCrmHouseMapper extends CommonMapper<BizCrmHouse> {


	List<HouseTreeList> getHouseInfoTree(@Param("projectId") String projectId, @Param("type") int type);


    List<UserRegistryVo> getRegistryByProject(String projectId);

    List<Map<String, String>> getCrmHouseByUserId(@Param("userId") String userId);

    int getProjectHouseCount(@Param("projectId") String projectId);
}
