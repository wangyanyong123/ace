package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizProductRecommendBiz;
import com.github.wxiaoqi.security.app.config.AppDefaultConfig;
import com.github.wxiaoqi.security.app.vo.recommend.out.RecommendProductVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.MunicipalityUtil;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品推荐表
 *
 * @author zxl
 * @Date 2018-12-10 10:10:55
 */
@RestController
@RequestMapping("bizProductRecommend")
@CheckClientToken
@CheckUserToken
@Api(tags = "小茂推荐")
public class BizProductRecommendController {

    @Autowired
    private BizProductRecommendBiz bizProductRecommendBiz;


    @RequestMapping(value = "/getRecommendProductApp", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询小茂推荐列表---App端", notes = "查询小茂推荐列表---App端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "社区ID", dataType = "String", paramType = "query", example = "1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "Integer", paramType = "query", example = "10")
    })
    public ObjectRestResponse<RecommendProductVo> getRecommendProductApp(String projectId, Integer page, Integer limit) {
        return bizProductRecommendBiz.getRecommendProduct(projectId, page, limit);
    }

    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/getRecommendProductForHome", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询首页推荐列表", notes = "查询首页推荐列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "社区ID", dataType = "String", paramType = "query", example = "1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "Integer", paramType = "query", example = "10")
    })
    public ObjectRestResponse<RecommendProductVo> getRecommendProductForHome(String projectId, String busId, Integer productType,
                                                                             Integer page, Integer limit, HttpServletRequest request) {

        List<String> cityCodeList = MunicipalityUtil.getDeliveryCityCodeList(RequestHeaderUtil.getCityCode(request));
        List<RecommendProductVo> recommendProduct = bizProductRecommendBiz.getRecommendProduct(projectId, busId, productType,
                cityCodeList,page, limit);
        int platformIntValue = RequestHeaderUtil.getPlatformIntValue(request);
        if(AceDictionary.APP_TYPE_MP == platformIntValue || AceDictionary.APP_TYPE_H5 ==platformIntValue){
            recommendProduct.forEach(item->{
                if(StringUtils.isNotEmpty(item.getRecommendImgUrl())){
                    item.setProductImage(item.getRecommendImgUrl());
                }
            });
        }
        return ObjectRestResponse.ok(recommendProduct);
    }
}
