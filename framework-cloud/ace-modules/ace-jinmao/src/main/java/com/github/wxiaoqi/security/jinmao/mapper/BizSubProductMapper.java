package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizSubProduct;
import com.github.wxiaoqi.security.jinmao.vo.account.AccountDetailVo;
import com.github.wxiaoqi.security.jinmao.vo.account.BusInfo;
import com.github.wxiaoqi.security.jinmao.vo.account.CheckAccountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单产品表
 * 
 * @author huangxl
 * @Date 2019-03-25 10:52:39
 */
public interface BizSubProductMapper extends CommonMapper<BizSubProduct> {


    /**
     * 查询商品销售明细列表
     * @param type
     * @param tenantId
     * @param searchVal
     * @param projectId
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<AccountDetailVo> selectAccountDetailList(@Param("type") String type, @Param("tenantId") String tenantId, @Param("searchVal") String searchVal,
                                                  @Param("projectId") String projectId, @Param("startTime") String startTime, @Param("endTime") String endTime,
                                                  @Param("page") Integer page, @Param("limit") Integer limit);


    int selectAccountDetailCount(@Param("type") String type, @Param("tenantId") String tenantId, @Param("searchVal") String searchVal,
                                 @Param("projectId") String projectId, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 导出商品销售明细列表
     * @param type
     * @param tenantId
     * @param searchVal
     * @param projectId
     * @param startTime
     * @param endTime
     * @return
     */
    List<AccountDetailVo> selectExportAccountDetail(@Param("type") String type, @Param("tenantId") String tenantId, @Param("searchVal") String searchVal,
                                                    @Param("projectId") String projectId, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 查询商品所属业务的销量比以及订单总金额占比
     * @param type
     * @param tenantId
     * @param searchVal
     * @param projectId
     * @param startTime
     * @param endTime
     * @return
     */
    List<BusInfo> selectBusInfo(@Param("type") String type, @Param("tenantId") String tenantId, @Param("searchVal") String searchVal,
                                @Param("projectId") String projectId, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 查询商品分类的销量比与订单总金额比
     * @param type
     * @param tenantId
     * @param searchVal
     * @param projectId
     * @param startTime
     * @param endTime
     * @return
     */
    List<BusInfo> selectClassifyInfo(@Param("type") String type, @Param("tenantId") String tenantId, @Param("searchVal") String searchVal,
                                     @Param("projectId") String projectId, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 查询项目销量与订单总金额的占比
     * @param type
     * @param tenantId
     * @param searchVal
     * @param projectId
     * @param startTime
     * @param endTime
     * @return
     */
    List<BusInfo> selectProjectInfo(@Param("type") String type, @Param("tenantId") String tenantId, @Param("searchVal") String searchVal,
                                    @Param("projectId") String projectId, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 查询起止时间销量与订单总金额的占比
     * @param type
     * @param tenantId
     * @param searchVal
     * @param projectId
     * @param startTime
     * @param endTime
     * @return
     */
    List<BusInfo> selectTimeInfo(@Param("type") String type, @Param("tenantId") String tenantId, @Param("searchVal") String searchVal,
                                 @Param("projectId") String projectId, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 查询商家对账列表
     * @param type
     * @param tenantId
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<CheckAccountVo> selectCheckAccountList(@Param("type") String type, @Param("tenantId") String tenantId,
                                                @Param("startTime") String startTime, @Param("endTime") String endTime,
                                                @Param("page") Integer page, @Param("limit") Integer limit);



    int selectCheckAccountCount(@Param("type") String type, @Param("tenantId") String tenantId,
                                @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 查询商家对账列表
     * @param type
     * @param tenantId
     * @param startTime
     * @param endTime
     * @return
     */
    List<CheckAccountVo> selectExportCheckAccount(@Param("type") String type, @Param("tenantId") String tenantId,
                                                  @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 查询周期结算金额占比
     * @param type
     * @param tenantId
     * @param startTime
     * @param endTime
     * @return
     */
    List<BusInfo> selectSettlementInfo(@Param("type") String type, @Param("tenantId") String tenantId,
                                       @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 查询结算状态占比
     * @param type
     * @param tenantId
     * @param startTime
     * @param endTime
     * @return
     */
    List<BusInfo> selectBalanceStatusInfo(@Param("type") String type, @Param("tenantId") String tenantId,
                                   @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<Map> selectReservationInfoBySubId(@Param("subId") String subId);

    List<Map> selectProductInfoBySubId(@Param("subId") String subId);
}
