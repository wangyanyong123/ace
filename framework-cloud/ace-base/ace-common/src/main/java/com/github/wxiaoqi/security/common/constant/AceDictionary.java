package com.github.wxiaoqi.security.common.constant;

import org.omg.CORBA.INTERNAL;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典类
 * @author: guohao
 * @create: 2020-04-19 14:47
 **/
public interface AceDictionary {

    /**
     * 数据类型
     */
    String DATA_STATUS_INVALID = "0";
    String DATA_STATUS_VALID = "1";
    Map<String, String> DATA_STATUS = new HashMap<String, String>() {

        private static final long serialVersionUID = 5180277603004149016L;

        {
            put(DATA_STATUS_INVALID, "无效");
            put(DATA_STATUS_VALID, "有效");
        }
    };

    /**
     * 发布状态
     */
    Integer PUBLISH_STATUS_NONE = 0;
    Integer PUBLISH_STATUS_YES = 1;
    Map<Integer, String> PUBLISH_STATUS = new HashMap<Integer, String>() {


        private static final long serialVersionUID = 3372924692751219654L;

        {
            put(PUBLISH_STATUS_NONE, "未发布");
            put(PUBLISH_STATUS_YES, "已发布");
        }
    };
    /**
     * 订单优惠类型
     */
    Integer ORDER_DISCOUNT_TYPE_COUPON = 1;
    Map<Integer, String> ORDER_DISCOUNT_TYPE = new HashMap<Integer, String>() {
        private static final long serialVersionUID = 2692541825968061669L;

        {
            put(ORDER_DISCOUNT_TYPE_COUPON, "优惠券");
        }
    };

    /**
     * 与订单对应类型 1：订单。2：订单明细
     */
    Integer ORDER_RELATION_TYPE_O = 1;
    Integer ORDER_RELATION_TYPE_OD = 2;
    Map<Integer, String> ORDER_RELATION_TYPE = new HashMap<Integer, String>() {

        private static final long serialVersionUID = 3918668937458411370L;

        {
            put(ORDER_RELATION_TYPE_O, "订单");
            put(ORDER_RELATION_TYPE_OD, "订单明细");
        }
    };

    /**
     * 订单状态 5：待支付，6：拼团中， 10：待发货，15：部分发货20：待签收,35：已完成；45：已取消；
     *
     * 以下两个只用于订单检索，不参与订单状态转换
     * 待评价：30， 退款中：40 退款完成：50
     */
    Integer ORDER_STATUS_W_PAY = 5;
    Integer ORDER_STATUS_GROUPING = 6;
    Integer ORDER_STATUS_W_SEND = 10;
    Integer ORDER_STATUS_SEND_PART = 15;
    Integer ORDER_STATUS_W_SIGN = 20;
    Integer ORDER_STATUS_W_COMMENT = 30;
    Integer ORDER_STATUS_COM = 35;
    Integer ORDER_STATUS_APPLY_REFUND = 40;
    Integer ORDER_STATUS_CAN = 45;
    Integer ORDER_STATUS_REFUND_COM = 50;

    // 服务订单状态
    Integer RESERVATION_ORDER_STATUS_ACCEPT = 110;
    Integer RESERVATION_ORDER_STATUS_TO = 115;

    Map<Integer, String> ORDER_STATUS = new HashMap<Integer, String>() {

        private static final long serialVersionUID = 3372924692751219654L;

        {
            put(ORDER_STATUS_W_PAY, "待支付");
            put(ORDER_STATUS_GROUPING, "拼团中");
            put(ORDER_STATUS_W_SEND, "待发货");
            put(ORDER_STATUS_SEND_PART, "部分发货");
            put(ORDER_STATUS_W_SIGN, "待收货");
            put(ORDER_STATUS_COM, "已完成");
            put(ORDER_STATUS_APPLY_REFUND, "退款中");
            put(ORDER_STATUS_CAN, "已关闭");
            put(ORDER_STATUS_W_COMMENT, "待评价");
            // 服务订单状态
            put(RESERVATION_ORDER_STATUS_ACCEPT, "待受理");
            put(RESERVATION_ORDER_STATUS_TO, "待上门");
        }
    };

