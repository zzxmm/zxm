/*

 Date: 23/01/2019 13:04:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_cardrecord
-- ----------------------------
DROP TABLE IF EXISTS `xys_cardrecord`;
CREATE TABLE `xys_cardrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '卡券记录id',
  `order_no` varchar(150) NOT NULL COMMENT '订单号',
  `wechat_coupon_id` varchar(32) DEFAULT NULL COMMENT '微信代金券id',
  `mer_id` int(11) DEFAULT NULL COMMENT '商户id',
  `bank_id` int(11) DEFAULT NULL COMMENT '银行id',
  `card_state` tinyint(4) DEFAULT NULL COMMENT '卡券状态：0未发放、1未核销、2已核销、3已过期 、4已退款',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `coupon_valid_time_start` datetime NOT NULL COMMENT '卡券有效期限起始',
  `coupon_valid_time_end` datetime NOT NULL COMMENT '卡券有效期限截止',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `user_tel` varchar(32) DEFAULT NULL COMMENT '用户电话',
  `wechat_openid` varchar(255) DEFAULT NULL COMMENT '用户微信openID',
  `coupon_id` int(11) NOT NULL COMMENT '卡卷ID',
  `record_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '卡卷记录类型  0商户号卡卷  1拼团卡券  2运营H5卡卷  3公众号卡卷 默认0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_card_state` (`card_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_coupon_id` (`coupon_id`) USING BTREE COMMENT 'couponid索引',
  KEY `IX_wechat_openid` (`wechat_openid`(191)) USING BTREE COMMENT 'open索引',
  KEY `UN_order_no` (`order_no`) USING BTREE COMMENT 'orderno索引',
  KEY `IX_record_type` (`record_type`) USING BTREE COMMENT '卡卷记录类型索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='卡卷记录表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
--  DDL for xys_cardrecord
-- ----------------------------

--  2019-3-29
--  新增免支付发卡类型 , 限制字段
ALTER TABLE `szxys`.`xys_cardrecord`
MODIFY COLUMN `record_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '卡卷记录类型  0商户号卡卷  1拼团卡券  2运营H5卡卷  3公众号卡卷 4免支付发卡 默认0' AFTER `coupon_id`;

--  2019-4-2
--  内部订单号NOT NULL 修改为 默认NULL
ALTER TABLE `szxys`.`xys_cardrecord`
MODIFY COLUMN `order_no` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号' AFTER `id`;

--
--  2019-4-3 ⬆️ Submitted  ✅
--

-- 结构同步
ALTER TABLE `szxys`.`xys_cardrecord` DROP INDEX `IX_wechat_openid`;
ALTER TABLE `szxys`.`xys_cardrecord` MODIFY COLUMN `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号' AFTER `id`;
ALTER TABLE `szxys`.`xys_cardrecord` MODIFY COLUMN `wechat_openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户微信openID' AFTER `user_tel`;
ALTER TABLE `szxys`.`xys_cardrecord` MODIFY COLUMN `trade_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡券发放单据号' AFTER `record_type`;
ALTER TABLE `szxys`.`xys_cardrecord` ADD INDEX `IX_wechat_openid`(`wechat_openid`) USING BTREE COMMENT 'open索引';

--
--  2019-5-28 ⬆️ Submitted  ✅
--