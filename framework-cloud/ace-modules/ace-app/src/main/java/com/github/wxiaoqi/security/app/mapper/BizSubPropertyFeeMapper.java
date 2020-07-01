package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizSubPropertyFee;
import com.github.wxiaoqi.security.app.vo.propertybill.ShouldDateList;
import com.github.wxiaoqi.security.app.vo.propertybill.out.AccountBook;
import com.github.wxiaoqi.security.app.vo.propertybill.out.InvoiceBillVo;
import com.github.wxiaoqi.security.app.vo.propertybill.out.PropertySubVo;
import com.github.wxiaoqi.security.app.vo.propertybill.out.ShouldBillOut;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 物业缴费订单明细表
 * 
 * @author huangxl
 * @Date 2019-01-29 17:31:29
 */
public interface BizSubPropertyFeeMapper extends CommonMapper<BizSubPropertyFee> {

    List<PropertySubVo> getPropertySub(@RequestParam("shouldId") String shouldId);

    List<ShouldBillOut> getPropertyBill(@Param("userId") String userId, @Param("projectId")String projectId,@Param("houseId")String houseId);

    List<ShouldDateList> getPropertyDateList(@Param("subId") String subId);

    AccountBook getBillAccountBook(@Param("subId") String subId);

    /**
     * 查询未开发票账单列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<InvoiceBillVo> selectInvoiceBillList(@Param("userId") String userId,@Param("projectId")String projectId,
                                              @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询已缴费账单列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<InvoiceBillVo> selectPayBillList(@Param("userId") String userId,@Param("projectId")String projectId,
                                          @Param("page") Integer page, @Param("limit") Integer limit);
}
