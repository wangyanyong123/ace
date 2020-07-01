-- 服务订单表添加隐私号绑定ID字段
ALTER TABLE biz_reservation_order ADD bind_id VARCHAR(36) comment '隐私号绑定ID';

-- 添加商品或服务时记录标签顺序
ALTER TABLE biz_product_label ADD `sort`  int(11) comment '标签插入顺序';