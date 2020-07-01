package com.github.wxiaoqi.security.merchant.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.merchant.entity.BizOrderInvoice;
import com.github.wxiaoqi.security.merchant.mapper.BizOrderInvoiceMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 商品订单发票
 *
 * @author wangyanyong
 * @Date 2020-04-26 09:10:30
 */
@Service
public class BizOrderInvoiceBiz extends BusinessBiz<BizOrderInvoiceMapper,BizOrderInvoice> {
}