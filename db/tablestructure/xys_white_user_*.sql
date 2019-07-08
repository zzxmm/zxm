/*

 Date: 03/04/2019 11:19:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_white_user_*
-- ----------------------------
DROP TABLE IF EXISTS `xys_white_user_*`;
CREATE TABLE `xys_white_user_1943105858` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `creator_id` int(11) DEFAULT NULL COMMENT '录入人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `IX_NI_PHONE`(`phone`) USING BTREE COMMENT '手机号唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='*-白名单用户表';

SET FOREIGN_KEY_CHECKS = 1;
