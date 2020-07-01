package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizIntegralProductBiz;
import com.github.wxiaoqi.security.jinmao.biz.BizIntegralScreenBiz;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.in.SaveIntegralProductParam;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.in.SaveProductInfo;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.in.SaveSpecInfo;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.in.UpdateStatusParam;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.out.CashProductVo;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.out.IntegralProductInfo;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.out.IntegralProductVo;
import com.github.wxiaoqi.security.jinmao.vo.integralscreen.in.SaveIntegralScreenParam;
import com.github.wxiaoqi.security.jinmao.vo.integralscreen.out.IntegralScreenVo;
import com.github.wxiaoqi.security.jinmao.vo.stat.in.DataExcel;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 积分商品表
 *
 * @author huangxl
 * @Date 2019-08-28 10:04:24
 */
@RestController
@RequestMapping("web/bizIntegralProduct")
@CheckClientToken
@CheckUserToken
@Api(tags = "积分商品管理")
public class BizIntegralProductController{

    @Autowired
    private BizIntegralProductBiz bizIntegralProductBiz;
    @Autowired
    private BizIntegralScreenBiz bizIntegralScreenBiz;


    /**
     * 查询积分商品列表
     * @return
     */
    @RequestMapping(value = "/getIntegralProductList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询积分商品列表---PC端", notes = "查询积分商品列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="isRecommend",value="是否推荐查询(0表示不推荐，1表示推荐)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="busStatus",value="状态查询(1-待发布,2-已发布3-已下架)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据商品编码、商品名称、商户名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<IntegralProductVo> getIntegralProductList(String projectId,String isRecommend,String busStatus,
                                                                   String searchVal, Integer page, Integer limit){
        List<IntegralProductVo> productList =  bizIntegralProductBiz.getIntegralProductList(projectId, isRecommend, busStatus, searchVal, page, limit);
        int total =bizIntegralProductBiz.selectIntegralProduct(projectId,isRecommend,busStatus, searchVal);
        return new TableResultResponse<IntegralProductVo>(total, productList);
    }



