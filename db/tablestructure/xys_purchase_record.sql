/*

 Date: 23/01/2019 13:06:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_purchase_record
-- ----------------------------
DROP TABLE IF EXISTS `xys_purchase_record`;
CREATE TABLE `xys_purchase_record` (
  `pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pbatch_id` int(11) DEFAULT NULL COMMENT '批次ID',
  `pdiscount_id` bigint(20) DEFAULT NULL COMMENT '优惠ID',
  `pdiscount_type` varchar(32) DEFAULT NULL COMMENT '优惠类型 : 0 全场定额立减   ',
  `pdiscount_money` decimal(20,0) DEFAULT NULL COMMENT '优惠金额',
  `porder_money` decimal(20,0) DEFAULT NULL COMMENT '订单总金额',
  `ptransaction_type` varchar(12) DEFAULT NULL COMMENT '交易类型   :   0 支付   ',
  `ppayment_number` varchar(32) DEFAULT NULL COMMENT '支付单号',
  `pconsume_time` datetime DEFAULT NULL COMMENT '消耗时间',
  `pconsume_mer` int(11) DEFAULT NULL COMMENT '消耗商户号',
  `pequipment_number` varchar(20) DEFAULT NULL COMMENT '设备号',
  `pbank_serialnumber` varchar(32) DEFAULT NULL COMMENT '银行流水号',
  `pcreate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `pcreator_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`pid`) USING BTREE,
  UNIQUE KEY `IX_payment_number` (`ppayment_number`) USING BTREE COMMENT '支付单号 索引',
  KEY `IX_batch_id` (`pbatch_id`) USING BTREE COMMENT '批次号索引',
  KEY `IX_discount_id` (`pdiscount_id`) USING BTREE COMMENT '优惠ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='卡卷购买记录表';

SET FOREIGN_KEY_CHECKS = 1;
