package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizProductBiz;
import com.github.wxiaoqi.security.jinmao.feign.OrderEngineFeign;
import com.github.wxiaoqi.security.jinmao.vo.Product.InputParam.UpdateProductInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.InputParam.UpdateStatusParam;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.*;
import com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam.ResultRecommendInfo;
import com.github.wxiaoqi.security.jinmao.vo.reservat.out.ReservationList;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品表
 *
 * @author zxl
 * @Date 2018-12-05 09:58:43
 */
@RestController
@RequestMapping("web/bizProduct")
@CheckClientToken
@CheckUserToken
@Api(tags = "商品管理")
public class BizProductController{

    @Autowired
    private BizProductBiz bizProductBiz;
    @Autowired
    private OrderEngineFeign orderEngineFeign;


    /**
     * 查询商品列表
     * @return
     */
    @RequestMapping(value = "/getProductListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品列表---PC端", notes = "查询商品列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="busStatus",value="状态查询(1-待发布，2-待审核，3-已发布，4已驳回，5-已下架,0:全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据商品编码、商品名称、商户名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultProductVo> getProductListPc(String busStatus,String searchVal, Integer page, Integer limit){
        List<ResultProductVo> productList =  bizProductBiz.getProductList(busStatus,searchVal, page, limit);
        int total =bizProductBiz.selectProductCount(busStatus, searchVal);
        return new TableResultResponse<ResultProductVo>(total, productList);
    }

    /**
     * 查询可选的秒杀商品列表
     * @return
     */
    @RequestMapping(value = "/getSpikeProductListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询可选的秒杀商品列表---PC端", notes = "查询可选的秒杀商品列表---PC端",httpMethod = "GET")
    public TableResultResponse<ResultProductVo> getSpikeProductListPc(String searchVal){
        List<ResultProductVo> spikeNameList =  bizProductBiz.getSpikeProductListPc(searchVal);
        return new TableResultResponse<ResultProductVo>(spikeNameList.size(), spikeNameList);
    }

    /**
     * 查询商户列表下的商品分类名称列表
     * @return
     */
    @RequestMapping(value = "/getClassifyNameListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商户列表下的商品分类名称列表---PC端", notes = "查询商户列表下的商品分类名称列表---PC端",httpMethod = "GET")
    public TableResultResponse<ResultClassifyVo> getClassifyNameListPc(){
        List<ResultClassifyVo> classifyNameList =  bizProductBiz.getClassifyNameList();
        return new TableResultResponse<ResultClassifyVo>(classifyNameList.size(), classifyNameList);
    }

    /**
     * 查询业务列表
     * @return
     */
    @RequestMapping(value = "/getProductBusinessListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询所有启用的业务列表---PC端", notes = "查询所有启用的业务列表---PC端",httpMethod = "GET")
    public TableResultResponse<ResultProductBusinessVo> getProductBusinessListPc(){
        List<ResultProductBusinessVo> productBusinessList =  bizProductBiz.getProductBusinessList();
        return new TableResultResponse<ResultProductBusinessVo>(productBusinessList.size(), productBusinessList);
    }

    /**
     * 查询商品分类列表
     * @param busId
     * @return
     */
    @RequestMapping(value = "/getProductClassifyListPc/{busId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询所有业务下的商品分类列表---PC端", notes = "查询所有业务下的商品分类列表---PC端",httpMethod = "GET")
    public TableResultResponse<ResultClassifyVo> getProductClassifyListPc(@PathVariable String busId){
        List<ResultClassifyVo> classifyVoList = bizProductBiz.getProductClassifyList(busId);
        return new TableResultResponse<ResultClassifyVo>(classifyVoList.size(),classifyVoList);
    }



    /**
     * 根据商户id查询商户下的项目列表
     * @param
     * @return
     */
    @RequestMapping(value = "/getTenantProjectListPc", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据商户id查询商户下的项目列表---PC端", notes = "根据商户id查询商户下的项目列表---PC端",httpMethod = "GET")
    public TableResultResponse<ResultProjectVo> getTenantProjectListPc(){
        List<ResultProjectVo> projectList = bizProductBiz.getTenantProjectList();
        return new TableResultResponse<ResultProjectVo>(projectList.size(),projectList);
    }

    /**
     * 根据商户id查询商户下的业务列表
     * @param
     * @return
     */
    @RequestMapping(value = "/getTenantBusinessPc", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据商户id查询商户下的业务列表---PC端", notes = "根据商户id查询商户下的业务列表---PC端",httpMethod = "GET")
    public TableResultResponse<ResultProductBusinessVo> getTenantBusinessPc(){
        List<ResultProductBusinessVo> businessList = bizProductBiz.getTenantBusiness();
        return new TableResultResponse<ResultProductBusinessVo>(businessList.size(),businessList);
    }




    /**
     * 保存商品管理
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveProductPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存商品管理---PC端", notes = "保存商品管理---PC端",httpMethod = "POST")
    public ObjectRestResponse saveProductPc(@RequestBody @ApiParam UpdateProductInfo params){
        return bizProductBiz.saveProduct(params);
    }


    /**
     * 查询商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getProductInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品详情---PC端", notes = "查询商品详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultProductInfo> getProductInfoPc(@PathVariable String id){
        List<ResultProductInfo> info = bizProductBiz.getProductInfo(id);
        return new TableResultResponse<ResultProductInfo>(info.size(),info);
    }



    /**
     * 编辑商品信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateProductInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑商品信息---PC端", notes = "编辑商品信息---PC端",httpMethod = "POST")
    public ObjectRestResponse updateProductInfoPc(@RequestBody @ApiParam UpdateProductInfo params){
        return bizProductBiz.updateProduct(params);
    }


    /**
     * 商品操作
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateStatusPc", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "商品操作---PC端", notes = "商品操作---PC端",httpMethod = "POST")
    public ObjectRestResponse updateStatusPc(@RequestBody @ApiParam UpdateStatusParam param){
        String id = param.getId();
        String status = param.getStatus();
        return bizProductBiz.updateStatus(id, status);
    }



    /**
     * 查询商品审核列表
     * @return
     */
    @RequestMapping(value = "/getProductAuditListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品审核列表---PC端", notes = "查询商品审核列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="busStatus",value="状态查询(2-待审核，3-已发布，4已驳回，5-已下架,0:全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据商品编码、商品名称、商户名称码模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="classifyId",value="根据商品分类查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultProductAuditVo> getProductAuditListPc(String busStatus,String searchVal,String classifyId, Integer page, Integer limit){
        List<ResultProductAuditVo> productAuditList =  bizProductBiz.getProductAuditList(busStatus, searchVal, classifyId, page, limit);
        int total =bizProductBiz.selectProductAuditCount(busStatus, searchVal, classifyId);
        return new TableResultResponse<ResultProductAuditVo>(total, productAuditList);
    }


    /**
     * 查询团购活动列表
     * @return
     */
    @RequestMapping(value = "/getGroupActiveListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询团购活动列表---PC端", notes = "查询团购活动列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="productStatus",value="团购状态：1 未开始、2 进行中、3 已成团、4 已售罄、5 已结束、0:全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据商品编码、商品名称、商户名称码模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultGroupActiveVo> getGroupActiveListPc(String productStatus,String searchVal, Integer page, Integer limit){
        List<ResultGroupActiveVo> productAuditList =  bizProductBiz.getGroupActiveList(productStatus, searchVal, page, limit);
        int total =bizProductBiz.selectGroupActiveCount(productStatus, searchVal);
        return new TableResultResponse<ResultGroupActiveVo>(total, productAuditList);
    }



    /**
     * 查询团购活动详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getGroupActiveInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询团购活动详情---PC端", notes = "查询团购活动详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultGroupActiveInfoVo> getGroupActiveInfoPc(@PathVariable String id){
        List<ResultGroupActiveInfoVo> info = bizProductBiz.getGroupActiveInfoByPid(id);
        return new TableResultResponse<ResultGroupActiveInfoVo>(info.size(),info);
    }


    @Deprecated
    @RequestMapping(value = "/finishGroupProduct/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "团购手动成团---PC端", notes = "团购手动成团---PC端",httpMethod = "PUT")
    public ObjectRestResponse finishGroupProduct(@PathVariable String id){
        return orderEngineFeign.finishGroupProduct(id);
    }



    /**
     * 根据项目下业务的所有商品
     * @return
     */
    @RequestMapping(value = "/getProductListByBusIdPc", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据业务查询商品列表---PC端", notes = "根据业务查询商品列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="busId",value="业务ID",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据商品编码、商品名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")

    })
    public TableResultResponse<ResultRecommendInfo> getProductListByBusIdPc(String searchVal,Integer page,Integer limit) {
        List<ResultRecommendInfo> productList = bizProductBiz.getProductListByBusId(searchVal,page,limit);
        int total = bizProductBiz.getProductListCount(searchVal);
        return new TableResultResponse<ResultRecommendInfo>(total, productList);
    }

    /**
     * 根据项目下业务的所有商品
     * @return
     */
    @RequestMapping(value = "/getProductForAD", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据项目查询商品列表---PC端", notes = "根据项目查询商品列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="busId",value="业务ID",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据商品编码、商品名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")

    })
    public TableResultResponse<ResultRecommendInfo> getProductForAD(String projectId,String searchVal,Integer page,Integer limit,String type) {
        List<ResultRecommendInfo> productList = bizProductBiz.getProductForAD(projectId,searchVal,page,limit,type);
        int total = bizProductBiz.getProductForADTotal(projectId,searchVal,type);
        return new TableResultResponse<ResultRecommendInfo>(total, productList);
    }


    @RequestMapping(value = "/getReservationForAD",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询服务列表广告位---PC端", notes = "查询服务列表广告位---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="classifyId",value="分类id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="reservaStatus",value="服务状态(0-全部,1-待发布，2-待审核，3-已发布，4已驳回,5-已撤回）",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据编码、名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ReservationList>getReservationForAD(String projectId, String reservaStatus,
                                                                   String searchVal, Integer page, Integer limit){
        List<ReservationList> reservationList = bizProductBiz.getReservationForAD(projectId, reservaStatus, searchVal, page, limit);
        int total =bizProductBiz.getReservationForADCount(projectId,reservaStatus, searchVal);
        return new TableResultResponse<ReservationList>(total, reservationList);
    }
}