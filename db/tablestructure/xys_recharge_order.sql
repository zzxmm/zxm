
-- ----------------------------
-- Table structure for xys_recharge_order
-- ----------------------------
DROP TABLE IF EXISTS `xys_recharge_order`;
CREATE TABLE `xys_recharge_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` varchar(48) DEFAULT NULL COMMENT '内部订单号',
  `wechat_order_no` varchar(48) DEFAULT NULL COMMENT '微信订单号',
  `order_amount` decimal(20,2) DEFAULT NULL COMMENT '订单金额',
  `order_state` tinyint(4) NOT NULL DEFAULT '2' COMMENT '充值订单状态：0已充值、1已退款、2未支付、3未充值 4、已支付  、5已撤销    默认2',
  `cheap_price` decimal(20,2) DEFAULT NULL COMMENT '订单优惠金额',
  `actual_price` decimal(20,2) DEFAULT NULL COMMENT '实际支付金额',
  `order_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '充值订单类型: 0话费充值 默认0',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
  `goods_name` varchar(64) DEFAULT NULL COMMENT '商品名称',
  `good_price` decimal(10,2) DEFAULT NULL COMMENT '商品单价',
  `wechat_openid` varchar(48) DEFAULT NULL COMMENT '用户微信openid',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `refresh_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
  PRIMARY KEY (`id`),
  KEY `IX_ORDER_NO` (`order_no`) USING BTREE COMMENT '订单号索引',
  KEY `IX_GOODS_ID` (`goods_id`) USING BTREE COMMENT '商品ID 索引',
  KEY `IX_wechat_openid` (`wechat_openid`) USING BTREE COMMENT '用户openID索引',
  KEY `IX_order_type` (`order_type`) USING BTREE COMMENT '订单类型索引',
  KEY `IX_order_state` (`order_state`) USING BTREE COMMENT '订单状态索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='充值订单表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
--  DDL for xys_recharge_order
-- ----------------------------

--  2019-3-21
--  新增充值手机号
ALTER TABLE `szxys`.`xys_recharge_order`
ADD COLUMN `recharge_tel` varchar(32) NULL COMMENT '充值手机号' AFTER `refresh_time`;

--
--  2019-3-21 ⬆️ Submitted  ✅
--

-- 结构同步
ALTER TABLE `szxys`.`xys_recharge_record` MODIFY COLUMN `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内部支付订单号' AFTER `id`;
ALTER TABLE `szxys`.`xys_recharge_record` MODIFY COLUMN `wechat_openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户微信openID' AFTER `state`;
ALTER TABLE `szxys`.`xys_recharge_record` MODIFY COLUMN `recharge_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内部充值订单号' AFTER `telephone_bill`;
ALTER TABLE `szxys`.`xys_recharge_record` MODIFY COLUMN `third_order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务商订单号' AFTER `recharge_no`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--

-- 增加用户ID
ALTER TABLE `szxys`.`xys_recharge_order`
ADD COLUMN `user_id` int(11) NULL COMMENT '用户ID' AFTER `good_price`;
ALTER TABLE `szxys`.`xys_recharge_order`
DROP INDEX `IX_wechat_openid`,
ADD INDEX `IX_USER_ID`(`user_id`) USING BTREE COMMENT '用户ID索引';

--
--  2019-6-19 ⬆️ Submitted  ✅
--













