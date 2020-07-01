package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizSubscribe;
import com.github.wxiaoqi.security.jinmao.vo.propertybill.BillDateVo;
import com.github.wxiaoqi.security.jinmao.vo.propertybill.BillDetailVo;
import com.github.wxiaoqi.security.jinmao.vo.propertybill.UserAllBillList;
import com.github.wxiaoqi.security.jinmao.vo.propertybill.UserBillVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单表
 * 
 * @author huangxl
 * @Date 2019-02-18 16:37:08
 */
public interface BizSubscribeMapper extends CommonMapper<BizSubscribe> {

    List<UserBillVo> getPropertyBillList(@Param("projectId") String projectId,@Param("subStatus") String subStatus,@Param("syncStatus")String syncStatus,
                                         @Param("searchVal") String searchVal ,@Param("page") Integer page,@Param("limit") Integer limit,@Param("tenantId")String tenantId,@Param("type")String type);

    BillDetailVo getPropertyBillDetail(@Param("id") String id);

    List<BillDateVo> selectPayBillFeeById(@Param("id") String id,@Param("shouldDate") String shouldDate);

    List<UserAllBillList> selectBillListFeeById(@Param("id") String id);

    List<BillDateVo> getPropertyFeeById(@Param("id") String id);

    int getPropertyBillCount(@Param("projectId") String projectId,@Param("subStatus") String subStatus,
                             @Param("searchVal") String searchVal,@Param("tenantId")String tenantId,@Param("type")String type);

    String getRoomNameBySubId(@Param("roomId") String roomId);

    String getProjectId(@Param("tenantId") String tenantId);

    List<Map> querySubList(Map params);
}
