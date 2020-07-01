-- ----------------------------
--  Procedure 商品订单计算商户的实付金额
-- ----------------------------
DROP FUNCTION IF EXISTS `GET_PRICE`;
DELIMITER ;;
CREATE FUNCTION `GET_PRICE`(`tenant_id` varchar(36),`order_id` varchar(36)) RETURNS decimal(18,6)
BEGIN
	DECLARE total_price DECIMAL(18,6) DEFAULT 0;
		SELECT
	(IFNULL(SUM(`od`.`total_price`),0)+(
		SELECT
			IFNULL(SUM(i.price), 0)
		FROM
			biz_order_increment i
		WHERE
			i.order_id = order_id
		AND i.tenant_id = tenant_id
		AND i. STATUS = 1
	)-(
		SELECT
			IFNULL(SUM(c.discount_price), 0)
		FROM
			biz_product_order_discount c
		WHERE
			c.order_id = order_id
		AND c.tenant_id = tenant_id
		AND c. STATUS = 1
	)) INTO total_price
FROM
	biz_product_order_detail od
INNER JOIN biz_product_order o ON od.order_id = o.id
AND o.`status` = 1
AND od.`status` = 1
AND od.order_id = order_id
WHERE
	od.tenant_id = tenant_id
GROUP BY
	od.tenant_id;
	RETURN total_price;
END
;;
DELIMITER ;

-- ----------------------------
--  Records 
-- ----------------------------


-- ----------------------------
--  View 汇总服务和商品订单统计
-- ----------------------------
DROP VIEW IF EXISTS `biz_order`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `biz_order` AS select `o`.`order_status` AS `detail_status`,`GET_PRICE`(`od`.`tenant_id`,`od`.`order_id`) AS `total_price`,`o`.`id` AS `order_id`,`od`.`tenant_id` AS `tenant_id`,`o`.`refund_status` AS `detail_refund_status`,`o`.`create_time` AS `create_time`,`o`.`comment_status` AS `comment_status` from (`biz_product_order_detail` `od` join `biz_product_order` `o` on(((`od`.`order_id` = `o`.`id`) and (`o`.`status` = 1) and (`od`.`status` = 1)))) group by `o`.`id`,`od`.`tenant_id` union all select `ro`.`order_status` AS `detail_status`,`ro`.`actual_price` AS `total_price`,`ro`.`id` AS `order_id`,`ro`.`tenant_id` AS `tenant_id`,`ro`.`refund_status` AS `detail_refund_status`,`ro`.`create_time` AS `create_time`,`ro`.`comment_status` AS `comment_status` from `biz_reservation_order` `ro` where (`ro`.`status` = 1);

