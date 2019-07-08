/*

 Date: 23/01/2019 13:06:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_group
-- ----------------------------
DROP TABLE IF EXISTS `xys_group`;
CREATE TABLE `xys_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` int(11) DEFAULT NULL COMMENT '活动ID',
  `spell_state` tinyint(4) DEFAULT NULL COMMENT '拼团状态：0进行中、1拼成 . 2 已结束',
  `start_time` datetime DEFAULT NULL COMMENT '开团时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `remainder` int(255) DEFAULT NULL COMMENT '剩余人数',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_activity_id` (`activity_id`) USING BTREE COMMENT 'activity索引',
  KEY `IX_spell_state` (`spell_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_remainder` (`remainder`) USING BTREE COMMENT 'remainder索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团表\n';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_group
-- ----------------------------

-- 结构同步
ALTER TABLE `szxys`.`xys_group` MODIFY COLUMN `remainder` int(11) NULL DEFAULT NULL COMMENT '剩余人数' AFTER `end_time`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--
