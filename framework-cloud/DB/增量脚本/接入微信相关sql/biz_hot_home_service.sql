-- 热门到家
CREATE TABLE `biz_hot_home_service` (
  `id` varchar(36)  NOT NULL,
  `title` varchar(50)  DEFAULT NULL,
  `img_url` varchar(500)  DEFAULT NULL COMMENT '图片地址',
  `position` tinyint(1) NOT NULL DEFAULT '1' COMMENT '展示位置 1：首页，....',
  `sort_num` int(1) NOT NULL DEFAULT '1' COMMENT '展示顺序',
  `tenant_id` varchar(36)  DEFAULT NULL COMMENT '租户ID',
  `bus_id` varchar(36) DEFAULT NULL COMMENT '业务ID（预约服务ID）',
  `create_by` varchar(36)  DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `modify_by` varchar(36)  DEFAULT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` char(1)  DEFAULT '1' COMMENT '数据状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `biz_hot_home_service_project` (
    `id` varchar(36)  NOT NULL,
    `hot_home_service_id` varchar(36)  NOT NULL,
    `project_id` varchar(36)  NOT NULL,
    `status` char(1)  DEFAULT '1',
    `create_time` datetime DEFAULT NULL,
    `create_by` varchar(36)  DEFAULT NULL,
    `modify_by` varchar(36)  DEFAULT NULL,
    `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB