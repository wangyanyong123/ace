
-- 收获地址绑定项目 用户预约服务下单验证是否满足项目条件
ALTER TABLE `biz_postal_address`
ADD COLUMN `project_id`  varchar(36) NULL AFTER `ID`;

