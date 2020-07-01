ALTER TABLE `biz_shopping_cart`
ADD COLUMN `app_type`  int(1) NOT NULL COMMENT '应用类型 1：ios， 2：android， 3：h5， 4：mp' AFTER `spec_Name`,
ADD COLUMN `open_id`  varchar(36) NULL AFTER `app_type`;

