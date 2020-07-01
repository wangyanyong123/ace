-- 库存表创建
-- 库存表
DROP TABLE IF EXISTS `biz_store`;
CREATE TABLE `biz_store` (
  `id` varchar(36) NOT NULL,
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '商户ID',
  `product_id` varchar(36) DEFAULT NULL COMMENT '商品Id',
  `spec_id` varchar(36) DEFAULT NULL,
  `product_code` varchar(36) DEFAULT NULL COMMENT '商品编码',
  `product_type` tinyint(4) DEFAULT NULL COMMENT '商品类型：1-商品类型；2-预约类型；',
  `product_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `is_limit` bit(1) DEFAULT NULL COMMENT '是否限制库存',
  `store_num` int(11) DEFAULT NULL COMMENT '库存数量',
  `time_slot` tinyint(1) DEFAULT NULL COMMENT '时间段：0-不限时间（商品类型）；1-上午（预约类型）；2-下午（预约类型）；',
  `status` bit(1) DEFAULT b'1' COMMENT '0-未删除；1-删除',
  `create_by` varchar(36) DEFAULT NULL COMMENT '操作人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_by` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
-- 库存记录表
DROP TABLE IF EXISTS `biz_store_log`;
CREATE TABLE `biz_store_log` (
  `id` varchar(36) NOT NULL,
  `spec_id` varchar(36) DEFAULT NULL COMMENT '规格ID',
  `order_id` varchar(36) DEFAULT NULL,
  `log_type` bit(1) DEFAULT NULL COMMENT '日志类型：0-参数校验日志；1-操作结果日志',
  `operation_type` tinyint(1) DEFAULT NULL COMMENT '操作类型',
  `operation_type_desc` varchar(50) DEFAULT NULL COMMENT '操作类型描述',
  `history_num` int(11) DEFAULT NULL COMMENT '历史库存',
  `access_num` int(11) DEFAULT NULL COMMENT '增加库存',
  `current_num` int(11) DEFAULT NULL COMMENT '当前库存',
  `is_limit` bit(1) DEFAULT NULL COMMENT '是否限制库存:1-限制；0-不限制；',
  `time_slot` tinyint(1) DEFAULT NULL COMMENT '时间段：0-不限时间（商品类型）；1-上午（预约类型）；2-下午（预约类型）；',
  `is_result` bit(1) DEFAULT NULL COMMENT '处理前校验是否通过',
  `result_desc` varchar(50) DEFAULT NULL COMMENT '校验失败说明',
  `create_by` varchar(36) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 数据迁移
-- 迁移商品库存
INSERT INTO biz_store (
	`id`,
	`tenant_id`,
	`product_id`,
	`spec_id`,
	`product_code`,
	`product_type`,
	`product_name`,
	`is_limit`,
	`store_num`,
	`time_slot`,
	`create_by`
) SELECT
	MD5(uuid()) AS id,
	p.tenant_id,
	p.id AS product_id,
	s.id spec_id,
	p.product_code,
	1 AS product_type,
	p.product_name,
	(
		CASE p.product_Num
		WHEN - 1 THEN
			FALSE
		ELSE
			TRUE
		END
	) AS is_limit,
	(
		CASE p.product_Num
		WHEN - 1 THEN
			0
		ELSE
			p.product_Num - p.sales
		END
	) AS store_num,
	0 AS time_slot,
	p.modify_By AS create_by
FROM
	biz_product_spec s
INNER JOIN biz_product p ON s.product_id = p.id;

-- 迁移预约上午库存
INSERT INTO biz_store (
	`id`,
	`tenant_id`,
	`product_id`,
	`spec_id`,
	`product_code`,
	`product_type`,
	`product_name`,
	`is_limit`,
	`store_num`,
	`time_slot`,
	`create_by`
) SELECT
	MD5(uuid()) AS id,
	r.tenant_id,
	r.id AS product_id,
	s.id spec_id,
	r.reservation_code AS product_code,
	2 AS product_type,
	r. NAME AS product_name,
	(
		CASE r.product_num_forenoon
		WHEN - 1 THEN
			FALSE
		ELSE
			TRUE
		END
	) AS is_limit,
	(
		CASE r.product_num_forenoon
		WHEN - 1 THEN
			0
		ELSE
			r.product_num_forenoon
		END
	) AS store_num,
	1 AS time_slot,
	r.modify_By AS create_by
FROM
	biz_product_spec s
INNER JOIN biz_reservation r ON s.product_id = r.id;

-- 迁移预约下午库存
INSERT INTO biz_store (
	`id`,
	`tenant_id`,
	`product_id`,
	`spec_id`,
	`product_code`,
	`product_type`,
	`product_name`,
	`is_limit`,
	`store_num`,
	`time_slot`,
	`create_by`
) SELECT
	MD5(uuid()) AS id,
	r.tenant_id,
	r.id AS product_id,
	s.id spec_id,
	r.reservation_code AS product_code,
	2 AS product_type,
	r. NAME AS product_name,
	(
		CASE r.product_num_afternoon
		WHEN - 1 THEN
			FALSE
		ELSE
			TRUE
		END
	) AS is_limit,
	(
		CASE r.product_num_afternoon
		WHEN - 1 THEN
			0
		ELSE
			r.product_num_afternoon
		END
	) AS store_num,
	2 AS time_slot,
	r.modify_By AS create_by
FROM
	biz_product_spec s
INNER JOIN biz_reservation r ON s.product_id = r.id;