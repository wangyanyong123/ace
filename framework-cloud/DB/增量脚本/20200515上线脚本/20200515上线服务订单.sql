/*
Navicat MySQL Data Transfer

Source Server         : 回家测试库
Source Server Version : 50720
Source Host           : rm-2zezhn17oe34ry863ho.mysql.rds.aliyuncs.com:3306
Source Database       : digital

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-05-12 13:03:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- 服务订单表
-- ----------------------------
DROP TABLE IF EXISTS `biz_reservation_order`;
CREATE TABLE `biz_reservation_order` (
  `id` varchar(36) NOT NULL,
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '商户id 未支付时取第一个商品的商户',
  `order_code` varchar(36) DEFAULT NULL COMMENT '订单编号',
  `project_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `order_type` int(1) unsigned zerofill DEFAULT '1' COMMENT '订单类型 1：\r\n普通订单；2：团购订单。3：秒杀订单',
  `order_status` int(1) DEFAULT NULL COMMENT '订单状态 5：待支付，10：已取消；15：待受理；20：待上门；  ',
  `refund_status` int(1) DEFAULT '0' COMMENT '订单状态 5：待支付，10：待受理；15：待上门；  ',
  `comment_status` int(1) DEFAULT '0',
  `title` varchar(255) DEFAULT NULL COMMENT '订单标题',
  `description` varchar(255) CHARACTER SET ucs2 DEFAULT '' COMMENT '订单描述',
  `app_type` int(1) DEFAULT NULL COMMENT '下单应用类型 H5:10,微信小程序：20；安卓：30.\r\nios：40',
  `product_price` decimal(18,6) DEFAULT NULL COMMENT '商品总金额',
  `reservation_Time` datetime DEFAULT NULL COMMENT '预约服务时间',
  `actual_price` decimal(18,6) DEFAULT NULL COMMENT '实收金额=商品总金额-优惠金额',
  `discount_price` decimal(18,6) DEFAULT NULL COMMENT '优惠金额',
  `invoice_type` int(1) DEFAULT NULL COMMENT '发票类型(0-不开发票,1-个人,2-公司)',
  `invoice_name` varchar(255) DEFAULT NULL COMMENT '发票名称',
  `duty_code` varchar(72) DEFAULT NULL COMMENT '税号',
  `contact_name` varchar(50) DEFAULT NULL COMMENT '收获联系人',
  `contact_tel` varchar(36) DEFAULT NULL COMMENT '收货人联系电话',
  `delivery_addr` varchar(512) DEFAULT NULL COMMENT '收货地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(36) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_by` varchar(36) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  `status` char(1) DEFAULT '1' COMMENT '数据状态 1：有效；0：无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约服务订单表';


-- ----------------------------
-- 服务分配人员表
-- ----------------------------
DROP TABLE IF EXISTS `biz_reservation_order_waiter`;
CREATE TABLE `biz_reservation_order_waiter` (
  `id` varchar(36) NOT NULL,
  `order_id` varchar(36) NOT NULL,
  `order_detail_id` varchar(36) DEFAULT NULL,
  `waiter_tel` varchar(36) DEFAULT NULL COMMENT '服务人员联系电话',
  `waiter_name` varchar(50) DEFAULT NULL COMMENT '服务人员联系名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT '1' COMMENT '数据状态 1：有效 0：无效',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(36) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_by` varchar(36) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务订单表';


-- ----------------------------
-- 服务订单操作记录表
-- ----------------------------
DROP TABLE IF EXISTS `biz_reservation_order_operation_record`;
CREATE TABLE `biz_reservation_order_operation_record` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `order_id` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `step_status` int(1) DEFAULT NULL COMMENT '当前订单状态',
  `curr_step` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '当前步骤',
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '步骤描述',
  `status` char(1) CHARACTER SET utf8 DEFAULT '1' COMMENT '数据状态',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `modify_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- 服务订单详情表
-- ----------------------------
DROP TABLE IF EXISTS `biz_reservation_order_detail`;
CREATE TABLE `biz_reservation_order_detail` (
  `id` varchar(36) NOT NULL COMMENT 'id',
  `order_id` varchar(36) DEFAULT NULL COMMENT '订单id',
  `product_id` varchar(36) DEFAULT NULL COMMENT '产品id',
  `product_name` varchar(128) DEFAULT NULL COMMENT '产品名称',
  `spec_id` varchar(36) DEFAULT NULL COMMENT '规格ID',
  `spec_name` varchar(32) DEFAULT NULL COMMENT '规格',
  `spec_img` varchar(512) DEFAULT NULL COMMENT '图片id,多张图片逗号分隔',
  `quantity` int(10) DEFAULT '0' COMMENT '数量',
  `sales_price` decimal(18,2) DEFAULT NULL COMMENT '单价',
  `total_price` decimal(18,6) DEFAULT NULL COMMENT '总价',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `comment_status` bit(1) DEFAULT b'0' COMMENT '是否评论 0：未评论，1.已评论',
  `status` char(1) DEFAULT '1' COMMENT '状态：0、删除；1、正常',
  `create_by` varchar(36) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `modify_by` varchar(36) DEFAULT NULL COMMENT '修改人',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约服务订单详情表';



-- ----------------------------
-- 服务订单优惠记录表
-- ----------------------------
DROP TABLE IF EXISTS `biz_reservation_order_discount`;
CREATE TABLE `biz_reservation_order_discount` (
  `id` varchar(36) NOT NULL,
  `discount_type` int(1) DEFAULT NULL COMMENT '优惠类型 1：优惠券',
  `order_id` varchar(36) DEFAULT NULL,
  `order_detail_id` varchar(36) DEFAULT NULL,
  `order_relation_type` int(1) DEFAULT '1' COMMENT '与订单对应类型 1：订单。2：订单明\r\n细',
  `relation_id` varchar(36) DEFAULT NULL COMMENT '优惠关联id',
  `discount_price` decimal(18,6) DEFAULT NULL,
  `create_by` varchar(36) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_by` varchar(36) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` char(1) DEFAULT '1' COMMENT '数据状态',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- 服务订单评论表
-- ----------------------------
DROP TABLE IF EXISTS `biz_reservation_order_comment`;
CREATE TABLE `biz_reservation_order_comment` (
  `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `product_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `img_ids` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `appraisal_val` tinyint(4) DEFAULT NULL,
  `is_arrive_ontime` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `create_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table 快递公司
-- ----------------------------
DROP TABLE IF EXISTS `biz_express_company`;
CREATE TABLE `biz_express_company` (
  `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `company_code` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '快递公司编码',
  `company_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '快递公司名称',
  `sort` char(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` bit(1) DEFAULT b'0' COMMENT '0:未删除；1：已删除',
  `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='快递公司';

-- ----------------------------
-- Records of biz_express_company
-- ----------------------------
INSERT INTO `biz_express_company` VALUES ('081b5357b1d111e981df7cd30ac1234a', 'JD', '京东', 'J', true, null, '2020-04-24 16:20:33', null, null, null);
INSERT INTO `biz_express_company` VALUES ('081b5357b1d111e981df7cd30ac1236a', 'QT', '其他', 'Z9', true, null, '2020-05-12 10:55:03', null, null, null);
INSERT INTO `biz_express_company` VALUES ('081b5357b1d111e981df7cd30ac123st', 'ST', '申通', 'S', true, null, '2020-05-12 13:08:07', null, null, null);
INSERT INTO `biz_express_company` VALUES ('081b5357b1d111e981df7cd30ac3345a', 'SF', '顺丰', 'S', true, null, '2020-04-24 16:19:59', null, null, null);
INSERT INTO `biz_express_company` VALUES ('081b5357b1d111e981df7cd30ac7896a', 'YT', '圆通', 'Y', true, null, '2020-04-24 16:20:02', null, null, null);
INSERT INTO `biz_express_company` VALUES ('081b5357b1d111e981df7cd30ac789db', 'DB', '德邦', 'D', true, null, '2020-05-12 13:10:24', null, null, null);
INSERT INTO `biz_express_company` VALUES ('081b5357b1d111e981df7cd30ac789tt', 'TT', '天天', 'T', true, null, '2020-05-12 13:10:21', null, null, null);
INSERT INTO `biz_express_company` VALUES ('081b5357b1d111e981df7cd30ac789yd', 'YD', '韵达', 'Y', true, null, '2020-05-12 13:08:10', null, null, null);
INSERT INTO `biz_express_company` VALUES ('081b5357b1d111e981df7cd30ac789zt', 'ZT', '中通', 'Z', true, null, '2020-05-12 13:08:14', null, null, null);
INSERT INTO `biz_express_company` VALUES ('081b5357b1d111e981df7cd30ac78bst', 'BST', '百世通', 'B', true, null, '2020-05-12 13:10:27', null, null, null);
INSERT INTO `biz_express_company` VALUES ('081b5357b1d111e981df7cd30ac78ems', 'EMS', 'EMS', 'E', true, null, '2020-05-12 13:08:17', null, null, null);
INSERT INTO `biz_express_company` VALUES ('081b5357b1d111e981df7cd30ac7zgyz', 'ZGYZ', '中国邮政', 'Z', true, null, '2020-05-12 13:10:29', null, null, null);
