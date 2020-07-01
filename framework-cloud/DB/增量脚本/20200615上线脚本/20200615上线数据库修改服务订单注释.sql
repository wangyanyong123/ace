ALTER TABLE biz_reservation_order MODIFY COLUMN `order_status`  int(1) NULL DEFAULT NULL COMMENT '订单状态5：待支付，110：待受理；115：待上门； 35：已完成；45：已取消';
ALTER TABLE biz_reservation_order MODIFY COLUMN `refund_status`  int(1) NULL DEFAULT 0 COMMENT '退款状态 0：无退款，10：退款中；15：部分退款；20：退款完成  ';
ALTER TABLE biz_reservation_order MODIFY COLUMN `comment_status`  int(1) NULL DEFAULT 0 COMMENT '评论状态：0：未评论；1：已评论';