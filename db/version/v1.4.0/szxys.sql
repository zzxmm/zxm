/*
 Navicat Premium Data Transfer

 Source Server         : production server
 Source Server Type    : MySQL
 Source Server Version : 50628
 Source Host           : bj-cdb-d7nuqu78.sql.tencentcdb.com:62699
 Source Schema         : szxys

 Target Server Type    : MySQL
 Target Server Version : 50628
 File Encoding         : 65001

 Date: 29/03/2019 17:02:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xys_activity
-- ----------------------------
DROP TABLE IF EXISTS `xys_activity`;
CREATE TABLE `xys_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动id',
  `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
  `activity_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '活动类型：默认0拼团、1优惠、2抢购',
  `activity_start_time` datetime DEFAULT NULL COMMENT '活动开始时间',
  `activity_end_time` datetime DEFAULT NULL COMMENT '活动结束时间',
  `cover_pic` varchar(255) DEFAULT NULL COMMENT '活动封面图',
  `activity_details` varchar(255) DEFAULT NULL COMMENT '活动详情',
  `matter` varchar(500) DEFAULT NULL COMMENT '注意事项',
  `activity_state` tinyint(4) NOT NULL DEFAULT '2' COMMENT '活动状态：默认 2  0已上架、1已下架、2待上架、3已满',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
  `activity_param_id` int(11) DEFAULT NULL COMMENT '活动参数id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  `goods_tag` varchar(32) DEFAULT NULL COMMENT '微信商品标记',
  `discount_price` decimal(20,2) NOT NULL COMMENT '活动优惠后金额',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_activity_type` (`activity_type`) USING BTREE COMMENT 'type索引',
  KEY `IX_activity_state` (`activity_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_activity_goods_id` (`goods_id`) USING BTREE COMMENT 'goodsid索引',
  KEY `IX_activity_param` (`activity_param_id`) USING BTREE COMMENT 'param索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动表';

-- ----------------------------
-- Table structure for xys_activity_param
-- ----------------------------
DROP TABLE IF EXISTS `xys_activity_param`;
CREATE TABLE `xys_activity_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动参数id',
  `up_limit_people` int(255) DEFAULT NULL COMMENT '开团人数',
  `subtract_price` decimal(20,2) DEFAULT NULL COMMENT '每人立减',
  `subtract_limit_price` decimal(20,2) DEFAULT NULL COMMENT '立减上限',
  `group_subtract_price` decimal(20,2) DEFAULT NULL COMMENT '开团立减',
  `preferen_amount` decimal(20,2) DEFAULT NULL COMMENT '优惠额度',
  `quick_buy_price` decimal(20,2) DEFAULT NULL COMMENT '抢购价格',
  `day_limit` int(255) DEFAULT NULL COMMENT '单日限制',
  `activity_limit` varchar(255) DEFAULT NULL COMMENT '活动限制',
  `account_limit` varchar(255) DEFAULT NULL COMMENT '账号限制',
  `part_activity_num` int(11) DEFAULT NULL COMMENT '参与活动数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动参数表\n';

-- ----------------------------
-- Table structure for xys_advert
-- ----------------------------
DROP TABLE IF EXISTS `xys_advert`;
CREATE TABLE `xys_advert` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '广告id',
  `advert_name` varchar(255) NOT NULL COMMENT '广告名称',
  `cover_pic` varchar(255) DEFAULT NULL COMMENT '广告封面图',
  `advert_details` varchar(255) DEFAULT NULL COMMENT '广告详情',
  `advert_url` varchar(1000) DEFAULT NULL COMMENT '广告外链',
  `upshelf_time` datetime DEFAULT NULL COMMENT '上架时间',
  `downshelf_time` datetime DEFAULT NULL COMMENT '下架时间',
  `advert_state` tinyint(4) NOT NULL DEFAULT '2' COMMENT '广告状态：0已上架、1已下架、2待上架',
  `advert_position` varchar(15) NOT NULL DEFAULT '2' COMMENT '广告位置: 0、信用卡banner；1、福利社banner；  2、福利社列表  .  多个banner直接逗号拼接',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  `priority` int(11) NOT NULL DEFAULT '0' COMMENT '广告优先级 0-9999',
  `is_select` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否精选 :  0非精选  1精选',
  `is_preferen` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否特惠 :  0非特惠   1特惠',
  `bank_id` int(11) DEFAULT NULL COMMENT '单选银行',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_advert_state` (`advert_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_upshelf_time` (`upshelf_time`) USING BTREE COMMENT 'uptime索引',
  KEY `IX_downshelf_time` (`downshelf_time`) USING BTREE COMMENT 'dotime索引'
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='广告表\n';

-- ----------------------------
-- Table structure for xys_attend
-- ----------------------------
DROP TABLE IF EXISTS `xys_attend`;
CREATE TABLE `xys_attend` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` int(11) NOT NULL COMMENT '拼团ID',
  `open_id` varchar(255) NOT NULL COMMENT '用户openID',
  `behavior` tinyint(4) DEFAULT NULL COMMENT '用户行为：0开团 , 1 参团',
  `user_state` tinyint(4) DEFAULT NULL COMMENT '用户状态: 0 未支付 , 1 已支付',
  `order_no` varchar(255) DEFAULT NULL COMMENT '内部订单号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_group_id` (`group_id`) USING BTREE COMMENT 'groupid索引',
  KEY `IX_open_id` (`open_id`(191)) USING BTREE COMMENT 'openid索引',
  KEY `IX_user_state` (`user_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_order_no` (`order_no`(191)) USING BTREE COMMENT 'orderno索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参团表\n';

-- ----------------------------
-- Table structure for xys_bank
-- ----------------------------
DROP TABLE IF EXISTS `xys_bank`;
CREATE TABLE `xys_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '银行id',
  `bank_name` varchar(50) NOT NULL COMMENT '银行全称',
  `bank_shot_name` varchar(32) DEFAULT NULL COMMENT '银行简称',
  `creditcard_apply_url` varchar(256) DEFAULT NULL COMMENT '信用卡申领页面url',
  `service_tel_number` varchar(32) DEFAULT NULL COMMENT '客服电话',
  `enable_state` tinyint(4) DEFAULT NULL COMMENT '银行启停状态：0停用、1启用',
  `logo_url` varchar(255) DEFAULT NULL COMMENT '银行logo的url',
  `level` int(11) DEFAULT NULL COMMENT '银行等级',
  `bank_linkman_name` varchar(32) DEFAULT NULL COMMENT '银行联系人姓名',
  `bank_linkman_tel` varchar(32) DEFAULT NULL COMMENT '银行联系人手机号',
  `bank_linkman_wechat` varchar(50) DEFAULT NULL COMMENT '银行联系人微信号',
  `bank_linkman_position` varchar(32) DEFAULT NULL COMMENT '银行联系人职位',
  `is_select` tinyint(4) DEFAULT '0' COMMENT '银行是否精选：0非精选、1精选',
  `priority` int(11) DEFAULT NULL COMMENT '银行优先级',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  `floor_logo` varchar(255) DEFAULT NULL COMMENT '银行反白logo',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_enable_state` (`enable_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_is_select` (`is_select`) USING BTREE COMMENT 'isselect索引'
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COMMENT='银行表';

-- ----------------------------
-- Table structure for xys_bank_scene_order
-- ----------------------------
DROP TABLE IF EXISTS `xys_bank_scene_order`;
CREATE TABLE `xys_bank_scene_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` varchar(150) NOT NULL COMMENT '内部订单号',
  `wechat_order_no` varchar(255) DEFAULT NULL COMMENT '微信订单号',
  `order_amount` decimal(20,2) DEFAULT NULL COMMENT '订单金额',
  `order_state` tinyint(4) NOT NULL COMMENT '订单状态：0已支付 1未支付 ',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `wechat_openid` varchar(32) DEFAULT NULL COMMENT '用户微信openid',
  `cheap_price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '卡券优惠金额',
  `actual_price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '实际支付金额',
  `main_openid` varchar(64) DEFAULT NULL COMMENT '主商户openid',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_order_state` (`order_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_wechat_openid` (`wechat_openid`) USING BTREE COMMENT 'openid索引',
  KEY `UN_order_no` (`order_no`) USING BTREE COMMENT 'orderno索引'
) ENGINE=InnoDB AUTO_INCREMENT=7659 DEFAULT CHARSET=utf8mb4 COMMENT='银行1分钱支付场景订单表';

-- ----------------------------
-- Table structure for xys_coupon_store
-- ----------------------------
DROP TABLE IF EXISTS `xys_coupon_store`;
CREATE TABLE `xys_coupon_store` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `coupon_id` int(11) NOT NULL COMMENT '卡券id',
  `store_id` int(11) NOT NULL COMMENT '门店id',
  `mer_id` int(11) NOT NULL COMMENT '商户id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_coupon_id` (`coupon_id`) USING BTREE COMMENT 'coupon索引',
  KEY `IX_store_id` (`store_id`) USING BTREE COMMENT 'store索引',
  KEY `IX_mer_id` (`mer_id`) USING BTREE COMMENT 'mer索引'
) ENGINE=InnoDB AUTO_INCREMENT=79904 DEFAULT CHARSET=utf8mb4 COMMENT='卡卷门店关系表';

-- ----------------------------
-- Table structure for xys_recharge_order
-- ----------------------------
DROP TABLE IF EXISTS `xys_recharge_order`;
CREATE TABLE `xys_recharge_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` varchar(48) DEFAULT NULL COMMENT '内部订单号',
  `wechat_order_no` varchar(48) DEFAULT NULL COMMENT '微信订单号',
  `order_amount` decimal(20,2) DEFAULT NULL COMMENT '订单金额',
  `order_state` tinyint(4) NOT NULL DEFAULT '2' COMMENT '充值订单状态：0已充值、1已退款、2未支付、3未充值 4、已支付  、5已撤销    默认2',
  `cheap_price` decimal(20,2) DEFAULT NULL COMMENT '订单优惠金额',
  `actual_price` decimal(20,2) DEFAULT NULL COMMENT '实际支付金额',
  `order_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '充值订单类型: 0话费充值 默认0',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
  `goods_name` varchar(64) DEFAULT NULL COMMENT '商品名称',
  `good_price` decimal(10,2) DEFAULT NULL COMMENT '商品单价',
  `wechat_openid` varchar(48) DEFAULT NULL COMMENT '用户微信openid',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `refresh_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
  `recharge_tel` varchar(32) DEFAULT NULL COMMENT '充值手机号',
  PRIMARY KEY (`id`),
  KEY `IX_ORDER_NO` (`order_no`) USING BTREE COMMENT '订单号索引',
  KEY `IX_GOODS_ID` (`goods_id`) USING BTREE COMMENT '商品ID 索引',
  KEY `IX_wechat_openid` (`wechat_openid`) USING BTREE COMMENT '用户openID索引',
  KEY `IX_order_type` (`order_type`) USING BTREE COMMENT '订单类型索引',
  KEY `IX_order_state` (`order_state`) USING BTREE COMMENT '订单状态索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值订单表';

-- ----------------------------
-- Table structure for xys_recharge_record
-- ----------------------------
DROP TABLE IF EXISTS `xys_recharge_record`;
CREATE TABLE `xys_recharge_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` varchar(48) DEFAULT NULL COMMENT '内部支付订单号',
  `state` tinyint(4) DEFAULT NULL COMMENT '充值状态：0充值中、1充值成功、2充值失败、3已退款',
  `wechat_openid` varchar(48) DEFAULT NULL COMMENT '用户微信openID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `record_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '充值记录类型  0话费充值  默认0',
  `recharge_tel` varchar(32) DEFAULT NULL COMMENT '充值电话',
  `create_time` datetime DEFAULT NULL COMMENT '下单时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '订单处理时间',
  `telephone_bill` decimal(20,2) DEFAULT NULL COMMENT '充值金额',
  `recharge_no` varchar(48) DEFAULT NULL COMMENT '内部充值订单号',
  `third_order_no` varchar(48) DEFAULT NULL COMMENT '服务商订单号',
  `third_order_cash` decimal(20,2) DEFAULT NULL COMMENT '服务商订单金额',
  PRIMARY KEY (`id`),
  KEY `IX_order_no` (`order_no`) USING BTREE COMMENT '订单号索引',
  KEY `IX_state` (`state`) USING BTREE COMMENT '订单状态索引',
  KEY `IX_goods_id` (`goods_id`) USING BTREE COMMENT '商品ID索引',
  KEY `IX_record_type` (`record_type`) USING BTREE COMMENT '记录类型索引',
  KEY `IX_wechat_openid` (`wechat_openid`) USING BTREE COMMENT '用户openID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值记录表';

-- ----------------------------
-- Table structure for xys_sell_rule
-- ----------------------------
DROP TABLE IF EXISTS `xys_sell_rule`;
CREATE TABLE `xys_sell_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `sell_period` tinyint(4) DEFAULT NULL COMMENT '星期(周一是1，周二是2以此类推)',
  `sell_time_start` time DEFAULT NULL COMMENT '售卖开始时间',
  `sell_time_end` time DEFAULT NULL COMMENT '售卖结束时间',
  `sell_number` int(11) DEFAULT '0' COMMENT '售卖数量 : 0 无限制',
  `rule_type` tinyint(4) DEFAULT NULL COMMENT '规则类型: 0卡卷 1活动',
  `sell_goods` int(11) DEFAULT NULL COMMENT '售卖商品ID',
  PRIMARY KEY (`id`),
  KEY `IX_sell_goods` (`sell_goods`) USING BTREE COMMENT '商品索引',
  KEY `IX_RULE_TYPE` (`rule_type`) USING BTREE COMMENT '规则类型索引',
  KEY `IX_SELL_PERIOD` (`sell_period`) USING BTREE COMMENT '星期索引',
  KEY `IX_SELL_NUMBER` (`sell_number`) USING BTREE COMMENT '售卖数量索引'
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COMMENT='售卖规则表';

-- ----------------------------
-- Table structure for xys_store
-- ----------------------------
DROP TABLE IF EXISTS `xys_store`;
CREATE TABLE `xys_store` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '门店ID',
  `store_name` varchar(64) DEFAULT NULL COMMENT '门店名称',
  `mer_id` int(11) NOT NULL COMMENT '商户ID',
  `address` varchar(64) NOT NULL COMMENT '详细地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `store_state` tinyint(4) DEFAULT '0' COMMENT '门店状态：0营业、1停业',
  `province` varchar(32) DEFAULT NULL COMMENT '省',
  `city` varchar(32) NOT NULL COMMENT '市',
  `area` varchar(32) DEFAULT NULL COMMENT '区',
  `business_hours` varchar(50) DEFAULT NULL COMMENT '营业时间',
  `servicenum` tinyint(4) DEFAULT '0' COMMENT '服务号关联 0关联 , 1解绑',
  `longitude` double(9,6) NOT NULL DEFAULT '0.000000' COMMENT '经度',
  `latitude` double(8,6) NOT NULL DEFAULT '0.000000' COMMENT '纬度',
  `geocoding` varchar(64) DEFAULT NULL COMMENT '地理编码',
  `store_center_id` int(11) DEFAULT NULL COMMENT '门店中心门店ID',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人ID',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `delted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识  默认0未删除、1已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_mer_id` (`mer_id`) USING BTREE COMMENT 'mer索引',
  KEY `IX_state` (`store_state`) USING BTREE COMMENT 'state索引',
  KEY `IX_store_center_id` (`store_center_id`) USING BTREE COMMENT 'centerid索引'
) ENGINE=InnoDB AUTO_INCREMENT=3363 DEFAULT CHARSET=utf8mb4 COMMENT='门店表\n';

-- ----------------------------
-- Table structure for xys_system_user
-- ----------------------------
DROP TABLE IF EXISTS `xys_system_user`;
CREATE TABLE `xys_system_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_account` varchar(255) NOT NULL COMMENT '用户账号',
  `user_password` varchar(255) NOT NULL COMMENT '账号密码',
  `user_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别：0男、1女',
  `e_mail` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `user_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态：默认0启用、1停用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  `delted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识',
  `user_headimg` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `openid` varchar(32) DEFAULT NULL COMMENT '微信openID',
  `address` varchar(255) DEFAULT NULL COMMENT '通讯地址',
  `user_center_id` int(11) DEFAULT NULL COMMENT '用户中心用户ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_user_center_id` (`user_center_id`) USING BTREE COMMENT 'centerid索引',
  KEY `IX_openid` (`openid`) USING BTREE COMMENT 'openid索引',
  KEY `IX_user_state` (`user_state`) USING BTREE COMMENT 'state索引'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表\n';

-- ----------------------------
-- Table structure for xys_type
-- ----------------------------
DROP TABLE IF EXISTS `xys_type`;
CREATE TABLE `xys_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `type_name` varchar(32) NOT NULL COMMENT '类型名称',
  `type_shot_name` varchar(32) DEFAULT NULL COMMENT '类型简称',
  `type_introduce` varchar(255) DEFAULT NULL COMMENT '类型简介',
  `matter` varchar(255) DEFAULT NULL COMMENT '注意事项',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `type_belonged` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型对象：默认0商家 , 1优惠',
  `enable_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型状态：默认0隐藏、1显示',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人ID',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_enable_state` (`enable_state`) USING BTREE COMMENT 'state索引'
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='类型表\n';

-- ----------------------------
-- Table structure for xys_type_relation
-- ----------------------------
DROP TABLE IF EXISTS `xys_type_relation`;
CREATE TABLE `xys_type_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关系表主键',
  `merchant_id` int(11) NOT NULL COMMENT '商户ID',
  `type_id` int(11) NOT NULL COMMENT '类型ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_merchant_id` (`merchant_id`) USING BTREE COMMENT 'merchant索引',
  KEY `IX_type_id` (`type_id`) USING BTREE COMMENT 'type索引'
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='商户类型关系表\n';

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
) ENGINE=InnoDB AUTO_INCREMENT=4816 DEFAULT CHARSET=utf8mb4 COMMENT='用户表\n';

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

-- ----------------------------
-- Table structure for xys_wccard_baseinfo
-- ----------------------------
DROP TABLE IF EXISTS `xys_wccard_baseinfo`;
CREATE TABLE `xys_wccard_baseinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logo_url` varchar(255) DEFAULT NULL COMMENT '卡券的商户logo',
  `brand_name` varchar(64) DEFAULT NULL COMMENT '商户名字',
  `code_type` varchar(64) DEFAULT NULL COMMENT '码型',
  `title` varchar(64) DEFAULT NULL COMMENT '卡卷名',
  `color` varchar(64) DEFAULT NULL COMMENT '券颜色',
  `notice` varchar(64) DEFAULT NULL COMMENT '卡券使用提醒',
  `service_phone` varchar(64) DEFAULT NULL COMMENT '客服电话',
  `description` varchar(3000) DEFAULT NULL COMMENT '卡券使用说明',
  `use_limit` int(11) DEFAULT NULL COMMENT '每人可核销的数量限制',
  `get_limit` int(11) DEFAULT NULL COMMENT '每人可领券的数量限制',
  `use_custom_code` tinyint(1) DEFAULT NULL COMMENT '是否自定义Code码',
  `bind_openid` tinyint(1) DEFAULT NULL COMMENT '是否指定用户领取',
  `can_share` tinyint(1) DEFAULT NULL COMMENT '卡券领取页面是否可分享',
  `can_give_friend` tinyint(1) DEFAULT NULL COMMENT '卡券是否可转赠',
  `center_title` varchar(255) DEFAULT NULL COMMENT '卡券顶部居中的按钮',
  `center_sub_title` varchar(255) DEFAULT NULL COMMENT '显示在入口下方的提示语 ，仅在卡券状态正常(可以核销)时显示',
  `center_url` varchar(255) DEFAULT NULL COMMENT '顶部居中的url',
  `custom_url_name` varchar(255) DEFAULT NULL COMMENT '自定义跳转外链的入口名字',
  `custom_url_sub_title` varchar(255) DEFAULT NULL COMMENT '显示在入口右侧的提示语',
  `promotion_url_name` varchar(255) DEFAULT NULL COMMENT '营销场景的自定义入口名称',
  `promotion_url` varchar(255) DEFAULT NULL COMMENT '入口跳转外链的地址链接',
  `get_custom_code_mode` varchar(255) DEFAULT NULL COMMENT '填入 GET_CUSTOM_CODE_MODE_DEPOSIT 表示该卡券为预存code模式卡券， 须导入超过库存数目的自定义code后方可投放， 填入该字段后，quantity字段须为0,须导入code 后再增加库存',
  `use_all_locations` varchar(255) DEFAULT NULL COMMENT 'UseCondition',
  `custom_app_brand_user_name` varchar(255) DEFAULT NULL COMMENT '卡券跳转的小程序的user_name',
  `custom_app_brand_pass` varchar(255) DEFAULT NULL COMMENT '卡券跳转的小程序的path',
  `card_id` int(11) DEFAULT NULL COMMENT '卡卷ID',
  `promotion_url_sub_title` varchar(255) DEFAULT NULL COMMENT '显示在营销入口右侧的提示语。',
  `promotion_app_brand_user_name` varchar(255) DEFAULT NULL COMMENT '卡券跳转的小程序的user_name，仅可跳转该 公众号绑定的小程序 ',
  `promotion_app_brand_pass` varchar(255) DEFAULT NULL COMMENT '卡券跳转的小程序的path',
  `custom_url` varchar(255) DEFAULT NULL COMMENT '自定义跳转的URL。',
  PRIMARY KEY (`id`),
  KEY `IX_CARD_ID` (`card_id`) USING BTREE COMMENT '卡卷ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信公众号卡卷基础信息表';

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

-- ----------------------------
-- Table structure for xys_wccard_card
-- ----------------------------
DROP TABLE IF EXISTS `xys_wccard_card`;
CREATE TABLE `xys_wccard_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `card_type` varchar(32) DEFAULT NULL COMMENT '卡卷类型',
  `default_detail` varchar(255) DEFAULT NULL COMMENT '优惠详情',
  `card_score` int(11) DEFAULT '0' COMMENT '兑换积分',
  `card_price` decimal(20,0) DEFAULT '0' COMMENT '兑换金额',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_edit_id` int(11) DEFAULT NULL COMMENT '修改人',
  `last_edit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `wc_chat_card_id` varchar(32) DEFAULT NULL COMMENT '微信公众号卡卷ID',
  `mutual_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '互通状态: 0未互通   1已互通 默认0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识 0正常 1删除 默认0',
  `coupon_name` varchar(255) DEFAULT NULL COMMENT '卡券全称',
  `coupon_shot_name` varchar(32) DEFAULT NULL COMMENT '卡券简称',
  `coupon_valid_time_start` datetime DEFAULT NULL COMMENT '卡券有效期限起始',
  `coupon_valid_time_end` datetime DEFAULT NULL COMMENT '卡券有效期限截止',
  `mer_id` int(11) DEFAULT NULL COMMENT '商户id',
  `bank_id` int(11) DEFAULT NULL COMMENT '银行id',
  `describes` varchar(500) DEFAULT NULL COMMENT '描述',
  `matter` varchar(2000) DEFAULT NULL COMMENT '事项',
  `price` decimal(20,2) DEFAULT NULL COMMENT '卡券金额',
  `discount_price` decimal(20,2) DEFAULT NULL COMMENT '卡券优惠后金额',
  `coupon_stock_id` varchar(50) DEFAULT NULL COMMENT '微信代金券批次id',
  `coupon_image` varchar(255) DEFAULT NULL COMMENT '卡券图片地址',
  `coupon_stocks` int(11) NOT NULL DEFAULT '0' COMMENT '卡卷库存',
  PRIMARY KEY (`id`),
  KEY `IX_WCCHAT_ID` (`wc_chat_card_id`) USING BTREE COMMENT '微信公众号卡卷ID索引',
  KEY `IX_DELETED` (`deleted`) USING BTREE COMMENT '删除标识索引',
  KEY `IX_CARD_SCORE` (`card_score`) USING BTREE COMMENT '兑换积分索引',
  KEY `IX_CARD_PRICE` (`card_price`) USING BTREE COMMENT '兑换金额索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信公众号卡卷表(-含商户号卡卷信息)';

SET FOREIGN_KEY_CHECKS = 1;
