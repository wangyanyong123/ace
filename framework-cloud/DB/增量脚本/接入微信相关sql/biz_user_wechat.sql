
--
CREATE TABLE `biz_user_wechat` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `app_type` int(1) NOT NULL COMMENT '应用类型',
  `app_id` varchar(36) CHARACTER SET utf8 NOT NULL COMMENT '应用appid',
  `user_id` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户ID',
  `open_id` varchar(36) CHARACTER SET utf8 NOT NULL COMMENT '微信openid',
  `union_id` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '微信开发平台唯一ID',
  `status` char(1) CHARACTER SET utf8 NOT NULL DEFAULT '1' COMMENT '数据状态',
  `create_time` datetime NOT NULL,
  `create_by` varchar(36) CHARACTER SET utf8 NOT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_by` varchar(36) CHARACTER SET utf8 NOT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_open_id` (`open_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8