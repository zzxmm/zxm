/*

 Date: 23/01/2019 13:07:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_user
-- ----------------------------
DROP TABLE IF EXISTS `xys_user`;
CREATE TABLE `xys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_account` varchar(255) DEFAULT NULL COMMENT '用户账号',
  `user_password` varchar(255) DEFAULT NULL COMMENT '账号密码',
  `user_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别：0男、1女',
  `e_mail` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `user_state` tinyint(4) DEFAULT '0' COMMENT '用户状态：默认0启用、1停用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_headimg` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `openid` varchar(255) DEFAULT NULL COMMENT '微信openID',
  `address` varchar(255) DEFAULT NULL COMMENT '通讯地址',
  `user_center_id` int(11) DEFAULT NULL COMMENT '用户中心用户ID',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_user_center_id` (`user_center_id`) USING BTREE COMMENT 'centerid索引',
  KEY `IX_openid` (`openid`(191)) USING BTREE COMMENT 'openid索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表\n';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- DDL for xys_coupon
-- ----------------------------

-- 分销等级佣金分数
ALTER TABLE `szxys`.`xys_user`
ADD COLUMN `branch_grade` int(11) NULL COMMENT '分销等级' AFTER `nick_name`,
ADD COLUMN `branch_money` decimal(20, 2) NULL COMMENT '分销奖励金' AFTER `branch_grade`;

ALTER TABLE `szxys`.`xys_user`
ADD COLUMN `branch_number` int(11) NOT NULL DEFAULT 0 COMMENT '分销邀请人数' AFTER `branch_money`;

-- 填充默认值
ALTER TABLE `szxys`.`xys_user`
MODIFY COLUMN `branch_grade` int(11) NOT NULL DEFAULT 0 COMMENT '分销等级' AFTER `nick_name`,
MODIFY COLUMN `branch_money` decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '分销奖励金' AFTER `branch_grade`;

-- 增加分销可提现金额
ALTER TABLE `szxys`.`xys_user`
MODIFY COLUMN `branch_money` decimal(20, 2) NOT NULL DEFAULT 0.00 COMMENT '分销收益金额' AFTER `branch_grade`,
ADD COLUMN `branch_withdrawable_money` decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '分销可提现金额' AFTER `branch_number`;
-- 删除分销等级
ALTER TABLE `szxys`.`xys_user`
DROP COLUMN `branch_grade`;

--
--  2019-5-21 ⬆️ Submitted  ✅
--

-- 结构同步
ALTER TABLE `szxys`.`xys_user` MODIFY COLUMN `user_account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户账号' AFTER `id`;
ALTER TABLE `szxys`.`xys_user` MODIFY COLUMN `openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信openID' AFTER `user_headimg`;

--
--  2019-5-28 ⬆️ Submitted  ✅
--

-- 增加用户积分
ALTER TABLE `szxys`.`xys_user`
ADD COLUMN `current_integral` int(11) NOT NULL DEFAULT 0 COMMENT '当前积分 : 默认值为0' AFTER `branch_withdrawable_money`;

--
--  2019-6-19 ⬆️ Submitted  ✅
--









