/*

 Date: 30/05/2019 12:49:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_integral_record
-- ----------------------------
DROP TABLE IF EXISTS `xys_integral_record`;
CREATE TABLE `xys_integral_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(32) DEFAULT NULL COMMENT '用户id',
  `openid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户OPENID',
  `integral_type` tinyint(4) NOT NULL COMMENT '积分类型 ：0购买 1分享 2消费 3 新人初始化',
  `record_type` tinyint(4) NOT NULL COMMENT '记录类型 : 0 获取积分 1消费积分',
  `integral_number` int(11) DEFAULT NULL COMMENT '积分记录数量',
  `coupon_id` int(11) DEFAULT NULL COMMENT '卡券id',
  `order_no` varchar(150) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '内部订单号',
  `integral_save_money` decimal(20,2) DEFAULT NULL COMMENT '积分抵扣钱数 ',
  `final_cheap_price` decimal(20,2) DEFAULT NULL COMMENT '抵扣后卡券的金额 ',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `nick_name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IX_openid` (`user_id`) USING BTREE COMMENT '微信ID索引',
  KEY `IX_coupon_id` (`coupon_id`) USING BTREE COMMENT '卡券id索引',
  KEY `IX_create_time` (`create_time`) USING BTREE COMMENT '创建时间索引',
  KEY `IX_integral_type` (`integral_type`) USING BTREE COMMENT '积分类型索引',
  KEY `IX_record_type` (`record_type`) USING BTREE COMMENT '记录类型索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='积分记录表';

SET FOREIGN_KEY_CHECKS = 1;
