package com.github.wxiaoqi.security.app.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.in.CompanyProduct;
import com.github.wxiaoqi.security.api.vo.order.in.SubProduct;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.app.biz.BizProductBiz;
import com.github.wxiaoqi.security.app.biz.BizProductNewBiz;
import com.github.wxiaoqi.security.app.biz.BizSubBiz;
import com.github.wxiaoqi.security.app.biz.BizUserIntegralBiz;
import com.github.wxiaoqi.security.app.mapper.BizProductMapper;
import com.github.wxiaoqi.security.app.vo.product.out.CompanyVo;
import com.github.wxiaoqi.security.app.vo.product.out.GroupProductInfo;
import com.github.wxiaoqi.security.app.vo.product.out.ProductInfo;
import com.github.wxiaoqi.security.app.vo.product.out.UserCommentVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 商品表
 *
 * @author zxl
 * @Date 2018-12-10 16:38:03
 */
@RestController
@RequestMapping("bizAppProduct")
@CheckClientToken
@CheckUserToken
@Api(tags="APP优选商品")
public class BizAppProductController{

    @Autowired
    private BizProductBiz bizProductBiz;
    @Resource
    private BizProductMapper bizProductMapper;
    @Autowired
    private BizSubBiz bizSubBiz;
    @Autowired
    private BizUserIntegralBiz integralBiz;

    @Autowired
    private BizProductNewBiz bizProductNewBiz;

