
-- ----------------------------
-- Table structure for xys_recharge_record
-- ----------------------------
DROP TABLE IF EXISTS `xys_recharge_record`;
CREATE TABLE `xys_recharge_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` varchar(48) DEFAULT NULL COMMENT '内部支付订单号',
  `state` tinyint(4) DEFAULT NULL COMMENT '充值状态：0充值中、1充值成功、2充值失败、3已退款',
  `wechat_openid` varchar(48) DEFAULT NULL COMMENT '用户微信openID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `record_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '充值记录类型  0话费充值  默认0',
  `recharge_tel` varchar(32) DEFAULT NULL COMMENT '充值电话',
  `create_time` datetime DEFAULT NULL COMMENT '下单时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '订单处理时间',
  `telephone_bill` decimal(20,2) DEFAULT NULL COMMENT '充值金额',
  `recharge_no` varchar(48) DEFAULT NULL COMMENT '内部充值订单号',
  `third_order_no` varchar(48) DEFAULT NULL COMMENT '服务商订单号',
  `third_order_cash` decimal(20,2) DEFAULT NULL COMMENT '服务商订单金额',
  PRIMARY KEY (`id`),
  KEY `IX_order_no` (`order_no`) USING BTREE COMMENT '订单号索引',
  KEY `IX_state` (`state`) USING BTREE COMMENT '订单状态索引',
  KEY `IX_goods_id` (`goods_id`) USING BTREE COMMENT '商品ID索引',
  KEY `IX_record_type` (`record_type`) USING BTREE COMMENT '记录类型索引',
  KEY `IX_wechat_openid` (`wechat_openid`) USING BTREE COMMENT '用户openID索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='充值记录表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
--  DDL for xys_recharge_record
-- ----------------------------

-- 增加用户ID
ALTER TABLE `szxys`.`xys_recharge_record`
ADD COLUMN `user_id` int(11) NULL COMMENT '用户ID' AFTER `state`;
ALTER TABLE `szxys`.`xys_recharge_record`
DROP INDEX `IX_wechat_openid`,
ADD INDEX `IX_USER_ID`(`user_id`) USING BTREE COMMENT '用户ID索引';

--
--  2019-6-19 ⬆️ Submitted  ✅
--











