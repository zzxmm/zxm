/*
 Navicat Premium Data Transfer

 Source Server         : production server
 Source Server Type    : MySQL
 Source Server Version : 50628
 Source Host           : bj-cdb-d7nuqu78.sql.tencentcdb.com:62699
 Source Schema         : ucenter

 Target Server Type    : MySQL
 Target Server Version : 50628
 File Encoding         : 65001

 Date: 25/04/2019 16:34:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for merchant
-- ----------------------------
DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant` (
  `merchant_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商户ID',
  `fmer_pcode` bigint(20) DEFAULT NULL COMMENT '上级商户id，商户目前按两级处理',
  `fmer_name` varchar(64) NOT NULL COMMENT '商户名称',
  `fmobile` varchar(11) NOT NULL COMMENT '电话',
  `flogin_id` varchar(8) DEFAULT NULL COMMENT '登录id',
  `password` varchar(60) DEFAULT NULL COMMENT '登录密码',
  `fmember_openid` varchar(50) DEFAULT NULL COMMENT '微信三方登录唯一标识',
  `fmember_state` tinyint(1) DEFAULT '1' COMMENT '商户状态 1为开启 0为关闭',
  `expiration_date` datetime NOT NULL COMMENT '有效截止日期',
  `level` tinyint(2) DEFAULT NULL COMMENT '等级',
  `head_portrait` varchar(200) DEFAULT NULL COMMENT 'logo',
  PRIMARY KEY (`merchant_id`),
  KEY `UK_merchant_mobile` (`fmobile`) USING BTREE,
  KEY `UK_merchant_loginid` (`flogin_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='商户基本信息（登录用）';

-- ----------------------------
-- Table structure for merchant_communication
-- ----------------------------
DROP TABLE IF EXISTS `merchant_communication`;
CREATE TABLE `merchant_communication` (
  `communication_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '通讯信息id',
  `merchant_id` bigint(20) NOT NULL COMMENT '商户id',
  `fcustomertel` varchar(15) DEFAULT NULL COMMENT '客服电话',
  `flinkman` varchar(10) DEFAULT NULL COMMENT '联系人姓名',
  `flkmphone` varchar(11) DEFAULT NULL COMMENT '联系人手机',
  `flkwx` varchar(32) DEFAULT NULL COMMENT '联系人微信',
  `flkzw` varchar(8) DEFAULT NULL COMMENT '联系人职位',
  PRIMARY KEY (`communication_id`),
  UNIQUE KEY `UK_merchant_id` (`merchant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COMMENT='商户通讯信息表';

-- ----------------------------
-- Table structure for merchant_detail
-- ----------------------------
DROP TABLE IF EXISTS `merchant_detail`;
CREATE TABLE `merchant_detail` (
  `detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商户详情ID',
  `merchant_id` bigint(20) NOT NULL COMMENT '商户id',
  `fmer_short_name` varchar(30) DEFAULT NULL COMMENT '商户简称',
  `fmer_type` tinyint(2) DEFAULT NULL COMMENT '商户类型',
  `fpriority` int(11) DEFAULT NULL COMMENT '优先级',
  `fselected` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0非精选，1精选',
  `star_level` tinyint(2) DEFAULT NULL COMMENT '评级星级',
  `fbuslicence` varchar(255) DEFAULT NULL COMMENT '营业执照图片',
  `unified_number` varchar(255) DEFAULT NULL COMMENT '统一编号',
  `fcreator_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `fcreate_time` datetime DEFAULT NULL COMMENT '创建时间',
  `flast_edit_time` datetime DEFAULT NULL COMMENT '修改时间',
  `flast_editor_id` varchar(32) DEFAULT NULL COMMENT '修改人',
  `fdeleted` int(11) DEFAULT '0' COMMENT '删除标识：0-未删除，1-已删除',
  `fdelete_user_id` varchar(32) DEFAULT NULL COMMENT '删除人',
  `fmark_deleted_time` datetime(6) DEFAULT NULL COMMENT '删除时间',
  `service_number` varchar(20) DEFAULT NULL COMMENT '服务号',
  `coverimage` varchar(255) DEFAULT NULL COMMENT '封面照片',
  `introduction` varchar(500) DEFAULT NULL COMMENT '简介',
  `wechat_mch_id` varchar(32) NOT NULL COMMENT '微信分配商户号',
  PRIMARY KEY (`detail_id`),
  UNIQUE KEY `UK_merchant_id` (`merchant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COMMENT='商户详细信息';

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
  `store_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '门店ID',
  `fmer_id` bigint(20) DEFAULT NULL COMMENT '所属商户',
  `fstr_pcode` bigint(20) DEFAULT NULL COMMENT '上级门店id，门店目前按两级处理',
  `fstr_name` varchar(64) NOT NULL COMMENT '门店名称',
  `fmobile` varchar(11) DEFAULT NULL COMMENT '电话',
  `fstr_state` tinyint(1) DEFAULT '1' COMMENT '门店状态 0开 1停',
  `expiration_date` datetime DEFAULT NULL COMMENT '有效截止日期',
  `level` tinyint(2) DEFAULT NULL COMMENT '等级',
  `head_portrait` varchar(200) DEFAULT NULL COMMENT 'logo',
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3891 DEFAULT CHARSET=utf8mb4 COMMENT='门店基本信息';

-- ----------------------------
-- Table structure for store_communication
-- ----------------------------
DROP TABLE IF EXISTS `store_communication`;
CREATE TABLE `store_communication` (
  `communication_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '通讯信息id',
  `store_id` bigint(20) NOT NULL COMMENT '门店id',
  `fcustomertel` varchar(15) DEFAULT NULL COMMENT '客服电话',
  `flinkman` varchar(10) DEFAULT NULL COMMENT '联系人姓名',
  `flkmphone` varchar(11) DEFAULT NULL COMMENT '联系人手机',
  `flkwx` varchar(32) DEFAULT NULL COMMENT '联系人微信',
  `fadress` varchar(64) DEFAULT NULL COMMENT '门店地址',
  PRIMARY KEY (`communication_id`),
  UNIQUE KEY `UK_store_id` (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3886 DEFAULT CHARSET=utf8mb4 COMMENT='门店通讯信息表';

-- ----------------------------
-- Table structure for store_detail
-- ----------------------------
DROP TABLE IF EXISTS `store_detail`;
CREATE TABLE `store_detail` (
  `detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '门店详情ID',
  `store_id` bigint(20) NOT NULL COMMENT '门店id',
  `fstr_short_name` varchar(30) DEFAULT NULL COMMENT '门店简称',
  `fstr_type` tinyint(2) DEFAULT NULL COMMENT '门店类型',
  `fpriority` tinyint(2) DEFAULT NULL COMMENT '优先级',
  `fselected` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0非精选，1精选',
  `star_level` tinyint(2) DEFAULT NULL COMMENT '评级星级',
  `fbuslicence` varchar(300) DEFAULT NULL COMMENT '营业执照图片',
  `unified_number` varchar(255) DEFAULT NULL COMMENT '统一编号',
  `fcreator_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `fcreate_time` datetime DEFAULT NULL COMMENT '创建时间',
  `flast_edit_time` datetime DEFAULT NULL COMMENT '修改时间',
  `flast_editor_id` varchar(32) DEFAULT NULL COMMENT '修改人',
  `fdeleted` int(11) DEFAULT '0' COMMENT '删除标识：0-未删除，1-已删除',
  `fdelete_user_id` varchar(32) DEFAULT NULL COMMENT '删除人',
  `fmark_deleted_time` datetime(6) DEFAULT NULL COMMENT '删除时间',
  `service_number` varchar(20) DEFAULT NULL COMMENT '服务号',
  `business_time` varchar(50) DEFAULT NULL COMMENT '营业时间',
  PRIMARY KEY (`detail_id`),
  UNIQUE KEY `UK_store_id` (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3891 DEFAULT CHARSET=utf8mb4 COMMENT='门店详细信息';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(18) DEFAULT NULL COMMENT '用户名称',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `head_portrait` varchar(200) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(11) DEFAULT NULL COMMENT '电话',
  `login_id` varchar(8) NOT NULL COMMENT '登录id',
  `password` varchar(60) DEFAULT NULL COMMENT '登录密码',
  `level` tinyint(2) DEFAULT NULL COMMENT '等级',
  `wx_openid` varchar(50) DEFAULT NULL COMMENT '微信三方登录唯一标识',
  `user_state` tinyint(1) DEFAULT '0' COMMENT '用户状态 0为开启 1为关闭',
  `data_source` tinyint(2) DEFAULT NULL COMMENT '数据来源0小程序，1后台',
  `sex` tinyint(2) DEFAULT '0' COMMENT '性别 默认男  0男 1女',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_user_loginid` (`login_id`),
  UNIQUE KEY `UK_user_openid` (`wx_openid`) USING BTREE,
  KEY `UK_user_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7434 DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息（登录用）';

-- ----------------------------
-- Table structure for user_detail
-- ----------------------------
DROP TABLE IF EXISTS `user_detail`;
CREATE TABLE `user_detail` (
  `u_detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '详情ID',
  `user_id` bigint(18) NOT NULL COMMENT '用户id',
  `id_type` tinyint(1) DEFAULT NULL COMMENT '证件类型',
  `id_card` varchar(18) DEFAULT NULL COMMENT '证件号',
  `address` varchar(60) DEFAULT NULL COMMENT '通讯地址',
  `e_mail` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `reg_time` datetime NOT NULL COMMENT '注册日期',
  PRIMARY KEY (`u_detail_id`),
  UNIQUE KEY `UK_user_userId` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7431 DEFAULT CHARSET=utf8mb4 COMMENT='用户详情';

SET FOREIGN_KEY_CHECKS = 1;
