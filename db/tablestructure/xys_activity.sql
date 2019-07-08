/*

 Date: 23/01/2019 13:03:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_activity
-- ----------------------------
DROP TABLE IF EXISTS `xys_activity`;
CREATE TABLE `xys_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动id',
  `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
  `activity_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '活动类型：默认0拼团、1优惠、2抢购',
  `activity_start_time` datetime DEFAULT NULL COMMENT '活动开始时间',
  `activity_end_time` datetime DEFAULT NULL COMMENT '活动结束时间',
  `cover_pic` varchar(255) DEFAULT NULL COMMENT '活动封面图',
  `activity_details` varchar(255) DEFAULT NULL COMMENT '活动详情',
  `matter` varchar(255) DEFAULT NULL COMMENT '注意事项',
  `activity_state` tinyint(4) NOT NULL DEFAULT '2' COMMENT '活动状态：默认 2  0已上架、1已下架、2待上架、3已满',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
  `activity_param_id` int(11) DEFAULT NULL COMMENT '活动参数id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  `goods_tag` varchar(32) DEFAULT NULL COMMENT '微信商品标记',
  `discount_price` decimal(20,2) NOT NULL COMMENT '活动优惠后金额',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_activity_type` (`activity_type`) USING BTREE COMMENT 'type索引',
  KEY `IX_activity_state` (`activity_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_activity_goods_id` (`goods_id`) USING BTREE COMMENT 'goodsid索引',
  KEY `IX_activity_param` (`activity_param_id`) USING BTREE COMMENT 'param索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_activity
-- ----------------------------

-- 结构同步
ALTER TABLE `szxys`.`xys_activity` MODIFY COLUMN `matter` VARCHAR ( 255 ) CHARACTER
SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '注意事项' AFTER `activity_details`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--


