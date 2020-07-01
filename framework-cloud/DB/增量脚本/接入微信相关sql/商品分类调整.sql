
-- 一级分类增加是否展示到首页
ALTER TABLE `biz_business`
    ADD COLUMN `show_home`  bit(1) NULL DEFAULT b'0' COMMENT '是否在首页展示' AFTER `deal_type`,
    ADD COLUMN `img_url`  varchar(500) NULL COMMENT '展示图片地址' AFTER `show_home`;

ALTER TABLE `biz_business_classify`
    ADD COLUMN `img_url`  varchar(500) NULL COMMENT '图片地址' AFTER `modify_time`;

-- 拼团抢购  - 社区团购
update biz_business set `bus_Name` = '社区团购',description = '社区团购',show_home = 1,is_classify = 1,modify_by = 'guohao',modify_time = now() where id = '3cd8f1aa29924399ae1bbbb038a81b48';

-- 优选商品  - 悦邻优选
update biz_business set `bus_Name` = '悦邻优选',description = '悦邻优选',show_home = 1,modify_by = 'guohao',modify_time = now() where id = '426fb699f74342c89212107e3883fb05';

-- 生活服务  - 悦邻到家
update biz_business set `bus_Name` = '悦邻到家',description = '悦邻到家',show_home = 1,modify_by = 'guohao',modify_time = now() where id = '06efc84f-43d1-11e9-9ab6-7cd30ac3345a';