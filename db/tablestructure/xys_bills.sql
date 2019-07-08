/*

 Date: 23/01/2019 13:03:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_bills
-- ----------------------------
DROP TABLE IF EXISTS `xys_bills`;
CREATE TABLE `xys_bills` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账单id',
  `bills_type` tinyint(4) NOT NULL COMMENT '账单交易类型: 0支出、1 收入',
  `bills_object` tinyint(4) NOT NULL COMMENT '账单交易对象: 0信用社 , 1银行 , 2商户',
  `out_acc_party` tinyint(4) NOT NULL COMMENT '出账方：0信用社 , 1 银行 , 2商户',
  `out_acc_party_id` int(11) NOT NULL COMMENT '出账方ID',
  `out_acc_party_name` varchar(255) DEFAULT NULL COMMENT '出账方名称',
  `out_acc_party_balance` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '出账方账户余额',
  `put_acc_party` tinyint(4) NOT NULL COMMENT '入账方0信用社 , 1 银行 , 2商户',
  `put_acc_party_id` int(11) NOT NULL COMMENT '入账方ID',
  `put_acc_party_name` varchar(255) DEFAULT NULL COMMENT '入账方名称',
  `put_acc_party_balance` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '入账方账户余额',
  `describes` varchar(255) DEFAULT NULL COMMENT '交易描述',
  `transaction_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '交易金额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `creator_name` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人ID',
  `last_edit_name` varchar(32) DEFAULT NULL COMMENT '修改人名称',
  `last_edit_describe` varchar(255) DEFAULT NULL COMMENT '修改描述',
  `pay_acc_party` tinyint(4) NOT NULL COMMENT '资金来源(0信用社 , 1 银行 , 2商户)',
  `pay_acc_id` int(11) NOT NULL COMMENT '资金来源ID',
  `pay_acc_party_name` varchar(255) DEFAULT NULL COMMENT '资金来源方名称',
  `pay_acc_party_balance` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '资金来源余额',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_bills_type` (`bills_type`) USING BTREE COMMENT 'type索引',
  KEY `IX_bills_object` (`bills_object`) USING BTREE COMMENT '所属索引',
  KEY `IX_transaction_amount` (`transaction_amount`) USING BTREE COMMENT '交易金额索引',
  KEY `IX_create_time` (`create_time`) USING BTREE COMMENT 'time索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账单表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_bills
-- ----------------------------

-- 结构同步
ALTER TABLE `szxys`.`xys_bills` MODIFY COLUMN `out_acc_party_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出账方名称' AFTER `out_acc_party_id`;
ALTER TABLE `szxys`.`xys_bills` MODIFY COLUMN `put_acc_party_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入账方名称' AFTER `put_acc_party_id`;
ALTER TABLE `szxys`.`xys_bills` MODIFY COLUMN `last_edit_describe` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改描述' AFTER `last_edit_name`;
ALTER TABLE `szxys`.`xys_bills` MODIFY COLUMN `pay_acc_party_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资金来源方名称' AFTER `pay_acc_id`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--