    /**
     * 订单优惠类型
     */
    Integer ORDER_REFUND_STATUS_NONE = 0;
    Integer ORDER_REFUND_STATUS_APPLY = 10;
    Integer ORDER_REFUND_STATUS_PART = 15;
    Integer ORDER_REFUND_STATUS_COM = 20;
    Map<Integer, String> ORDER_REFUND_STATUS = new HashMap<Integer, String>() {

        private static final long serialVersionUID = 3918668937458411370L;

        {
            put(ORDER_REFUND_STATUS_NONE, "无退款");
            put(ORDER_REFUND_STATUS_APPLY, "退款中");
            put(ORDER_REFUND_STATUS_PART, "部分退款");
            put(ORDER_REFUND_STATUS_COM, "退款完成");
        }
    };

    /**
     * 商品评论状态类型
     */
    Integer PRODUCT_COMMENT_NONE = 0;
    Integer PRODUCT_COMMENT_COM = 1;
    Map<Integer, String> PRODUCT_COMMENT = new HashMap<Integer, String>() {

        private static final long serialVersionUID = -8066904339688118143L;

        {
            put(PRODUCT_COMMENT_NONE, "未评论");
            put(PRODUCT_COMMENT_COM, "已评论");
        }
    };

    /**
     *
     * 订单类型 1：普通订单；2：团购订单。3：疯抢订单， 4：秒杀订单
     */
    Integer ORDER_TYPE_ORDINARY = 1;
    Integer ORDER_TYPE_GROUP = 2;
    Integer ORDER_TYPE_BERSERK = 3;
    Integer ORDER_TYPE_SPIKE = 4;
    Map<Integer, String> ORDER_TYPE = new HashMap<Integer, String>() {

        private static final long serialVersionUID = 8918707984767183827L;
        {
            put(ORDER_TYPE_ORDINARY, "普通订单");
            put(ORDER_TYPE_GROUP, "团购订单");
            put(ORDER_TYPE_BERSERK, "疯抢订单");
            put(ORDER_TYPE_SPIKE, "秒杀订单");
        }
    };

    /**
     *
     * 应用类型 1：ios， 2：android， 3：h5， 4：mp
     */
    Integer APP_TYPE_IOS = 1;
    Integer APP_TYPE_ANDROID = 2;
    Integer APP_TYPE_H5 = 3;
    Integer APP_TYPE_MP = 4;
    Map<Integer, String> APP_TYPE = new HashMap<Integer, String>() {

        private static final long serialVersionUID = -7475856117601601223L;

        {
            put(APP_TYPE_IOS, "APP苹果");
            put(APP_TYPE_ANDROID, "APP安卓");
            put(APP_TYPE_H5, "H5");
            put(APP_TYPE_MP, "微信小程序");
        }
    };
 /**
     *
     *  与订单对应类型 1：订单。2：订单明细
     */
    Integer DISCOUNT_RELATION_TYPE_ORDER = 1;
    Integer DISCOUNT_RELATION_TYPE_DETAIL = 2;
    Map<Integer, String> DISCOUNT_RELATION_TYPE = new HashMap<Integer, String>() {
        private static final long serialVersionUID = 494666947368128400L;

        {
            put(DISCOUNT_RELATION_TYPE_ORDER, "订单");
            put(DISCOUNT_RELATION_TYPE_DETAIL, "订单明细");
        }
    };
    /**
     *
     *  业务类型 0：旧业务 ，1：商品订单，2：服务订单
     */
    Integer BUS_TYPE_OLD = 0;
    Integer BUS_TYPE_PRODUCT_ORDER = 1;
    Integer BUS_TYPE_RESERVE_ORDER = 2;
    Map<Integer, String> BUS_TYPE = new HashMap<Integer, String>() {

        private static final long serialVersionUID = 2730479617491550696L;

        {
            put(BUS_TYPE_OLD, "原业务");
            put(BUS_TYPE_PRODUCT_ORDER, "商品订单");
            put(BUS_TYPE_RESERVE_ORDER, "预约订单");
        }
    };
    /**
     *
     *  支付状态(1-未支付,2-已支付,3-支付失败)
     */
    String PAY_STATUS_UN_PAID = "1";
    String PAY_STATUS_PAID = "2";
    String PAY_STATUS_FAIL = "3";
    Map<String, String> PAY_STATUS = new HashMap<String, String>() {

        private static final long serialVersionUID = 8565910240158796300L;

        {
            put(PAY_STATUS_UN_PAID, "未支付");
            put(PAY_STATUS_PAID, "已支付");
            put(PAY_STATUS_FAIL, "支付失败");
        }
    };

