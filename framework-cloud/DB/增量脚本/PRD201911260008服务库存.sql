alter table biz_reservation add column product_num_forenoon int(32) default -1 comment '上午库存' after forenoon_End_Time;
alter table biz_reservation add column product_num_afternoon int(32) default -1 comment '下午库存' after afternoon_End_Time;
ALTER TABLE biz_sub_product ADD INDEX `product_id`(`product_Id`) USING BTREE;