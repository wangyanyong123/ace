package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizReservationOrder;
import com.github.wxiaoqi.security.jinmao.entity.BizReservationOrderDetail;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizReservationOrderMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizReservationOrderOperationRecordMapper;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.UserInfo;
import com.github.wxiaoqi.security.jinmao.vo.reservation.order.in.ReservationOrderQuery;
import com.github.wxiaoqi.security.jinmao.vo.reservation.order.out.ReservationOrderDetailInfo;
import com.github.wxiaoqi.security.jinmao.vo.reservation.order.out.ReservationOrderInfoVO;
import com.github.wxiaoqi.security.jinmao.vo.reservation.order.out.ReservationOrderOperationRecord;
import com.github.wxiaoqi.security.jinmao.vo.reservation.order.out.ReservationOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 预约服务订单表
 *
 * @author wangyanyong
 * @Date 2020-04-24 13:13:42
 */
@Slf4j
@Service
public class BizReservationOrderBiz extends BusinessBiz<BizReservationOrderMapper, BizReservationOrder> {

    @Resource
    private BaseTenantMapper baseTenantMapper;

    @Resource
    private BizReservationOrderDetailBiz bizReservationOrderDetailBiz;

    @Resource
    private BizReservationOrderOperationRecordMapper bizReservationOrderOperationRecordMapper;

    @Resource
    private OssExcelFeign ossExcelFeign;

    @Autowired
    private BaseTenantProjectBiz baseTenantProjectBiz;

    /**
     * Web后台查询预约服务订单列表
     * @param reservationOrderQuery
     * @return
     */
    public TableResultResponse queryReservationOrderPage(ReservationOrderQuery reservationOrderQuery){
        TableResultResponse tableResultResponse = new TableResultResponse();
        if(!ObjectUtils.isEmpty(reservationOrderQuery) && StringUtils.isEmpty(reservationOrderQuery.getTenantId())){
            UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
            if(userInfo == null){
                userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
            }
            if(userInfo!=null){
                if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                    //非平台用户需要传入公司Id
                    reservationOrderQuery.setTenantId(BaseContextHandler.getTenantID());
                }else if(AceDictionary.TENANT_TYPE_CENTER.equals(userInfo.getTenantType())){
                    List<String> projectIdList = baseTenantProjectBiz.findProjectIdList(BaseContextHandler.getTenantID());
                    reservationOrderQuery.setProjectId(projectIdList);
                }
            }else{
                tableResultResponse.setStatus(101);
                tableResultResponse.setMessage("非法用户");
                return tableResultResponse;
            }
        }

