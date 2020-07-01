ALTER TABLE `biz_account_book`
ADD COLUMN `app_id`  varchar(20) NULL COMMENT '支付宝、微信应用appid' AFTER `pay_date`,
ADD COLUMN `bus_type`  int(1) UNSIGNED NULL DEFAULT 0 COMMENT '业务类型 0：旧业务 ，1：商品订单，2：服务订单' AFTER `sub_id`;



ALTER TABLE `biz_account_book`
MODIFY COLUMN `refund_status`  char(1)  NULL DEFAULT 0 COMMENT '1、退款中，2、退款成功，3、退款失败' AFTER `actual_cost`;

