package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.app.entity.BizActivity;
import com.github.wxiaoqi.security.app.entity.BizActivityApply;
import com.github.wxiaoqi.security.app.vo.activity.out.ActivityInfo;
import com.github.wxiaoqi.security.app.vo.activity.out.ActivityVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
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
     * 查询小组活动贴列表
     * @param groupId
     * @param page
     * @param limit
     * @return
     */
    List<ActivityVo> selectActivityList(@Param("groupId") String groupId, @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询app邻里活动
     * @param projectId
     * @return
     */
    List<ActivityVo> selectHoodActivityList(String projectId, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询活动报名人头像
     * @param activityId
     * @return
     */
    List<ImgInfo> selectApplyPhoto(String activityId);


    List<ImgInfo> selectApplyPhotoByThree(String activityId);

    /**
     * 查询活动报名人数
     * @param activityId
     * @return
     */
    int selectActivityApplyNum(String activityId);

    /**
     * 查询活动详情
     * @param id
     * @return
     */
    ActivityInfo selectActivityInfo(@Param("id") String id, @Param("userId") String userId);

    /**
     * 查询用户是否加入该活动所发的小组
     * @param userId
     * @param activityId
     * @return
     */
    String selectIsGroupMember(@Param("userId") String userId,@Param("activityId") String activityId);

    /**
     * 查询当前项目下的活动列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<ActivityVo> selectActivityListByProject(@Param("day") String day,@Param("projectId") String projectId, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询用户是否已报名
     * @param userId
     * @param activityId
     * @return
     */
    int selectIsApplyByUser(@Param("userId") String userId,@Param("activityId") String activityId);


    /**
     * 支付宝、微信回调通知成功后 活动操作报名状态
     * @param userId
     * @param subId
     * @return
     */
    int updateActivityApplyStatus(@Param("userId") String userId, @Param("subId") String subId);

    /**
     * 活动退款
     * @param userId
     * @param id
     * @return
     */
    int updateActivityRefundStatus(@Param("userId") String userId, @Param("id") String id);

    /**
     * 退款审核
     * @param status
     * @param userId
     * @param subId
     * @return
     */
    int updateRefundAuditStatus(@Param("status") String status, @Param("userId") String userId, @Param("subId") String subId);

    String selectCompanyIdById(String id);


    BizActivityApply selectIsActivityBySubId(String subId);

    /**
     * 判断该用户是否签到
     * @param userId
     * @param id
     * @return
     */
    String selectIsSigntype(@Param("userId") String userId,@Param("id") String id);

    int updatesignTypeStatus(@Param("userId") String userId,@Param("applyId") String applyId);

    int selectIsSigntypeById(@Param("userId") String userId,@Param("id") String id);
}
