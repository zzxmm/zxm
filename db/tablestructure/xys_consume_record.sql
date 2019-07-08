/*

 Date: 23/01/2019 13:04:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_consume_record
-- ----------------------------
DROP TABLE IF EXISTS `xys_consume_record`;
CREATE TABLE `xys_consume_record` (
  `cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cbatch_id` int(11) DEFAULT NULL COMMENT '批次ID',
  `cdiscount_id` bigint(20) DEFAULT NULL COMMENT '优惠ID',
  `cdiscount_type` varchar(32) DEFAULT NULL COMMENT '优惠类型 : 0 全场代金券',
  `cdiscount_money` decimal(20,0) DEFAULT NULL COMMENT '优惠金额',
  `corder_money` decimal(20,0) DEFAULT NULL COMMENT '订单总金额',
  `ctransaction_type` varchar(32) DEFAULT NULL COMMENT '交易类型   :   0 支付   ',
  `cpayment_number` varchar(32) DEFAULT NULL COMMENT '支付单号',
  `cconsume_time` datetime DEFAULT NULL COMMENT '消耗时间',
  `cconsume_mer` int(11) DEFAULT NULL COMMENT '消耗商户号',
  `cequipment_number` varchar(20) DEFAULT NULL COMMENT '设备号',
  `cbank_serialnumber` varchar(32) DEFAULT NULL COMMENT '银行流水号',
  `ccreate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ccreator_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `cinformation` varchar(64) DEFAULT NULL COMMENT '单品信息',
  PRIMARY KEY (`cid`) USING BTREE,
  UNIQUE KEY `IX_payment_number` (`cpayment_number`) USING BTREE COMMENT '支付单号 索引',
  KEY `IX_batch_id` (`cbatch_id`) USING BTREE COMMENT '批次号索引',
  KEY `IX_discount_id` (`cdiscount_id`) USING BTREE COMMENT '优惠ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='卡卷使用记录表';

SET FOREIGN_KEY_CHECKS = 1;
