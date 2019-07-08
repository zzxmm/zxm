/*

 Date: 23/01/2019 13:07:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_type
-- ----------------------------
DROP TABLE IF EXISTS `xys_type`;
CREATE TABLE `xys_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `type_name` varchar(32) NOT NULL COMMENT '类型名称',
  `type_shot_name` varchar(32) DEFAULT NULL COMMENT '类型简称',
  `type_introduce` varchar(255) DEFAULT NULL COMMENT '类型简介',
  `matter` varchar(255) DEFAULT NULL COMMENT '注意事项',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `type_belonged` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型对象：默认0商家 , 1优惠',
  `enable_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型状态：默认0隐藏、1显示',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人ID',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_enable_state` (`enable_state`) USING BTREE COMMENT 'state索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='类型表\n';

SET FOREIGN_KEY_CHECKS = 1;