    /**
     *
     *  支付类型(0-零原订单1-支付宝,2-微信)
     */
    String PAY_TYPE_ZERO = "0";
    String PAY_TYPE_ALIPAY = "1";
    String PAY_TYPE_WECHAT = "2";
    Map<String, String> PAY_TYPE = new HashMap<String, String>() {


        private static final long serialVersionUID = 8911637267365081584L;

        {
            put(PAY_TYPE_ZERO, "零元订单");
            put(PAY_TYPE_ALIPAY, "支付宝");
            put(PAY_TYPE_WECHAT, "微信");
        }
    };

    /**
     *
     *  订单增值金额类型 1：运费
     */
    Integer ORDER_INCREMENT_EXPRESS = 1;
    Map<Integer, String> ORDER_INCREMENT = new HashMap<Integer, String>() {

        private static final long serialVersionUID = 8911637267365081584L;

        {
            put(ORDER_INCREMENT_EXPRESS, "运费");
        }
    };
    /**
     *
     *  审核状态：0、退款审核中；1、退款中；2、退款成功；3、退款失败; 4、退款取消
     */
    String   REFUND_AUDIT_STATUS_CHECKING = "0";
    String   REFUND_AUDIT_STATUS_REFUNDING ="1";
    String   REFUND_AUDIT_STATUS_COM ="2";
    String   REFUND_AUDIT_STATUS_FAIL ="3";
    String   REFUND_AUDIT_STATUS_CAN ="4";
    Map<String, String> refund_audit_Status = new HashMap<String, String>() {

        private static final long serialVersionUID = -2688559037458293932L;

        {
            put(REFUND_AUDIT_STATUS_CHECKING, "退款审核中");
            put(REFUND_AUDIT_STATUS_REFUNDING, "退款中");
            put(REFUND_AUDIT_STATUS_COM, "退款成功");
            put(REFUND_AUDIT_STATUS_FAIL, "退款失败");
            put(REFUND_AUDIT_STATUS_CAN, "退款取消");
        }
    };

    /**
     * 发票类型(0-不开发票,1-个人,2-公司)
     */
    Integer   INVOICE_TYPE_NONE =0;
    Integer   INVOICE_TYPE_PERSONAL =1;
    Integer   INVOICE_TYPE_COMPANY =2;
    Map<Integer, String> INVOICE_TYPE = new HashMap<Integer, String>() {

        private static final long serialVersionUID = -5696866749363335637L;

        {
            put(INVOICE_TYPE_NONE, "不开发票");
            put(INVOICE_TYPE_PERSONAL, "个人");
            put(INVOICE_TYPE_COMPANY, "公司");
        }
    };

    /**
     * 用户类型 1、买家，2、商业人员
     */

    String   USER_TYPE_BUYER ="1";
    String   USER_TYPE_TENANT ="2";
    Map<String, String> USER_TYPE = new HashMap<String, String>() {

        private static final long serialVersionUID = 8040261985616202457L;
        {
            put(USER_TYPE_BUYER, "买家");
            put(USER_TYPE_TENANT, "商业人员");
        }
    };


    /**
     * 库存操作类型
     */
    Integer STORE_PC_ADD = 1;
    Integer STORE_PC_UPDATE = 2;
    Integer STORE_SALE_REDIS_OUT = 3;
    Integer STORE_CANCEL_REDIS_IN = 4;
    Integer STORE_PAY_SUCCESS_DB_OUT = 5;
    Integer STORE_PAY_CANCEL_DB_IN = 6;
    Map<Integer, String> STORE_OPERATION_TYPE = new HashMap<Integer, String>() {

        private static final long serialVersionUID = 8040261985616202457L;
        {
            put(STORE_PC_ADD,"后台添加");
            put(STORE_PC_UPDATE,"后台更新");
            put(STORE_SALE_REDIS_OUT,"redis下单出库");
            put(STORE_CANCEL_REDIS_IN,"redis取消归还库存");
            put(STORE_PAY_SUCCESS_DB_OUT,"支付成功DB出库");
            put(STORE_PAY_CANCEL_DB_IN,"支付后取消DB入库");
        }
    };

