alter table biz_reservation add column supplier varchar(128) default '' comment '供应商';
alter table biz_reservation add column sales_way varchar(64) default '' comment '销售方式';
alter table biz_product add column supplier varchar(128) default '' comment '供应商';
alter table biz_product add column sales_way varchar(64) default '' comment '销售方式';
ALTER TABLE biz_refund_audit ADD INDEX `sub_id`(`sub_id`) USING BTREE;
ALTER TABLE biz_transaction_log ADD INDEX `wo_id`(`wo_id`) USING BTREE;
ALTER TABLE biz_sub_product ADD INDEX `sub_id`(`sub_id`) USING BTREE;