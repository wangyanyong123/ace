-- 退款表新增退款完成字段
ALTER TABLE biz_refund_audit ADD refund_success_time datetime comment '退款完成时间';