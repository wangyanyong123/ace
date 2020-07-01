package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizInvoice;
import com.github.wxiaoqi.security.app.vo.invoice.InvoiceListVo;
import com.github.wxiaoqi.security.app.vo.invoice.InvoiceParams;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 发票抬头表
 * 
 * @author huangxl
 * @Date 2019-04-16 10:49:40
 */
public interface BizInvoiceMapper extends CommonMapper<BizInvoice> {


    List<InvoiceListVo> getInvoiceList(@Param("userId") String userId);

    int updateInvoiceStatus(@Param("userId") String userId);

    InvoiceParams getDefaultInvoice(@Param("userId") String userId);

    int deleteInvoice(@Param("id") String id);
}
