/*

 Date: 23/01/2019 13:05:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_coupon_store
-- ----------------------------
DROP TABLE IF EXISTS `xys_coupon_store`;
CREATE TABLE `xys_coupon_store` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `coupon_id` int(11) NOT NULL COMMENT '卡券id',
  `store_id` int(11) NOT NULL COMMENT '门店id',
  `mer_id` int(11) NOT NULL COMMENT '商户id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_coupon_id` (`coupon_id`) USING BTREE COMMENT 'coupon索引',
  KEY `IX_store_id` (`store_id`) USING BTREE COMMENT 'store索引',
  KEY `IX_mer_id` (`mer_id`) USING BTREE COMMENT 'mer索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='卡卷门店关系表';

SET FOREIGN_KEY_CHECKS = 1;