    /**
     * 查询优选商品下的商品分类列表
     * @return
     */
    @RequestMapping(value = "/getClassifyList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询优选商品下的商品分类列表---APP端", notes = "查询优选商品下的商品分类列表---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="busId",value="业务id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse getClassifyList(String busId){
        return  bizProductBiz.getClassifyList(busId);
    }


    /**
     * 查询商品分类下的上架商品列表
     * @param classifyId
     * @return
     */
    @RequestMapping(value = "/getProductList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品分类下的上架商品列表---APP端", notes = "查询商品分类下的上架商品列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="classifyId",value="分类id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse getProductList(String projectId,String classifyId, Integer page, Integer limit){
        return bizProductBiz.getProductList(projectId,classifyId, null,page,limit);
    }



    /**
     * 查询商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getProductInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品详情---APP端", notes = "查询商品详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="商品id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<ProductInfo> getProductInfo(String id){
        //完成任务-浏览优选商城任务
        integralBiz.finishDailyTask("task_103", BaseContextHandler.getUserID());
        return bizProductNewBiz.getProductInfo(id, false);
    }

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getShareProductInfo", method = RequestMethod.GET)
    @ResponseBody
    @IgnoreUserToken
    @ApiOperation(value = "查询商品详情---APP端", notes = "查询商品详情---APP端分享",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="商品id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<ProductInfo> getProductInfoByShare(String id){
        return bizProductNewBiz.getProductInfo(id, true);
    }


    /**
     * 查询拼团抢购下的商品列表
     * @return
     */
    @RequestMapping(value = "/getGroupProductList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询拼团抢购下的商品列表---APP端", notes = "查询拼团抢购下的商品列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="groupStatus",value="团购状态(1-未开始,2-进行中,3-已过期,4-已告罄)",dataType="Integer",paramType = "query",example="1"),
            @ApiImplicitParam(name="type",value="商品类型(2-拼团抢购,4-疯抢商品)",dataType="Integer",paramType = "query",example="1")
    })
    public ObjectRestResponse getGroupProductList(String projectId,Integer page, Integer limit,Integer groupStatus,Integer type){
        return  bizProductBiz.getGroupProductList(projectId,page, limit,groupStatus,type);
    }


    /**
     * 查询拼购商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getGroupProductInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询拼购商品详情---APP端", notes = "查询拼购商品详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="商品id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse getGroupProductInfo(String id){
        //完成任务-浏览优选商城任务
        integralBiz.finishDailyTask("task_103", BaseContextHandler.getUserID());
        return bizProductNewBiz.getGroupProductInfo(id);
    }



    /**
     * 查询疯抢商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getBerserkProductInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询疯抢商品详情---APP端", notes = "查询疯抢商品详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="商品id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse getBerserkProductInfo(String id){
        //完成任务-浏览优选商城任务
        integralBiz.finishDailyTask("task_103", BaseContextHandler.getUserID());
        return bizProductNewBiz.getBerserkProductInfo(id);
    }





    /**
     * 查询商品的用户评价
     * @param productId
     * @return
     */
    @RequestMapping(value = "/getUserCommentList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品的用户评价---APP端", notes = "查询商品的用户评价---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="productId",value="商品id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<UserCommentVo>> getUserCommentList(String productId, Integer page, Integer limit){
        return bizProductBiz.getUserCommentList(productId, page, limit);
    }


    /**
     * 查询商家信息
     * @param companyId
     * @return
     */
    @RequestMapping(value = "/getCompanyInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商家信息---APP端", notes = "查询商家信息---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="companyId",value="公司id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<CompanyVo> getCompanyInfo(String companyId){
        return bizProductBiz.getCompanyInfo(companyId);
    }


    /**
     * 疯抢商品产品下单
     * @return
     */
    @RequestMapping(value = "/buySeckillProduct" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "疯抢商品产品下单", notes = "疯抢商品产品下单",httpMethod = "POST")
    public ObjectRestResponse<BuyProductOutVo> buySeckillProduct(@RequestBody @ApiParam BuyProductInfo buyProductInfo) throws Exception{
        ObjectRestResponse result = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(buyProductInfo==null) {
            result.setStatus(101);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isAnyoneEmpty(buyProductInfo.getContactName(),buyProductInfo.getContactTel(),buyProductInfo.getAddr(),
                buyProductInfo.getProjectId(),buyProductInfo.getSource(),buyProductInfo.getProcCode())) {
            result.setStatus(101);
            result.setMessage("参数为空");
            return result;
        }
        List<CompanyProduct> companyProductList = buyProductInfo.getCompanyProductList();
        if(companyProductList==null || companyProductList.size()==0){
            result.setStatus(101);
            result.setMessage("参数为空");
            return result;
        }
        String busId = BusinessConstant.getSeckillBusId();
        //效验疯抢时间
        for(CompanyProduct temp : companyProductList){
            GroupProductInfo groupProductInfo = bizProductMapper.selectBerserkProductInfo(temp.getCompanyId());
            if(groupProductInfo != null){
                if(groupProductInfo.getBegTime()==null || groupProductInfo.getEndTime()==null){
                    result.setStatus(104);
                    result.setMessage("疯抢起始时间设置错误");
                    return result;
                }
                if (new Date().before(sdf.parse(groupProductInfo.getBegTime()))) {
                    result.setStatus(104);
                    result.setMessage("疯抢还未开始");
                    return result;
                }
                if (new Date().after(sdf.parse(groupProductInfo.getEndTime()))) {
                    result.setStatus(104);
                    result.setMessage("疯抢已结束");
                    return result;
                }
            }
            //效验疯抢商品的购买份数
            List<SubProduct> subProductList = temp.getSubProductList();
            if(subProductList != null && subProductList.size() > 1){
                result.setStatus(104);
                result.setMessage("此商品每人限购1份");
                return result;
            }
            for(SubProduct subProduct : subProductList){
                if(subProduct.getSubNum() > 1){
                    result.setStatus(104);
                    result.setMessage("此商品每人限购1份");
                    return result;
                }
                //直接判断库存是否够并减库存
                int stockResult = bizProductMapper.updateStockById(subProduct.getProductId(), 1);
                if (stockResult<=0) {
                    result.setStatus(104);
                    result.setMessage("商品已售罄");
                    return result;
                }
            }
        }
        return bizSubBiz.buyProduct(buyProductInfo,busId);
    }

}
