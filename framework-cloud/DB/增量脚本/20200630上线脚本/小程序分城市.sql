ALTER TABLE `biz_crm_city`
ADD COLUMN `c_code`  varchar(10) NULL COMMENT '全国统一城市编码' AFTER `city_code`;

-- 修复旧数据
update biz_crm_city set c_code ='320100' where `name` ='南京';
update biz_crm_city set c_code ='440300' where `name` ='深圳';
update biz_crm_city set c_code ='320200' where `name` ='无锡';
update biz_crm_city set c_code ='440600' where `name` ='佛山';
update biz_crm_city set c_code ='320300' where `name` ='徐州';
update biz_crm_city set c_code ='320400' where `name` ='常州';
update biz_crm_city set c_code ='320500' where `name` ='苏州';
update biz_crm_city set c_code ='441900' where `name` ='东莞';
update biz_crm_city set c_code ='330100' where `name` ='杭州';
update biz_crm_city set c_code ='330200' where `name` ='宁波';
update biz_crm_city set c_code ='330300' where `name` ='温州';
update biz_crm_city set c_code ='330400' where `name` ='嘉兴';
update biz_crm_city set c_code ='330500' where `name` ='湖州';
update biz_crm_city set c_code ='340100' where `name` ='合肥';
update biz_crm_city set c_code ='500100' where `name` ='重庆';
update biz_crm_city set c_code ='510100' where `name` ='成都';
update biz_crm_city set c_code ='350200' where `name` ='厦门';
update biz_crm_city set c_code ='350500' where `name` ='泉州';
update biz_crm_city set c_code ='360100' where `name` ='南昌';
update biz_crm_city set c_code ='110100' where `name` ='北京';
update biz_crm_city set c_code ='120100' where `name` ='天津';
update biz_crm_city set c_code ='520100' where `name` ='贵阳';
update biz_crm_city set c_code ='370100' where `name` ='济南';
update biz_crm_city set c_code ='130600' where `name` ='保定';
update biz_crm_city set c_code ='370200' where `name` ='青岛';
update biz_crm_city set c_code ='131000' where `name` ='廊坊';
update biz_crm_city set c_code ='530100' where `name` ='昆明';
update biz_crm_city set c_code ='350100' where `name` ='福州';
update biz_crm_city set c_code ='530700' where `name` ='丽江';
update biz_crm_city set c_code ='410100' where `name` ='郑州';
update biz_crm_city set c_code ='410200' where `name` ='开封';
update biz_crm_city set c_code ='410300' where `name` ='洛阳';
update biz_crm_city set c_code ='610100' where `name` ='西安';
update biz_crm_city set c_code ='420100' where `name` ='武汉';
update biz_crm_city set c_code ='430100' where `name` ='长沙';
update biz_crm_city set c_code ='430200' where `name` ='株洲';
update biz_crm_city set c_code ='440100' where `name` ='广州';
update biz_crm_city set c_code ='310100' where `name` ='上海';

-- 预约服务配送范围
CREATE TABLE `biz_reservation_delivery` (
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
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约服务配送范围'