    /**
     * 保存积分商品
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveIntegralProduct",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存积分商品---PC端", notes = "保存积分商品---PC端",httpMethod = "POST")
    public ObjectRestResponse saveIntegralProduct(@RequestBody @ApiParam SaveIntegralProductParam params){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(params == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空");
            return msg;
        }
        SaveProductInfo productInfo = params.getProductInfo();
        SaveSpecInfo specInfo = params.getSpecInfo();
        if(productInfo == null || specInfo == null){
            msg.setStatus(1002);
            msg.setMessage("商品基本信息不能为空");
            return msg;
        }
        if(StringUtils.isAnyEmpty(productInfo.getProductClassify())){
            msg.setStatus(1002);
            msg.setMessage("商品分类不能为空");
            return msg;
        }
        if(StringUtils.isAnyEmpty(productInfo.getProductName(),productInfo.getProductName(),specInfo.getSpecName(),
                specInfo.getSpecIntegral())){
            msg.setStatus(1003);
            msg.setMessage("商品名称/总数量/规格名称/规格积分不能为空");
            return msg;
        }
        if(productInfo.getProductImageList() == null || productInfo.getProductImageList().size() == 0){
            msg.setStatus(201);
            msg.setMessage("商品封面不能为空!");
            return msg;
        }
        if(productInfo.getSelectionImageList() == null || productInfo.getSelectionImageList().size() == 0){
            msg.setStatus(201);
            msg.setMessage("商品精选图片不能为空!");
            return msg;
        }
        if(productInfo.getProjectVo() == null || productInfo.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目范围不能为空!");
            return msg;
        }
        return bizIntegralProductBiz.saveIntegralProduct(params);
    }


    /**
     * 查询积分商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getIntegralProductInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询积分商品详情---PC端", notes = "查询积分商品详情---PC端",httpMethod = "GET")
    public TableResultResponse<IntegralProductInfo> getIntegralProductInfo(@PathVariable String id){
        List<IntegralProductInfo> info = bizIntegralProductBiz.getIntegralProductInfo(id);
        return new TableResultResponse<IntegralProductInfo>(info.size(),info);
    }



    /**
     * 编辑积分商品
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateIntegralProduct",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑积分商品---PC端", notes = "编辑积分商品---PC端",httpMethod = "POST")
    public ObjectRestResponse updateIntegralProduct(@RequestBody @ApiParam SaveIntegralProductParam params){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(params == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空");
            return msg;
        }
        SaveProductInfo productInfo = params.getProductInfo();
        SaveSpecInfo specInfo = params.getSpecInfo();
        if(productInfo == null || specInfo == null){
            msg.setStatus(1002);
            msg.setMessage("商品基本信息不能为空");
            return msg;
        }
        if(StringUtils.isAnyEmpty(productInfo.getProductName(),productInfo.getProductName(),specInfo.getSpecName(),
                specInfo.getSpecIntegral())){
            msg.setStatus(1003);
            msg.setMessage("商品名称/总数量/规格名称/规格积分不能为空");
            return msg;
        }
        if(productInfo.getProductImageList() == null || productInfo.getProductImageList().size() == 0){
            msg.setStatus(201);
            msg.setMessage("商品封面不能为空!");
            return msg;
        }
        if(productInfo.getSelectionImageList() == null || productInfo.getSelectionImageList().size() == 0){
            msg.setStatus(201);
            msg.setMessage("商品精选图片不能为空!");
            return msg;
        }
        if(productInfo.getProjectVo() == null || productInfo.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目范围不能为空!");
            return msg;
        }
        return bizIntegralProductBiz.updateIntegralProduct(params);
    }

    /**
     * 商品操作
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateProductStatus", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "商品操作---PC端", notes = "商品操作---PC端",httpMethod = "POST")
    public ObjectRestResponse updateProductStatus(@RequestBody @ApiParam UpdateStatusParam param){
        return bizIntegralProductBiz.updateProductStatus(param);
    }



    /**
     * 保存积分范围
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveIntegralScreen", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存积分范围---PC端", notes = "保存积分范围---PC端",httpMethod = "POST")
    public ObjectRestResponse saveIntegralScreen(@RequestBody @ApiParam SaveIntegralScreenParam param){
        return bizIntegralScreenBiz.saveIntegralScreen(param);
    }



    /**
     * 查询积分范围
     * @return
     */
    @RequestMapping(value = "/getIntegralScreenInfo",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询积分范围---PC端", notes = "查询积分范围---PC端",httpMethod = "GET")
    public TableResultResponse<IntegralScreenVo> getIntegralScreenInfo(){
        List<IntegralScreenVo> screenVoList = bizIntegralScreenBiz.getIntegralScreenInfo();
        return new TableResultResponse<IntegralScreenVo>(screenVoList.size(), screenVoList);
    }



    /**
     * 查询商品兑换列表
     * @return
     */
    @RequestMapping(value = "/getCashProductList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品兑换列表---PC端", notes = "查询商品兑换列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="startTime",value="开始时间(yyyy-MM-dd)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endTime",value="结束时间(yyyy-MM-dd)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据兑换编号、商品名称、客户名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<CashProductVo> getCashProductList(String projectId, String startTime, String endTime, String searchVal,
                                                                     Integer page, Integer limit){
        List<CashProductVo> cashproductList =  bizIntegralProductBiz.getCashProductList(projectId, startTime, endTime, searchVal, page, limit);
        int total =bizIntegralProductBiz.selectCashProductCount(projectId, startTime, endTime, searchVal);
        return new TableResultResponse<CashProductVo>(total, cashproductList);
    }

    /**
     * 查询商品兑换列表
     * @return
     */
    @RequestMapping(value = "/getResignCardList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询补签卡使用列表---PC端", notes = "查询补签卡使用列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="startTime",value="开始时间(yyyy-MM-dd)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endTime",value="结束时间(yyyy-MM-dd)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据兑换编号、商品名称、客户名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<CashProductVo> getResignCardList(String startTime, String endTime, String searchVal,
                                                                 Integer page, Integer limit){
        List<CashProductVo> cashproductList =  bizIntegralProductBiz.getResignCardList(startTime, endTime, searchVal, page, limit);
        int total =bizIntegralProductBiz.getResignCardCount(startTime, endTime, searchVal);
        return new TableResultResponse<CashProductVo>(total, cashproductList);
    }



    /**
     * 查询商品兑换详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCashProductInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品兑换详情---PC端", notes = "查询商品兑换详情---PC端",httpMethod = "GET")
    public TableResultResponse<CashProductVo> getCashProductInfo(@PathVariable String id){
        List<CashProductVo> info = bizIntegralProductBiz.getCashProductInfo(id);
        return new TableResultResponse<CashProductVo>(info.size(),info);
    }



    /**
     * 导出兑换列表excel
     * @return
     */
    @RequestMapping(value = "/exportCashListExcel",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出兑换列表excel---PC端", notes = "导出兑换列表excel---PC端",httpMethod = "POST")
    public ObjectRestResponse exportCashListExcel(@RequestBody @ApiParam DataExcel excel){
        return bizIntegralProductBiz.exportCashListExcel(excel.getProjectId(),excel.getStartTime(), excel.getEndTime(), excel.getSearchVal());
    }







}