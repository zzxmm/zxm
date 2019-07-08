/*

 Date: 23/01/2019 13:06:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_system_user
-- ----------------------------
DROP TABLE IF EXISTS `xys_system_user`;
CREATE TABLE `xys_system_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_account` varchar(255) NOT NULL COMMENT '用户账号',
  `user_password` varchar(255) NOT NULL COMMENT '账号密码',
  `user_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别：0男、1女',
  `e_mail` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `user_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态：默认0启用、1停用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  `delted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识',
  `user_headimg` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `openid` varchar(32) DEFAULT NULL COMMENT '微信openID',
  `address` varchar(255) DEFAULT NULL COMMENT '通讯地址',
  `user_center_id` int(11) DEFAULT NULL COMMENT '用户中心用户ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_user_center_id` (`user_center_id`) USING BTREE COMMENT 'centerid索引',
  KEY `IX_openid` (`openid`) USING BTREE COMMENT 'openid索引',
  KEY `IX_user_state` (`user_state`) USING BTREE COMMENT 'state索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表\n';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_system_user
-- ----------------------------

-- 结构同步
ALTER TABLE `szxys`.`xys_system_user` MODIFY COLUMN `user_account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号' AFTER `id`;
ALTER TABLE `szxys`.`xys_system_user` MODIFY COLUMN `user_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号密码' AFTER `user_account`;
ALTER TABLE `szxys`.`xys_system_user` MODIFY COLUMN `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名' AFTER `user_password`;
ALTER TABLE `szxys`.`xys_system_user` MODIFY COLUMN `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号' AFTER `user_name`;
ALTER TABLE `szxys`.`xys_system_user` MODIFY COLUMN `e_mail` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱' AFTER `sex`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--

