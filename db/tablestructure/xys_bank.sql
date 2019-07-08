/*

 Date: 23/01/2019 13:03:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_bank
-- ----------------------------
DROP TABLE IF EXISTS `xys_bank`;
CREATE TABLE `xys_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '银行id',
  `bank_name` varchar(50) NOT NULL COMMENT '银行全称',
  `bank_shot_name` varchar(32) DEFAULT NULL COMMENT '银行简称',
  `creditcard_apply_url` varchar(256) DEFAULT NULL COMMENT '信用卡申领页面url',
  `service_tel_number` varchar(32) DEFAULT NULL COMMENT '客服电话',
  `enable_state` tinyint(4) DEFAULT NULL COMMENT '银行启停状态：0停用、1启用',
  `logo_url` varchar(255) DEFAULT NULL COMMENT '银行logo的url',
  `level` int(11) DEFAULT NULL COMMENT '银行等级',
  `bank_linkman_name` varchar(32) DEFAULT NULL COMMENT '银行联系人姓名',
  `bank_linkman_tel` varchar(32) DEFAULT NULL COMMENT '银行联系人手机号',
  `bank_linkman_wechat` varchar(50) DEFAULT NULL COMMENT '银行联系人微信号',
  `bank_linkman_position` varchar(32) DEFAULT NULL COMMENT '银行联系人职位',
  `is_select` tinyint(4) DEFAULT '0' COMMENT '银行是否精选：0非精选、1精选',
  `priority` int(11) DEFAULT NULL COMMENT '银行优先级',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  `floor_logo` varchar(255) DEFAULT NULL COMMENT '银行反白logo',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_enable_state` (`enable_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_is_select` (`is_select`) USING BTREE COMMENT 'isselect索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='银行表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_bank
-- ----------------------------

-- 结构同步
ALTER TABLE `szxys`.`xys_bank` MODIFY COLUMN `creditcard_apply_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '信用卡申领页面url' AFTER `bank_shot_name`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--

