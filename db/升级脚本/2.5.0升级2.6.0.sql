CREATE TABLE `base_mall`.`bargain_info` (
  `id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` VARCHAR(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `sort` INT(11) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `enable` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '（1：开启；0：关闭）',
  `spu_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品Id',
  `sku_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'skuId',
  `name` VARCHAR(100) DEFAULT NULL COMMENT '砍价名称',
  `valid_begin_time` DATETIME NOT NULL COMMENT '开始时间',
  `valid_end_time` DATETIME NOT NULL COMMENT '结束时间',
  `bargain_price` DECIMAL(10,2) NOT NULL COMMENT '砍价底价',
  `self_cut` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '自砍一刀（1：开启；0：关闭）',
  `floor_buy` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '必须底价购买（1：是；0：否）',
  `launch_num` INT(11) DEFAULT '0' COMMENT '发起人数',
  `cut_max` DECIMAL(10,2) NOT NULL COMMENT '单次可砍最高金额',
  `cut_min` DECIMAL(10,2) NOT NULL COMMENT '单次可砍最低金额',
  `pic_url` VARCHAR(500) DEFAULT NULL COMMENT '图片',
  `cut_rule` TEXT COMMENT '砍价规则',
  `share_title` VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分享标题 ',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='砍价';

CREATE TABLE `base_mall`.`bargain_user` (
  `id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` VARCHAR(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `bargain_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '砍价ID',
  `user_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `spu_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品Id',
  `sku_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'skuId',
  `begin_time` DATETIME NOT NULL COMMENT '开始时间',
  `end_time` DATETIME NOT NULL COMMENT '结束时间',
  `bargain_price` DECIMAL(10,2) NOT NULL COMMENT '砍价底价',
  `floor_buy` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '必须底价购买（1：是；0：否）',
  `cut_num` INT(11) NOT NULL COMMENT '帮砍人数',
  `status` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '状态（0：砍价中；1：完成砍价）',
  `is_buy` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '是否购买（1：是；0：否）',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='砍价记录';

CREATE TABLE `base_mall`.`bargain_cut` (
  `id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` VARCHAR(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `bargain_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '砍价ID',
  `bargain_user_id` VARCHAR(32) DEFAULT NULL COMMENT '砍价用户ID',
  `user_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `cut_price` DECIMAL(10,2) NOT NULL COMMENT '砍价金额',
  `nick_name` VARCHAR(100) DEFAULT NULL COMMENT '昵称',
  `headimg_url` VARCHAR(1000) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='砍价帮砍记录';

UPDATE `base_upms`.`sys_menu` SET `sort` = 1 WHERE `sort` IS NULL;
ALTER TABLE `base_upms`.`sys_menu` CHANGE `sort` `sort` INT(11) DEFAULT 1 NOT NULL COMMENT '排序值'; 

ALTER TABLE `base_mall`.`order_info` ADD COLUMN `order_type` CHAR(2) DEFAULT '0' NULL COMMENT '订单类型（0、普通订单；1、砍价订单）' AFTER `transaction_id`, ADD COLUMN `relation_id` VARCHAR(32) NULL COMMENT '关联ID（砍价记录ID）' AFTER `order_type`, CHANGE `remark` `remark` VARCHAR(255) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注' AFTER `relation_id`; 
UPDATE `base_mall`.`order_info` SET `order_type` = '0' WHERE `order_type` IS NULL;

ALTER TABLE `base_mall`.`bargain_user` ADD COLUMN `order_id` VARCHAR(32) NULL COMMENT '订单Id' AFTER `is_buy`; 

ALTER TABLE `base_mall`.`bargain_user` CHANGE `begin_time` `valid_begin_time` DATETIME NOT NULL COMMENT '开始时间', CHANGE `end_time` `valid_end_time` DATETIME NOT NULL COMMENT '结束时间'; 

ALTER TABLE `base_mall`.`bargain_user` DROP COLUMN `cut_num`;

ALTER TABLE `base_mall`.`bargain_user` CHANGE `is_buy` `is_buy` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' NOT NULL COMMENT '是否购买（1：是；0：否）'; 

INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('4e2f07380b3a3a3c83491b44becc87d1','砍价记录列表','mall_bargainuser_index',NULL,'cb3e50350c8d982ee23474146281f7ed',NULL,NULL,'0','0','1','2020-01-03 19:50:15',NULL,'0');
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('cb3e50350c8d982ee23474146281f7ed','砍价记录',NULL,'bargainuser','5b1483fcac1c14bcabc28b7e50f21921','el-icon-s-order','views/mall/bargainuser/index','2','0','0','2020-01-03 19:49:23','2020-01-03 19:49:37','0');
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('33f86fb25ecde4e2bc5ec0bdf9229e28','砍价配置',NULL,'bargaininfo','5b1483fcac1c14bcabc28b7e50f21921','el-icon-s-tools','views/mall/bargaininfo/index','1','0','0','2019-12-28 19:06:53','2019-12-28 19:08:58','0');
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('17e1ead2dacd1d312105b396b3517032','砍价删除','mall_bargaininfo_del',NULL,'33f86fb25ecde4e2bc5ec0bdf9229e28',NULL,NULL,'1','0','1','2019-12-28 19:04:39','2019-12-28 19:09:50','0');
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('99de545b2709c749074253793b0b3579','砍价修改','mall_bargaininfo_edit',NULL,'33f86fb25ecde4e2bc5ec0bdf9229e28',NULL,NULL,'1','0','1','2019-12-28 19:04:18','2019-12-28 19:09:30','0');
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('8aae802c5e76c10fb00c342870aec822','砍价新增','mall_bargaininfo_add',NULL,'33f86fb25ecde4e2bc5ec0bdf9229e28',NULL,NULL,'1','0','1','2019-12-28 19:03:56','2019-12-28 19:09:22','0');
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('bec7e2f7cf4ad760bbe550fa05f7eec4','砍价查询','mall_bargaininfo_get',NULL,'33f86fb25ecde4e2bc5ec0bdf9229e28',NULL,NULL,'1','0','1','2019-12-28 19:03:36','2019-12-28 19:09:11','0');
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('5a46087c7799d15b3f7dec6373459c18','砍价列表','mall_bargaininfo_index',NULL,'33f86fb25ecde4e2bc5ec0bdf9229e28',NULL,NULL,'1','0','1','2019-12-28 19:03:15','2019-12-28 19:09:39','0');
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('5b1483fcac1c14bcabc28b7e50f21921','砍价管理',NULL,'bargain','8000000','el-icon-watermelon','Layout','7','0','0','2019-12-28 18:59:37','2019-12-28 19:02:29','0');

INSERT INTO `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) VALUES('1','4e2f07380b3a3a3c83491b44becc87d1','2020-01-03 19:50:15');
INSERT INTO `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) VALUES('1','cb3e50350c8d982ee23474146281f7ed','2020-01-03 19:49:23');
INSERT INTO `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) VALUES('1','33f86fb25ecde4e2bc5ec0bdf9229e28','2019-12-28 19:06:53');
INSERT INTO `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) VALUES('1','17e1ead2dacd1d312105b396b3517032','2019-12-28 19:04:39');
INSERT INTO `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) VALUES('1','99de545b2709c749074253793b0b3579','2019-12-28 19:04:18');
INSERT INTO `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) VALUES('1','8aae802c5e76c10fb00c342870aec822','2019-12-28 19:03:56');
INSERT INTO `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) VALUES('1','bec7e2f7cf4ad760bbe550fa05f7eec4','2019-12-28 19:03:36');
INSERT INTO `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) VALUES('1','5a46087c7799d15b3f7dec6373459c18','2019-12-28 19:03:15');
INSERT INTO `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) VALUES('1','5b1483fcac1c14bcabc28b7e50f21921','2019-12-28 18:59:37');