package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.logistics.OrderLogisticsVO;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BaseTenantProjectBiz;
import com.github.wxiaoqi.security.jinmao.biz.BizProductOrderBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizProductOrder;
import com.github.wxiaoqi.security.jinmao.feign.AceAppFeign;
import com.github.wxiaoqi.security.jinmao.feign.AceMerchantFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantMapper;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.UserInfo;
import com.github.wxiaoqi.security.jinmao.vo.productorder.in.QueryProductOrderIn;
import com.github.wxiaoqi.security.jinmao.vo.productorder.out.ProductOrderInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.productorder.out.ProductOrderListVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * 商品订单表
 *
 * @author guohao
 * @Date 2020-04-27 18:57:18
 */
@RestController
@RequestMapping("bizProductOrder")
@CheckClientToken
@CheckUserToken
public class BizProductOrderController extends BaseController<BizProductOrderBiz,BizProductOrder,String> {


    @Autowired
    private BizProductOrderBiz bizProductOrderBiz;

    @Resource
    private BaseTenantMapper baseTenantMapper;
    @Resource
    private BaseTenantProjectBiz baseTenantProjectBiz;

    @Resource
    private AceAppFeign aceAppFeign;
    @Resource
    private AceMerchantFeign aceMerchantFeign;


    @RequestMapping(value = "/search",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "商品订单查询列表---PC端", notes = "商品订单查询列表---PC端",httpMethod = "POST")
    public TableResultResponse<ProductOrderListVo> search(QueryProductOrderIn queryProductOrderIn) {
        queryProductOrderIn.check();
        if(StringUtils.isEmpty(queryProductOrderIn.getTenantId())){
            UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
            if(userInfo == null){
                userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
            }
            if(userInfo!=null){
                if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                    //非平台用户需要传入公司Id
                    queryProductOrderIn.setTenantId(BaseContextHandler.getTenantID());
                }else if(AceDictionary.TENANT_TYPE_CENTER.equals(userInfo.getTenantType())){
                    List<String> projectIdList = baseTenantProjectBiz.findProjectIdList(BaseContextHandler.getTenantID());
                    queryProductOrderIn.setProjectId(projectIdList);
                }
            }else{
                TableResultResponse restResponse = new TableResultResponse();
                restResponse.setStatus(101);
                restResponse.setMessage("非法用户");
                return restResponse;
            }
        }
        int count = bizProductOrderBiz.countProductOrderList(queryProductOrderIn);
        List<ProductOrderListVo> productOrderList;
        if(count > 0){
            productOrderList = bizProductOrderBiz.findProductOrderList(queryProductOrderIn);
        }else{
            productOrderList = Collections.emptyList();
        }
        return new TableResultResponse<>(count, productOrderList);
    }

    @RequestMapping(value = "/findProductOrderInfo",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品订单详情---PC端", notes = "查询商品订单详情---PC端",httpMethod = "GET")
    public ObjectRestResponse<ProductOrderInfoVo> findProductOrderInfo(@RequestParam String orderId) {
        String tenantId = null;
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo == null){
            userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
        }
        if(userInfo!=null){
            if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                //非平台用户需要传入公司Id
                tenantId = BaseContextHandler.getTenantID();
            }
        }else{
            ObjectRestResponse objectRestResponse = new ObjectRestResponse();
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("非法用户");
            return objectRestResponse;
        }
        ProductOrderInfoVo productOrderInfoVo = bizProductOrderBiz.findProductOrderInfo(orderId,tenantId);
        return ObjectRestResponse.ok(productOrderInfoVo);
    }

    @RequestMapping(value = "/exportProductOrderExcel" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "Web后台导出商品订单", notes = "Web后台导出商品订单",httpMethod = "POST")
    public ObjectRestResponse exportProductOrderExcel(QueryProductOrderIn queryProductOrderIn) throws Exception {
        return this.baseBiz.exportProductOrderExcel(queryProductOrderIn);
    }

    /**
     * 取消订单
     * @param orderId
     * @param remark
     * @return
     */
    @RequestMapping(value = "/cancelOrder" ,method = RequestMethod.GET)
    public ObjectRestResponse cancelOrder(String orderId,String remark){
        BizProductOrder productOrder = this.baseBiz.selectById(orderId);
        if(productOrder.getOrderStatus().equals(AceDictionary.ORDER_STATUS_W_PAY) && productOrder.getRefundStatus().equals(AceDictionary.ORDER_REFUND_STATUS_NONE)){
            return aceAppFeign.cancelOrder(orderId);

        }
        return aceAppFeign.applyRefund(orderId,remark);
    }

    /**
     * 申请退款（和取消合并了）
     * @param orderId
     * @param remark
     * @return
     */
    @RequestMapping(value = "/applyRefund" ,method = RequestMethod.GET)
    public ObjectRestResponse applyRefund(String orderId,String remark){
        return aceAppFeign.applyRefund(orderId,remark);
    }

    /**
     * 签收
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/signOrder" ,method = RequestMethod.GET)
    @ApiOperation(value = "商品签收", notes = "商品签收",httpMethod = "GET")
    public ObjectRestResponse signOrder(String orderId){
        return aceAppFeign.signOrder(orderId);
    }

    /**
     * 商品发货
     * @param orderLogisticsVO
     * @return
     */
    @PostMapping("sendProduct")
    @ApiOperation(value = "商品发货", notes = "商品发货",httpMethod = "POST")
    public ObjectRestResponse sendProduct(@RequestBody OrderLogisticsVO orderLogisticsVO){
        return aceMerchantFeign.sendProduct(orderLogisticsVO);
    }

    @IgnoreUserToken
    @IgnoreClientToken
    @GetMapping("queryExpressList")
    @ApiOperation(value = "获取快递公司", notes = "获取快递公司",httpMethod = "GET")
    public ObjectRestResponse queryExpressCompanyList(){
        return aceMerchantFeign.queryExpressList();
    }

    /**
     * 更新物流信息
     */
    @PostMapping("/updateOrderLogistics")
    @ApiOperation(value = "更新物流信息", notes = "更新物流信息",httpMethod = "POST")
    public ObjectRestResponse saveOrUpdateOrderLogistics(@RequestBody @Valid OrderLogisticsVO orderLogisticsVO){
        return aceMerchantFeign.saveOrUpdateOrderLogistics(orderLogisticsVO);
    }

    /**
     * 完成后申请退款
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/returnAudit" ,method = RequestMethod.GET)
    @ApiOperation(value = "完成后申请退款", notes = "完成后申请退款",httpMethod = "GET")
    public ObjectRestResponse returnAudit(String orderId){
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo!=null){
            if("3".equals(userInfo.getTenantType())) {
                return aceAppFeign.productOrderRefundAudit(orderId);
            }
        }
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.setStatus(101);
        objectRestResponse.setMessage("非法用户");
        return objectRestResponse;
    }

}
