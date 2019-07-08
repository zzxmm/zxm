
-- ----------------------------
-- Records of xys_system_user
-- ----------------------------
-- 管理员初始化数据  密码 123456
INSERT INTO `szxys`.`xys_system_user`(`user_account`, `user_password`, `user_name`) VALUES ('admin', '4665b4df9a3967269372447816f6b329', '系统管理员')


-- ----------------------------
-- Records of xys_zone_whole_control
-- ----------------------------
-- 全局控制权限初始化数据
INSERT INTO `szxys`.`xys_zone_whole_control`(`type`, `state`) VALUES (0, 1)
INSERT INTO `szxys`.`xys_zone_whole_control`(`type`, `state`) VALUES (1, 1)


-- ----------------------------
-- Records of xys_extend_rule
-- ----------------------------
-- 分销规则初始化数据
INSERT INTO `xys_extend_rule`(`details`,`restricted_up`,`restricted_low`,`creator_id`,`restricted_interval`) VALUES ('<p>f分销规则</p>', 5.00, 1.00, 1, 0);
-- ----------------------------
-- Records of xys_extend_rule_param
-- ----------------------------
INSERT INTO `xys_extend_rule_param` VALUES (1, 0, 0, 0, 0.000000, 1.00);
INSERT INTO `xys_extend_rule_param` VALUES (2, 1, 1, 2, 1.000000, 2.00);
INSERT INTO `xys_extend_rule_param` VALUES (3, 2, 3, 5, 2.000000, 3.00);
INSERT INTO `xys_extend_rule_param` VALUES (4, 3, 6, 10, 3.000000, 4.00);
INSERT INTO `xys_extend_rule_param` VALUES (5, 4, 11, 100, 4.000000, 5.00);
INSERT INTO `xys_extend_rule_param` VALUES (6, 5, 101, 1000, 5.000000, 6.00);
INSERT INTO `xys_extend_rule_param` VALUES (7, 6, 1001, NULL, 6.000000, 7.00);

-- ----------------------------
-- Records of xys_integral_rule
-- ----------------------------
-- 积分规则初始化数据
INSERT INTO `xys_integral_rule`(`rule_name`, `rule_type`, `day_limit`, `obtain_integral`, `account_limit`, `rule_details`, `creator_id`) VALUES ('购买得积分', 0, -1, 80, -1, '<p>我是积分</p>', 1);
INSERT INTO `xys_integral_rule`(`rule_name`, `rule_type`, `day_limit`, `obtain_integral`, `account_limit`, `rule_details`, `creator_id`) VALUES ('分享得积分', 1, 20, 20, 60, '<p>分享获得积分</p>', 1);
INSERT INTO `xys_integral_rule`(`rule_name`, `rule_type`, `day_limit`, `obtain_integral`, `account_limit`, `rule_details`, `creator_id`) VALUES ('新人积分礼', 3, 1, 100, 1, '<p>我是新人初始化</p>', 1);


-- ----------------------------
-- Records of 'ucenter' user
-- ----------------------------
-- 用户中心 初始化数据
INSERT INTO `user`(`name`, `nick_name`, `mobile`, `login_id`, `password`, `user_state`, `data_source`) VALUES ('系统管理员', '管理员', '99999999999', 'admin', '0dc24b3d73d0bcad304a460c4a61d8f7', 0, 2);


-- ----------------------------
-- Records of xys_achievements_rule
-- ----------------------------
-- 绩效规则初始化数据
INSERT INTO `szxys`.`xys_achievements_rule`(`rule_name`, `restricted_up`, `restricted_low`, `rule_details`) VALUES ('绩效规则', 100, 1, '规则详情')