    /**
     * 直辖市编码
     *  //4个直辖市 北京,天津，上海，重庆
     *     {"110000","120000","310000","500000"};
     */
    String MUNICIPALITY_BJ = "110000";
    String MUNICIPALITY_TJ = "120000";
    String MUNICIPALITY_SH = "310000";
    String MUNICIPALITY_CQ = "500000";
    Map<String, String> MUNICIPALITY = new HashMap<String, String>() {

        private static final long serialVersionUID = 3880343934571374302L;

        {
            put(MUNICIPALITY_BJ,"北京市");
            put(MUNICIPALITY_TJ,"天津市");
            put(MUNICIPALITY_SH,"上海市");
            put(MUNICIPALITY_CQ,"重庆市");
        }
    };

    /**
     * 用户优惠券使用状态
     * 使用状态(0-未领取,1-已领取,2-已使用,3-已退款,4-已过期)
     */
      String USER_COUPON_UN_RECEIVED = "0";
      String USER_COUPON_RECEIVED = "1";
      String USER_COUPON_USED = "2";
      String USER_COUPON_REFUND = "3";
      String USER_COUPON_EXPIRED = "4";
    Map<String, String> USER_COUPON_STATUS = new HashMap<String, String>() {

        private static final long serialVersionUID = -7232127370454725438L;

        {
            put(USER_COUPON_UN_RECEIVED,"未领取");
            put(USER_COUPON_RECEIVED,"已领取");
            put(USER_COUPON_USED,"已使用");
            put(USER_COUPON_REFUND,"已退款");
            put(USER_COUPON_EXPIRED,"已过期");
        }
    };

    /**
     * 预约时间限制 多少小时后
     */

    Integer RESERVATION_HOW_MANY_HOURS_LATER = 24;

    /**
     * 事件类型 1：一般事件 2：特殊事件
     */
    Integer DECISION_EVENT_TYPE_ORDINARY = 1;
    Integer DECISION_EVENT_TYPE_SPECIAL = 2;
    Map<Integer, String> DECISION_EVENT_TYPE = new HashMap<Integer, String>() {


        private static final long serialVersionUID = -8134972557604541231L;

        {
            put(DECISION_EVENT_TYPE_ORDINARY,"一般事件");
            put(DECISION_EVENT_TYPE_SPECIAL,"特殊事件");
        }
    };

    /**
     * 决策结果
     * 0：决策中
     * 1：通过
     * 2：未通过
     */
    Integer DECISION_STATUS_NONE = 0;
    Integer DECISION_STATUS_PASS = 1;
    Integer DECISION_STATUS_UN_PASS = 2;
    Map<Integer, String> DECISION_STATUS = new HashMap<Integer, String>() {

        private static final long serialVersionUID = 4900698836673617151L;

        {
            put(DECISION_STATUS_NONE,"决策中");
            put(DECISION_STATUS_PASS,"通过");
            put(DECISION_STATUS_UN_PASS,"未通过");
        }
    };

    /**
     * 进度状态  0：未开始，1：进行中，2：已结束
     */
    Integer PROGRESS_STATUS_UN_START = 0;
    Integer PROGRESS_STATUS_START = 1;
    Integer PROGRESS_STATUS_END =2;
    Map<Integer, String> PROGRESS_STATUS = new HashMap<Integer, String>() {

        private static final long serialVersionUID = -2980669679915127115L;

        {
            put(PROGRESS_STATUS_UN_START,"未开始");
            put(PROGRESS_STATUS_START,"进行中");
            put(PROGRESS_STATUS_END,"已结束");
        }
    };

    /**
     * 投票状态 0:不同意 1：同意
     */
    Integer VOTE_STATUS_DISAGREE = 0;
    Integer VOTE_STATUS_AGREE = 1;
    Map<Integer, String> VOTE_STATUS = new HashMap<Integer, String>() {


        private static final long serialVersionUID = -2980669679915127115L;

        {
            put(VOTE_STATUS_DISAGREE,"不同意");
            put(VOTE_STATUS_AGREE,"同意");
        }
    };

    /**
     * 类型(1-公司,2-商户,3-系统平台,4-中心城市)
     */
    String TENANT_TYPE_COMPANY = "1";
    String TENANT_TYPE_MERCHANT = "2";
    String TENANT_TYPE_PLATFORM = "3";
    String TENANT_TYPE_CENTER = "4";
    Map<String, String> TENANT_TYPE = new HashMap<String, String>() {

        private static final long serialVersionUID = 4388246086073276268L;

        {
            put(TENANT_TYPE_COMPANY,"公司");
            put(TENANT_TYPE_MERCHANT,"商户");
            put(TENANT_TYPE_PLATFORM,"系统平台");
            put(TENANT_TYPE_CENTER,"中心城市");
        }
    };
}
