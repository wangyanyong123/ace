-- 小程序上次图片 文件名过长，（小程序端，控制不了，所以增加该字段长度）
ALTER TABLE `upload_info`
MODIFY COLUMN `file_name`  varchar(132) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称' AFTER `size`;

