package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizFamilyAdvertisingProject;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import com.github.wxiaoqi.security.jinmao.vo.reservat.out.ReservationList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 家里人广告项目关联表
 * 
 * @author huangxl
 * @Date 2019-08-12 14:50:41
 */
public interface BizFamilyAdvertisingProjectMapper extends CommonMapper<BizFamilyAdvertisingProject> {


    /**
     * 查询所属项目
     * @param adId
     * @return
     */
    List<String> selectProjectNameByAdId(String adId);

    /**
     * 查询所属项目列表
     * @param adId
     * @return
     */
    List<ResultProjectVo> selectProjectInfoById(String adId);

    /**
     * 查询所属项目列表
     * @param adId
     * @return
     */
    int deleteFamilyAdById(String adId);

    /**
     * 查询项目列表
     * @param projectId
     * @return
     */
    List<String> selectProjectNameList(@Param("projectId") List<String> projectId);


    List<ReservationList> getReservationForAD(@Param("reservaStatus") String reservaStatus, @Param("projectIds") List<String> projectIds, @Param("searchVal") String searchVal,
                                              @Param("page") Integer page, @Param("limit") Integer limit);


}
