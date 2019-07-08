/*

 Date: 23/01/2019 13:07:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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

SET FOREIGN_KEY_CHECKS = 1;
