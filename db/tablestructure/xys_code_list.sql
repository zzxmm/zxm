/*

 Date: 15/04/2019 13:01:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_code_list
-- ----------------------------
DROP TABLE IF EXISTS `xys_code_list`;
CREATE TABLE `xys_code_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `base_id` int(11) DEFAULT NULL COMMENT '串码库ID',
  `code` varchar(32) DEFAULT NULL COMMENT '串码',
  `creator_id` int(11) DEFAULT NULL COMMENT '录入人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UI_CODE` (`code`) USING BTREE COMMENT '串码唯一索引',
  KEY `IX_BASE_ID` (`base_id`) USING BTREE COMMENT '串码库索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='串码表';

SET FOREIGN_KEY_CHECKS = 1;

--  2019-4-17
--  修改索引 为复合索引
ALTER TABLE `szxys`.`xys_code_list`
DROP INDEX `UI_CODE`,
DROP INDEX `IX_BASE_ID`,
ADD UNIQUE INDEX `IX_BASE_CODE`(`base_id`, `code`) USING BTREE COMMENT '串码库Code复合索引';

--
--  2019-6-19 ⬆️ Submitted  ✅
--











