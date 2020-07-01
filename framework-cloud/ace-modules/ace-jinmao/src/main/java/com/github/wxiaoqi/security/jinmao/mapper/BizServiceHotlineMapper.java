package com.github.wxiaoqi.security.jinmao.mapper;


import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizServiceHotline;
import com.github.wxiaoqi.security.jinmao.vo.Hotline.OutParam.ResultAppHotlineVo;
import com.github.wxiaoqi.security.jinmao.vo.Hotline.OutParam.ResultHotlineInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Hotline.OutParam.ResultHotlineVo;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 服务热线
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-26 13:57:08
 */
public interface BizServiceHotlineMapper extends CommonMapper<BizServiceHotline> {

    /**
     * 查询服务热线列表
     * @param projectId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ResultHotlineVo> selectHotlineList(@Param("type") String type, @Param("tenantId") String tenantId,
                                            @Param("projectId") String projectId, @Param("searchVal") String searchVal,
                                            @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 删除服务热线
     * @param id
     * @param userId
     * @return
     */
    int delHotlineInfo(@Param("id") String id, @Param("userId") String userId);


    /**
     * 查询最后一条记录的排序值
     * @return
     */
    String selectLastSortByTime();


    /**
     * 根据热线id查询服务热线详情
     * @param id
     * @return
     */
    ResultHotlineInfoVo selectHotlineInfo(String id);

    List<ResultHotlineInfoVo> selectNameByProjectId(String projectId);

    String selectDataByProjectId(@Param("tenantId")String tenantId , @Param("projectId")String projectId);


    /**
     * 根据项目id查询服务热线--App
     * @param projectId
     * @return
     */
    List<ResultAppHotlineVo> selectAppHotlineList(String projectId);

    int delHotline(String projectId);

    /**
     * 根据租户id查询项目
     * @param id
     * @return
     */
    List<ProjectListVo> selectProjectById(String id);

    String selectIdByTitle(String title);
	
}
