/*

 Date: 23/01/2019 13:07:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_type_relation
-- ----------------------------
DROP TABLE IF EXISTS `xys_type_relation`;
CREATE TABLE `xys_type_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关系表主键',
  `merchant_id` int(11) NOT NULL COMMENT '商户ID',
  `type_id` int(11) NOT NULL COMMENT '类型ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_merchant_id` (`merchant_id`) USING BTREE COMMENT 'merchant索引',
  KEY `IX_type_id` (`type_id`) USING BTREE COMMENT 'type索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户类型关系表\n';

SET FOREIGN_KEY_CHECKS = 1;
