CREATE TABLE `base_mall`.`material` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `type` char(2) NOT NULL COMMENT '类型1、图片；2、视频',
  `group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '分组ID',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '素材名',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '素材链接',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='素材库';

CREATE TABLE `base_mall`.`material_group` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分组名',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='素材分组';


ALTER TABLE `base_mall`.`goods_spu` ADD COLUMN `points_give_switch` CHAR(2) NULL COMMENT '积分赠送开关（1开 0关）' AFTER `del_flag`, ADD COLUMN `points_give_scale` INT NULL COMMENT '积分赠送比例（0~100）' AFTER `points_give_switch`, ADD COLUMN `points_deduct_switch` CHAR(2) NULL COMMENT '积分抵扣开关（1开 0关）' AFTER `points_give_scale`, ADD COLUMN `points_deduct_scale` INT NULL COMMENT '积分抵扣比例（0~100）' AFTER `points_deduct_switch`, ADD COLUMN `points_deduct_max` INT NULL COMMENT '积分抵扣最多使用积分数' AFTER `points_deduct_scale`, ADD COLUMN `points_deduct_min` INT NULL COMMENT '积分抵扣最少使用积分数' AFTER `points_deduct_max`; 

ALTER TABLE `base_mall`.`goods_spu` DROP COLUMN `points_deduct_min`, CHANGE `points_give_scale` `points_give_num` INT(11) NULL COMMENT '积分赠送数量', CHANGE `points_deduct_scale` `points_deduct_scale` INT(11) NULL COMMENT '积分抵扣商品金额比例（0~100）', CHANGE `points_deduct_max` `points_deduct_amount` INT(11) NULL COMMENT '1积分数可抵多少元'; 

ALTER TABLE `base_mall`.`goods_spu` CHANGE `points_deduct_amount` `points_deduct_amount` DECIMAL(10,2) NULL COMMENT '1积分数可抵多少元'; 

CREATE TABLE `base_mall`.`vip_user` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号码',
  `app_type` char(2) NOT NULL COMMENT '来源应用类型1、小程序；2、公众号',
  `app_id` char(2) NOT NULL COMMENT '来源应用id',
  `vip_grade` smallint(6) DEFAULT NULL COMMENT '会员等级',
  `points_current` int(11) DEFAULT NULL COMMENT '当前积分',
  `points_accrued` int(11) DEFAULT NULL COMMENT '累积积分',
  `amount_accrued` decimal(10,2) DEFAULT NULL COMMENT '累积消费金额',
  `number_accrued` int(11) DEFAULT NULL COMMENT '累积消费次数',
  `nick_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '昵称',
  `sex` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '性别（1：男，2：女，0：未知）',
  `headimg_url` varbinary(1000) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`,`phone`),
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='vip用户';

CREATE TABLE `base_mall`.`notice` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用ID',
  `type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型1、小程序首页轮播图；2、小程序首页公告',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '通知名',
  `remarks` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `enable` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '（1：开启；0：关闭）',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='商城通知';

/*Data for the table `notice` */

insert  into `base_mall`.`notice`(`id`,`tenant_id`,`del_flag`,`create_time`,`update_time`,`create_id`,`app_id`,`type`,`name`,`remarks`,`enable`) values 
('1','1','0','2019-11-09 20:08:12','2019-11-10 16:07:41','1','wxd5b98bbec200013b','1','小程序首页轮播图',NULL,'1'),
('2','1','0','2019-11-09 20:19:26','2019-11-10 16:14:16','1','wxd5b98bbec200013b','2','小程序首页公告',NULL,'1');

/*Table structure for table `notice_item` */


CREATE TABLE `base_mall`.`notice_item` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `notice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '通知ID',
  `type` char(2) NOT NULL COMMENT '类型1、图片；2、视频；3、文字',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '通知链接',
  `page` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '跳转页面',
  `content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '内容',
  `tag` varchar(10) DEFAULT NULL COMMENT '标签',
  `enable` char(2) DEFAULT NULL COMMENT '（1：开启；0：关闭）',
  `sort` int(11) DEFAULT NULL COMMENT '排序字段',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='商城通知详情';

/*Data for the table `notice_item` */

