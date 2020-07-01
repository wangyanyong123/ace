CREATE TABLE `biz_product_delivery` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `company_id` varchar(36) NOT NULL COMMENT '商户ID',
  `product_id` varchar(32) DEFAULT NULL,
  `proc_code` varchar(50) DEFAULT NULL COMMENT '区域编码',
  `proc_name` varchar(32) DEFAULT NULL COMMENT '区域名称',
  `city_code` varchar(32) DEFAULT NULL COMMENT '城市编码',
  `city_name` varchar(50) DEFAULT NULL COMMENT '城市名称',
  `full_Name` varchar(255) DEFAULT NULL COMMENT '区域全称',
  `status` char(1) DEFAULT '1' COMMENT '状态',
  `create_by` varchar(36) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `modify_by` varchar(36) DEFAULT NULL COMMENT '修改人',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品配送范围'