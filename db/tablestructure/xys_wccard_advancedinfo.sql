/*

 Date: 23/01/2019 13:07:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_wccard_advancedinfo
-- ----------------------------
DROP TABLE IF EXISTS `xys_wccard_advancedinfo`;
CREATE TABLE `xys_wccard_advancedinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accept_category` varchar(255) DEFAULT NULL COMMENT '指定可用的商品类目',
  `reject_category` varchar(255) DEFAULT NULL COMMENT '指定不可用的商品类目',
  `can_use_with_other_discount` tinyint(1) DEFAULT NULL COMMENT '满减门槛字段',
  `abstract` varchar(255) DEFAULT NULL COMMENT '封面摘要简介',
  `image_url` varchar(255) DEFAULT NULL COMMENT '图片链接',
  `text` varchar(255) DEFAULT NULL COMMENT '图文描述',
  `types` varchar(255) DEFAULT NULL COMMENT '限制类型枚举值：支持填入 MONDAY 周一 TUESDAY 周二 WEDNESDAY 周三 THURSDAY 周四 FRIDAY 周五 SATURDAY 周六 SUNDAY 周日 此处只控制显示，',
  `begin_hour` int(11) DEFAULT NULL COMMENT '当前type类型下的起始时间（小时） ，如当前结构体内填写了MONDAY， 此处填写了10，则此处表示周一 10:00可用',
  `end_hour` int(11) DEFAULT NULL COMMENT '当前type类型下的起始时间（分钟） ，如当前结构体内填写了MONDAY， begin_hour填写10，此处填写了59， 则此处表示周一 10:59可用',
  `begin_minute` int(11) DEFAULT NULL COMMENT '当前type类型下的结束时间（小时） ，如当前结构体内填写了MONDAY， 此处填写了20， 则此处表示周一 10:00-20:00可用',
  `end_minute` int(11) DEFAULT NULL COMMENT '当前type类型下的结束时间（分钟） ，如当前结构体内填写了MONDAY， begin_hour填写10，此处填写了59， 则此处表示周一 10:59-00:59可用',
  `business_service` varchar(255) DEFAULT NULL COMMENT '商家服务类型： BIZ_SERVICE_DELIVER 外卖服务； BIZ_SERVICE_FREE_PARK 停车位； BIZ_SERVICE_WITH_PET 可带宠物； BIZ_SERVICE_FREE_WIFI 免费wifi， 可多选',
  `card_id` int(11) DEFAULT NULL COMMENT '卡卷ID',
  `icon_url_list` varchar(255) DEFAULT NULL COMMENT '封面图片列表，仅支持填入一 个封面图片链接',
  PRIMARY KEY (`id`),
  KEY `IX_CARD_ID` (`card_id`) USING BTREE COMMENT '卡卷ID索引',
  KEY `IX_TYPES` (`types`(191)) USING BTREE COMMENT '时间类型索引',
  KEY `IX_BUSINESS_SERVICE` (`business_service`(191)) USING BTREE COMMENT '服务类型索引',
  KEY `IX_ABSTRACT` (`abstract`(191)) USING BTREE COMMENT '封面摘要索引',
  KEY `IX_IMAGE_URL` (`image_url`(191)) USING BTREE COMMENT '图片链接索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信公众号卡卷高级信息';

SET FOREIGN_KEY_CHECKS = 1;
