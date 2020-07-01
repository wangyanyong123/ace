

-- 需要提供 短信文案
INSERT INTO `sys_msg_theme` (`id`, `theme_code`, `theme_desc`, `is_open`, `status`, `create_by`, `create_time`, `modify_by`, `modify_time`)
VALUES ('7ec79d1b7a024f8a919088a999da3bb3', 'CUSTOMER_BIND_MOBILE', '微信端登录通知', '1', '1', '18645cb4e4d04e5b9eb347c89478f0d5', '2020-04-15 11:12:02', '18645cb4e4d04e5b9eb347c89478f0d5', '2020-04-15 11:12:34');
INSERT INTO `sys_msg_templet` (`id`, `theme_id`, `templet_type`, `templet_jump_type`, `title`, `templet_content`, `templet_code`, `sound`, `page`, `page_html`, `status`, `create_by`, `create_time`, `modify_by`, `modify_time`)
VALUES ('de072a8ae7c040888e76ac49a34ecb69', '7ec79d1b7a024f8a919088a999da3bb3', '3', '1', '微信端登录通知',
'您的手机验证码为：#(randomCode)。如非本人操作，请注意账户安全，验证码提供给他人可能导致账号被盗，请勿泄漏，谨防被骗。', 'CUSTOMER_BIND_MOBILE', NULL, NULL, NULL, '1',
 '18645cb4e4d04e5b9eb347c89478f0d5', '2020-04-15 11:21:10', '18645cb4e4d04e5b9eb347c89478f0d5',
 '2020-04-15 11:21:16');