/*

 Date: 23/01/2019 13:06:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_sell_rule
-- ----------------------------
DROP TABLE IF EXISTS `xys_sell_rule`;
CREATE TABLE `xys_sell_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `sell_period` tinyint(4) DEFAULT NULL COMMENT '星期(周一是1，周二是2以此类推)',
  `sell_time_start` time DEFAULT NULL COMMENT '售卖开始时间',
  `sell_time_end` time DEFAULT NULL COMMENT '售卖结束时间',
  `sell_number` int(11) DEFAULT '0' COMMENT '售卖数量 : 0 无限制',
  `rule_type` tinyint(4) DEFAULT NULL COMMENT '规则类型: 0卡卷 1活动',
  `sell_goods` int(11) DEFAULT NULL COMMENT '售卖商品ID',
  PRIMARY KEY (`id`),
  KEY `IX_sell_goods` (`sell_goods`) USING BTREE COMMENT '商品索引',
  KEY `IX_RULE_TYPE` (`rule_type`) USING BTREE COMMENT '规则类型索引',
  KEY `IX_SELL_PERIOD` (`sell_period`) USING BTREE COMMENT '星期索引',
  KEY `IX_SELL_NUMBER` (`sell_number`) USING BTREE COMMENT '售卖数量索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='售卖规则表';

SET FOREIGN_KEY_CHECKS = 1;
