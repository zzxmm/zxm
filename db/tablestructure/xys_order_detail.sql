/*

 Date: 23/01/2019 13:06:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `xys_order_detail`;
CREATE TABLE `xys_order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单详情id',
  `order_id` int(11) NOT NULL COMMENT '订单id',
  `goods_id` varchar(32) NOT NULL COMMENT '商品id',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `goods_num` int(11) NOT NULL COMMENT '商品数量',
  `good_price` decimal(20,2) NOT NULL COMMENT '商品单价',
  `mer_id` int(11) DEFAULT NULL COMMENT '商户id',
  `mer_name` varchar(255) DEFAULT NULL COMMENT '商户名称',
  `bank_id` int(11) DEFAULT NULL COMMENT '银行id',
  `bank_name` varchar(255) DEFAULT NULL COMMENT '银行名称',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_order_id` (`order_id`) USING BTREE COMMENT 'order索引',
  KEY `IX_goods_id` (`goods_id`) USING BTREE COMMENT 'goods索引',
  KEY `IX_mer_id` (`mer_id`) USING BTREE COMMENT 'mer索引',
  KEY `IX_bank_id` (`bank_id`) USING BTREE COMMENT 'bank索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详情表\n';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
--  DDL for xys_order_detail
-- ----------------------------

--  2019-1-24
--  取消 商户ID 银行ID 为必填项
ALTER TABLE `szxys`.`xys_order_detail`
MODIFY COLUMN `mer_id` int(11) NULL COMMENT '商户id' AFTER `good_price`,
MODIFY COLUMN `bank_id` int(11) NULL COMMENT '银行id' AFTER `mer_name`;

--
--  2018-*-* ⬆️ Submitted  ✅
--

-- 结构同步
ALTER TABLE `szxys`.`xys_order_detail` MODIFY COLUMN `goods_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称' AFTER `goods_id`;
ALTER TABLE `szxys`.`xys_order_detail` MODIFY COLUMN `mer_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户名称' AFTER `mer_id`;
ALTER TABLE `szxys`.`xys_order_detail` MODIFY COLUMN `bank_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行名称' AFTER `bank_id`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--
