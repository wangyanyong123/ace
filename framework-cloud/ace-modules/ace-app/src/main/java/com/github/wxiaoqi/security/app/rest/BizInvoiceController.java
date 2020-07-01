package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizInvoiceBiz;
import com.github.wxiaoqi.security.app.vo.invoice.InvoiceParams;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 发票抬头表
 *
 * @author huangxl
 * @Date 2019-04-16 10:49:40
 */
@RestController
@RequestMapping("bizInvoice")
@CheckClientToken
@CheckUserToken
@Api(tags = "发票接口")
public class BizInvoiceController {

    @Autowired
    private BizInvoiceBiz bizInvoiceBiz;

    @RequestMapping("/saveInvoice")
    @ResponseBody
    @ApiOperation(value = "保存发票", notes = "保存发票",httpMethod = "POST")
    public ObjectRestResponse saveInvoice(@RequestBody @ApiParam InvoiceParams invoiceParams) {
        return bizInvoiceBiz.saveInvoice(invoiceParams);
    }

    @RequestMapping("/updateInvoice")
    @ResponseBody
    @ApiOperation(value = "修改发票", notes = "修改发票",httpMethod = "POST")
    public ObjectRestResponse updateInvoice(@RequestBody @ApiParam InvoiceParams invoiceParams) {
        return bizInvoiceBiz.updateInvoice(invoiceParams);
    }

    @RequestMapping("/deleteInvoice")
    @ResponseBody
    @ApiOperation(value = "删除发票", notes = "删除发票",httpMethod = "GET")
    public ObjectRestResponse deleteInvoice(String id) {
        return bizInvoiceBiz.deleteInvoice(id);
    }

    @RequestMapping(value = "/getInvoiceList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询发票列表", notes = "查询发票列表",httpMethod = "GET")
    public ObjectRestResponse<InvoiceParams> getInvoiceList() {
        return bizInvoiceBiz.getInvoiceList();
    }

    @RequestMapping(value = "/getInvoiceDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询发票详情", notes = "查询发票详情",httpMethod = "GET")
    @ApiIgnore
    public ObjectRestResponse<InvoiceParams> getInvoiceDetail(String id) {
        return new ObjectRestResponse();
    }


    @RequestMapping(value = "/getDefaultInvoice", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询当前默认发票", notes = "查询当前默认发票",httpMethod = "GET")
    public ObjectRestResponse<InvoiceParams> getDefaultInvoice() {
        return bizInvoiceBiz.getDefaultInvoice();
    }
}