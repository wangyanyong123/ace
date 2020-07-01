package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizOrderInvoice;
import com.github.wxiaoqi.security.jinmao.entity.BizProductOrder;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizOrderInvoiceMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductOrderMapper;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.UserInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultOrderList;
import com.github.wxiaoqi.security.jinmao.vo.productorder.in.QueryProductOrderIn;
import com.github.wxiaoqi.security.jinmao.vo.productorder.out.ProductOrderDetailVo;
import com.github.wxiaoqi.security.jinmao.vo.productorder.out.ProductOrderInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.productorder.out.ProductOrderListVo;
import com.github.wxiaoqi.security.jinmao.vo.productorder.out.ProductOrderOperationVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品订单表
 *
 * @author guohao
 * @Date 2020-04-27 18:57:18
 */
@Slf4j
@Service
public class BizProductOrderBiz extends BusinessBiz<BizProductOrderMapper,BizProductOrder> {

    @Resource
    private BaseTenantMapper baseTenantMapper;
    @Resource
    private OssExcelFeign ossExcelFeign;
    @Resource
    private BizOrderInvoiceMapper bizOrderInvoiceMapper;

    public List<ProductOrderListVo> findProductOrderList(QueryProductOrderIn queryProductOrderIn) {
        queryProductOrderIn.check();
        return this.mapper.selectProductOrderList(queryProductOrderIn);
    }
    public int countProductOrderList(QueryProductOrderIn queryProductOrderIn) {
        queryProductOrderIn.check();
        return this.mapper.countProductOrderList(queryProductOrderIn);
    }

