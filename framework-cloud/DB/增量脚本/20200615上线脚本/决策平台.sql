INSERT INTO `sys_module` (`ID`, `pid`, `code`, `system`, `logo`, `name`, `en_us`, `show_type`, `module_type`, `is_leaf`, `url`, `ios_version`, `android_version`, `page_show_type`, `bus_Id`, `status`, `time_Stamp`, `create_By`, `create_Time`, `modify_By`, `modify_Time`)
VALUES ('999cf2ac-03fb-11e9-9ab6-7cd30ac3345b', '934508df-6f49-11e6-ba7f-020sssf4f50', 'M0109', '1', 'http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/dev/01/20200617/fbbd01da8251427d8f0bb8fa0847632b.png', '决策平台', '', '1', '1', '1', '', '0.9.3', '0.9.3', '0', '', '1', '2018-11-20 12:48:52', 'huangxl', '2018-11-20 12:48:52', 'system', now());



CREATE TABLE `biz_decision` (
  `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `project_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目id',
  `event_type` tinyint(1) NOT NULL COMMENT '事件类型 1：一般事件 2：特殊事件',
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '发布状态 0：未发布， 1：已发布',
  `decision_status` tinyint(1) DEFAULT '0' COMMENT '决策结果 0:决策中，1：通过 2：未通过',
  `progress_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '进度比例',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `house_count` int(1) DEFAULT '0' COMMENT '项目房屋数量',
  `house_area` decimal(10,2) DEFAULT '0.00' COMMENT '房屋总面积',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `modify_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  `status` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '数据状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='决策表';

CREATE TABLE `biz_decision_annex` (
  `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `decision_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='决策附件表';

CREATE TABLE `biz_decision_vote` (
  `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `project_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `decision_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `house_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `house_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `identity_type` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '房屋认证类型  1、家属；2、租客；3、业主',
  `progress_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '进度占比',
  `vote_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '投票状态 0:不同意 1：同意',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '数据状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='决策投票表';
