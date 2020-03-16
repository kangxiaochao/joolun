insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1227203300044042241','发送邮件','sys_email_add','','1227202599968567298','','','0','0','1','2020-02-11 20:10:41',NULL,'0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1227202599968567298','邮件管理','','email','1227202152985784322','el-icon-s-promotion','views/admin/email/form','10','0','0','2020-02-11 20:07:54','2020-02-11 20:11:34','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1227202152985784322','常用工具','','/tool','-1','el-icon-s-cooperation','Layout','3','0','0','2020-02-11 20:06:08','2020-02-11 20:26:55','0');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1227203300044042241','2020-02-12 17:26:13');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1227202599968567298','2020-02-12 17:26:13');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1227202152985784322','2020-02-12 17:26:13');

delete from `base_upms`.`sys_menu` where `id` = '2700000';
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('2700000','文档管理',NULL,'https://demo.joolun.com/doc.html','1227202152985784322','el-icon-s-claim','https://demo.joolun.com/doc.html','8','0','0','2019-07-31 11:41:39','2019-07-31 11:57:07','0');

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1227524435889614850','发短信','sys_sms_add','','1227524298689736706','','','0','0','1','2020-02-12 17:26:46',NULL,'0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1227524298689736706','短信工具','','sms','1227202152985784322','el-icon-s-comment','views/admin/sms/form','20','0','0','2020-02-12 17:26:13',NULL,'0');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1227524435889614850','2020-02-12 17:26:46');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1227524298689736706','2020-02-12 17:26:13');

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1227568833306177537','规格查询','mall_goodsspec_get','','8400000','','','0','0','1','2020-02-12 20:23:11',NULL,'0');

ALTER TABLE `base_upms`.`sys_organ` ADD COLUMN `status` CHAR(2) NULL COMMENT '0:正常，9:冻结' AFTER `tenant_id`; 

UPDATE `base_upms`.`sys_organ` SET `status` = '0' WHERE `status` IS NULL AND `parent_id`='0';

insert into `base_upms`.`sys_dict_value` (`id`, `dict_id`, `value`, `label`, `type`, `description`, `sort`, `create_time`, `update_time`, `remarks`, `del_flag`) values('1227915814432108545','1224310636680245250','4','腾讯cos','storage_type','腾讯cos','4','2020-02-13 19:21:58','2020-02-13 19:21:58',NULL,'0');

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1228676836369616897','小程序用户详情','wxmp_wxuser_get','','1228676296659161089','','','0','0','1','2020-02-15 21:46:00',NULL,'0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1228676665992794113','小程序用户列表','wxmp_wxuser_index','','1228676296659161089','','','0','0','1','2020-02-15 21:45:19',NULL,'0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1228676296659161089','小程序用户','','wxuser','ae0619ea3e5b86a3e382081680785f7e','el-icon-user-solid','views/wxma/wxuser/index','2','0','0','2020-02-15 21:43:51',NULL,'0');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1228676836369616897','2020-02-15 21:46:00');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1228676665992794113','2020-02-15 21:45:19');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1228676296659161089','2020-02-15 21:43:51');

UPDATE `base_upms`.`sys_menu` SET `update_time` = NOW() WHERE `update_time` IS NULL;
ALTER TABLE `base_upms`.`sys_menu` CHANGE `create_time` `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间', CHANGE `update_time` `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间'; 

delete from `base_upms`.`sys_menu` where `id` = '8150000';
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('8150000','用户标签管理',NULL,'wxusertags','8100000','el-icon-s-flag','views/wxmp/wxusertags/index','1','0','0','2019-04-16 17:28:44','2020-02-16 11:03:47','0');

UPDATE `base_upms`.`sys_menu` SET `parent_id` = '0' WHERE `parent_id` = '-1';
UPDATE `base_wx`.`wx_menu` SET `parent_id` = '0' WHERE `parent_id` = '-1';

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1229323003373785090','数据统计列表','wxmp_wxsummary_index','','1229322873778180098','','','0','0','1','2020-02-17 16:33:38','2020-02-17 16:33:38','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1229322873778180098','数据统计','','wxsummary','8100000','el-icon-s-marketing','views/wxmp/wxsummary/index','20','0','0','2020-02-17 16:33:07','2020-02-17 16:33:07','0');

insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1229323003373785090','2020-02-17 16:33:38');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1229322873778180098','2020-02-17 16:33:07');

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1229675965086429186','微信模块','','/weixin','0','icon-weixin','Layout','0','0','0','2020-02-18 15:56:11','2020-02-18 15:58:33','0');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1229675965086429186','2020-02-18 15:56:11');

