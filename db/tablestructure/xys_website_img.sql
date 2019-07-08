/*

 Date: 21/05/2019 12:16:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_website_img
-- ----------------------------
DROP TABLE IF EXISTS `xys_website_img`;
CREATE TABLE `xys_website_img` (
  `id` int(11) NOT NULL,
  `text_id` int(11) DEFAULT NULL COMMENT '案例ID',
  `img_url` varchar(255) DEFAULT NULL COMMENT '图片路径名称',
  `is_cover` tinyint(4) DEFAULT NULL COMMENT '是否封面 : 0是 1否',
  PRIMARY KEY (`id`),
  KEY `IX_TEXT_ID` (`text_id`) USING BTREE COMMENT '案例ID索引',
  KEY `IX_IS_COVER` (`is_cover`) USING BTREE COMMENT '是否封面索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='案例图片';

SET FOREIGN_KEY_CHECKS = 1;
