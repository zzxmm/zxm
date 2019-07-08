/*

 Date: 23/01/2019 13:04:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_coupon
-- ----------------------------
DROP TABLE IF EXISTS `xys_coupon`;
CREATE TABLE `xys_coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '卡券id',
  `coupon_name` varchar(64) NOT NULL COMMENT '卡券全称',
  `coupon_shot_name` varchar(32) DEFAULT NULL COMMENT '卡券简称',
  `coupon_type` tinyint(4) NOT NULL COMMENT '卡券类型：默认0代金券',
  `coupon_valid_time_start` datetime NOT NULL COMMENT '卡券有效期限起始',
  `coupon_valid_time_end` datetime NOT NULL COMMENT '卡券有效期限截止',
  `coupon_stocks` int(11) NOT NULL COMMENT '卡券库存',
  `coupon_state` tinyint(4) NOT NULL DEFAULT '2' COMMENT '卡券状态：0已上架、1待上架、2售罄、3已下架',
  `mer_id` int(11) NOT NULL COMMENT '商户id',
  `bank_id` int(11) NOT NULL COMMENT '银行id',
  `upshelf_time` datetime DEFAULT NULL COMMENT '卡券上架时间',
  `downshelf_time` datetime DEFAULT NULL COMMENT '卡券下架时间',
  `limit_buy_type` tinyint(4) DEFAULT '0' COMMENT '限购类型 ：默认0无限购、1单人限购、2人数限购、3数量限购、4期间限购',
  `limit_person_num` int(11) DEFAULT NULL COMMENT '限购人数',
  `limit_buy_num` int(11) DEFAULT NULL COMMENT '限购数量',
  `describes` varchar(500) DEFAULT NULL COMMENT '描述',
  `matter` varchar(2000) DEFAULT NULL COMMENT '事项',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  `is_preferen` tinyint(4) NOT NULL DEFAULT '0' COMMENT '卡券是否特惠：0非特惠、1特惠',
  `is_select` tinyint(4) NOT NULL DEFAULT '0' COMMENT '卡券是否精选：0非精选、1精选',
  `priority` int(11) DEFAULT NULL COMMENT '卡券优先级',
  `goods_tag` varchar(50) DEFAULT NULL COMMENT '微信商品标记',
  `price` decimal(20,2) NOT NULL COMMENT '卡券金额',
  `discount_price` decimal(20,2) NOT NULL COMMENT '卡券优惠后金额',
  `coupon_stock_id` varchar(50) NOT NULL COMMENT '微信代金券批次id',
  `coupon_image` varchar(255) DEFAULT NULL COMMENT '卡券图片地址',
  `coupon_use_range` varchar(5000) DEFAULT NULL COMMENT '卡卷使用范围',
  `is_sell` tinyint(4) DEFAULT '1' COMMENT '是否售卖: 0是 1否  默认1否',
  `is_display` tinyint(4) DEFAULT '1' COMMENT '是否显示: 0是 1否  默认1否',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_coupon_state` (`coupon_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_time_start` (`coupon_valid_time_start`) USING BTREE COMMENT 'start索引',
  KEY `IX_time_end` (`coupon_valid_time_end`) USING BTREE COMMENT 'end索引',
  KEY `IX_create_time` (`create_time`) USING BTREE COMMENT '时间索引',
  KEY `IX_coupon_stock_id` (`coupon_stock_id`) USING BTREE COMMENT '批次索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='卡卷表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_coupon
-- ----------------------------

-- 卡卷返佣金额
ALTER TABLE `szxys`.`xys_coupon`
ADD COLUMN `return_money` decimal(22, 2) NOT NULL DEFAULT 0 COMMENT '分销返佣金额' AFTER `is_display`;

--
--  2019-5-21 ⬆️ Submitted  ✅
--

-- 增加积分字段
ALTER TABLE `szxys`.`xys_coupon`
ADD COLUMN `is_partake_cash` tinyint(4) NOT NULL COMMENT '是否参与积分抵现 : 0参与 1不参与' AFTER `return_money`,
ADD COLUMN `use_integral` int(11) NULL COMMENT '每个卡卷可使用积分抵用' AFTER `is_partake_cash`,
ADD COLUMN `integral_cash` double(10, 2) NULL COMMENT '积分可抵现' AFTER `use_integral`,
ADD COLUMN `integral_sign` varchar(32) NULL COMMENT '积分购买标记' AFTER `integral_cash`,
ADD COLUMN `is_integral` tinyint(4) NOT NULL COMMENT '是否返积分标识   0 是 , 1 否' AFTER `integral_sign`,
ADD COLUMN `make_integral` int(11) NULL COMMENT '每个卡卷可以获得的积分' AFTER `is_integral`;

-- 增加微信卡包ID
ALTER TABLE `szxys`.`xys_coupon`
ADD COLUMN `card_package_id` varchar(50) NULL COMMENT '微信卡包id' AFTER `make_integral`;

ALTER TABLE `szxys`.`xys_coupon`
MODIFY COLUMN `is_partake_cash` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否参与积分抵现 : 0参与 1不参与' AFTER `return_money`,
MODIFY COLUMN `is_integral` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否返积分标识   0 是 , 1 否' AFTER `integral_sign`;

ALTER TABLE `szxys`.`xys_coupon`
MODIFY COLUMN `is_partake_cash` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否参与积分抵现 : 0参与 1不参与' AFTER `return_money`,
MODIFY COLUMN `use_integral` int(11) NOT NULL COMMENT '每个卡卷可使用积分抵用' AFTER `is_partake_cash`,
MODIFY COLUMN `integral_cash` double(10, 2) NOT NULL DEFAULT 0 COMMENT '积分可抵现' AFTER `use_integral`,
MODIFY COLUMN `is_integral` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否返积分标识   0 是 , 1 否' AFTER `integral_sign`,
MODIFY COLUMN `make_integral` int(11) NOT NULL DEFAULT 0 COMMENT '每个卡卷可以获得的积分' AFTER `is_integral`;

ALTER TABLE `szxys`.`xys_coupon`
MODIFY COLUMN `use_integral` int(11) NOT NULL DEFAULT 0 COMMENT '每个卡卷可使用积分抵用' AFTER `is_partake_cash`;

--
--  2019-6-19 ⬆️ Submitted  ✅
--












