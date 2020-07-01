package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.api.vo.order.out.SubListForWebVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizReservation;
import com.github.wxiaoqi.security.jinmao.entity.BizTransactionLog;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultClassifyVo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import com.github.wxiaoqi.security.jinmao.vo.reservat.out.ReservatPersonVo;
import com.github.wxiaoqi.security.jinmao.vo.reservat.out.ReservationInfo;
import com.github.wxiaoqi.security.jinmao.vo.reservat.out.ReservationList;
import com.github.wxiaoqi.security.jinmao.vo.reservat.out.ResrevationAuditVo;
import com.github.wxiaoqi.security.jinmao.vo.statement.out.TenameInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 预约服务表
 * 
 * @author huangxl
 * @Date 2019-03-12 09:26:32
 */
public interface BizReservationMapper extends CommonMapper<BizReservation> {

    /**
     * 查询服务列表
     * @param type
     * @param tenantId
     * @param reservaStatus
     * @param classifyId
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<ReservationList> selectReservationList(@Param("type") String type, @Param("tenantId") String tenantId, @Param("reservaStatus") String reservaStatus
              ,@Param("classifyId") String classifyId,@Param("projectId") String projectId,@Param("searchVal") String searchVal,
                                                @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询服务列表数量
     * @param type
     * @param tenantId
     * @param reservaStatus
     * @param classifyId
     * @param projectId
     * @return
     */
    int selectReservationCount(@Param("type") String type, @Param("tenantId") String tenantId, @Param("reservaStatus") String reservaStatus
            ,@Param("classifyId") String classifyId,@Param("projectId") String projectId,@Param("searchVal") String searchVal);


    /**
     * 查询预约服务人员列表
     * @param tenantId
     * @param startTime
     * @param endTime
     * @param projectId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ReservatPersonVo> selectReservationPersonList(@Param("tenantId") String tenantId, @Param("startTime") String startTime
            ,@Param("endTime") String endTime,@Param("projectId") String projectId,@Param("searchVal") String searchVal,
                                     @Param("dealStatus") String dealStatus, @Param("page") Integer page, @Param("limit") Integer limit);


    int selectReservationPersonCount(@Param("tenantId") String tenantId, @Param("startTime") String startTime
            ,@Param("endTime") String endTime,@Param("projectId") String projectId,@Param("searchVal") String searchVal, @Param("dealStatus") String dealStatus);


    /**
     * 查询服务详情
     * @param id
     * @return
     */
    ReservationInfo  selectReservationInfo(String id);


    /**
     * 申请发布,服务状态为待审核
     * @param id
     * @param userId
     * @return
     */
    int updateAuditStatus(@Param("id") String id, @Param("userId") String userId);

    /**
     *撤回,服务状态为已撤回
     * @param id
     * @param userId
     * @return
     */
    int updateSoldStatus(@Param("id") String id, @Param("userId") String userId);

    /**
     * 发布,服务状态为已发布
     * @param id
     * @param userId
     * @return
     */
    int updatePutawayStatus(@Param("id") String id, @Param("userId") String userId);

    /**
     * 驳回,服务状态为已驳回
     * @param id
     * @param userId
     * @return
     */
    int updateRejectStatus(@Param("id") String id, @Param("userId") String userId);


    /**
     * 根据服务id查询服务状态
     * @param id
     * @return
     */
    String selectReservaStatusById(String id);


    /**
     * 查询服务下的服务范围
     * @param id
     * @return
     */
    List<ResultProjectVo> selectProjectList(String id);

    List<ResultClassifyVo> selectClassifyListById(String id);

    /**
     * 查询服务下的服务范围
     * @param id
     * @return
     */
    List<String> selectProjectIdById(String id);

    /**
     * 查询商户下业务的所属分类名称
     * @param id
     * @return
     */
    List<String> selectClassifyNameById(String id);

    /**
     * 查询服务审核列表
     * @return
     */
    List<ResrevationAuditVo> selectResrevationAuditList(@Param("type") String type, @Param("tenantId") String tenantId, @Param("reservaStatus") String reservaStatus
            ,@Param("classifyId") String classifyId,@Param("projectId") String projectId,@Param("searchVal") String searchVal,
                                                        @Param("page") Integer page, @Param("limit") Integer limit);


    int selectResrevationAuditCount(@Param("type") String type, @Param("tenantId") String tenantId, @Param("reservaStatus") String reservaStatus
            ,@Param("classifyId") String classifyId,@Param("projectId") String projectId,@Param("searchVal") String searchVal);


    /**
     * 服务工单操作
     * @param userId
     * @param id
     * @return
     */
    int updateReservatStatus(@Param("status") String status,@Param("userId") String userId, @Param("id") String id);



    List<ResultProjectVo> selectProjectName();

    /**
     * 查询商户负责人
     * @param tenantId
     * @return
     */
    TenameInfo seelctTenameInfoById(String tenantId);

    /**
     * 插入日志
     * @param bizTransactionLog
     * @return
     */
    int insertReservatLog(BizTransactionLog bizTransactionLog);


    /**
     * Web后台查询预约服务订单列表
     * @param map
     * @return
     */
    List<SubListForWebVo> querySubListByWeb(Map<?, ?> map);

    /**
     * Web后台查询预约服务订单列表总数
     * @param map
     * @return
     */
    int querySubListByWebTotal(Map<?, ?> map);




}
