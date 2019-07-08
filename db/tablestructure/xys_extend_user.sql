/*

 Date: 21/05/2019 12:17:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_extend_user
-- ----------------------------
DROP TABLE IF EXISTS `xys_extend_user`;
CREATE TABLE `xys_extend_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inviter_user_openid` varchar(32) NOT NULL COMMENT '邀请者',
  `be_invited_user_openid` varchar(32) NOT NULL COMMENT '受邀者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `IX_INVITER` (`inviter_user_openid`) USING BTREE COMMENT '邀请者openid索引',
  KEY `IX_BE_INVITER` (`be_invited_user_openid`) USING BTREE COMMENT '受邀者openid索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='分销-用户关系表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
--  DDL for xys_extend_user
-- ----------------------------

-- 增加用户ID
ALTER TABLE `szxys`.`xys_extend_user`
ADD COLUMN `inviter_user_id` int(11) NULL COMMENT '邀请者ID' AFTER `id`,
ADD COLUMN `be_inviter_user_id` int(11) NULL COMMENT '受邀者ID' AFTER `inviter_user_openid`;
ALTER TABLE `szxys`.`xys_extend_user`
ADD INDEX `IX_INVITER_ID`(`inviter_user_id`) USING BTREE COMMENT '邀请者ID索引',
ADD INDEX `IX_BE_INVITER_ID`(`be_inviter_user_id`) USING BTREE COMMENT '受邀者ID索引';

--
--  2019-6-19 ⬆️ Submitted  ✅
--














