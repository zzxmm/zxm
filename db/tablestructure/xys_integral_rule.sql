/*

 Date: 30/05/2019 12:49:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_integral_rule
-- ----------------------------
DROP TABLE IF EXISTS `xys_integral_rule`;
CREATE TABLE `xys_integral_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rule_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '规则名称',
  `rule_type` tinyint(4) NOT NULL COMMENT '积分类型： 0购买 1分享 2消费 3新人初始化',
  `day_limit` int(11) NOT NULL DEFAULT '-1' COMMENT '每日限制 ：默认 -1无限制',
  `obtain_integral` int(11) NOT NULL DEFAULT '0' COMMENT '获取积分 ：默认为0',
  `account_limit` int(11) NOT NULL DEFAULT '-1' COMMENT '账号限制 :  默认 -1无限制',
  `rule_details` varchar(2000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '规则详情',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人ID',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `IX_rule_type` (`rule_type`) USING BTREE COMMENT '规则类型索引',
  KEY `IX_day_limit` (`day_limit`) USING BTREE COMMENT '每日限制索引',
  KEY `IX_obtain_integral obtain_integral` (`obtain_integral`) USING BTREE COMMENT '获得积分索引',
  KEY `IX_account_limit` (`account_limit`) USING BTREE COMMENT '账号限制索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='积分规则表';

SET FOREIGN_KEY_CHECKS = 1;
