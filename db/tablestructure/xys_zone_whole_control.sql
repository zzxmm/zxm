/*
 Date: 21/05/2019 12:16:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_zone_whole_control
-- ----------------------------
DROP TABLE IF EXISTS `xys_zone_whole_control`;
CREATE TABLE `xys_zone_whole_control` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL COMMENT '全局类型 :  0 分销',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 :  0 上线 1下线  默认 1',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '操作人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `IX_STATE` (`state`) USING BTREE COMMENT '全局状态索引',
  KEY `IX_TYPE` (`type`) USING BTREE COMMENT '全局类型索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='全局活动上线控制表';

-- ----------------------------
-- Records of xys_zone_whole_control
-- ----------------------------
BEGIN;
INSERT INTO `xys_zone_whole_control` VALUES (1, 0, 0, 1, '2019-05-21 12:00:22');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_zone_whole_control
-- ----------------------------

-- 增加全局类型
ALTER TABLE `szxys`.`xys_zone_whole_control`
MODIFY COLUMN `type` tinyint(4) NOT NULL COMMENT '全局类型 :  0 分销  1积分' AFTER `id`;

--
--  2019-6-19 ⬆️ Submitted  ✅
--



