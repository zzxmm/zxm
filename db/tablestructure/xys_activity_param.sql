/*

 Date: 23/01/2019 13:03:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_activity_param
-- ----------------------------
DROP TABLE IF EXISTS `xys_activity_param`;
CREATE TABLE `xys_activity_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动参数id',
  `up_limit_people` int(255) DEFAULT NULL COMMENT '开团人数',
  `subtract_price` decimal(20,2) DEFAULT NULL COMMENT '每人立减',
  `subtract_limit_price` decimal(20,2) DEFAULT NULL COMMENT '立减上限',
  `group_subtract_price` decimal(20,2) DEFAULT NULL COMMENT '开团立减',
  `preferen_amount` decimal(20,2) DEFAULT NULL COMMENT '优惠额度',
  `quick_buy_price` decimal(20,2) DEFAULT NULL COMMENT '抢购价格',
  `day_limit` int(255) DEFAULT NULL COMMENT '单日限制',
  `activity_limit` varchar(255) DEFAULT NULL COMMENT '活动限制',
  `account_limit` varchar(255) DEFAULT NULL COMMENT '账号限制',
  `part_activity_num` int(11) DEFAULT NULL COMMENT '参与活动数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动参数表\n';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_activity_param
-- ----------------------------

-- 结构同步
ALTER TABLE `szxys`.`xys_activity_param` MODIFY COLUMN `up_limit_people` int(11) NULL DEFAULT NULL COMMENT '开团人数' AFTER `id`;
ALTER TABLE `szxys`.`xys_activity_param` MODIFY COLUMN `day_limit` int(11) NULL DEFAULT NULL COMMENT '单日限制' AFTER `quick_buy_price`;
ALTER TABLE `szxys`.`xys_activity_param` MODIFY COLUMN `activity_limit` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动限制' AFTER `day_limit`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--

