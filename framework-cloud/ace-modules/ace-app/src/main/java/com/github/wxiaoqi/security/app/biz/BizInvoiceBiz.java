package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.vo.invoice.InvoiceListVo;
import com.github.wxiaoqi.security.app.vo.invoice.InvoiceParams;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizInvoice;
import com.github.wxiaoqi.security.app.mapper.BizInvoiceMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发票抬头表
 *
 * @author huangxl
 * @Date 2019-04-16 10:49:40
 */
@Service
public class BizInvoiceBiz extends BusinessBiz<BizInvoiceMapper,BizInvoice> {

    @Autowired
    private BizInvoiceMapper bizInvoiceMapper;
    
    public ObjectRestResponse saveInvoice(InvoiceParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        BizInvoice bizInvoice = new BizInvoice();
        String id = UUIDUtils.generateUuid();
        bizInvoice.setId(id);
        bizInvoice.setUserId(BaseContextHandler.getUserID());
        bizInvoice.setInvoiceName(params.getInvoiceName());
        bizInvoice.setInvoiceType(params.getInvoiceType());
        bizInvoice.setUnitAddr(params.getUnitAddr());
        bizInvoice.setTelphone(params.getTelphone());
        bizInvoice.setBankName(params.getBankName());
        bizInvoice.setBankNum(params.getBankNum());
        if ("2".equals(params.getInvoiceType())) {
            bizInvoice.setDutyNum(params.getDutyNum());
        }
        bizInvoice.setIsDefault(params.getIsDefault());
        bizInvoice.setCreateBy(BaseContextHandler.getUserID());
        bizInvoice.setCreateTime(new Date());
        if ("1".equals(params.getIsDefault())) {
            bizInvoiceMapper.updateInvoiceStatus(BaseContextHandler.getUserID());
        }
        bizInvoice.setIsDefault(params.getIsDefault());
        bizInvoiceMapper.insertSelective(bizInvoice);
        response.setMessage("添加成功");
        response.setData(id);
        return response;
    }

    public ObjectRestResponse updateInvoice(InvoiceParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        BizInvoice bizInvoice = new BizInvoice();
        bizInvoice.setId(params.getId());
        bizInvoice.setUserId(BaseContextHandler.getUserID());
        bizInvoice.setInvoiceName(params.getInvoiceName());
        bizInvoice.setInvoiceType(params.getInvoiceType());
        bizInvoice.setUnitAddr(params.getUnitAddr());
        bizInvoice.setTelphone(params.getTelphone());
        bizInvoice.setBankName(params.getBankName());
        bizInvoice.setBankNum(params.getBankNum());
        if ("2".equals(params.getInvoiceType())) {
            bizInvoice.setDutyNum(params.getDutyNum());
        }
        bizInvoice.setIsDefault(params.getIsDefault());
        bizInvoice.setCreateBy(BaseContextHandler.getUserID());
        bizInvoice.setCreateTime(new Date());
        if ("1".equals(params.getIsDefault())) {
            bizInvoiceMapper.updateInvoiceStatus(BaseContextHandler.getUserID());
        }
        bizInvoice.setIsDefault(params.getIsDefault());
        bizInvoiceMapper.updateByPrimaryKeySelective(bizInvoice);
        response.setMessage("修改成功");
        return response;
    }

    public ObjectRestResponse<InvoiceParams> getInvoiceList() {
        ObjectRestResponse response = new ObjectRestResponse();
        List<InvoiceListVo> invoiceList = bizInvoiceMapper.getInvoiceList(BaseContextHandler.getUserID());
        if (invoiceList == null || invoiceList.size() == 0) {
            invoiceList = new ArrayList<>();
        }
        response.setData(invoiceList);
        return response;
    }

    public ObjectRestResponse<InvoiceParams> getDefaultInvoice() {
        ObjectRestResponse response = new ObjectRestResponse();
        InvoiceParams defaultInvoice = bizInvoiceMapper.getDefaultInvoice(BaseContextHandler.getUserID());
        if (defaultInvoice == null) {
            defaultInvoice = new InvoiceParams();
        }
        response.setData(defaultInvoice);
        return response;
    }

    public ObjectRestResponse deleteInvoice(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        int i = bizInvoiceMapper.deleteInvoice(id);
        if (i > 0) {
            response.setMessage("删除成功");
        }
        return response;
    }
}