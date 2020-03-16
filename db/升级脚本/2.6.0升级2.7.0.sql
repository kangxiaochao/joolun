ALTER TABLE `base_mall`.`user_info` DROP PRIMARY KEY, ADD PRIMARY KEY (`id`);
ALTER TABLE `base_wx`.`wx_user` DROP PRIMARY KEY, ADD PRIMARY KEY (`id`); 

CREATE TABLE `base_mall`.`undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `base_wx`.`undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

DELETE FROM `base_upms`.`sys_menu` WHERE `id` = '8400000';
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('8400000','规格管理','','goodsspec','8500000','el-icon-s-operation','views/mall/goodsspec/index','1','0','0','2019-08-13 16:22:40','2019-08-13 16:23:51','0');


CREATE TABLE `base_upms`.`sys_config_storage` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `storage_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '存储类型1、阿里OSS；2、七牛云；3、minio',
  `endpoint` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '地域节点',
  `access_key_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'access_key',
  `access_key_secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'access_secret',
  `bucket` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '域名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='存储配置';

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1224307007944609794','文件存储配置修改','sys_configstorage_edit',NULL,'1224306298712969218',NULL,NULL,'0','0','1','2020-02-03 20:21:51',NULL,'0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1224306628599173121','文件存储配置查询','sys_configstorage_get',NULL,'1224306298712969218',NULL,NULL,'0','0','1','2020-02-03 20:20:21',NULL,'0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1224306298712969218','文件存储配置',NULL,'configstorage','2000000','el-icon-s-management','views/admin/configstorage/form','15','0','0','2020-02-03 20:19:02',NULL,'0');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1224307007944609794','2020-02-03 20:21:51');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1224306628599173121','2020-02-03 20:20:21');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1224306298712969218','2020-02-03 20:19:02');

insert into `base_upms`.`sys_dict` (`id`, `type`, `description`, `create_time`, `update_time`, `remarks`, `del_flag`) values('1224310636680245250','storage_type','存储类型','2020-02-03 20:36:17','2020-02-03 20:36:17',NULL,'0');

insert into `base_upms`.`sys_dict_value` (`id`, `dict_id`, `value`, `label`, `type`, `description`, `sort`, `create_time`, `update_time`, `remarks`, `del_flag`) values('1224310888170713090','1224310636680245250','3','minio或本地','storage_type','minio','3','2020-02-03 20:37:17','2020-02-03 20:37:17',NULL,'0');
insert into `base_upms`.`sys_dict_value` (`id`, `dict_id`, `value`, `label`, `type`, `description`, `sort`, `create_time`, `update_time`, `remarks`, `del_flag`) values('1224310831061069825','1224310636680245250','2','七牛云','storage_type','七牛云','2','2020-02-03 20:37:03','2020-02-03 20:37:03',NULL,'0');
insert into `base_upms`.`sys_dict_value` (`id`, `dict_id`, `value`, `label`, `type`, `description`, `sort`, `create_time`, `update_time`, `remarks`, `del_flag`) values('1224310769123782658','1224310636680245250','1','阿里OSS','storage_type','阿里OSS','1','2020-02-03 20:36:48','2020-02-03 20:36:48',NULL,'0');


ALTER TABLE `base_upms`.`sys_config_storage` ADD COLUMN `water_mark_content` VARCHAR(64) NULL COMMENT '图片水印内容' AFTER `bucket`; 


CREATE TABLE `base_mall`.`delivery_place` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `place` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地方',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='发货地';

ALTER TABLE `base_mall`.`goods_spu` ADD COLUMN `delivery_place_id` VARCHAR(32) NULL COMMENT '发货地ID' AFTER `freight_templat_id`; 

CREATE TABLE `base_mall`.`ensure` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '保障名',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '保障详情',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='保障服务';

CREATE TABLE `base_mall`.`ensure_goods` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `ensure_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '保障ID',
  `spu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品spuID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='商品保障';

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226526082179481602','保障服务删除','mall_ensure_del','','1226525445274419201','','','0','0','1','2020-02-09 23:19:40','2020-02-09 23:35:42','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226525967616262146','保障服务修改','mall_ensure_edit','','1226525445274419201','','','0','0','1','2020-02-09 23:19:13','2020-02-09 23:35:45','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226525832920383490','保障服务新增','mall_ensure_add','','1226525445274419201','','','0','0','1','2020-02-09 23:18:40','2020-02-09 23:35:47','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226525700103553025','保障服务查询','mall_ensure_get','','1226525445274419201','','','0','0','1','2020-02-09 23:18:09','2020-02-09 23:35:50','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226525576396750849','保障服务列表','mall_ensure_index','','1226525445274419201','','','0','0','1','2020-02-09 23:17:39','2020-02-09 23:35:57','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226525445274419201','保障服务管理','','ensure','f6cb055276971023060a341236237bb2','el-icon-s-management','views/mall/ensure/index','20','0','0','2020-02-09 23:17:08','2020-02-09 23:34:55','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226514858779820034','发货地删除','mall_deliveryplace_del','','1226513939979780098','','','0','0','1','2020-02-09 22:35:04',NULL,'0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226514630622265346','发货地修改','mall_deliveryplace_edit','','1226513939979780098','','','0','0','1','2020-02-09 22:34:10',NULL,'0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226514436212080641','发货地新增','mall_deliveryplace_add','','1226513939979780098','','','0','0','1','2020-02-09 22:33:23',NULL,'0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226514304217333762','发货地列表','mall_deliveryplace_index','','1226513939979780098','','','0','0','1','2020-02-09 22:32:52','2020-02-09 22:34:33','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226514140274573313','发货地查询','mall_deliveryplace_get','','1226513939979780098','','','0','0','1','2020-02-09 22:32:13',NULL,'0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226513939979780098','发货地管理','','deliveryplace','f6cb055276971023060a341236237bb2','el-icon-s-home','views/mall/deliveryplace/index','10','0','0','2020-02-09 22:31:25','2020-02-09 23:15:01','0');

insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226526082179481602','2020-02-09 23:19:40');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226525967616262146','2020-02-09 23:19:13');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226525832920383490','2020-02-09 23:18:40');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226525700103553025','2020-02-09 23:18:09');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226525576396750849','2020-02-09 23:17:39');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226525445274419201','2020-02-09 23:17:08');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226514858779820034','2020-02-09 22:35:04');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226514630622265346','2020-02-09 22:34:10');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226514436212080641','2020-02-09 22:33:23');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226514304217333762','2020-02-09 22:32:52');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226514140274573313','2020-02-09 22:32:13');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226513939979780098','2020-02-09 22:31:25');

delete from `base_upms`.`sys_menu` where `id` = 'e2ab8932bc86ad98b917c22731205caa';
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('e2ab8932bc86ad98b917c22731205caa','素材库',NULL,'material','f6cb055276971023060a341236237bb2','el-icon-s-platform','views/mall/material/index','30','0','0','2019-10-26 19:33:45','2020-02-10 11:46:41','0');

CREATE TABLE `base_upms`.`sys_config_editor` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `editor_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '编辑器类型1、quill-editor；2、froala',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='编辑器配置';

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226762096135340033','系统配置','','config','2000000','el-icon-s-tools','Layout','20','0','0','2020-02-10 14:57:30',NULL,'0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226760046232137729','文本编辑器修改','sys_configeditor_edit','','1226759608866893825','','','0','0','1','2020-02-10 14:49:21',NULL,'0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226759834914713601','文本编辑器查询','sys_configeditor_get','','1226759608866893825','','','0','0','1','2020-02-10 14:48:31',NULL,'0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1226759608866893825','文本编辑器配置','','configeditor','1226762096135340033','el-icon-s-unfold','views/admin/configeditor/form','30','0','0','2020-02-10 14:47:37','2020-02-10 14:59:02','0');

insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226762096135340033','2020-02-10 14:57:30');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226760046232137729','2020-02-10 14:49:21');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226759834914713601','2020-02-10 14:48:31');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1226759608866893825','2020-02-10 14:47:37');

delete from `base_upms`.`sys_menu` where `id` = '1224306298712969218';

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1224306298712969218','文件存储配置',NULL,'configstorage','1226762096135340033','el-icon-s-management','views/admin/configstorage/form','15','0','0','2020-02-03 20:19:02','2020-02-05 22:07:13','0');

insert into `base_upms`.`sys_dict` (`id`, `type`, `description`, `create_time`, `update_time`, `remarks`, `del_flag`) values('1226758602905022466','editor_type','文本编辑器','2020-02-10 14:43:37','2020-02-10 14:43:37',NULL,'0');
insert into `base_upms`.`sys_dict_value` (`id`, `dict_id`, `value`, `label`, `type`, `description`, `sort`, `create_time`, `update_time`, `remarks`, `del_flag`) values('1226758779615244290','1226758602905022466','2','froala','editor_type','froala','2','2020-02-10 14:44:19','2020-02-10 14:44:19',NULL,'0');
insert into `base_upms`.`sys_dict_value` (`id`, `dict_id`, `value`, `label`, `type`, `description`, `sort`, `create_time`, `update_time`, `remarks`, `del_flag`) values('1226758708496625665','1226758602905022466','1','quill-editor','editor_type','quill-editor','1','2020-02-10 14:44:02','2020-02-10 14:44:02',NULL,'0');
