/*

 Date: 21/05/2019 12:17:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_extend_rule
-- ----------------------------
DROP TABLE IF EXISTS `xys_extend_rule`;
CREATE TABLE `xys_extend_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rule_name` varchar(56) DEFAULT NULL COMMENT '规则名称',
  `rule_type` tinyint(4) DEFAULT NULL COMMENT '规则类型 : 0 返佣 1 邀请 2 提现',
  `details` varchar(5000) DEFAULT NULL COMMENT '规则详情',
  `inviter_reward` decimal(20,2) DEFAULT NULL COMMENT '邀请奖励',
  `restricted_up` decimal(20,2) DEFAULT NULL COMMENT '提现上限',
  `restricted_low` decimal(20,2) DEFAULT NULL COMMENT '提现下限',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `IX_RULE_TYPE` (`rule_type`) USING BTREE COMMENT '规则类型索引',
  KEY `IX_restricted` (`restricted_up`) USING BTREE COMMENT '提现上限索引',
  KEY `IX_restricted_low` (`restricted_low`) USING BTREE COMMENT '提现下限索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='分销-规则表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_extend_rule
-- ----------------------------

-- 增加提现间隔
ALTER TABLE `szxys`.`xys_extend_rule`
DROP COLUMN `inviter_reward`,
ADD COLUMN `restricted_interval` int(11) NULL COMMENT '提现间隔' AFTER `last_edit_id`;
ALTER TABLE `szxys`.`xys_extend_rule`
DROP COLUMN `rule_name`,
DROP COLUMN `rule_type`;

--
--  2019-6-24 ⬆️ Submitted  ✅
--






