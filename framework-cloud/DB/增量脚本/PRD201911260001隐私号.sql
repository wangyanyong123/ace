/*
Navicat MySQL Data Transfer

Source Server         : ace
Source Server Version : 50720
Source Host           : rm-2zezhn17oe34ry863ho.mysql.rds.aliyuncs.com:3306
Source Database       : digital

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-04-03 19:39:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for biz_pns_call
-- ----------------------------
DROP TABLE IF EXISTS `biz_pns_call`;
CREATE TABLE `biz_pns_call` (
  `id` varchar(36) NOT NULL,
  `tel_a` varchar(32) DEFAULT NULL COMMENT 'A号码',
  `tel_b` varchar(32) DEFAULT NULL COMMENT 'B号码',
  `tel_x` varchar(32) DEFAULT NULL COMMENT 'X号码',
  `area_code` varchar(32) DEFAULT NULL COMMENT '需要X号码所属区号',
  `record` bit(1) DEFAULT NULL COMMENT '是否录音，1：录音；0：不录音"',
  `expiration` int(11) DEFAULT NULL COMMENT '绑定失效时间（秒）',
  `customer` varchar(100) DEFAULT NULL COMMENT '业务侧随传数据，可以是json和任意字符串',
  `bind_id` varchar(32) DEFAULT NULL COMMENT '绑定ID',
  `binding_flag` bit(1) DEFAULT b'1' COMMENT '是否绑定：0-解绑；1-绑定；',
  `over_time` datetime DEFAULT NULL COMMENT '超时截止时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for biz_pns_call_record
-- ----------------------------
DROP TABLE IF EXISTS `biz_pns_call_record`;
CREATE TABLE `biz_pns_call_record` (
  `id` varchar(36) NOT NULL,
  `call_id` varchar(32) DEFAULT NULL COMMENT '通话Id',
  `bind_id` varchar(32) DEFAULT NULL COMMENT '绑定Id，和绑定接口返回的bindId一致',
  `ani` varchar(32) DEFAULT NULL COMMENT '主叫号码 格式：手机或固话座机',
  `dnis` varchar(32) DEFAULT NULL COMMENT '被叫号码 格式：手机或固话座机',
  `tel_x` varchar(32) DEFAULT NULL COMMENT 'X号码',
  `mode_type` varchar(10) DEFAULT NULL COMMENT 'AXB、AX等',
  `talking_time_len` int(11) DEFAULT NULL COMMENT '通话时长，单位：秒',
  `start_time` datetime DEFAULT NULL COMMENT '拨打时间 格式： yyyy-MM-dd hh:mm:ss',
  `talking_time` datetime DEFAULT NULL COMMENT ' 通话时间 格式： yyyy-MM-dd hh:mm:ss',
  `end_time` datetime DEFAULT NULL COMMENT '挂断时间 格式： yyyy-MM-dd hh:mm:ss',
  `end_type` smallint(6) DEFAULT NULL COMMENT '挂机结束方 (0表示平台释放,1表示主叫，2表示被叫)',
  `end_type_desc` varchar(32) DEFAULT NULL COMMENT '挂机结束方描述',
  `end_state` smallint(6) DEFAULT NULL COMMENT '挂机状态原因',
  `end_state_desc` varchar(32) DEFAULT NULL COMMENT '挂机原因描述',
  `rec_url` varchar(255) DEFAULT NULL COMMENT '通话录音地址',
  `customer` varchar(100) DEFAULT NULL COMMENT '业务侧随传数据，可以是json和任意字符串',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for biz_pns_call_log
-- ----------------------------
DROP TABLE IF EXISTS `biz_pns_call_log`;
CREATE TABLE `biz_pns_call_log` (
  `id` varchar(36) NOT NULL,
  `tel_a` varchar(32) DEFAULT NULL COMMENT 'A号码',
  `tel_b` varchar(32) DEFAULT NULL COMMENT 'B号码',
  `tel_x` varchar(32) DEFAULT NULL COMMENT 'X号码',
  `sub_id` varchar(36) DEFAULT NULL COMMENT '订单号',
  `area_code` varchar(32) DEFAULT NULL COMMENT '需要X号码所属区号',
  `record` bit(1) DEFAULT NULL COMMENT '是否录音，1：录音；0：不录音"',
  `expiration` int(11) DEFAULT NULL COMMENT '绑定失效时间（秒）',
  `customer` varchar(100) DEFAULT NULL COMMENT '业务侧随传数据，可以是json和任意字符串',
  `bind_id` varchar(32) DEFAULT NULL COMMENT '绑定ID',
  `binding_type` bit(1) DEFAULT NULL COMMENT '绑定或解绑类型：0-手动；1-自动；',
  `binding_flag` bit(1) DEFAULT NULL COMMENT '是否绑定：0-解绑；1-绑定；',
  `binding_code` varchar(10) DEFAULT NULL COMMENT '绑定返回异常码',
  `binding_msg` varchar(50) DEFAULT NULL COMMENT '绑定异常描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;