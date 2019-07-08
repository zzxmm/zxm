/*

 Date: 23/01/2019 13:06:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_store
-- ----------------------------
DROP TABLE IF EXISTS `xys_store`;
CREATE TABLE `xys_store` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '门店ID',
  `store_name` varchar(64) DEFAULT NULL COMMENT '门店名称',
  `mer_id` int(11) NOT NULL COMMENT '商户ID',
  `address` varchar(64) NOT NULL COMMENT '详细地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `store_state` tinyint(4) DEFAULT '0' COMMENT '门店状态：0营业、1停业',
  `province` varchar(32) DEFAULT NULL COMMENT '省',
  `city` varchar(32) NOT NULL COMMENT '市',
  `area` varchar(32) DEFAULT NULL COMMENT '区',
  `business_hours` varchar(50) DEFAULT NULL COMMENT '营业时间',
  `servicenum` tinyint(4) DEFAULT '0' COMMENT '服务号关联 0关联 , 1解绑',
  `longitude` double(9,6) NOT NULL DEFAULT '0.000000' COMMENT '经度',
  `latitude` double(8,6) NOT NULL DEFAULT '0.000000' COMMENT '纬度',
  `geocoding` varchar(64) DEFAULT NULL COMMENT '地理编码',
  `store_center_id` int(11) DEFAULT NULL COMMENT '门店中心门店ID',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人ID',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `delted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识  默认0未删除、1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_mer_id` (`mer_id`) USING BTREE COMMENT 'mer索引',
  KEY `IX_state` (`store_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_store_center_id` (`store_center_id`) USING BTREE COMMENT 'centerid索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='门店表\n';

SET FOREIGN_KEY_CHECKS = 1;
