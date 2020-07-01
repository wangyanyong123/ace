package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizProductBiz;
import com.github.wxiaoqi.security.app.biz.BizProductNewBiz;
import com.github.wxiaoqi.security.app.vo.product.out.CompanyVo;
import com.github.wxiaoqi.security.app.vo.product.out.ProductInfo;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.MunicipalityUtil;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 商品相关
 */
@RestController
@RequestMapping("bizProduct")
@IgnoreUserToken
@IgnoreClientToken
@Api(tags = "商品相关 -- 微信端")
public class BizProductController {

    @Autowired
    private BizProductBiz bizProductBiz;
    @Autowired
    private BizProductNewBiz bizProductNewBiz;


    /**
     * 查询商品分类下的上架商品列表
     */
    @RequestMapping(value = "/getProductList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品分类下的上架商品列表", notes = "查询商品分类下的上架商品列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "classifyId", value = "分类id", dataType = "String", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "Integer", paramType = "query", example = "1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse getProductList(String projectId, String classifyId,
                                              Integer page, Integer limit,HttpServletRequest request) {
        String cityCode = RequestHeaderUtil.getCityCode(request);
        List<String> cityCodeList = MunicipalityUtil.getDeliveryCityCodeList(cityCode);
        return bizProductBiz.getProductList(projectId, classifyId, cityCodeList,page, limit);
    }


    /**
     * 查询商品详情
     */
    @RequestMapping(value = "/getProductInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "商品id", dataType = "String", paramType = "query", required = true)
    public ObjectRestResponse<ProductInfo> getProductInfo(@RequestParam String id,boolean isShare) {
        return bizProductNewBiz.getProductInfo(id, isShare);
    }

    /**
     * 查询拼团抢购下的商品列表
     */
    @RequestMapping(value = "/getGroupProductList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询拼团抢购下的商品列表---APP端", notes = "查询拼团抢购下的商品列表---APP端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "Integer", paramType = "query", example = "1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="groupStatus",value="团购状态(1-未开始,2-进行中,3-已过期,4-已告罄)",dataType="Integer",paramType = "query",example="1"),
            @ApiImplicitParam(name="type",value="商品类型(2-拼团抢购,4-疯抢商品)",dataType="Integer",paramType = "query",example="1")
    })
    public ObjectRestResponse getGroupProductList(String projectId,Integer groupStatus,
                                                  Integer page, Integer limit,Integer type,
                                                  HttpServletRequest request) {
        String cityCode = RequestHeaderUtil.getCityCode(request);
        List<String> cityCodeList = MunicipalityUtil.getDeliveryCityCodeList(cityCode);
        return bizProductNewBiz.getGroupProductList(projectId, page, limit,groupStatus,type,cityCodeList);
    }


    /**
     * 查询拼购商品详情
     */
    @RequestMapping(value = "/getGroupProductInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询拼购商品详情", notes = "查询拼购商品详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "商品id", dataType = "String", paramType = "query", required = true)
    public ObjectRestResponse getGroupProductInfo(@RequestParam String id) {
        return bizProductNewBiz.getGroupProductInfo(id);
    }


    /**
     * 查询疯抢商品详情
     */
    @RequestMapping(value = "/getBerserkProductInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询疯抢商品详情", notes = "查询疯抢商品详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "商品id", dataType = "String", paramType = "query", example = "4")
    public ObjectRestResponse getBerserkProductInfo(String id) {
        return bizProductNewBiz.getBerserkProductInfo(id);
    }


    /**
     * 查询商家信息
     */
    @RequestMapping(value = "/getCompanyInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商家信息", notes = "查询商家信息", httpMethod = "GET")
    @ApiImplicitParam(name = "companyId", value = "公司id", dataType = "String", paramType = "query",required = true)
    public ObjectRestResponse<CompanyVo> getCompanyInfo(String companyId) {
        return bizProductNewBiz.getCompanyInfo(companyId);
    }


}