delete from `base_upms`.`sys_menu` where `id` = 'f513f9e7cb53c16e5de982e12268b070';
delete from `base_upms`.`sys_menu` where `id` = 'ae0619ea3e5b86a3e382081680785f7e';
delete from `base_upms`.`sys_menu` where `id` = '8100000';
delete from `base_upms`.`sys_menu` where `id` = '8000000';
delete from `base_upms`.`sys_menu` where `id` = '1229675965086429186';
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('f513f9e7cb53c16e5de982e12268b070','敏捷开发',NULL,'develop','1227202152985784322','el-icon-s-marketing','Layout','4','0','0','2019-11-05 18:12:32','2020-02-18 16:09:24','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('ae0619ea3e5b86a3e382081680785f7e','小程序管理',NULL,'wxma','1229675965086429186','el-icon-orange','Layout','10','0','0','2019-09-25 14:25:17','2020-02-18 16:07:54','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('8100000','公众号管理','','wxmp','1229675965086429186','icon-gongzhonghao','Layout','0','0','0','2018-01-20 13:17:19','2020-02-18 16:07:41','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('8000000','商城模块','','/mall','0','el-icon-s-shop','Layout','0','0','0','2018-01-20 13:17:19','2020-02-18 16:06:15','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1229675965086429186','微信模块','','/weixin','0','icon-weixin','Layout','0','0','0','2020-02-18 15:56:11','2020-02-18 15:58:33','0');

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1229750964161814530','通知删除','mall_noticeitem_del','','1229750327311278082','','','0','0','1','2020-02-18 20:54:12','2020-02-18 20:54:12','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1229750881777295362','通知列表','mall_noticeitem_index','','1229750327311278082','','','0','0','1','2020-02-18 20:53:52','2020-02-18 20:53:52','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1229750794644824065','通知新增','mall_noticeitem_add','','1229750327311278082','','','0','0','1','2020-02-18 20:53:31','2020-02-18 20:53:31','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1229750717675151362','通知修改','mall_noticeitem_edit','','1229750327311278082','','','0','0','1','2020-02-18 20:53:13','2020-02-18 20:53:13','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1229750619910119425','通知详情','mall_noticeitem_get','','1229750327311278082','','','0','0','1','2020-02-18 20:52:50','2020-02-18 20:52:50','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1229750327311278082','首页公告','','notice2','ae0619ea3e5b86a3e382081680785f7e','el-icon-message-solid','views/mall/noticeitem/index-text','20','0','0','2020-02-18 20:51:40','2020-02-18 20:51:40','0');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1229750964161814530','2020-02-18 20:54:12');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1229750881777295362','2020-02-18 20:53:52');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1229750794644824065','2020-02-18 20:53:31');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1229750717675151362','2020-02-18 20:53:13');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1229750619910119425','2020-02-18 20:52:50');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`) values('1','1229750327311278082','2020-02-18 20:51:40');

delete from `base_upms`.`sys_menu` where `id` = '8bd8eabf33a6b8b56c04392063bade07';
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('8bd8eabf33a6b8b56c04392063bade07','首页轮播图',NULL,'notice1','ae0619ea3e5b86a3e382081680785f7e','el-icon-picture','views/mall/noticeitem/index','10','0','0','2019-11-10 19:06:26','2020-02-18 20:49:04','0');

delete from `base_upms`.`sys_menu` where `id` = '2300000';
delete from `base_upms`.`sys_menu` where `id` = 'f513f9e7cb53c16e5de982e12268b070';
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('2300000','代码生成器','','gen','f513f9e7cb53c16e5de982e12268b070','icon-daimashengcheng','views/gen/index','4','0','0','2018-01-20 13:17:19','2020-02-18 21:07:24','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('f513f9e7cb53c16e5de982e12268b070','代码生成',NULL,'develop','1227202152985784322','el-icon-s-marketing','Layout','4','0','0','2019-11-05 18:12:32','2020-02-18 21:07:16','0');

DELETE FROM `base_upms`.`sys_menu` WHERE `id` = 'a96328161a39450238e8cafa243b7c35';
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('a96328161a39450238e8cafa243b7c35','小程序列表','','wxapp','ae0619ea3e5b86a3e382081680785f7e','el-icon-s-operation','views/wxma/wxapp/index','0','0','0','2019-09-25 17:09:00','2020-02-18 18:16:24','0');

DELETE FROM `base_upms`.`sys_menu` WHERE `id` = '8110000';
insert into `sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('8110000','公众号列表','','wxapp','8100000','icon-peizhi','views/wxmp/wxapp/index','0','0','0','2018-01-20 13:17:19','2020-02-18 18:15:10','0');

DELETE FROM `base_upms`.`sys_menu` WHERE `id` = '8150000';
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('8150000','用户标签',NULL,'wxusertags','8100000','el-icon-s-flag','views/wxmp/wxusertags/index','1','0','0','2019-04-16 17:28:44','2020-02-18 18:15:24','0');

DELETE FROM `base_upms`.`sys_menu` WHERE `id` = '8120000';
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('8120000','公众号用户','','wxuser','8100000','icon-yonghu','views/wxmp/wxuser/index','2','0','0','2018-01-20 13:17:19','2019-04-02 09:50:46','0');

DELETE FROM `base_upms`.`sys_menu` WHERE `id` = '8170000';
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('8170000','用户消息','','wxmsg','8100000','el-icon-s-comment','views/wxmp/wxmsg/index','3','0','0','2019-05-28 16:04:18','2020-02-18 18:15:32','0');

DELETE FROM `base_upms`.`sys_menu` WHERE `id` = '8130000';
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('8130000','素材管理','','wxmaterial','8100000','el-icon-s-cooperation','views/wxmp/wxmaterial/index','4','0','0','2018-01-20 13:17:19','2019-04-02 09:50:46','0');

DELETE FROM `base_upms`.`sys_menu` WHERE `id` = '8140000';
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('8140000','自定义菜单','','wxmenu','8100000','el-icon-s-unfold','views/wxmp/wxmenu/detail','5','0','0','2018-01-20 13:17:19','2020-02-18 18:15:40','0');

DELETE FROM `base_upms`.`sys_menu` WHERE `id` = '8160000';
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('8160000','消息自动回复','','wxautoreply','8100000','el-icon-s-comment','views/wxmp/wxautoreply/index','6','0','0','2018-01-20 13:17:19','2018-07-29 13:38:19','0');

DELETE FROM `base_upms`.`sys_menu` WHERE `id` = '8180000';
INSERT INTO `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) VALUES('8180000','消息群发','','wxmassmsg','8100000','el-icon-s-comment','views/wxmp/wxmassmsg/index','8','0','0','2018-01-20 13:17:19','2020-02-18 18:15:48','0');


