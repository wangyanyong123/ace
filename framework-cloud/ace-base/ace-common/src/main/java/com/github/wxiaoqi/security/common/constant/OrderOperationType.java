package com.github.wxiaoqi.security.common.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 订单操作类型
 */
public enum  OrderOperationType {

    CreateOrder(5,"已下单","请支付。"),
    PaySuccess(10,"支付成功","已支付，请等待商家处理。"),
    WaitingGroupComplete(15,"支付成功","已支付，请等待团购成功。"),
    GroupComplete(16,"团购成功","团购成功，请等待商家处理。"),
    SendOrder(20,"已发货","商家已发货，快递公司：{0}，快递单号：{1}。我们正在努力接入物流信息跟踪，给您造成不便敬请谅解。"),
    SignOrder(25,"已收货","已收货，请您对我们的服务进行评价。"),
    AutoSignOrder(26,"已收货","已自动收货，如您未收到商品，请及时与我们联系。请您对我们的服务进行评价。"),
    CommentOrder(30,"已评价","评分：{0}星，内容：{1}。"),
    CancelOrderUnPay(40,"已取消","订单已取消。如有退款，款项将在7个工作日内退回到您的支付账户中（如有延迟请联系支付卡所在银行)。"),
    CancelOrderUnSend(45,"退款审核","您的退款已发起，请等待商家处理。"),
    CancelReject(46,"退款驳回","您的退款申请被拒绝。"),
    ApplyRefund(50,"申请售后","售后申请审核中，请等待商家处理。"),
    RefundReject(55,"售后驳回","您的售后申请被拒绝。"),
    RefundSuccess(60,"已退款","支付款已退还，请注意查收。"),
    // 服务订单
    Accepted(18,"已受理","服务人员{0}将于{1}上门服务，联系电话：{2}。"),
    OverService(27,"服务完成","服务完成，请您对我们的服务进行评价。"),
    ;
    private Integer stepStatus;
    private String currStep;
    private String description;

    OrderOperationType(Integer stepStatus, String currStep, String description) {
        this.stepStatus = stepStatus;
        this.currStep = currStep;
        this.description = description;
    }

    /**
     * 格式化需要参数的操作描述信息
     * @param orderOperationType
     * @param param
     * @return
     */
    public static String getFormatDescription(OrderOperationType orderOperationType,String...param){
        String description = orderOperationType.getDescription();
        Matcher m= Pattern.compile("\\{(\\d)\\}").matcher(description);
        while(m.find()){
            description=description.replace(m.group(),param[Integer.parseInt(m.group(1))]);
        }
        return description;
    }

    public Integer getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(Integer stepStatus) {
        this.stepStatus = stepStatus;
    }

    public String getCurrStep() {
        return currStep;
    }

    public void setCurrStep(String currStep) {
        this.currStep = currStep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
