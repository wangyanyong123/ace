CREATE TABLE `biz_product_order` (
  `id` varchar(36) NOT NULL,
  `parent_id` varchar(36) NOT NULL COMMENT '父订单id，与支付记录一致，',
  `tenant_id` varchar(36) NOT NULL COMMENT '商户id 未支付时取第一个商品的商户',
  `order_code` varchar(36) NOT NULL COMMENT '订单编号',
  `project_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `order_type` int(1) unsigned zerofill NOT NULL DEFAULT '1' COMMENT '订单类型 1：普通订单；2：团购订单。3：秒杀订单',
  `order_status` int(1) NOT NULL COMMENT '订单状态 5：待支付，6：拼团中， 10：待发货，15：部分发货20：待签收 ，30待评价35：已完成；40：退款中 ；45：已关闭；',
  `refund_status` int(1) NOT NULL DEFAULT '0' COMMENT '退款状态：0：无退款，10：退款中，15:部分退款 20：已退款',
  `comment_status` int(1) unsigned DEFAULT '0' COMMENT '是否评论 0：未评论，1.已评论',
  `title` varchar(255) NOT NULL COMMENT '订单标题',
  `description` varchar(255) DEFAULT NULL COMMENT '订单描述',
  `app_type` int(1) NOT NULL COMMENT '应用类型 1：ios， 2：android， 3：h5， 4：mp',
  `product_price` decimal(18,6) NOT NULL COMMENT '商品总金额',
  `express_price` decimal(18,6) NOT NULL COMMENT '运费',
  `actual_price` decimal(18,6) NOT NULL COMMENT '实收金额=商品总金额+运费-优惠金额',
  `discount_price` decimal(18,6) NOT NULL COMMENT '优惠金额',
  `quantity` int(5) NOT NULL COMMENT '商品总件数',
  `contact_name` varchar(50) NOT NULL COMMENT '收获联系人',
  `contact_tel` varchar(36) NOT NULL COMMENT '收货人联系电话',
  `delivery_addr` varchar(512) DEFAULT NULL COMMENT '收货地址',
  `paid_time` datetime DEFAULT NULL,
  `send_time` datetime DEFAULT NULL COMMENT '最后一次发货时间',
  `confirm_time` datetime DEFAULT NULL COMMENT '确认收货日期',
  `create_by` varchar(36) NOT NULL,
  `create_time` datetime NOT NULL COMMENT '支付时间',
  `modify_by` varchar(36) NOT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '数据状态 1：有效；0：无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单表';


CREATE TABLE `biz_product_order_detail` (
  `id` varchar(36) NOT NULL COMMENT 'id',
  `order_id` varchar(36) NOT NULL COMMENT '订单id',
  `parent_id` varchar(36) NOT NULL,
  `detail_status` int(1) DEFAULT '5' COMMENT '订单状态 5：待支付，10：待发货，15：部分发货20：待签收, 30待评价 35：已完成；40：退款中 ；45：已关闭；',
  `detail_refund_status` int(1) DEFAULT '0' COMMENT '退款状态：0：无退款，10：退款中，15:部分退款 20：已退款',
  `tenant_id` varchar(36) NOT NULL COMMENT '商户id',
  `product_id` varchar(36) NOT NULL COMMENT '产品id',
  `product_name` varchar(128) NOT NULL COMMENT '产品名称',
  `spec_id` varchar(36) NOT NULL COMMENT '规格ID',
  `spec_name` varchar(32) NOT NULL COMMENT '规格',
  `spec_img` varchar(512) NOT NULL COMMENT '图片id,多张图片逗号分隔',
  `quantity` int(10) NOT NULL DEFAULT '0' COMMENT '数量',
  `sales_price` decimal(18,2) NOT NULL COMMENT '单价',
  `total_price` decimal(18,6) NOT NULL COMMENT '总价',
  `unit` varchar(32) NOT NULL COMMENT '单位',
  `comment_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否评论 0：未评论，1.已评论',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态：0、删除；1、正常',
  `create_by` varchar(36) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `modify_by` varchar(36) NOT NULL COMMENT '修改人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单产品表';

CREATE TABLE `biz_order_remark` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `parent_id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `order_id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `tenant_id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `remark` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` char(1) CHARACTER SET utf8 NOT NULL,
  `create_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单备注表';

CREATE TABLE `biz_product_order_discount` (
  `id` varchar(36) NOT NULL,
  `discount_type` char(10) NOT NULL COMMENT '优惠类型 1：优惠券',
  `parent_id` varchar(36) NOT NULL,
  `order_id` varchar(36) NOT NULL,
  `tenant_id` varchar(36) NOT NULL,
  `order_detail_id` varchar(36) DEFAULT NULL,
  `order_relation_type` int(1) DEFAULT '1' COMMENT '与订单对应类型 1：订单。2：订单明细',
  `relation_id` varchar(36) NOT NULL COMMENT '优惠关联id',
  `discount_price` decimal(18,6) NOT NULL,
  `create_by` varchar(36) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_by` varchar(36) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` char(1) DEFAULT '1' COMMENT '数据状态',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `biz_order_increment` (
  `id` varchar(36) NOT NULL,
  `increment_type` int(1) unsigned DEFAULT '1' COMMENT '增值金额类型 1：运费',
  `parent_id` varchar(36) NOT NULL,
  `order_id` varchar(36) NOT NULL,
  `tenant_id` varchar(36) NOT NULL,
  `price` decimal(18,6) DEFAULT NULL,
  `status` char(1) NOT NULL,
  `create_by` varchar(36) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_by` varchar(36) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单增值金额表';

CREATE TABLE `biz_order_operation_record` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `order_id` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `parent_id` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `step_status` int(1) NOT NULL COMMENT ' 已下单：5，支付成功：10 。 支付成功：15， 团购成功：16， 已发货：20， 确认收货：25， 评价完成：30， 取消订单：40， 退款审核：45， 退款驳回：46， 申请售后：50，售后驳回：55，已退款：60',
  `curr_step` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '当前步骤',
  `description` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '步骤描述',
  `status` char(1) CHARACTER SET utf8 DEFAULT '1' COMMENT '数据状态',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `modify_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_parent_id` (`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `biz_order_logistics` (
  `id` varchar(36) NOT NULL,
  `order_id` varchar(36) NOT NULL,
  `order_detail_id` varchar(36) NOT NULL DEFAULT '0',
  `logistics_code` varchar(36) NOT NULL COMMENT '物流公司编码',
  `logistics_name` varchar(50) NOT NULL COMMENT '物流公司名称',
  `logistics_no` varchar(50) NOT NULL COMMENT '物流单号',
  `send_time` datetime NOT NULL COMMENT '发货时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '数据状态 1：有效 0：无效',
  `create_time` datetime NOT NULL,
  `create_by` varchar(36) NOT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_by` varchar(36) NOT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单物流信息表';

CREATE TABLE `biz_order_invoice` (
  `id` varchar(36)  NOT NULL,
  `parent_id` varchar(36)  NOT NULL,
  `order_id` varchar(36)  NOT NULL,
  `tenant_id` varchar(36)  NOT NULL,
  `invoice_type` int(1) DEFAULT '0' COMMENT '发票类型(0-不开发票,1-个人,2-公司)',
  `invoice_name` varchar(255)  DEFAULT NULL,
  `duty_code` varchar(255)  DEFAULT NULL,
  `create_by` varchar(36)  DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_by` varchar(36)  DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  `status` char(1)  DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;