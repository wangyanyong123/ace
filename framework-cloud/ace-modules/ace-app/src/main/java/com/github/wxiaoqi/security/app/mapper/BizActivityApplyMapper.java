package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizActivityApply;
import com.github.wxiaoqi.security.app.vo.activity.out.ActivityVo;
import com.github.wxiaoqi.security.app.vo.activity.out.ApplyInfo;
import com.github.wxiaoqi.security.app.vo.activity.out.ApplyVo;
import com.github.wxiaoqi.security.app.vo.activity.out.UserInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小组活动报名表
 * 
 * @author zxl
 * @Date 2018-12-21 17:43:42
 */
public interface BizActivityApplyMapper extends CommonMapper<BizActivityApply> {

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    UserInfo selectUserInfo(String userId);

    /**
     * 查询该活动是否免费
     * @param id
     * @return
     */
    String selectIsFreeActivity(String id);

    /**
     * 查询该活动是否可取消
     * @param id
     * @return
     */
    String selectisCancelActivity(String id);

    /**
     * 查询用户活动报名状态
     * @param userId
     * @param id
     * @return
     */
    ApplyInfo selectActivityApplyStatus(@Param("userId") String userId, @Param("id") String id);

    /**
     * 用户取消报名操作
     * @param payStatus
     * @param userId
     * @param id
     * @return
     */
    int updateActivityApplyById(@Param("payStatus") String payStatus,@Param("userId") String userId, @Param("id") String id);

    /**
     * 查询活动报名列表
     * @param id
     * @return
     */
    List<ApplyVo> selectActivityApplyList(@Param("id") String id, @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询我的活动
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    List<ActivityVo> selectActivityListByUser(@Param("userId") String userId, @Param("page") Integer page, @Param("limit") Integer limit);
}
