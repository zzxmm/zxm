/*

 Date: 21/05/2019 12:16:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_website_customer
-- ----------------------------
DROP TABLE IF EXISTS `xys_website_customer`;
CREATE TABLE `xys_website_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `select_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '选择类型 :  0手赞信用社  1微信支付  默认0',
  `customer_type` tinyint(4) DEFAULT NULL COMMENT '客户身份 : 0 商家  1银行',
  `customer_name` varchar(32) DEFAULT NULL COMMENT '客户名称',
  `phone` varchar(32) DEFAULT NULL COMMENT '联系方式',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `state` tinyint(4) DEFAULT NULL COMMENT '处理状态 0待回访  1已回访',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '处理人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '处理时间',
  PRIMARY KEY (`id`),
  KEY `IX_SELECT` (`select_type`) USING BTREE COMMENT '选择类型索引',
  KEY `IX_TYPE` (`customer_type`) USING BTREE COMMENT '客户类型索引',
  KEY `IX_STATE` (`state`) USING BTREE COMMENT '状态索引',
  KEY `IX_TIME` (`create_time`) USING BTREE COMMENT '时间索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户合作申请记录';

SET FOREIGN_KEY_CHECKS = 1;
