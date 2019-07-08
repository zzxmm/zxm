/*
 Navicat Premium Data Transfer

 Source Server         : production server
 Source Server Type    : MySQL
 Source Server Version : 50628
 Source Host           : bj-cdb-d7nuqu78.sql.tencentcdb.com:62699
 Source Schema         : whitelist

 Target Server Type    : MySQL
 Target Server Version : 50628
 File Encoding         : 65001

 Date: 27/05/2019 17:53:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_white_user_1557204658876
-- ----------------------------
DROP TABLE IF EXISTS `xys_white_user_1557204658876`;
CREATE TABLE `xys_white_user_1557204658876` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `creator_id` int(11) DEFAULT NULL COMMENT '录入人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_NI_PHONE` (`phone`) USING BTREE COMMENT '手机号唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='sad-白名单用户表';

-- ----------------------------
-- Table structure for xys_white_user_1557204685155
-- ----------------------------
DROP TABLE IF EXISTS `xys_white_user_1557204685155`;
CREATE TABLE `xys_white_user_1557204685155` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `creator_id` int(11) DEFAULT NULL COMMENT '录入人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_NI_PHONE` (`phone`) USING BTREE COMMENT '手机号唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='asd-白名单用户表';

-- ----------------------------
-- Table structure for xys_white_user_1557204735087
-- ----------------------------
DROP TABLE IF EXISTS `xys_white_user_1557204735087`;
CREATE TABLE `xys_white_user_1557204735087` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `creator_id` int(11) DEFAULT NULL COMMENT '录入人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_NI_PHONE` (`phone`) USING BTREE COMMENT '手机号唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='asd-白名单用户表';

-- ----------------------------
-- Table structure for xys_white_user_1557204940247
-- ----------------------------
DROP TABLE IF EXISTS `xys_white_user_1557204940247`;
CREATE TABLE `xys_white_user_1557204940247` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `creator_id` int(11) DEFAULT NULL COMMENT '录入人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_NI_PHONE` (`phone`) USING BTREE COMMENT '手机号唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='88-白名单用户表';

-- ----------------------------
-- Table structure for xys_white_user_1557218198894
-- ----------------------------
DROP TABLE IF EXISTS `xys_white_user_1557218198894`;
CREATE TABLE `xys_white_user_1557218198894` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `creator_id` int(11) DEFAULT NULL COMMENT '录入人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_NI_PHONE` (`phone`) USING BTREE COMMENT '手机号唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='123-白名单用户表';

-- ----------------------------
-- Table structure for xys_white_user_1557283076934
-- ----------------------------
DROP TABLE IF EXISTS `xys_white_user_1557283076934`;
CREATE TABLE `xys_white_user_1557283076934` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `creator_id` int(11) DEFAULT NULL COMMENT '录入人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_NI_PHONE` (`phone`) USING BTREE COMMENT '手机号唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试-白名单用户表';

-- ----------------------------
-- Table structure for xys_white_user_1948103558
-- ----------------------------
DROP TABLE IF EXISTS `xys_white_user_1948103558`;
CREATE TABLE `xys_white_user_1948103558` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `creator_id` int(11) DEFAULT NULL COMMENT '录入人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_NI_PHONE` (`phone`) USING BTREE COMMENT '手机号唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='浦发银行测试白名单-白名单用户表';

-- ----------------------------
-- Table structure for xys_white_user_1948103559
-- ----------------------------
DROP TABLE IF EXISTS `xys_white_user_1948103559`;
CREATE TABLE `xys_white_user_1948103559` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `creator_id` int(11) DEFAULT NULL COMMENT '录入人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_NI_PHONE` (`phone`) USING BTREE COMMENT '手机号唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='浦发银行测试白名单-白名单用户表';

-- ----------------------------
-- Table structure for xys_white_user_1948103561
-- ----------------------------
DROP TABLE IF EXISTS `xys_white_user_1948103561`;
CREATE TABLE `xys_white_user_1948103561` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `creator_id` int(11) DEFAULT NULL COMMENT '录入人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_NI_PHONE` (`phone`) USING BTREE COMMENT '手机号唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb4 COMMENT='浦发银行测试白名单-白名单用户表';

SET FOREIGN_KEY_CHECKS = 1;
