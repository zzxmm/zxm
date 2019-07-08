/*

 Date: 03/04/2019 11:16:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_white_list_base
-- ----------------------------
DROP TABLE IF EXISTS `xys_white_list_base`;
CREATE TABLE `xys_white_list_base` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `base_name` varchar(64) DEFAULT NULL COMMENT '白名单名称',
  `base_sign` varchar(32) DEFAULT NULL COMMENT '名单库标识码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `IX_SIGN_ID` (`base_sign`) USING BTREE COMMENT '标识码索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='白名单库';

SET FOREIGN_KEY_CHECKS = 1;
