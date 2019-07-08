/*

 Date: 23/01/2019 13:07:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_wccard_baseinfo_add
-- ----------------------------
DROP TABLE IF EXISTS `xys_wccard_baseinfo_add`;
CREATE TABLE `xys_wccard_baseinfo_add` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `types` varchar(2000) DEFAULT NULL COMMENT '使用时间的类型   DATE_TYPE_FIX _TIME_RANGE 表示固定日期区间，DATETYPE FIX_TERM 表示固定时长 （自领取后按天算。',
  `begin_timestamp` bigint(20) DEFAULT NULL COMMENT '表示起用时间。从1970年1月1日00:00:00至起用时间的秒数，最终需转换为字符串形态传入。（东八区时间,UTC+8，单位为秒）. 可用于DATE_TYPE_FIX_TERM时间类型',
  `end_timestamp` bigint(20) DEFAULT NULL COMMENT '表示结束时间 ， 建议设置为截止日期的23:59:59过期 。 （ 东八区时间,UTC+8，单位为秒 ）',
  `fixed_term` int(11) DEFAULT NULL COMMENT '表示自领取后多少天内有效，不支持填写0。',
  `fixed_begin_term` int(11) DEFAULT NULL COMMENT '表示自领取后多少天开始生效，领取后当天生效填写0。（单位为天）',
  `quantity` bigint(20) DEFAULT NULL COMMENT '卡券库存的数量，上限为100000000。',
  `location_id_list` int(11) DEFAULT NULL COMMENT '门店位置poiid',
  `card_id` int(11) DEFAULT NULL COMMENT '卡卷ID',
  PRIMARY KEY (`id`),
  KEY `IX_CARD_ID` (`card_id`) USING BTREE COMMENT '卡卷ID索引',
  KEY `IX_TYPES` (`types`(191)) USING BTREE COMMENT '时间类型索引',
  KEY `IX_QUANTITY` (`quantity`) USING BTREE COMMENT '库存数量索引',
  KEY `IX_LOCATION_ID` (`location_id_list`) USING BTREE COMMENT 'poi门店ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信公众号卡卷基础信息附加表';

SET FOREIGN_KEY_CHECKS = 1;
