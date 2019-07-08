/*

 Date: 23/01/2019 13:04:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_order
-- ----------------------------
DROP TABLE IF EXISTS `xys_order`;
CREATE TABLE `xys_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` varchar(150) NOT NULL COMMENT '内部订单号',
  `wechat_order_no` varchar(255) DEFAULT NULL COMMENT '微信订单号',
  `order_amount` decimal(20,2) DEFAULT NULL COMMENT '订单金额',
  `order_state` tinyint(4) NOT NULL DEFAULT '3' COMMENT '订单状态：0已发放、1已退款、2未支付、3未发放、4部分发放、5已撤销 ',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `wechat_openid` varchar(32) DEFAULT NULL COMMENT '用户微信openid',
  `refresh_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
  `cheap_price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '卡券优惠金额',
  `actual_price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '实际支付金额',
  `order_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单类型:  0 正常订单 , 1 拼团订单  2运营H5订单  3 公众号订单',
  `main_openid` varchar(64) DEFAULT NULL COMMENT '主商户openid',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_order_state` (`order_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_wechat_openid` (`wechat_openid`) USING BTREE COMMENT 'openid索引',
  KEY `IX_main_openid` (`main_openid`) USING BTREE COMMENT 'mainopenid索引',
  KEY `UN_order_no` (`order_no`) USING BTREE COMMENT 'orderno索引',
  KEY `IX_ORCER_TYPE` (`order_type`) USING BTREE COMMENT '订单类型索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

SET FOREIGN_KEY_CHECKS = 1;

--  2019-4-15
--  新增订单类型 串码
ALTER TABLE `szxys`.`xys_order` MODIFY COLUMN `order_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '订单类型:  0 正常订单 , 1 拼团订单  2运营H5订单  3 公众号订单  4串码订单' AFTER `actual_price`;

--
--  2019-4-19 ⬆️ Submitted  ✅
--

-- 结构同步
ALTER TABLE `szxys`.`xys_order` MODIFY COLUMN `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内部订单号' AFTER `id`;
ALTER TABLE `szxys`.`xys_order` MODIFY COLUMN `wechat_order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信订单号' AFTER `order_no`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--

-- 增加用户ID
ALTER TABLE `szxys`.`xys_order`
ADD COLUMN `user_id` int(11) NULL COMMENT '用户ID' AFTER `create_time`;
ALTER TABLE `szxys`.`xys_order`
DROP INDEX `IX_wechat_openid`,
ADD INDEX `IX_USER_ID`(`user_id`) USING BTREE COMMENT '用户ID索引';

--
--  2019-6-19 ⬆️ Submitted  ✅
--














