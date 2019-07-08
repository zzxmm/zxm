/*

 Date: 23/01/2019 13:03:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_advert
-- ----------------------------
DROP TABLE IF EXISTS `xys_advert`;
CREATE TABLE `xys_advert` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '广告id',
  `advert_name` varchar(255) NOT NULL COMMENT '广告名称',
  `cover_pic` varchar(255) DEFAULT NULL COMMENT '广告封面图',
  `advert_details` varchar(255) DEFAULT NULL COMMENT '广告详情',
  `advert_url` varchar(255) DEFAULT NULL COMMENT '广告外链',
  `upshelf_time` datetime DEFAULT NULL COMMENT '上架时间',
  `downshelf_time` datetime DEFAULT NULL COMMENT '下架时间',
  `advert_state` tinyint(255) NOT NULL DEFAULT '2' COMMENT '广告状态：0已上架、1已下架、2待上架',
  `advert_position` tinyint(255) DEFAULT NULL COMMENT '广告位置：0全部位置',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_advert_state` (`advert_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_upshelf_time` (`upshelf_time`) USING BTREE COMMENT 'uptime索引',
  KEY `IX_downshelf_time` (`downshelf_time`) USING BTREE COMMENT 'dotime索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='广告表\n';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_advert
-- ----------------------------

-- 2019-01-28 修改广告位置属性 新增 优先级等字段

ALTER TABLE `szxys`.`xys_advert`
MODIFY COLUMN `advert_state` tinyint(4) NOT NULL DEFAULT 2 COMMENT '广告状态：0已上架、1已下架、2待上架' AFTER `downshelf_time`,
MODIFY COLUMN `advert_position` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '广告位置: 0、信用卡banner；1、福利社banner；  2、福利社列表  .  多个banner直接逗号拼接' AFTER `advert_state`,
ADD COLUMN `priority` int(11) NULL COMMENT '广告优先级 0-9999' AFTER `last_edit_id`,
ADD COLUMN `is_select` tinyint(4) NULL COMMENT '是否精选 :  0非精选  1精选' AFTER `priority`,
ADD COLUMN `is_preferen` tinyint(4) NULL COMMENT '是否特惠 :  0非特惠   1特惠' AFTER `is_select`,
ADD COLUMN `bank_id` int(0) NULL COMMENT '单选银行' AFTER `is_preferen`;

-- 2019-01-29 修改部分属性默认值

ALTER TABLE `szxys`.`xys_advert`
MODIFY COLUMN `advert_position` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 2 COMMENT '广告位置: 0、信用卡banner；1、福利社banner；  2、福利社列表  .  多个banner直接逗号拼接' AFTER `advert_state`,
MODIFY COLUMN `priority` int(11) NOT NULL DEFAULT 0 COMMENT '广告优先级 0-9999' AFTER `last_edit_id`,
MODIFY COLUMN `is_select` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否精选 :  0非精选  1精选' AFTER `priority`,
MODIFY COLUMN `is_preferen` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否特惠 :  0非特惠   1特惠' AFTER `is_select`;

-- 2019-01-29 修改url容量不够问题

ALTER TABLE `szxys`.`xys_advert`
MODIFY COLUMN `advert_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '广告外链' AFTER `advert_details`;

--
--  2018-*-* ⬆️ Submitted  ✅
--

-- 结构同步
ALTER TABLE `szxys`.`xys_advert` MODIFY COLUMN `advert_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '广告名称' AFTER `id`;
ALTER TABLE `szxys`.`xys_advert` MODIFY COLUMN `advert_details` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '广告详情' AFTER `cover_pic`;
ALTER TABLE `szxys`.`xys_advert` MODIFY COLUMN `advert_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '广告外链' AFTER `advert_details`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--

