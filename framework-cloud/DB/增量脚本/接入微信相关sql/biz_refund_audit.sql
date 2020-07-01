ALTER TABLE `biz_refund_audit`
ADD COLUMN `project_id`  varchar(36) NULL AFTER `id`,
ADD COLUMN `sub_code`  varchar(36) NULL COMMENT '业务单号' AFTER `sub_id`,
ADD COLUMN `sub_title`  varchar(500) NULL COMMENT '单据标题' AFTER `sub_code`,
ADD COLUMN `sub_create_time`  datetime NULL COMMENT '单据创建时间' AFTER `sub_title`,
ADD COLUMN `user_id`  varchar(36) NULL COMMENT '单据所属用户id' AFTER `sub_create_time`,
ADD COLUMN `apply_price`  decimal(18,4) NULL DEFAULT 0 COMMENT '申请金额' AFTER `user_id`,
ADD COLUMN `actual_id`  varchar(36) NULL COMMENT '实际支付id' AFTER `apply_price`,
ADD COLUMN `bus_type`  int(1) NULL DEFAULT 0 COMMENT '业务类型 0：旧业务 ，1：商品订单，2：服务订单' AFTER `apply_price`;

