/*

 Date: 23/01/2019 13:07:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_wccard_card
-- ----------------------------
DROP TABLE IF EXISTS `xys_wccard_card`;
CREATE TABLE `xys_wccard_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `card_type` varchar(32) DEFAULT NULL COMMENT '卡卷类型',
  `default_detail` varchar(255) DEFAULT NULL COMMENT '优惠详情',
  `card_score` int(11) DEFAULT '0' COMMENT '兑换积分',
  `card_price` decimal(20,0) DEFAULT '0' COMMENT '兑换金额',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `wc_chat_card_id` varchar(32) DEFAULT NULL COMMENT '微信公众号卡卷ID',
  `mutual_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '互通状态: 0未互通   1已互通 默认0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识 0正常 1删除 默认0',
  `coupon_name` varchar(255) DEFAULT NULL COMMENT '卡券全称',
  `coupon_shot_name` varchar(32) DEFAULT NULL COMMENT '卡券简称',
  `coupon_valid_time_start` datetime DEFAULT NULL COMMENT '卡券有效期限起始',
  `coupon_valid_time_end` datetime DEFAULT NULL COMMENT '卡券有效期限截止',
  `mer_id` int(11) DEFAULT NULL COMMENT '商户id',
  `bank_id` int(11) DEFAULT NULL COMMENT '银行id',
  `describes` varchar(500) DEFAULT NULL COMMENT '描述',
  `matter` varchar(2000) DEFAULT NULL COMMENT '事项',
  `price` decimal(20,2) DEFAULT NULL COMMENT '卡券金额',
  `discount_price` decimal(20,2) DEFAULT NULL COMMENT '卡券优惠后金额',
  `coupon_stock_id` varchar(50) DEFAULT NULL COMMENT '微信代金券批次id',
  `coupon_image` varchar(255) DEFAULT NULL COMMENT '卡券图片地址',
  `coupon_stocks` int(11) NOT NULL DEFAULT '0' COMMENT '卡卷库存',
  PRIMARY KEY (`id`),
  KEY `IX_WCCHAT_ID` (`wc_chat_card_id`) USING BTREE COMMENT '微信公众号卡卷ID索引',
  KEY `IX_DELETED` (`deleted`) USING BTREE COMMENT '删除标识索引',
  KEY `IX_CARD_SCORE` (`card_score`) USING BTREE COMMENT '兑换积分索引',
  KEY `IX_CARD_PRICE` (`card_price`) USING BTREE COMMENT '兑换金额索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信公众号卡卷表(-含商户号卡卷信息)';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_wccard_card
-- ----------------------------

-- 结构同步
ALTER TABLE `szxys`.`xys_wccard_card` MODIFY COLUMN `coupon_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡券全称' AFTER `deleted`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--


