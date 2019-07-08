/*

 Date: 15/04/2019 10:18:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_code_record
-- ----------------------------
DROP TABLE IF EXISTS `xys_code_record`;
CREATE TABLE `xys_code_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `order_no` varchar(150) NOT NULL COMMENT '内部支付订单号',
  `code` varchar(32) DEFAULT NULL COMMENT '串码code',
  `card_state` tinyint(4) DEFAULT NULL COMMENT '卡券状态：0未发放、1已发放',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_tel` varchar(32) DEFAULT NULL COMMENT '用户电话',
  `wechat_openid` varchar(32) DEFAULT NULL COMMENT '用户微信openID',
  `activity_id` int(11) NOT NULL COMMENT '活动ID',
  `record_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '记录类型  0普通串码 默认0',
  PRIMARY KEY (`id`),
  KEY `IX_CARD_STATE` (`card_state`) USING BTREE COMMENT '卡卷状态索引',
  KEY `IX_ACTIVITY_ID` (`activity_id`) USING BTREE COMMENT '活动ID索引',
  KEY `IX_RECORD_TYPE` (`record_type`) USING BTREE COMMENT '记录类型索引',
  KEY `IX_CODE` (`code`) USING BTREE COMMENT '串码索引',
  KEY `IX_WECHAT_ID` (`wechat_openid`) USING BTREE COMMENT '微信openID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='串码记录表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_code_record
-- ----------------------------

-- 结构同步
ALTER TABLE `szxys`.`xys_code_record` MODIFY COLUMN `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内部支付订单号' AFTER `id`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--

-- 增加用户ID
ALTER TABLE `szxys`.`xys_code_record`
ADD COLUMN `user_id` int(11) NULL COMMENT '用户ID' AFTER `user_tel`;
ALTER TABLE `szxys`.`xys_code_record`
DROP INDEX `IX_WECHAT_ID`,
ADD INDEX `IX_USER_ID`(`user_id`) USING BTREE COMMENT 'userID索引';

--
--  2019-6-19 ⬆️ Submitted  ✅
--









