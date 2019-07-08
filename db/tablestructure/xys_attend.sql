/*

 Date: 23/01/2019 13:03:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_attend
-- ----------------------------
DROP TABLE IF EXISTS `xys_attend`;
CREATE TABLE `xys_attend` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` int(11) NOT NULL COMMENT '拼团ID',
  `open_id` varchar(255) NOT NULL COMMENT '用户openID',
  `behavior` tinyint(4) DEFAULT NULL COMMENT '用户行为：0开团 , 1 参团',
  `user_state` tinyint(4) DEFAULT NULL COMMENT '用户状态: 0 未支付 , 1 已支付',
  `order_no` varchar(255) DEFAULT NULL COMMENT '内部订单号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_group_id` (`group_id`) USING BTREE COMMENT 'groupid索引',
  KEY `IX_open_id` (`open_id`(191)) USING BTREE COMMENT 'openid索引',
  KEY `IX_user_state` (`user_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_order_no` (`order_no`(191)) USING BTREE COMMENT 'orderno索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参团表\n';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_attend
-- ----------------------------

-- 结构同步
ALTER TABLE `szxys`.`xys_attend` DROP INDEX `IX_open_id`;
ALTER TABLE `szxys`.`xys_attend` DROP INDEX `IX_order_no`;
ALTER TABLE `szxys`.`xys_attend` MODIFY COLUMN `open_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户openID' AFTER `group_id`;
ALTER TABLE `szxys`.`xys_attend` MODIFY COLUMN `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内部订单号' AFTER `user_state`;
ALTER TABLE `szxys`.`xys_attend` ADD INDEX `IX_open_id`(`open_id`) USING BTREE COMMENT 'openid索引';
ALTER TABLE `szxys`.`xys_attend` ADD INDEX `IX_order_no`(`order_no`) USING BTREE COMMENT 'orderno索引';

--
--  2019-5-28 ⬆️ Submitted  ✅
--

-- 增加用户Id
ALTER TABLE `szxys`.`xys_attend`
ADD COLUMN `user_id` int(0) NULL COMMENT '用户Id' AFTER `group_id`;

--
--  2019-6-19 ⬆️ Submitted  ✅
--












