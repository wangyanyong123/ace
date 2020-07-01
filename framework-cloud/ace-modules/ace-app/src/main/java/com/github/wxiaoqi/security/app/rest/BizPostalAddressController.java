package com.github.wxiaoqi.security.app.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.postaladdress.in.PostalAddress;
import com.github.wxiaoqi.security.api.vo.postaladdress.out.PostalAddressDeliveryOut;
import com.github.wxiaoqi.security.api.vo.postaladdress.out.PostalAddressOut;
import com.github.wxiaoqi.security.app.entity.BizPostalAddress;
import com.github.wxiaoqi.security.app.mapper.BizPostalAddressMapper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import io.swagger.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 收货地址
 *
 * @author huangxl
 * @Date 2018-12-18 18:34:14
 */
@RestController
@RequestMapping("postalAddress")
@CheckClientToken
@CheckUserToken
@Api(tags="维护收货地址")
public class BizPostalAddressController {

    @Autowired
    private BizPostalAddressMapper bizPostalAddressMapper;

    /**
     * 新增收货地址
     * @return
     */
    @RequestMapping(value = "/insertPostalAddress" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增收货地址", notes = "维护收货地址---新增收货地址",httpMethod = "POST")
    public ObjectRestResponse insertPostalAddress(@RequestBody @ApiParam PostalAddress postalAddress){
        ObjectRestResponse result = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        if(StringUtils.isAnyoneEmpty(userId)){
            result.setStatus(102);
            result.setMessage("未登陆，请登陆系统");
            return result;
        }
        if(StringUtils.isAnyoneEmpty(postalAddress.getContactName(),postalAddress.getContactTel(),postalAddress.getAddr(),postalAddress.getProcCode(),postalAddress.getCityName(),postalAddress.getDistrictCode(),postalAddress.getDistrictName(),postalAddress.getIsUse())){
            result.setStatus(101);
            result.setMessage("参数不能为空");
            return result;
        }
        if(!StringUtils.isMobile(postalAddress.getContactTel())){
            result.setStatus(101);
            result.setMessage("联系电话手机格式不对");
            return result;
        }

        if("1".equals(postalAddress.getIsUse())){
            //去掉其他默认地址
            bizPostalAddressMapper.delDefaultPostalAddress(userId);
        }

        BizPostalAddress bizPostalAddress = new BizPostalAddress();
        BeanUtils.copyProperties(postalAddress,bizPostalAddress);
        bizPostalAddress.setId(UUIDUtils.generateUuid());
        bizPostalAddress.setUserId(userId);
        bizPostalAddress.setStatus("1");
        bizPostalAddress.setProjectId(postalAddress.getProjectId());
        bizPostalAddress.setTimeStamp(DateTimeUtil.getLocalTime());
        bizPostalAddress.setCreateTime(DateTimeUtil.getLocalTime());
        bizPostalAddress.setCreateBy(userId);
        int count = bizPostalAddressMapper.insertSelective(bizPostalAddress);
        if(count<1){
            result.setStatus(101);
            result.setMessage("插入数据异常");
        }
        return result;
    }

    /**
     * 新增收货地址
     * @return
     */
    @RequestMapping(value = "/updatePostalAddress" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新收货地址", notes = "维护收货地址---新增收货地址",httpMethod = "POST")
    public ObjectRestResponse updatePostalAddress(@RequestBody @ApiParam PostalAddress postalAddress){
        ObjectRestResponse result = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        if(StringUtils.isAnyoneEmpty(userId)){
            result.setStatus(102);
            result.setMessage("未登陆，请登陆系统");
            return result;
        }
        if(StringUtils.isAnyoneEmpty(postalAddress.getId(),postalAddress.getContactName(),postalAddress.getContactTel(),postalAddress.getAddr(),postalAddress.getProcCode(),postalAddress.getCityName(),postalAddress.getDistrictCode(),postalAddress.getDistrictName(),postalAddress.getIsUse())){
            result.setStatus(101);
            result.setMessage("参数不能为空");
            return result;
        }
        if(!StringUtils.isMobile(postalAddress.getContactTel())){
            result.setStatus(101);
            result.setMessage("联系电话手机格式不对");
            return result;
        }
        if("1".equals(postalAddress.getIsUse())){
            //去掉其他默认地址
            bizPostalAddressMapper.delDefaultPostalAddress(userId);
        }

        BizPostalAddress bizPostalAddress = new BizPostalAddress();
        BeanUtils.copyProperties(postalAddress,bizPostalAddress);
        bizPostalAddress.setStatus("1");
        bizPostalAddress.setTimeStamp(DateTimeUtil.getLocalTime());
        bizPostalAddress.setModifyTime(DateTimeUtil.getLocalTime());
        bizPostalAddress.setModifyBy(userId);
        int count = bizPostalAddressMapper.updateByPrimaryKeySelective(bizPostalAddress);
        if(count<1){
            result.setStatus(101);
            result.setMessage("更新数据异常");
        }
        return result;
    }

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    @RequestMapping(value = "/delPostalAddress", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除收货地址", notes = "维护收货地址---删除收货地址",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="收货地址ID",dataType="String",required = true ,paramType = "query",example="")
    public ObjectRestResponse delPostalAddress(String id){
        ObjectRestResponse result = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        if(StringUtils.isAnyoneEmpty(userId)){
            result.setStatus(102);
            result.setMessage("未登陆，请登陆系统");
            return result;
        }
        BizPostalAddress bizPostalAddress = new BizPostalAddress();
        bizPostalAddress.setId(id);
        bizPostalAddress.setStatus("0");
        bizPostalAddress.setTimeStamp(DateTimeUtil.getLocalTime());
        bizPostalAddress.setModifyTime(DateTimeUtil.getLocalTime());
        bizPostalAddress.setModifyBy(userId);
        int count = bizPostalAddressMapper.updateByPrimaryKeySelective(bizPostalAddress);
        if(count<1){
            result.setStatus(101);
            result.setMessage("删除数据异常");
        }
        return result;
    }

    /**
     * 设置为默认收货地址
     * @param id
     * @return
     */
    @RequestMapping(value = "/setDefaultPostalAddress/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "设置为默认收货地址", notes = "维护收货地址---设置为默认收货地址",httpMethod = "GET")
    public ObjectRestResponse setDefaultPostalAddress(@PathVariable String id){
        ObjectRestResponse result = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        if(StringUtils.isAnyoneEmpty(userId)){
            result.setStatus(102);
            result.setMessage("未登陆，请登陆系统");
            return result;
        }
        //1.去掉其他默认地址
        bizPostalAddressMapper.delDefaultPostalAddress(userId);

        //2.设置当前id为默认地址
        BizPostalAddress bizPostalAddress = new BizPostalAddress();
        bizPostalAddress.setId(id);
        bizPostalAddress.setIsUse("1");
        bizPostalAddress.setTimeStamp(DateTimeUtil.getLocalTime());
        bizPostalAddress.setModifyTime(DateTimeUtil.getLocalTime());
        bizPostalAddress.setModifyBy(userId);
        int count = bizPostalAddressMapper.updateByPrimaryKeySelective(bizPostalAddress);
        if(count<1){
            result.setStatus(101);
            result.setMessage("设置默认收货地址操作数据异常");
        }
        return result;
    }

    /**
     * 查询我的收货地址列表
     * @return
     */
    @RequestMapping(value = "/getPostalAddressList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询我的收货地址列表", notes = "维护收货地址---查询我的收货地址列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example=""),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="")
    })
    public ObjectRestResponse<List<PostalAddressOut>> getPostalAddressList(@RequestParam(defaultValue = "1") int page,
                                                                           @RequestParam(defaultValue = "10")int limit){
        ObjectRestResponse result = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        if(StringUtils.isAnyoneEmpty(userId)){
            result.setStatus(102);
            result.setMessage("未登陆，请登陆系统");
            return result;
        }
        if (page<1) {
            page = 1;
        }
        if (limit<1) {
            limit = 10;
        }
        //分页
        int startIndex = (page - 1) * limit;
        List<PostalAddressOut> postalAddressList =  bizPostalAddressMapper.getPostalAddressList(userId, startIndex, limit);
        if(postalAddressList==null || postalAddressList.size()==0){
            postalAddressList = new ArrayList<>();
        }
        result.setData(postalAddressList);
        return result;
    }

    /**
     * 查询我的默认收货地址
     * @return
     */
    @RequestMapping(value = "/getUsePostalAddress", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询我的收货地址列表", notes = "维护收货地址---查询我的收货地址列表",httpMethod = "GET")
    public ObjectRestResponse<PostalAddressOut> getUsePostalAddress(){
        ObjectRestResponse result = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        if(StringUtils.isAnyoneEmpty(userId)){
            result.setStatus(102);
            result.setMessage("未登陆，请登陆系统");
            return result;
        }
        PostalAddressOut postalAddressOut =  bizPostalAddressMapper.getUsePostalAddress(userId);
        if(postalAddressOut==null){
            result.setStatus(103);
            result.setMessage("未设置默认收货地址");
            return result;
        }
        result.setData(postalAddressOut);
        return result;
    }

    /**
     * 校验收获地址是否配送
     * @return
     */
    @RequestMapping(value = "/verifyAddressDelivery" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "校验收货地址", notes = "校验收货地址---是否配送",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tenantId",value="商户ID",dataType="String",paramType = "query",example=""),
            @ApiImplicitParam(name="procCode",value="区域编码",dataType="String",paramType = "query",example="")
    })
    public ObjectRestResponse verifyAddressDelivery(String tenantId, String procCode) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(procCode)) {
            msg.setStatus(101);
            msg.setMessage("编码不能为空");
            return msg;
        }
        if (StringUtils.isEmpty(tenantId)) {
            msg.setStatus(101);
            msg.setMessage("商户ID不能为空");
            return msg;
        }
        PostalAddressDeliveryOut result =  bizPostalAddressMapper.getNoDeliveryAddress(tenantId,procCode);
        if (result != null) {
            if (result.getTenantId().equals(tenantId) && result.getProcCode().equals(procCode)) {
                msg.setStatus(101);
                msg.setData(result);
                msg.setMessage("该区域不配送");
                return msg;
            }
        }
        msg.setMessage("该区域可以配送");
        return msg;
    }
    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/verifyAddressDeliveryUnLogin" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "校验收货地址", notes = "校验收货地址---是否配送",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tenantId",value="商户ID",dataType="String",paramType = "query",example=""),
            @ApiImplicitParam(name="procCode",value="区域编码",dataType="String",paramType = "query",example="")
    })
    public ObjectRestResponse verifyAddressDeliveryUnLogin(String tenantId, String procCode) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(procCode)) {
            msg.setStatus(101);
            msg.setMessage("编码不能为空");
            return msg;
        }
        if (StringUtils.isEmpty(tenantId)) {
            msg.setStatus(101);
            msg.setMessage("商户ID不能为空");
            return msg;
        }
        PostalAddressDeliveryOut result =  bizPostalAddressMapper.getNoDeliveryAddress(tenantId,procCode);
        if (result != null) {
            if (result.getTenantId().equals(tenantId) && result.getProcCode().equals(procCode)) {
                msg.setStatus(101);
                msg.setData(result);
                msg.setMessage("该区域不配送");
                return msg;
            }
        }
        msg.setMessage("该区域可以配送");
        return msg;
    }
}