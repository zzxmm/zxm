/*

 Date: 21/05/2019 12:17:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_extend_exchange
-- ----------------------------
DROP TABLE IF EXISTS `xys_extend_exchange`;
CREATE TABLE `xys_extend_exchange` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(48) DEFAULT NULL COMMENT '用户昵称',
  `user_openid` varchar(32) DEFAULT NULL COMMENT '用户openID',
  `ex_order` varchar(32) DEFAULT NULL COMMENT '提现订单号',
  `exchange_money` decimal(20,2) DEFAULT NULL COMMENT '提现金额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `deal_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '提现状态 :  0 申请提现  1 提现失败  2 提现成功 3 拒绝提现 ',
  `deal_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '处理时间',
  `deal_user` int(11) DEFAULT NULL COMMENT '处理人 ID',
  `notes` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `IX_USER_OPENID` (`user_openid`) USING BTREE COMMENT '用户openid索引',
  KEY `IX_DEAL_STATE` (`deal_state`) USING BTREE COMMENT '提现状态索引',
  KEY `IX_TIME` (`create_time`) USING BTREE COMMENT '创建时间索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分销-提现交易表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
--  DDL for xys_extend_exchange
-- ----------------------------

-- 增加用户ID
ALTER TABLE `szxys`.`xys_extend_exchange`
ADD COLUMN `user_id` int(11) NULL COMMENT '用户ID' AFTER `user_name`;
ALTER TABLE `szxys`.`xys_extend_exchange`
ADD INDEX `IX_USER_ID`(`user_id`) USING BTREE COMMENT '用户ID索引';

--
--  2019-6-19 ⬆️ Submitted  ✅
--