        // 请求参数处理
        this.requestParamHandle(reservationOrderQuery);
        List<ReservationOrderVO> reservationOrderVOList = this.mapper.queryReservationOrderPage(reservationOrderQuery);
        int total = this.mapper.queryReservationOrderCount(reservationOrderQuery);
        TableResultResponse.TableData data = tableResultResponse.new TableData(total, reservationOrderVOList);
        tableResultResponse.setData(data);
        return tableResultResponse;
    }


    // 请求参数处理
    private void requestParamHandle(ReservationOrderQuery reservationOrderQuery){
        int page = reservationOrderQuery.getPage();
        int limit = reservationOrderQuery.getLimit();
        if (page<0) {
            page = 1;
        }
        if (limit<0) {
            limit = 10;
        }
        //分页
        int startIndex = (page - 1) * limit;
        if (page != 0 && limit != 0) {
            reservationOrderQuery.setPage(startIndex);
            reservationOrderQuery.setLimit(limit);
        }
        // 订单状态
        Integer orderStatus = null;
        // 退款状态
        Integer refundStatus = null;
        // 评论状态
        Integer commentStatus = null;
        if(!ObjectUtils.isEmpty(reservationOrderQuery.getOrderStatus())){
            if(AceDictionary.ORDER_STATUS_APPLY_REFUND.equals(reservationOrderQuery.getOrderStatus())){
                refundStatus = AceDictionary.ORDER_REFUND_STATUS_APPLY;
            }else if (AceDictionary.ORDER_STATUS_W_COMMENT.equals(reservationOrderQuery.getOrderStatus())){
                commentStatus = AceDictionary.PRODUCT_COMMENT_NONE;
                orderStatus = AceDictionary.ORDER_STATUS_COM;
            }else {
                orderStatus = reservationOrderQuery.getOrderStatus();
            }
        }
        reservationOrderQuery.setOrderStatus(orderStatus);
        reservationOrderQuery.setRefundStatus(refundStatus);
        reservationOrderQuery.setCommentStatus(commentStatus);
    }


    /**
     * 查询服务订单详情
     * @param orderId
     * @return
     */
    public ObjectRestResponse queryReservationOrderInfo(String orderId) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        ReservationOrderInfoVO reservationOrderInfoVO = new ReservationOrderInfoVO();
        //1.获取订单详情
        BizReservationOrder bizReservationOrder = this.selectById(orderId);
        if(ObjectUtils.isEmpty(bizReservationOrder)){
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("该ID获取不到订单详情信息");
            return objectRestResponse;
        }
        BeanUtils.copyProperties(bizReservationOrder,reservationOrderInfoVO);
        //2.获取订单产品信息
        BizReservationOrderDetail bizReservationOrderDetail = new BizReservationOrderDetail();
        bizReservationOrderDetail.setOrderId(orderId);
        bizReservationOrderDetail = bizReservationOrderDetailBiz.selectOne(bizReservationOrderDetail);
        if(ObjectUtils.isEmpty(bizReservationOrderDetail)){
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("该ID获取不到订单详情信息");
            return objectRestResponse;
        }

        ReservationOrderDetailInfo reservationOrderDetailInfo = new ReservationOrderDetailInfo();
        BeanUtils.copyProperties(bizReservationOrderDetail,reservationOrderDetailInfo);
        reservationOrderInfoVO.setReservationOrderDetailInfo(reservationOrderDetailInfo);

        //4.获取操作流水日志
        List<ReservationOrderOperationRecord> reservationOrderOperationRecordList = bizReservationOrderOperationRecordMapper.queryReservationOrderOperationRecord(orderId);
        if(CollectionUtils.isEmpty(reservationOrderOperationRecordList)){
            reservationOrderOperationRecordList = Collections.EMPTY_LIST;
        }
        reservationOrderInfoVO.setReservationOrderOperationRecordList(reservationOrderOperationRecordList);

        return objectRestResponse.data(reservationOrderInfoVO);
    }

    /**
     * 查询要导出的订单数据
     * @param reservationOrderQuery
     * @return
     * @throws Exception
     */
    public ObjectRestResponse exportReservationOrderExcel(ReservationOrderQuery reservationOrderQuery) throws Exception {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if(!ObjectUtils.isEmpty(reservationOrderQuery) && StringUtils.isEmpty(reservationOrderQuery.getTenantId())){
            UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
            if(userInfo == null){
                userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
            }
            if(userInfo!=null){
                if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                    //非平台用户需要传入公司Id
                    reservationOrderQuery.setTenantId(BaseContextHandler.getTenantID());
                }
            }else{
                objectRestResponse.setStatus(101);
                objectRestResponse.setMessage("非法用户");
                return objectRestResponse;
            }
        }

        List<Map<String, Object>> result = this.mapper.exportReservationOrder(reservationOrderQuery);
        if (CollectionUtils.isEmpty(result)) {
            objectRestResponse.setStatus(102);
            objectRestResponse.setMessage("没有数据，导出失败！");
            return objectRestResponse;
        }
        AtomicInteger index=new AtomicInteger(1);
        result.forEach(map->{
            map.put("num", index.getAndIncrement());
            map.put("orderStatus",AceDictionary.ORDER_STATUS.get(map.get("orderStatus")));
            map.put("refundStatus",AceDictionary.ORDER_REFUND_STATUS.get(map.get("refundStatus")));
            map.put("commentStatus",AceDictionary.PRODUCT_COMMENT.get(map.get("commentStatus")));
            map.put("appType",AceDictionary.APP_TYPE.get(Integer.parseInt(map.get("appType").toString())));
        });

        String[] titles = {"序号","订单编号","商户订单号","订单类型","订单标题","下单时间","服务时间","客户姓名","联系方式","项目","联系地址","订单实际支付金额","优惠金额","支付方式","订单状态","退款状态","评论状态","签收时间","退款金额","退款时间","服务名称","服务规格","服务数量","服务金额","销售方式","供方","订单来源","备注"};
        String[] keys = {"num","orderCode","actualId","orderType","title","createTime","reservationTime","contactName","contactTel","projectName","deliveryAddr","actualPrice","discountPrice","payType","orderStatus","refundStatus","commentStatus","confirmTime","applyPrice","applyTime","productName","specName","quantity","salesPrice","salesWay","supplier","appType","remark"};
        String fileName = "订单.xlsx";
        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(result);
        excelInfoVo.setFileName(fileName);
        objectRestResponse = ossExcelFeign.uploadExcel(excelInfoVo);
        return objectRestResponse;
    }

}
