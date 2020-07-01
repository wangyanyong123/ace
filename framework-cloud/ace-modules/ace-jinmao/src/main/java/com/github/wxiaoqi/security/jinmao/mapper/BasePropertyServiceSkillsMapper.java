package com.github.wxiaoqi.security.jinmao.mapper;


import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BasePropertyServiceSkills;
import com.github.wxiaoqi.security.jinmao.vo.Service.OutParam.ResultSkillVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物业人员服务技能表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-21 13:55:50
 */
public interface BasePropertyServiceSkillsMapper extends CommonMapper<BasePropertyServiceSkills> {

    /**
     * 查询技能列表
     * @param searchVal
     * @param idList
     * @return
     */
    List<ResultSkillVo> selectSkillList(@Param("searchVal") String searchVal,@Param("idList") List<String> idList);


    /**
     * 根据物业人员id查询其技能
     * @param id
     * @return
     */
    List<ResultSkillVo>  selectSkillNameById(String id );


    /**
     * 根据用户id删除原有的技能
     * @param id
     * @return
     */
    int deleteServiceSkillBySId(String id);


    String selectProjectIdByUserId(String userId);

    //int delBuild(@Param("userId") String userId, @Param("builds") List<String> builds, @Param("muserId") String muserId, @Param("tenantId")String tenantId);

    int selectHouseBulids(@Param("userId") String userId,@Param("tenantId") String tenantId );

    int delHouseBulids(@Param("userId") String userId, @Param("muserId") String muserId, @Param("tenantId")String tenantId);
	
}
