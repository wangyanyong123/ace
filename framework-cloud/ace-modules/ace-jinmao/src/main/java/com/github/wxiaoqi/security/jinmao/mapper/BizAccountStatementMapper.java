package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizAccountStatement;
import com.github.wxiaoqi.security.jinmao.vo.statement.out.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author huangxl
 * @Date 2019-03-19 15:54:18
 */
public interface BizAccountStatementMapper extends CommonMapper<BizAccountStatement> {


    /**
     * 查询账单列表
     * @param tenantId
     * @param startTime
     * @param endTime
     * @param balanceStatus
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<StatementVo> selectBillList(@Param("tenantId") String tenantId, @Param("startTime") String startTime, @Param("endTime") String endTime,
                                     @Param("balanceStatus") String balanceStatus, @Param("projectId") String projectId,
                                     @Param("page") Integer page,@Param("limit") Integer limit);


    /**
     * 查询账单数量
     * @param tenantId
     * @param startTime
     * @param endTime
     * @param balanceStatus
     * @param projectId
     * @return
     */
    int selectBillCount(@Param("tenantId") String tenantId, @Param("startTime") String startTime, @Param("endTime") String endTime,
                        @Param("balanceStatus") String balanceStatus, @Param("projectId") String projectId);


    /**
     * 导出账单列表数据
     * @param tenantId
     * @param startTime
     * @param endTime
     * @param balanceStatus
     * @param projectId
     * @return
     */
    List<StatementVo> selectExportBillList(@Param("tenantId") String tenantId, @Param("startTime") String startTime, @Param("endTime") String endTime,
                                           @Param("balanceStatus") String balanceStatus, @Param("projectId") String projectId);



    int updateBalanceStatus(@Param("status") String status, @Param("balanceImg") String balanceImg, @Param("id") String id);

    /**
     * 查询账单详情
     * @param tenantId
     * @return
     */
    BillInfo selectBillInfo(@Param("id") String id, @Param("tenantId") String tenantId);

    /**
     * 查询账单明细列表
     * @param tenantId
     * @param startTime
     * @param endTime
     * @return
     */
    List<BillDetailList> selectBillDetail(@Param("tenantId") String tenantId,@Param("startTime") String startTime,
                                          @Param("endTime") String endTime,@Param("page") Integer page,@Param("limit") Integer limit);


    int selectBillDetailCount(@Param("tenantId") String tenantId,@Param("startTime") String startTime,
                              @Param("endTime") String endTime);


    /**
     * 查询账单明细列表 支付款的所有状态
     * @param tenantId
     * @param startTime
     * @param endTime
     * @return
     */
    List<BillDetailList> selectBillAllDetail(@Param("tenantId") String tenantId,@Param("startTime") String startTime,
                                          @Param("endTime") String endTime,@Param("page") Integer page,@Param("limit") Integer limit);


    int selectBillDetailAllCount(@Param("tenantId") String tenantId,@Param("startTime") String startTime,
                              @Param("endTime") String endTime);


    /**
     * 导出账单明细
     * @param tenantId
     * @param startTime
     * @param endTime
     * @return
     */
    List<BillDetailList> selectExportBillDetail(@Param("tenantId") String tenantId,@Param("startTime") String startTime, @Param("endTime") String endTime);



    List<BillDetailList> selectExportBillDetailAll(@Param("tenantId") String tenantId,@Param("startTime") String startTime, @Param("endTime") String endTime);




    /**
     * 统计各商户收益金额
     * @return
     */
    List<StatisticsVo> selectTenantMoneyById();



    String selectSettlementById(String id);

    /**
     * 查询凭证信息
     * @param id
     * @return
     */
    AccountInfo selectAccountInfoById(String id);


}
