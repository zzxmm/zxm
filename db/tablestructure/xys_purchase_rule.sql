/*

 Date: 23/01/2019 13:06:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_purchase_rule
-- ----------------------------
DROP TABLE IF EXISTS `xys_purchase_rule`;
CREATE TABLE `xys_purchase_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `purchase_type` tinyint(4) DEFAULT NULL COMMENT '限购类型: 0 单人限购',
  `purchase_period` tinyint(4) DEFAULT NULL COMMENT '期间限购: 0 单日限购 1 上架期间',
  `purchase_number` int(11) DEFAULT NULL COMMENT '限购数量',
  `rule_type` tinyint(4) DEFAULT NULL COMMENT '规则类型: 0卡卷 1 活动',
  `purchase_goods` int(11) DEFAULT NULL COMMENT '限购商品ID',
  PRIMARY KEY (`id`),
  KEY `IX_purc_goods` (`purchase_goods`) USING BTREE COMMENT '商品索引',
  KEY `IX_PURCHASE_TYPE` (`purchase_type`) USING BTREE COMMENT '限购类型索引',
  KEY `IX_PURCHASE_PERIOD` (`purchase_period`) USING BTREE COMMENT '期间限购索引',
  KEY `IX_RULE_TYPE` (`rule_type`) USING BTREE COMMENT '限购类型索引',
  KEY `IX_PURCHASE_NUMBER` (`purchase_number`) USING BTREE COMMENT '限购数量索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='限购规则表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
--  DDL for xys_purchase_rule
-- ----------------------------

--  2019-3-29
--  新增免支付发卡类型
ALTER TABLE `szxys`.`xys_purchase_rule`
MODIFY COLUMN `rule_type` tinyint(4) NULL DEFAULT NULL COMMENT '规则类型: 0卡卷 1拼团活动  2运营H5活动' AFTER `purchase_number`;

--
--  2019-4-3 ⬆️ Submitted  ✅
--

-- 新增 单月单周 限购
ALTER TABLE `szxys`.`xys_purchase_rule`
MODIFY COLUMN `purchase_period` tinyint(4) NULL DEFAULT NULL COMMENT '期间限购: 0 单日限购 1 上架期间   2 单月限购  3 单周限购' AFTER `purchase_type`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--