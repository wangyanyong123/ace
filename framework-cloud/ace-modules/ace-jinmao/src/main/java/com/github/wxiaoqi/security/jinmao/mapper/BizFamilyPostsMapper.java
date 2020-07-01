package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizFamilyPosts;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.out.GradeRuleVo;
import com.github.wxiaoqi.security.jinmao.vo.familyPosts.FamilyPostsInfo;
import com.github.wxiaoqi.security.jinmao.vo.familyPosts.FamilyPostsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 家里人帖子表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
public interface BizFamilyPostsMapper extends CommonMapper<BizFamilyPosts> {


    /**
     * 查询家里人帖子列表
     * @param projectId
     * @param showType
     * @param isTop
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<FamilyPostsVo> selectFamilyPostsList(@Param("projectId") String projectId, @Param("showType") String showType,
                                    @Param("isTop") String isTop, @Param("startTime") String startTime, @Param("endTime") String endTime,
                                    @Param("searchVal") String searchVal, @Param("page") Integer page, @Param("limit") Integer limit);
    List<FamilyPostsVo> selectNewFamilyPostsList(@Param("projectId") String projectId, @Param("showType") String showType,
                                              @Param("isTop") String isTop, @Param("startTime") String startTime, @Param("endTime") String endTime,
                                              @Param("searchVal") String searchVal, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询家里人帖子列表数量
     * @param projectId
     * @param showType
     * @param isTop
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
    int selectFamilyPostsCount(@Param("projectId") String projectId, @Param("showType") String showType,
                               @Param("isTop") String isTop, @Param("startTime") String startTime, @Param("endTime") String endTime,
                               @Param("searchVal") String searchVal);


    /**
     * 查询家里人帖子详情
     * @param id
     * @return
     */
    FamilyPostsInfo selectFamilyPostsInfo(String id);


    /**
     * 家里人帖子操作置顶,隐藏
     * @param status
     * @param id
     * @param userId
     * @return
     */
    int updateFamilyPostsStatusById(@Param("status") String status, @Param("id") String id, @Param("userId") String userId);


    /**
     * 查询用户的签到积分
     * @param userId
     * @return
     */
    String selectUserPointById(String userId);

    /**
     * 查询用户的个人积分值
     * @param userId
     * @return
     */
    String selectUserValueById(String userId);

    /**
     * 查询等级规则
     * @return
     */
    List<GradeRuleVo> selectGradeRuleList();





}
