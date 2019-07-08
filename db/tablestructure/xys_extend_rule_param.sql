/*

 Date: 21/05/2019 12:17:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_extend_rule_param
-- ----------------------------
DROP TABLE IF EXISTS `xys_extend_rule_param`;
CREATE TABLE `xys_extend_rule_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` int(11) DEFAULT NULL COMMENT '级别',
  `min_num` int(11) DEFAULT NULL COMMENT '最少人数',
  `max_num` int(11) DEFAULT NULL COMMENT '最多人数',
  `percentage` decimal(10,6) DEFAULT NULL COMMENT '返佣比例',
  PRIMARY KEY (`id`),
  KEY `IX_MIN` (`min_num`) USING BTREE COMMENT '最少人数索引',
  KEY `IX_MAX` (`max_num`) USING BTREE COMMENT '最大人数索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='分销-反佣等级规则表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_extend_rule_param
-- ----------------------------

-- 增加邀请奖励
ALTER TABLE `szxys`.`xys_extend_rule_param`
ADD COLUMN `inviter_reward` decimal(20, 2) NULL COMMENT '邀请奖励' AFTER `percentage`;

--
--  2019-6-24 ⬆️ Submitted  ✅
--




