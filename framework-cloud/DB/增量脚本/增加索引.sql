ALTER TABLE biz_subscribe ADD INDEX `project_id`(`project_id`) USING BTREE;
ALTER TABLE biz_account_book ADD INDEX `sub_id`(`sub_id`) USING BTREE;
ALTER TABLE biz_subscribe_wo ADD INDEX `user_id`(`user_id`) USING BTREE;
ALTER TABLE biz_community_topic_project ADD INDEX `community_topic_id`(`community_topic_id`) USING BTREE;
ALTER TABLE biz_community_topic ADD INDEX `user_id`(`user_id`) USING BTREE;
ALTER TABLE biz_family_posts ADD INDEX `user_id`(`user_id`) USING BTREE;