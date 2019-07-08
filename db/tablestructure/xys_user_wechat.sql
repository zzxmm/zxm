/*

 Date: 12/06/2019 11:43:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_user_wechat
-- ----------------------------
DROP TABLE IF EXISTS `xys_user_wechat`;
CREATE TABLE `xys_user_wechat` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wechat_openid` varchar(32) NOT NULL COMMENT '微信openid',
  `openid_type` tinyint(4) NOT NULL COMMENT 'openid类型: 0手赞公众号  1手赞小程序',
  `wechat_unionid` varchar(32) NOT NULL COMMENT '微信unionid',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UI_OPEN_ID` (`wechat_openid`) USING BTREE COMMENT 'openID索引',
  KEY `IX_OPEN_TYPE` (`openid_type`) USING BTREE COMMENT 'open类型索引',
  KEY `IX_UNIONID` (`wechat_unionid`) USING BTREE COMMENT 'unionId索引',
  KEY `IX_USER_ID` (`user_id`) USING BTREE COMMENT '用户ID索引'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='用户OPENID关系表';

SET FOREIGN_KEY_CHECKS = 1;
