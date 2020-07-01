package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BasePropertyServiceArea;
import com.github.wxiaoqi.security.jinmao.vo.Service.OutParam.ResultServiceAreaInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物业人员服务范围表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-21 13:55:50
 */
public interface BasePropertyServiceAreaMapper extends CommonMapper<BasePropertyServiceArea> {

    /**
     * 根据用户id查询物业人员服务范围
     * @param id
     * @return
     */
    List<ResultServiceAreaInfoVo> selectServiceAreaInfo(@Param("id") String id,@Param("tenantId")String tenantId);

    /**
     * 查询物业服务范围
     * @return
     */
    List<ResultServiceAreaInfoVo> selectServiceArea(String projectId);

    /**
     * 根据用户id删除原有的服务范围
     * @param id
     * @return
     */
    int deleteServiceAreaBySId(@Param("id") String id,@Param("projectId")String projectId);
	
}