insert  into `base_mall`.`notice_item`(`id`,`tenant_id`,`del_flag`,`create_time`,`update_time`,`create_id`,`notice_id`,`type`,`name`,`url`,`page`,`content`,`tag`,`enable`,`sort`) values 
('1','1','0','2019-11-09 20:10:10','2019-11-10 18:38:18','1','1','1',NULL,'http://img14.360buyimg.com/cms/jfs/t1/41875/15/15234/177824/5d7e4bbdE9d92026d/a22352695fb54048.jpg','/pages/goods/goods-detail/index?id=2835671ff031c18cb181b1a199f86b01',NULL,NULL,'1',1),
('2','1','0','2019-11-09 20:10:36','2019-11-10 18:38:19','1','1','1',NULL,'http://img10.360buyimg.com/cms/jfs/t1/63177/15/10387/240991/5d7f9dceEeeb37fc9/836c313d04150d0f.jpg','/pages/goods/goods-list/index?categorySecond=862a74f109f7f14bcbfff1d5adf73cdc&title=%E5%8D%8E%E4%B8%BA',NULL,NULL,'1',2),
('3','1','0','2019-11-09 20:11:07','2019-11-10 18:38:19','1','1','1',NULL,'http://img12.360buyimg.com/cms/jfs/t1/52071/29/11410/442751/5d84357aE3604f88b/0da811f943ecd2d3.jpg','/pages/goods/goods-detail/index?id=58c12341a226b641435b9aa435a1133c',NULL,NULL,'1',3),
('4','1','0','2019-11-09 20:20:08','2019-11-10 18:38:24','1','2','3',NULL,'','/pages/goods/goods-detail/index?id=58c12341a226b641435b9aa435a1133c','小程序商城版正式上线，源码0加密码，立即购买','最新','1',1),
('5','1','0','2019-11-09 21:00:27','2019-11-10 18:38:25','1','2','3',NULL,'','','积分模块即将上线，价格将上调至￥1999，立即抢购','涨价通知','1',2);

ALTER TABLE `base_mall`.`order_info` ADD COLUMN `app_type` CHAR(2) NOT NULL COMMENT '应用类型1、小程序' AFTER `update_time`, ADD COLUMN `payment_way` CHAR(2) NOT NULL COMMENT '支付方式1、货到付款；2、在线支付' AFTER `logistics_price`, CHANGE `payment_type` `payment_type` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '支付类型1、微信支付；2、支付宝支付', ADD COLUMN `is_pay` CHAR(2) NOT NULL COMMENT '是否支付0、未支付 1、已支付' AFTER `payment_type`, CHANGE `status` `status` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '订单状态1、待发货 2、待收货 3、确认收货/已完成 5、已关闭 6、退款中' AFTER `is_pay`, ADD COLUMN `is_refund` CHAR(2) NULL COMMENT '是否退款1、是；0、否' AFTER `status`, ADD COLUMN `appraises_status` CHAR(2) NULL COMMENT '评价状态0、未评；1、已评；2、已追评' AFTER `is_refund`; 

UPDATE `order_info` SET `app_type` = '1' WHERE `app_type` = '';
UPDATE `order_info` SET `payment_way` = '2' WHERE `payment_way` = '';
UPDATE `order_info` SET `is_pay` = '1' WHERE `status` IN('1','2','3','4');
UPDATE `order_info` SET `is_pay` = '0' WHERE `status` IN('0','5');
UPDATE `order_info` SET `appraises_status` = '0' WHERE `status` IN('3');
UPDATE `order_info` SET `appraises_status` = '1' WHERE `status` IN('4');
UPDATE `order_info` SET `status` = '3' WHERE `status` IN('4');

UPDATE `goods_sku` SET `enable` = '1' WHERE `enable` = 'true';
UPDATE `goods_sku` SET `enable` = '0' WHERE `enable` = 'false';
ALTER TABLE `base_mall`.`goods_sku` CHANGE `enable` `enable` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '1' NOT NULL COMMENT '是否启用1、是；0否'; 

ALTER TABLE `base_mall`.`order_info` DROP COLUMN `is_refund`, CHANGE `status` `status` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '订单状态1、待发货 2、待收货 3、确认收货/已完成 5、已关闭'; 


CREATE TABLE `base_mall`.`order_refunds` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_item_id` varchar(32) DEFAULT NULL COMMENT '订单详情ID',
  `status` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '退款状态1、申请中 2、同意 3、拒绝',
  `refund_amount` decimal(10,2) NOT NULL COMMENT '退款金额',
  `refund_trade_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '退款流水号',
  `refund_reson` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '退款原因',
  `refuse_refund_reson` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '拒绝退款原因',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='订单退款记录表';
