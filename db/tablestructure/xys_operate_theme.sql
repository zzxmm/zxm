/*

 Date: 23/01/2019 13:04:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_operate_theme
-- ----------------------------
DROP TABLE IF EXISTS `xys_operate_theme`;
CREATE TABLE `xys_operate_theme` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `background` varchar(255) DEFAULT NULL COMMENT '主题背景图片',
  `card_img` varchar(255) DEFAULT NULL COMMENT '卡卷图片',
  `card_details` varchar(10000) DEFAULT NULL COMMENT '卡卷详情',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人ID',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `card_name` varchar(64) DEFAULT NULL COMMENT '卡卷名称',
  `card_stocks` int(11) NOT NULL DEFAULT '0' COMMENT '卡卷库存  默认0',
  `card_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '卡卷状态: 0上架  1售罄   2下架   默认0',
  `reduce_tag` varchar(32) DEFAULT NULL COMMENT '立减标记',
  `wechat_batches` varchar(32) DEFAULT NULL COMMENT '微信批次号',
  `card_price` decimal(20,2) DEFAULT NULL COMMENT '卡卷金额',
  `discount_price` decimal(20,2) DEFAULT NULL COMMENT '优惠后金额',
  `upshelf_time` datetime DEFAULT NULL COMMENT '上架时间',
  `downshelf_time` datetime DEFAULT NULL COMMENT '下架时间',
  `link_url` varchar(255) DEFAULT NULL COMMENT '页面外链',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识: 0正常  1已删除 默认0',
  `coupon_valid_time_start` datetime DEFAULT NULL COMMENT '卡卷有效期始',
  `coupon_valid_time_end` datetime DEFAULT NULL COMMENT '卡卷有效期止',
  PRIMARY KEY (`id`),
  KEY `IX_CARD_STATE` (`card_state`) USING BTREE COMMENT '卡卷状态索引',
  KEY `IX_REDUCE_TAG` (`reduce_tag`) USING BTREE COMMENT '立减标识索引',
  KEY `IX_WC_BATCH` (`wechat_batches`) USING BTREE COMMENT '微信批次号索引',
  KEY `IX_DELETED` (`deleted`) USING BTREE COMMENT '删除标识索引',
  KEY `IX_CARD_DOWN` (`downshelf_time`) USING BTREE COMMENT '下架时间索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='运营页基础信息表(含卡卷)';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
--  DDL for xys_operate_theme
-- ----------------------------

--  2019-2-28
--  新增活动类型 , 话费金额字段
ALTER TABLE `szxys`.`xys_operate_theme`
MODIFY COLUMN `card_state` tinyint(4) NOT NULL DEFAULT 0 COMMENT '运营状态: 0上架  1售罄   2下架   默认0' AFTER `card_stocks`,
ADD COLUMN `operate_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '活动类型:   0卡卷  1话费   默认0' AFTER `coupon_valid_time_end`,
ADD COLUMN `telephone_bill` int(11) NULL COMMENT '话费金额' AFTER `operate_type`,
DROP INDEX `IX_REDUCE_TAG`,
ADD INDEX `IX_TYPE`(`operate_type`) USING BTREE COMMENT '运营类型索引';

--  2019-3-6
--  修改话费金额字段类型 , 支出小数
ALTER TABLE `szxys`.`xys_operate_theme`
MODIFY COLUMN `telephone_bill` decimal(20, 2) NULL DEFAULT NULL COMMENT '充值话费金额' AFTER `operate_type`;

--  2019-3-6
--  修改部分注释
ALTER TABLE `szxys`.`xys_operate_theme`
MODIFY COLUMN `card_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动图片' AFTER `background`,
MODIFY COLUMN `card_details` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动详情' AFTER `card_img`,
MODIFY COLUMN `card_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动名称' AFTER `last_edit_time`,
MODIFY COLUMN `card_stocks` int(11) NOT NULL DEFAULT 0 COMMENT '商品库存  默认0' AFTER `card_name`,
MODIFY COLUMN `card_price` decimal(20, 2) NULL DEFAULT NULL COMMENT '活动金额' AFTER `wechat_batches`,
MODIFY COLUMN `coupon_valid_time_start` datetime(0) NULL DEFAULT NULL COMMENT '活动有效期始' AFTER `deleted`,
MODIFY COLUMN `coupon_valid_time_end` datetime(0) NULL DEFAULT NULL COMMENT '活动有效期止' AFTER `coupon_valid_time_start`;

--
--  2019-3-18 ⬆️ Submitted  ✅
--

--  2019-3-29
--  新增免支付发卡类型
ALTER TABLE `szxys`.`xys_operate_theme`
MODIFY COLUMN `operate_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '活动类型 :   0卡卷  1话费  2免支付发卡  默认0' AFTER `coupon_valid_time_end`,
ADD COLUMN `tel_limit_count` int(11) NULL COMMENT '单个手机号可参与次数' AFTER `telephone_bill`;

--  2019-3-29
--  新增白名单库ID字段
ALTER TABLE `szxys`.`xys_operate_theme`
ADD COLUMN `white_base_id` int(11) NULL COMMENT '白名单库ID' AFTER `tel_limit_count`;

--
--  2019-4-3 ⬆️ Submitted  ✅
--

--  2019-4-15
--  新增活动类型 , 串码库ID
ALTER TABLE `szxys`.`xys_operate_theme`
MODIFY COLUMN `operate_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '活动类型 :   0卡卷  1话费  2免支付发卡  3串码   默认0' AFTER `coupon_valid_time_end`,
ADD COLUMN `code_base_id` int(11) NULL DEFAULT NULL COMMENT '串码库ID' AFTER `white_base_id`;

--
--  2019-4-19 ⬆️ Submitted  ✅
--

--  2019-4-24
--  新增短信内容
ALTER TABLE `szxys`.`xys_operate_theme`
ADD COLUMN `message` varchar(450) NULL COMMENT '短信内容' AFTER `code_base_id`;

--
--  2019-4-25 ⬆️ Submitted  ✅
--
