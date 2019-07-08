/*

 Date: 23/01/2019 13:04:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_merchants
-- ----------------------------
DROP TABLE IF EXISTS `xys_merchants`;
CREATE TABLE `xys_merchants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mer_name` varchar(64) NOT NULL COMMENT '商户名称',
  `mer_shot_name` varchar(64) DEFAULT NULL COMMENT '商户简称',
  `service_tel_number` varchar(15) DEFAULT NULL COMMENT '客服电话',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `logo_url` varchar(255) DEFAULT NULL COMMENT '商家logo',
  `linkman_name` varchar(32) DEFAULT NULL COMMENT '联系人姓名',
  `linkman_tel` varchar(32) DEFAULT NULL COMMENT '联系人手机号',
  `linkman_wechat` varchar(32) DEFAULT NULL COMMENT '联系人微信',
  `enable_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '商户状态：0停用、1启用',
  `mer_introduce` varchar(255) DEFAULT NULL COMMENT '商家简介',
  `mer_grade` tinyint(4) DEFAULT '3' COMMENT '门店等级 : 1-5 ',
  `evaluate_grade` tinyint(4) DEFAULT '3' COMMENT '评价星级 : 1-5 ',
  `business_hours` varchar(32) DEFAULT NULL COMMENT '营业时间',
  `business_license` varchar(255) DEFAULT NULL COMMENT '营业执照',
  `unified_number` varchar(255) DEFAULT NULL COMMENT '统一编号',
  `servicenum` tinyint(4) DEFAULT NULL COMMENT '服务号关联: 0关联 , 1解绑',
  `is_select` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否精选：0非精选、1精选',
  `mer_cover` varchar(255) DEFAULT NULL COMMENT '商户封面图',
  `mer_center_id` int(11) DEFAULT NULL COMMENT '商户中心商户ID',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人ID',
  `last_edit_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `wechat_mch_id` varchar(32) NOT NULL COMMENT '微信分配商户号',
  `priority` int(11) DEFAULT NULL COMMENT '商户优先级',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_enable_state` (`enable_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_mer_center_id` (`mer_center_id`) USING BTREE COMMENT 'centerid索引',
  KEY `IX_wechat_mch_id` (`wechat_mch_id`) USING BTREE COMMENT 'wechatid索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='商户表\n';

SET FOREIGN_KEY_CHECKS = 1;
