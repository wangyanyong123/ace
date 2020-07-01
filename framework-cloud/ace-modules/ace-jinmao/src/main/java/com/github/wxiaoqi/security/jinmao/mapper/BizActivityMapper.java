package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizActivity;
import com.github.wxiaoqi.security.jinmao.vo.activity.out.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小组活动表
 * 
 * @author zxl
 * @Date 2018-12-21 17:43:42
 */
public interface BizActivityMapper extends CommonMapper<BizActivity> {

    /**
     * 查询各小组活动列表
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ActivityVo> selectActivityList(@Param("type") String type, @Param("tenantId") String tenantId,
            @Param("enableStatus") String enableStatus, @Param("searchVal") String searchVal,
                                        @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询活动数量
     * @param enableStatus
     * @param searchVal
     * @return
     */
    int selectActivityCount(@Param("type") String type, @Param("tenantId") String tenantId,
            @Param("enableStatus") String enableStatus, @Param("searchVal") String searchVal);

    /**
     * 查询该租户下所属项目
     * @param tenantId
     * @return
     */
    List<ProjectVo> selectProjectByTenantId(@Param("type") String type, @Param("tenantId") String tenantId);


    /**
     * 查询项目下所属小组
     * @return
     */
    List<GroupVo> selectGroupByProjectId(String projectId);

    /**
     * 查询小组活动详情
     * @param id
     * @return
     */
    ActivityInfo selectActivityInfo(String id);

    /**
     * 根据活动id查询项目名称
     * @param id
     * @return
     */
    List<ProjectVo> selectProjectById(String id);

    /**
     * 根据活动id查询小组名称
     * @param id
     * @return
     */
    List<GroupVo> selectGroupById(String id);

    /**
     * 活动操作(0-删除,2-发布,3-撤回)
     * @param userId
     * @param status
     * @return
     */
    int updateActivityStatus(@Param("userId") String userId,@Param("status") String status,@Param("id") String id);


    /**
     * 查询用户反馈列表
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<FeedbackVo> selectFeedbackList(@Param("type") String type,@Param("projectId") String projectId,
            @Param("startTime") String startTime, @Param("endTime") String endTime,
                                        @Param("searchVal") String searchVal ,@Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询用户反馈数量
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
    int selectFeedbackCount(@Param("type") String type,@Param("projectId") String projectId,@Param("startTime") String startTime, @Param("endTime") String endTime,
                            @Param("searchVal") String searchVal );

    String selectProjectIdByTenantId(String tenantId);

    String selectUserTypeByUserId(String userId);


    /**
     * 查询活动下的报名列表
     * @param id
     * @param page
     * @param limit
     * @return
     */
    List<UserApplyVo> selectUserApplyList(@Param("id") String id ,@Param("signType") String signType,@Param("page") Integer page, @Param("limit") Integer limit);

    int selectUserApplyCount(@Param("id") String id,@Param("signType") String signType);

    /**
     * 导出报名人列表
     * @param id
     * @return
     */
    List<UserApplyVo> selectExportUserApplyList(String id);


    List<QRVo> selectAllQRList(String id);
}
