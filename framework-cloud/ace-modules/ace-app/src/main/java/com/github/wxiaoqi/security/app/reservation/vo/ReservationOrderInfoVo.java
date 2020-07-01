package com.github.wxiaoqi.security.app.reservation.vo;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.app.vo.order.out.*;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品订单详情
 */
@Data
public class ReservationOrderInfoVo implements Serializable {

    private static final long serialVersionUID = 1426711899219869759L;
    //订单Id
    private String id;

    //父订单id，与支付记录一致，与order_id 相同表示没有拆单
    private String parentId;

    //订单编号
    private String orderCode;

    // 项目Id
    private String projectId;

    // 服务ID
    private String productId;

    //订单状态 5：待支付，10：待受理；15：待上门；20:服务中；25：待评价；30：已完成；35：退款中 ；40：已关闭；
    private Integer orderStatus;
    private String orderStatusStr;
    // 评论状态
    private Integer commentStatus;
    private String commentStatusStr;
    // 退款状态
    private Integer refundStatus;
    private String refundStatusStr;

    //订单标题
    private String title;

    //订单描述
    private String description;

    //商品总金额
    private BigDecimal productPrice;

    //预约服务时间
    private Date reservationTime;

    //实收金额=商品总金额-优惠金额
    private BigDecimal actualPrice;

    //优惠金额
    private BigDecimal discountPrice;

    //商品总件数
    private Integer quantity;

    //发票类型(0-不开发票,1-个人,2-公司)
    private Integer invoiceType;

    //发票名称
    private String invoiceName;

    //税号
    private String dutyCode;

    //收获联系人
    private String contactName;

    //收货人联系电话
    private String contactTel;

    //收货地址
    private String deliveryAddr;

    //商户联系电话
    private String tenantTel;

    //商户绑定的隐私电话
    private String bindTel;

    //是否绑定：0-解绑；1-绑定；
    private Integer bindFlag;

    //备注
    private String remark;
    // 支付ID
    private String actualId;

    // 用户名称
    private String userName;

    //图片id,多张图片逗号分隔
    private String imgId;

    // 下单时间
    private Date createTime;

    //单位
    private String unit;

    //图片路径
    @ApiModelProperty(value = "图片路径(如果有多张，指显示图片列表)")
    private List<String> imgList;

    private ReservationOrderWaiterVO reservationOrderWaiterVO;


    public List<ImgInfo> getImgList(){
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(imgId)){
            String[] imgArrayIds =new String[] { imgId };
            if(imgId.indexOf(",")!=-1){
                imgArrayIds=imgId.split(",");
            }
            for (String url : imgArrayIds){
                ImgInfo imgInfo = new ImgInfo();
                imgInfo.setUrl(url);
                list.add(imgInfo);
            }
        }
        return list;
    }
    public String getOrderStatusStr(){
        return AceDictionary.ORDER_STATUS.get(orderStatus);

    }


    public String getCommentStatusStr(){
        return AceDictionary.PRODUCT_COMMENT.get(commentStatus);

    }

    public String getRefundStatusStr(){
        return AceDictionary.ORDER_REFUND_STATUS.get(refundStatus);

    }

}
