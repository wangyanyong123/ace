package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizReservationPerson;
import com.github.wxiaoqi.security.app.vo.reservation.out.*;
import com.github.wxiaoqi.security.app.vo.shopping.out.CompanyInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 预约服务人员表
 * 
 * @author huangxl
 * @Date 2019-03-12 09:26:32
 */
public interface BizReservationPersonMapper extends CommonMapper<BizReservationPerson> {

    /**
     * 查询分类下的服务列表
     * @param projectId
     * @param classifyId
     * @param page
     * @param limit
     * @return
     */
    List<ReservationVo> selectReservationByClassifyId(@Param("projectId") String projectId, @Param("classifyId") String classifyId,
                                                      @Param("page") Integer page, @Param("limit") Integer limit,
                                                      @Param("cityCodeList") List<String> cityCodeList);

    /**
     * 查询服务详情
     * @param id
     * @return
     */
    ReservationInfo selectReservationInfoById(String id);

    /**
     * 查询服务详情(新接口)
     * @param id
     * @return
     */
    ReservationInfo selectNewReservationInfoById(@Param("id") String id,@Param("userId") String userId);

    /**
     * 查询服务详情(新接口) by 分享
     * @param id
     * @return
     */
    ReservationInfo selectShareNewReservationInfoById(@Param("id") String id);

    /**
     * 根据服务id查询所属公司信息
     * @param id
     * @return
     */
    CompanyInfo selectCompanyInfoByRId(String id);

    /**
     * 查询用户的预约列表
     * @param userId
     * @param dealStatus
     * @param page
     * @param limit
     * @return
     */
    List<MyReservationVo> selectUserReservation(@Param("userId") String userId,@Param("dealStatus") String dealStatus,
                                                @Param("page") Integer page, @Param("limit") Integer limit);

    int selectWoStatusById(String woId);

    /**
     * 查询用户预约详情
     * @param id
     * @return
     */
    MyReservationInfo selectUserReservationInfo(String id);


    String selectLogoById(String reservationId);

    int selectSalesById(String reservationId);

    String selectTenantIdById(String reservationId);

    String seelctClassifyNameById(String classifyId);


    /**
     * 查询预约工单详情
     * @param woId
     * @return
     */
    ReservatPersonInfo selectReservationPersonInfo(String woId);

    /**
     * 查询商户负责人
     * @param tenantId
     * @return
     */
    TenameInfo seelctTenameInfoById(String tenantId);

    /**
     * 查询服务下的服务范围
     * @param id
     * @return
     */
    List<String> selectProjectIdById(String id);

    /**
     * 判断登录的用户是否是系统平台
     * @param tenantId
     * @return
     */
    String selectSystemByTenantId(String tenantId);


    List<ReservationVo> getReservationIndex(String projectId);

    List<ReservationVo> getReservationMore(@Param("projectId") String projectId,@Param("page") Integer page,@Param("limit") Integer limit);


    /**
     * 客户端APP查询我的预约列表
     * @param map
     * @return
     */
    List<SubReservationListVo> getReservationListForApp(Map<?, ?> map);


    int getUserBuyNumById(@Param("id") String id,@Param("userId") String userId);
}