    public ProductOrderInfoVo findProductOrderInfo(String orderId,String tenantId) {
        BizProductOrder productOrder = this.selectById(orderId);
        ProductOrderInfoVo orderInfoVo = new ProductOrderInfoVo();
        BeanUtils.copyProperties(productOrder,orderInfoVo);
        orderInfoVo.setOrderId(orderId);

        BizOrderInvoice orderInvoice = new BizOrderInvoice();
        orderInvoice.setOrderId(orderId);
        orderInvoice.setTenantId(tenantId);
        orderInvoice.setStatus(AceDictionary.DATA_STATUS_VALID);
        List<BizOrderInvoice> orderInvoices = bizOrderInvoiceMapper.select(orderInvoice);
        orderInfoVo.setInvoiceName("");
        orderInfoVo.setInvoiceType(AceDictionary.INVOICE_TYPE_NONE);
        orderInfoVo.setDutyCode("");
        if(CollectionUtils.isNotEmpty(orderInvoices)){
            orderInvoice = orderInvoices.get(0);
            orderInfoVo.setInvoiceName(orderInvoice.getInvoiceName());
            orderInfoVo.setInvoiceType(orderInvoice.getInvoiceType());
            orderInfoVo.setDutyCode(orderInvoice.getDutyCode());
        }

        List<ProductOrderDetailVo> detailVos = this.mapper.selectProductOrderDetailVo(orderId,tenantId);
        // 统计商品总金额
        BigDecimal productPrice = detailVos.stream().map(sku -> sku.getSalesPrice().multiply(BigDecimal.valueOf(sku.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        //优惠金额
        BigDecimal discountPrice = this.mapper.getDiscountPrice(orderId,tenantId);
        //运费
        BigDecimal expressPrice = this.mapper.getExpressPrice(orderId,tenantId);
        // 设置总价
        orderInfoVo.setProductPrice(productPrice.subtract(discountPrice).add(expressPrice));

        List<ProductOrderOperationVo> productOrderOperationVos = this.mapper.selectProductOrderOperationList(orderId);
        orderInfoVo.setProductList(detailVos);
        orderInfoVo.setOperationList(productOrderOperationVos);
        return orderInfoVo;
    }

    /**
     * 团购订单列表
     */
    public List<ResultOrderList> findOrderListByProductId(Integer orderType,String productId){

        return this.mapper.selectOrderListForProductId(orderType,productId);
    }


    /**
     * 导出的订单数据
     * @param queryProductOrderIn
     * @return
     * @throws Exception
     */
    public ObjectRestResponse exportProductOrderExcel(QueryProductOrderIn queryProductOrderIn) throws Exception {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if(queryProductOrderIn!=null && StringUtils.isEmpty(queryProductOrderIn.getTenantId())){
            UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
            if(userInfo == null){
                userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
            }
            if(userInfo!=null){
                if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                    //非平台用户需要传入公司Id
                    queryProductOrderIn.setTenantId(BaseContextHandler.getTenantID());
                }
            }else{
                objectRestResponse.setStatus(101);
                objectRestResponse.setMessage("非法用户");
                return objectRestResponse;
            }
        }
        List result = this.queryExportProductOrderList(queryProductOrderIn);
        if (result == null || result.size() == 0) {
            objectRestResponse.setStatus(102);
            objectRestResponse.setMessage("没有数据，导出失败！");
            return objectRestResponse;
        }
        String[] titles = {"序号","订单编号","商户订单号","订单类型","订单标题","下单时间","客户姓名","联系方式","项目","联系地址","商品总数","订单实际支付","优惠金额","运费","支付方式","订单状态","退款状态","评论状态","签收时间","退款金额","退款时间","商品名称","商品规格","商品数量","商品金额","供方","销售方式","订单来源","备注"};
        String[] keys = {"num","orderCode","actualId","orderType","title","createTime","contactName","contactTel","projectName","deliveryAddr","quantity","actualPrice","discountPrice","expressPrice","payType","orderStatus","refundStatus","commentStatus","confirmTime","applyPrice","applyTime","productName","specName","detailQuantity","salesPrice","supplier","salesWay","appType","remark"};
        String fileName = "订单.xlsx";
        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(result);
        excelInfoVo.setFileName(fileName);
        objectRestResponse = ossExcelFeign.uploadExcel(excelInfoVo);

        return objectRestResponse;
    }

    /**
     * 查询要导出的订单数据
     * @param queryProductOrderIn
     * @return
     */
    private List<Map> queryExportProductOrderList(QueryProductOrderIn queryProductOrderIn) {
        List<String> busIdList = new ArrayList<>();

        List<Map> woList = this.mapper.exportProductOrderExcel(queryProductOrderIn);

        List<Map> resList = new ArrayList<>();
        if(woList==null || woList.size()==0){
            return resList;
        }

        Map<String, String> projectIdNameMap = new HashMap<>();
        int size = woList.size();
        for (int i = 0; i < size; i++) {
            Map<String, Object> map = woList.get(i);
            map.put("num", i+1);
            String orderId = map.get("orderId").toString();

            map.put("orderStatus", AceDictionary.ORDER_STATUS.get(map.get("orderStatus")));
            map.put("refundStatus",AceDictionary.ORDER_REFUND_STATUS.get(map.get("refundStatus")));
            map.put("commentStatus",AceDictionary.PRODUCT_COMMENT.get(Integer.parseInt(map.get("commentStatus").toString())));
            map.put("appType",AceDictionary.APP_TYPE.get(Integer.parseInt(map.get("appType").toString())));

            List<Map> productList = this.mapper.queryProductOrderDetailInfo(orderId);
            if (ObjectUtils.isEmpty(productList)) {
                continue;
            }
            int productListSize = productList.size();
            for (int j = 0; j < productListSize; j++) {
                Map<String, Object> tmpProductMap = productList.get(j);
                Map<String, Object> tmpResMap = new HashMap<>();
                if (j == 0) {
                    tmpResMap.putAll(map);
                } else {
                    tmpResMap.put("num", i+1);
                }
                tmpResMap.putAll(tmpProductMap);
                resList.add(tmpResMap);
            }
        }
        return resList;
    }
}