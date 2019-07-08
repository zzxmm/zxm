/*

 Date: 21/05/2019 12:16:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_website_text
-- ----------------------------
DROP TABLE IF EXISTS `xys_website_text`;
CREATE TABLE `xys_website_text` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `case_name` varchar(64) DEFAULT NULL COMMENT '案例名称',
  `introduction` varchar(2000) DEFAULT NULL COMMENT '案例简介',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `IX_DATE` (`create_time`) USING BTREE COMMENT '时间索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='官网图文表';

SET FOREIGN_KEY_CHECKS = 1;
