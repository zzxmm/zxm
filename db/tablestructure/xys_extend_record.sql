/*

 Date: 21/05/2019 12:17:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_extend_record
-- ----------------------------
DROP TABLE IF EXISTS `xys_extend_record`;
CREATE TABLE `xys_extend_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inviter_openid` varchar(32) DEFAULT NULL COMMENT '邀请者openID',
  `inviter_name` varchar(48) DEFAULT NULL COMMENT '邀请者名称',
  `be_inviter_openid` varchar(32) DEFAULT NULL COMMENT '受邀者openID',
  `be_inviter_name` varchar(48) DEFAULT NULL COMMENT '受邀者名称',
  `reward_money` decimal(20,2) DEFAULT NULL COMMENT '奖励金额',
  `reward_type` tinyint(4) DEFAULT NULL COMMENT '奖励类型 : 0 邀请奖励  1返佣奖励',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `IX_INVITER` (`inviter_openid`) USING BTREE COMMENT '邀请者用户openid索引',
  KEY `IX_BE_INVITER` (`be_inviter_openid`) USING BTREE COMMENT '受邀者openid索引',
  KEY `IX_MONEY` (`reward_money`) USING BTREE COMMENT '奖励金额索引',
  KEY `IX_TYPE` (`reward_type`) USING BTREE COMMENT '奖励类型索引',
  KEY `IX_TIME` (`create_time`) USING BTREE COMMENT '时间索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='分销-记录表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
--  DDL for xys_extend_record
-- ----------------------------

-- 增加用户ID
ALTER TABLE `szxys`.`xys_extend_record`
ADD COLUMN `inviter_user_id` int(11) NULL COMMENT '邀请者用户ID' AFTER `id`,
ADD COLUMN `be_inviter_user_id` int(11) NULL COMMENT '受邀者用户ID' AFTER `inviter_name`;
ALTER TABLE `szxys`.`xys_extend_record`
DROP INDEX `IX_INVITER`,
DROP INDEX `IX_BE_INVITER`,
ADD INDEX `IX_INVITER`(`inviter_user_id`) USING BTREE COMMENT '邀请者用户id索引',
ADD INDEX `IX_BE_INVITER`(`be_inviter_user_id`) USING BTREE COMMENT '受邀者id索引';

--
--  2019-6-19 ⬆️ Submitted  ✅
--












