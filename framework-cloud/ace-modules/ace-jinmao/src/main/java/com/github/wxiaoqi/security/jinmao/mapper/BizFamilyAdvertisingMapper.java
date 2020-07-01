package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizFamilyAdvertising;
import com.github.wxiaoqi.security.jinmao.vo.activity.out.ActivityVo;
import com.github.wxiaoqi.security.jinmao.vo.familyad.out.FamilyAdInfo;
import com.github.wxiaoqi.security.jinmao.vo.familyad.out.FamilyAdVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 家里人广告位
 * 
 * @author huangxl
 * @Date 2019-08-12 14:50:41
 */
public interface BizFamilyAdvertisingMapper extends CommonMapper<BizFamilyAdvertising> {


    /**
     * 查询家里人广告位列表
     * @param projectId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<FamilyAdVo> selectFamilyAdList(@Param("projectId") String projectId, @Param("searchVal") String searchVal,
                                     @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询家里人广告位列表数量
     * @param projectId
     * @param searchVal
     * @return
     */
    int selectFamilyAdCount(@Param("projectId") String projectId, @Param("searchVal") String searchVal);

    /**
     * 查询家里人广告位详情
     * @param id
     * @return
     */
    FamilyAdInfo selectFamilyAdInfo(String id);


    /**
     * 删除广告位
     * @param id
     * @param userId
     * @return
     */
    int delFamilyAdById(@Param("id") String id, @Param("userId") String userId);


    /**
     * 查询邻里活动列表
     * @param projectId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ActivityVo> selectActivityList(@Param("projectId") String projectId, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("searchVal") String searchVal,
                                        @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询邻里活动列表数量
     * @param projectId
     * @param searchVal
     * @return
     */
    int selectActivityCount(@Param("projectId") String projectId,  @Param("startTime") String startTime, @Param("endTime") String endTime,@Param("searchVal") String searchVal);
 }
