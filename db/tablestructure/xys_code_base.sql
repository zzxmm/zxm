/*

 Date: 15/04/2019 10:18:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_code_base
-- ----------------------------
DROP TABLE IF EXISTS `xys_code_base`;
CREATE TABLE `xys_code_base` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `base_name` varchar(32) DEFAULT NULL COMMENT '串码库名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='串码活动-串码库';

SET FOREIGN_KEY_CHECKS = 1;
