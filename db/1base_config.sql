/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.15 : Database - base_config
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`base_config` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

USE `base_config`;

/*Table structure for table `config_info` */

DROP TABLE IF EXISTS `config_info`;

CREATE TABLE `config_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='config_info';

/*Data for the table `config_info` */

insert  into `config_info`(`id`,`data_id`,`group_id`,`content`,`md5`,`gmt_create`,`gmt_modified`,`src_user`,`src_ip`,`app_name`,`tenant_id`,`c_desc`,`c_use`,`effect`,`type`,`c_schema`) values 
(1,'dynamic_routes','DEFAULT_GROUP','routes:\r\n# base-auth\r\n- id: base-auth\r\n  predicates:\r\n  - name: Path\r\n    args: \r\n      _genkey_0: /auth/**\r\n  filters:\r\n  - name: ValidateCodeGatewayFilter\r\n    args: {}\r\n  - name: PasswordDecoderFilter\r\n    args: {}\r\n  uri: lb://base-auth\r\n  order: 0\r\n# base-upms-admin\r\n- id: base-upms-admin\r\n  predicates:\r\n  - name: Path\r\n    args: \r\n      _genkey_0: /admin/**\r\n  filters:\r\n  - name: RequestRateLimiter\r\n    args: \r\n      # 限流策略\r\n      key-resolver: \'#{@remoteAddrKeyResolver}\'\r\n      # 令牌桶每秒填充率\r\n      redis-rate-limiter.burstCapacity: 20\r\n      # 令牌桶容量\r\n      redis-rate-limiter.replenishRate: 10\r\n  # 熔断\r\n  - name: Hystrix\r\n    args: \r\n      fallbackUri: \'forward:/fallback\'\r\n      name: default\r\n  uri: lb://base-upms-admin\r\n  order: 0\r\n# base-codegen\r\n- id: base-codegen\r\n  predicates:\r\n  - name: Path\r\n    args: \r\n      _genkey_0: /gen/**\r\n  filters: []\r\n  uri: lb://base-codegen\r\n  order: 0\r\n# base-weixin-admin\r\n- id: base-weixin-admin\r\n  predicates:\r\n  - name: Path\r\n    args: \r\n      _genkey_0: /weixin/**\r\n  filters: []\r\n  uri: lb://base-weixin-admin\r\n  order: 0\r\n# base-mall-admin\r\n- id: base-mall-admin\r\n  predicates:\r\n  - name: Path\r\n    args: \r\n      _genkey_0: /mall/**\r\n  filters: []\r\n  uri: lb://base-mall-admin\r\n  order: 0\r\n# base-cms-admin\r\n- id: base-cms-admin\r\n  predicates:\r\n  - name: Path\r\n    args: \r\n      _genkey_0: /cms/**\r\n  filters: []\r\n  uri: lb://base-cms-admin\r\n  order: 0','1116afca7c41cb907827274a7472684c','2019-07-30 14:26:08','2019-10-17 21:01:28',NULL,'192.168.31.203','','','动态路由配置','null','null','yaml','null'),
(2,'application-dev.yml','DEFAULT_GROUP','# 加解密根密码\njasypt:\n  encryptor:\n    #根密码\n    password: joolun\n# redis 相关\nspring:\n  redis:\n    host: ${REDIS-HOST:base-redis}\n    port: 6379\n    password: \n    database: 0\n# logging日志\nlogging:\n  level:\n    com.alibaba.nacos.client.naming: error\n# 暴露监控端点\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n\n# feign 配置\nfeign:\n  hystrix:\n    enabled: true\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  client:\n    config:\n      default:\n        connectTimeout: 10000\n        readTimeout: 10000\n  compression:\n    request:\n      enabled: true\n    response:\n      enabled: true\n# hystrix If you need to use ThreadLocal bound variables in your RequestInterceptor`s\n# you will need to either set the thread isolation strategy for Hystrix to `SEMAPHORE or disable Hystrix in Feign.\nhystrix:\n  command:\n    default:\n      execution:\n        isolation:\n          strategy: SEMAPHORE\n          thread:\n            timeoutInMilliseconds: 60000\n  shareSecurityContext: true\n\n#请求处理的超时时间\nribbon:\n  ReadTimeout: 10000\n  ConnectTimeout: 10000\n\n# mybaits-plus配置\nmybatis-plus:\n  # MyBatis Mapper所对应的XML文件位置\n  mapper-locations: classpath:/mapper/*Mapper.xml\n  # 自定义TypeHandler\n  type-handlers-package: com.joolun.cloud.common.data.mybatis.typehandler\n  global-config:\n    sql-parser-cache: true\n    # 关闭MP3.0自带的banner\n    banner: false\n    db-config:\n      # 主键类型\n      id-type: auto\n\n#swagger公共信息\nswagger:\n  title: JooLun API\n  description: JooLun微信快速开发框架\n  license: Powered By joolun\n  licenseUrl: http://www.joolun.com/\n  terms-of-service-url: http://www.joolun.com/\n  authorization:\n    name: OAuth\n    auth-regex: ^.*$\n    authorization-scope-list:\n      - scope: server\n        description: server all\n    token-url-list:\n      - http://${GATEWAY-HOST:base-gateway}:${GATEWAY-PORT:9999}/auth/oauth/token\n\n## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      # 无需token访问的url,如果子模块重写这里的配置就会被覆盖\n      release-urls:\n        - /actuator/**\n        - /v2/api-docs\n    resource:\n      loadBalanced: true\n      token-info-uri: http://${AUTH-HOST:base-auth}/oauth/check_token\n##阿里云配置\naliyun:\n  oss:\n    region: oss-cn-zhangjiakou\n    endpoint: oss-cn-zhangjiakou.aliyuncs.com\n    access-key-id: 你的access-key-id\n    access-key-secret: 你的access-key-secret\n    bucket: joolun-base-test\n##文件存放目录配置\nhome-dir:\n  windows: D:/joolun-file/\n  linux: /mnt/install/joolun-file/','314182637e03cf0b1ec42abefca15e7c','2019-07-28 23:14:26','2019-11-19 17:25:53',NULL,'192.168.1.13','','','主配置文件','null','null','yaml','null'),
(3,'base-gateway-dev.yml','DEFAULT_GROUP','security:\n  encode:\n    # 前端密码密钥，必须16位\n    key: \'1234567891234567\'\n\n# 不校验验证码终端\nignore:\n  clients:\n    - swagger\n  swagger-providers:\n    - ${AUTH-HOST:base-auth}','951580823377e19ddbae79a4276ff678','2019-07-28 23:14:26','2019-07-30 14:29:24',NULL,'192.168.1.117','','','网关配置','null','null','yaml','null'),
(4,'base-auth-dev.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_upms}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n  freemarker:\n    allow-request-override: false\n    allow-session-override: false\n    cache: true\n    charset: UTF-8\n    check-template-location: true\n    content-type: text/html\n    enabled: true\n    expose-request-attributes: false\n    expose-session-attributes: false\n    expose-spring-macro-helpers: true\n    prefer-file-system-access: true\n    suffix: .ftl\n    template-loader-path: classpath:/templates/','3476b23886681325f3b3ca7ac6a800fc','2019-07-28 23:14:26','2019-11-21 14:54:23',NULL,'192.168.1.13','','','认证授权配置','null','null','yaml','null'),
(5,'base-upms-admin-dev.yml','DEFAULT_GROUP','## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: admin\n      client-secret: admin\n      scope: server\n      # 无需token访问的url\n      release-urls:\n        - /actuator/**\n        - /v2/api-docs\n        - /user/register\n\n# 数据源\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_upms}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n\n# Logger Config sql日志\nlogging:\n  level:\n    com.joolun.cloud.upms.admin.mapper: debug\n\nbase:\n  # 租户表维护\n  tenant:\n    column: tenant_id\n    tables:\n      - sys_user\n      - sys_role\n      - sys_organ\n      - sys_log\n  #数据权限配置\n  datascope:\n    mapperIds:\n      - com.joolun.cloud.upms.admin.mapper.SysUserMapper.getUserVosPage\n        \n#邮箱配置\nemail:\n  mailSmtpHost: smtpdm.aliyun.com\n  mailSmtpUsername: 你的邮箱\n  mailSmtpPassword: 邮箱密码\n  siteName: JooLun科技','eaa421b2558adcf1ca51fb9b0087e46e','2019-07-28 23:14:26','2019-11-21 14:54:34',NULL,'192.168.1.13','','','用户权限管理配置','null','null','yaml','null'),
(6,'base-codegen-dev.yml','DEFAULT_GROUP','## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: gen\n      client-secret: gen\n      scope: server\n# 数据源配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_upms}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n  resources:\n    static-locations: classpath:/static/,classpath:/views/\n\n\nbase:\n  tenant:\n    column: tenant_id\n    tables:\n      - sys_datasource','a16d6b5a6f63d37b5febaedc525156a8','2019-07-28 23:14:26','2019-11-21 14:54:45',NULL,'192.168.1.13','','','代码生成配置','null','null','yaml','null'),
(7,'base-weixin-admin-dev.yml','DEFAULT_GROUP','## spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: weixin\r\n      client-secret: weixin\r\n      scope: server\r\n      # 无需token访问的url\r\n      release-urls:\r\n        - /actuator/**\r\n        - /v2/api-docs\r\n        - /portal/**\r\n        - /ws/**\r\n        - /open/notify/**\r\n        - /open/auth/**\r\n        - /api/**\r\n# 数据源配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    druid:\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      username: ${MYSQL-USER:root}\r\n      password: ${MYSQL-PWD:root}\r\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_wx}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n  resources:\r\n    static-locations: classpath:/static/,classpath:/views/\r\n\r\n# Logger Config sql日志\r\nlogging:\r\n  level:\r\n    com.joolun.cloud.weixin.admin.mapper: debug\r\n    \r\n# 租户表维护\r\nbase:\r\n  tenant:\r\n    column: tenant_id\r\n    tables:\r\n      - wx_app\r\n      - wx_menu\r\n      - wx_user\r\n      - wx_auto_reply\r\n      - wx_msg\r\n      - wx_mass_msg\r\n  #数据权限配置\r\n  datascope:\r\n    mapperIds:\r\n      - com.joolun.cloud.weixin.mp.mapper.WxAppMapper.selectPage\r\n\r\n# 微信第三方平台配置，请自行申请\r\nwx:\r\n  component:\r\n    appId: 你的appId\r\n    appSecret: 你的appSecret\r\n    token: 你的token\r\n    aesKey: 你的aesKey','2be50707afb22fca956154eaf0006347','2019-07-28 23:14:26','2019-11-21 14:54:56',NULL,'192.168.1.13','','','微信公众号配置','null','null','yaml','null'),
(8,'base-mall-admin-dev.yml','DEFAULT_GROUP','## spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: weixin\r\n      client-secret: weixin\r\n      scope: server\r\n      # 无需token访问的url\r\n      release-urls:\r\n        - /actuator/**\r\n        - /v2/api-docs\r\n        - /api/**\r\n# 数据源配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    druid:\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      username: ${MYSQL-USER:root}\r\n      password: ${MYSQL-PWD:root}\r\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_mall}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n  resources:\r\n    static-locations: classpath:/static/,classpath:/views/\r\n\r\n# Logger Config sql日志\r\nlogging:\r\n  level:\r\n    com.joolun.cloud.mall.admin.mapper: debug\r\n    \r\n# 租户表维护\r\nbase:\r\n  tenant:\r\n    column: tenant_id\r\n    tables:\r\n      - goods_category\r\n      - goods_spu\r\n      - goods_spu_spec\r\n      - goods_sku\r\n      - goods_sku_spec_value\r\n      - goods_spec\r\n      - goods_spec_value\r\n      - goods_appraises\r\n      - shopping_cart\r\n      - order_info\r\n      - order_item\r\n      - order_logistics\r\n      - order_logistics_detail\r\n      - user_address\r\n      - user_collect\r\n      - material\r\n      - material_group\r\n      - notice\r\n      - notice_item\r\n      - order_refunds\r\n  #商城相关配置\r\n  mall:\r\n    #支付、物流回调地址域名，注：快递100不支持https回调\r\n    notify-host: http://test.joolun.com\r\n    #快递100授权key\r\n    logistics-key: 你的logistics-key','916ce961a7cd25761647bb1806e6a76a','2019-08-12 12:03:16','2019-11-21 14:55:06',NULL,'192.168.1.13','','','商城管理配置','null','null','yaml','null'),
(142,'base-cms-admin-dev.yml','DEFAULT_GROUP','## spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: weixin\r\n      client-secret: weixin\r\n      scope: server\r\n      # 无需token访问的url\r\n      release-urls:\r\n        - /actuator/**\r\n        - /v2/api-docs\r\n        - /api/**\r\n# 数据源配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    druid:\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      username: ${MYSQL-USER:root}\r\n      password: ${MYSQL-PWD:root}\r\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_cms}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n  resources:\r\n    static-locations: classpath:/static/,classpath:/views/\r\n\r\n# Logger Config sql日志\r\nlogging:\r\n  level:\r\n    com.joolun.cloud.cms.admin.mapper: debug\r\n    \r\n# 租户表维护\r\nbase:\r\n  tenant:\r\n    column: tenant_id\r\n    tables:\r\n      - cms_channel\r\n      - cms_post\r\n      - cms_post_info','2897008ab3859f5ab9391d5b70540e6a','2019-10-17 21:03:46','2019-11-21 14:55:14',NULL,'192.168.1.13','','','null','null','null','yaml','null');

/*Table structure for table `config_info_aggr` */

DROP TABLE IF EXISTS `config_info_aggr`;

CREATE TABLE `config_info_aggr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='增加租户字段';

/*Data for the table `config_info_aggr` */

/*Table structure for table `config_info_beta` */

DROP TABLE IF EXISTS `config_info_beta`;

CREATE TABLE `config_info_beta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='config_info_beta';

/*Data for the table `config_info_beta` */

/*Table structure for table `config_info_tag` */

DROP TABLE IF EXISTS `config_info_tag`;

CREATE TABLE `config_info_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='config_info_tag';

/*Data for the table `config_info_tag` */

/*Table structure for table `config_tags_relation` */

DROP TABLE IF EXISTS `config_tags_relation`;

CREATE TABLE `config_tags_relation` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='config_tag_relation';

/*Data for the table `config_tags_relation` */

/*Table structure for table `group_capacity` */

DROP TABLE IF EXISTS `group_capacity`;

CREATE TABLE `group_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_group_id` (`group_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='集群、各Group容量信息表';

/*Data for the table `group_capacity` */

/*Table structure for table `his_config_info` */

DROP TABLE IF EXISTS `his_config_info`;

CREATE TABLE `his_config_info` (
  `id` bigint(64) unsigned NOT NULL,
  `nid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin,
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  KEY `idx_gmt_create` (`gmt_create`) USING BTREE,
  KEY `idx_gmt_modified` (`gmt_modified`) USING BTREE,
  KEY `idx_did` (`data_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='多租户改造';

/*Data for the table `his_config_info` */

insert  into `his_config_info`(`id`,`nid`,`data_id`,`group_id`,`app_name`,`content`,`md5`,`gmt_create`,`gmt_modified`,`src_user`,`src_ip`,`op_type`,`tenant_id`) values 
(8,147,'base-mall-admin-dev.yml','DEFAULT_GROUP','','## spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: weixin\r\n      client-secret: weixin\r\n      scope: server\r\n      # 无需token访问的url\r\n      release-urls:\r\n        - /actuator/**\r\n        - /v2/api-docs\r\n        - /api/**\r\n# 数据源配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    druid:\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      username: ${MYSQL-USER:root}\r\n      password: ${MYSQL-PWD:root}\r\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_mall}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\r\n  resources:\r\n    static-locations: classpath:/static/,classpath:/views/\r\n\r\n# Logger Config sql日志\r\nlogging:\r\n  level:\r\n    com.joolun.cloud.mall.admin.mapper: debug\r\n    \r\n# 租户表维护\r\nbase:\r\n  tenant:\r\n    column: tenant_id\r\n    tables:\r\n      - goods_category\r\n      - goods_spu\r\n      - goods_spu_spec\r\n      - goods_sku\r\n      - goods_sku_spec_value\r\n      - goods_spec\r\n      - goods_spec_value\r\n      - goods_appraises\r\n      - shopping_cart\r\n      - order_info\r\n      - order_item\r\n      - order_logistics\r\n      - order_logistics_detail\r\n      - user_address\r\n      - user_collect\r\n      - material\r\n      - material_group\r\n  #商城相关配置\r\n  mall:\r\n    #支付、物流回调地址域名，注：快递100不支持https回调\r\n    notify-host: http://test.joolun.com\r\n    #快递100授权key\r\n    logistics-key: NXQCqRoe7950','2f798734d868f7c29cc552f2f20c41b8','2010-05-05 00:00:00','2019-11-09 19:44:20',NULL,'192.168.31.203','U',''),
(8,148,'base-mall-admin-dev.yml','DEFAULT_GROUP','','## spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: weixin\r\n      client-secret: weixin\r\n      scope: server\r\n      # 无需token访问的url\r\n      release-urls:\r\n        - /actuator/**\r\n        - /v2/api-docs\r\n        - /api/**\r\n# 数据源配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    druid:\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      username: ${MYSQL-USER:root}\r\n      password: ${MYSQL-PWD:root}\r\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_mall}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\r\n  resources:\r\n    static-locations: classpath:/static/,classpath:/views/\r\n\r\n# Logger Config sql日志\r\nlogging:\r\n  level:\r\n    com.joolun.cloud.mall.admin.mapper: debug\r\n    \r\n# 租户表维护\r\nbase:\r\n  tenant:\r\n    column: tenant_id\r\n    tables:\r\n      - goods_category\r\n      - goods_spu\r\n      - goods_spu_spec\r\n      - goods_sku\r\n      - goods_sku_spec_value\r\n      - goods_spec\r\n      - goods_spec_value\r\n      - goods_appraises\r\n      - shopping_cart\r\n      - order_info\r\n      - order_item\r\n      - order_logistics\r\n      - order_logistics_detail\r\n      - user_address\r\n      - user_collect\r\n      - material\r\n      - material_group\r\n      - notice\r\n      - notice_item\r\n  #商城相关配置\r\n  mall:\r\n    #支付、物流回调地址域名，注：快递100不支持https回调\r\n    notify-host: http://test.joolun.com\r\n    #快递100授权key\r\n    logistics-key: NXQCqRoe7950','b274b7cef6640813d70e8b5a804e8f53','2010-05-05 00:00:00','2019-11-14 16:37:07',NULL,'192.168.1.106','U',''),
(8,149,'base-mall-admin-dev.yml','DEFAULT_GROUP','','## spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: weixin\r\n      client-secret: weixin\r\n      scope: server\r\n      # 无需token访问的url\r\n      release-urls:\r\n        - /actuator/**\r\n        - /v2/api-docs\r\n        - /api/**\r\n# 数据源配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    druid:\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      username: ${MYSQL-USER:root}\r\n      password: ${MYSQL-PWD:root}\r\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_mall}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\r\n  resources:\r\n    static-locations: classpath:/static/,classpath:/views/\r\n\r\n# Logger Config sql日志\r\nlogging:\r\n  level:\r\n    com.joolun.cloud.mall.admin.mapper: debug\r\n    \r\n# 租户表维护\r\nbase:\r\n  tenant:\r\n    column: tenant_id\r\n    tables:\r\n      - goods_category\r\n      - goods_spu\r\n      - goods_spu_spec\r\n      - goods_sku\r\n      - goods_sku_spec_value\r\n      - goods_spec\r\n      - goods_spec_value\r\n      - goods_appraises\r\n      - shopping_cart\r\n      - order_info\r\n      - order_item\r\n      - order_logistics\r\n      - order_logistics_detail\r\n      - user_address\r\n      - user_collect\r\n      - material\r\n      - material_group\r\n      - notice\r\n      - notice_item\r\n      - order_refunds\r\n  #商城相关配置\r\n  mall:\r\n    #支付、物流回调地址域名，注：快递100不支持https回调\r\n    notify-host: http://test.joolun.com\r\n    #快递100授权key\r\n    logistics-key: NXQCqRoe7950','a283d550abaa9262e058d002fcb046dd','2010-05-05 00:00:00','2019-11-17 16:25:03',NULL,'192.168.1.13','U',''),
(2,150,'application-dev.yml','DEFAULT_GROUP','','# 加解密根密码\njasypt:\n  encryptor:\n    #根密码\n    password: joolun\n# redis 相关\nspring:\n  redis:\n    host: ${REDIS-HOST:base-redis}\n    port: 6379\n    password: \n    database: 0\n# logging日志\nlogging:\n  level:\n    com.alibaba.nacos.client.naming: error\n# 暴露监控端点\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n\n# feign 配置\nfeign:\n  hystrix:\n    enabled: true\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  client:\n    config:\n      default:\n        connectTimeout: 10000\n        readTimeout: 10000\n  compression:\n    request:\n      enabled: true\n    response:\n      enabled: true\n# hystrix If you need to use ThreadLocal bound variables in your RequestInterceptor`s\n# you will need to either set the thread isolation strategy for Hystrix to `SEMAPHORE or disable Hystrix in Feign.\nhystrix:\n  command:\n    default:\n      execution:\n        isolation:\n          strategy: SEMAPHORE\n          thread:\n            timeoutInMilliseconds: 60000\n  shareSecurityContext: true\n\n#请求处理的超时时间\nribbon:\n  ReadTimeout: 10000\n  ConnectTimeout: 10000\n\n# mybaits-plus配置\nmybatis-plus:\n  # MyBatis Mapper所对应的XML文件位置\n  mapper-locations: classpath:/mapper/*Mapper.xml\n  # 自定义TypeHandler\n  type-handlers-package: com.joolun.cloud.common.data.mybatis.typehandler\n  global-config:\n    sql-parser-cache: true\n    # 关闭MP3.0自带的banner\n    banner: false\n    db-config:\n      # 主键类型\n      id-type: auto\n\n#swagger公共信息\nswagger:\n  title: JooLun API\n  description: JooLun微信快速开发框架\n  license: Powered By joolun\n  licenseUrl: http://www.joolun.com/\n  terms-of-service-url: http://www.joolun.com/\n  authorization:\n    name: OAuth\n    auth-regex: ^.*$\n    authorization-scope-list:\n      - scope: server\n        description: server all\n    token-url-list:\n      - http://${GATEWAY-HOST:base-gateway}:${GATEWAY-PORT:9999}/auth/oauth/token\n\n## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      # 无需token访问的url,如果子模块重写这里的配置就会被覆盖\n      release-urls:\n        - /actuator/**\n        - /v2/api-docs\n    resource:\n      loadBalanced: true\n      token-info-uri: http://${AUTH-HOST:base-auth}/oauth/check_token\n##阿里云配置\naliyun:\n  oss:\n    region: oss-cn-zhangjiakou\n    endpoint: oss-cn-zhangjiakou.aliyuncs.com\n    access-key-id: LTAI2ppRUSjC9q5F\n    access-key-secret: jKqtsTxbRInIyD3BALPsjX6z0UzZ33\n    bucket: joolun-base-test','d401a8a0c1edf7f13e8a6d4d0fc2fa06','2010-05-05 00:00:00','2019-11-19 17:21:51',NULL,'192.168.1.13','U',''),
(2,151,'application-dev.yml','DEFAULT_GROUP','','# 加解密根密码\njasypt:\n  encryptor:\n    #根密码\n    password: joolun\n# redis 相关\nspring:\n  redis:\n    host: ${REDIS-HOST:base-redis}\n    port: 6379\n    password: \n    database: 0\n# logging日志\nlogging:\n  level:\n    com.alibaba.nacos.client.naming: error\n# 暴露监控端点\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n\n# feign 配置\nfeign:\n  hystrix:\n    enabled: true\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  client:\n    config:\n      default:\n        connectTimeout: 10000\n        readTimeout: 10000\n  compression:\n    request:\n      enabled: true\n    response:\n      enabled: true\n# hystrix If you need to use ThreadLocal bound variables in your RequestInterceptor`s\n# you will need to either set the thread isolation strategy for Hystrix to `SEMAPHORE or disable Hystrix in Feign.\nhystrix:\n  command:\n    default:\n      execution:\n        isolation:\n          strategy: SEMAPHORE\n          thread:\n            timeoutInMilliseconds: 60000\n  shareSecurityContext: true\n\n#请求处理的超时时间\nribbon:\n  ReadTimeout: 10000\n  ConnectTimeout: 10000\n\n# mybaits-plus配置\nmybatis-plus:\n  # MyBatis Mapper所对应的XML文件位置\n  mapper-locations: classpath:/mapper/*Mapper.xml\n  # 自定义TypeHandler\n  type-handlers-package: com.joolun.cloud.common.data.mybatis.typehandler\n  global-config:\n    sql-parser-cache: true\n    # 关闭MP3.0自带的banner\n    banner: false\n    db-config:\n      # 主键类型\n      id-type: auto\n\n#swagger公共信息\nswagger:\n  title: JooLun API\n  description: JooLun微信快速开发框架\n  license: Powered By joolun\n  licenseUrl: http://www.joolun.com/\n  terms-of-service-url: http://www.joolun.com/\n  authorization:\n    name: OAuth\n    auth-regex: ^.*$\n    authorization-scope-list:\n      - scope: server\n        description: server all\n    token-url-list:\n      - http://${GATEWAY-HOST:base-gateway}:${GATEWAY-PORT:9999}/auth/oauth/token\n\n## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      # 无需token访问的url,如果子模块重写这里的配置就会被覆盖\n      release-urls:\n        - /actuator/**\n        - /v2/api-docs\n    resource:\n      loadBalanced: true\n      token-info-uri: http://${AUTH-HOST:base-auth}/oauth/check_token\n##阿里云配置\naliyun:\n  oss:\n    region: oss-cn-zhangjiakou\n    endpoint: oss-cn-zhangjiakou.aliyuncs.com\n    access-key-id: LTAI2ppRUSjC9q5F\n    access-key-secret: jKqtsTxbRInIyD3BALPsjX6z0UzZ33\n    bucket: joolun-base-test\n##文件存放目录配置\njoolun-file:\n  home-dir:\n    windows: D:/joolun-file/\n    linux: /mnt/install/joolun-file/','1458b5d7f414ef60ed614d6ae5a0387f','2010-05-05 00:00:00','2019-11-19 17:22:39',NULL,'192.168.1.13','U',''),
(2,152,'application-dev.yml','DEFAULT_GROUP','','# 加解密根密码\njasypt:\n  encryptor:\n    #根密码\n    password: joolun\n# redis 相关\nspring:\n  redis:\n    host: ${REDIS-HOST:base-redis}\n    port: 6379\n    password: \n    database: 0\n# logging日志\nlogging:\n  level:\n    com.alibaba.nacos.client.naming: error\n# 暴露监控端点\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n\n# feign 配置\nfeign:\n  hystrix:\n    enabled: true\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  client:\n    config:\n      default:\n        connectTimeout: 10000\n        readTimeout: 10000\n  compression:\n    request:\n      enabled: true\n    response:\n      enabled: true\n# hystrix If you need to use ThreadLocal bound variables in your RequestInterceptor`s\n# you will need to either set the thread isolation strategy for Hystrix to `SEMAPHORE or disable Hystrix in Feign.\nhystrix:\n  command:\n    default:\n      execution:\n        isolation:\n          strategy: SEMAPHORE\n          thread:\n            timeoutInMilliseconds: 60000\n  shareSecurityContext: true\n\n#请求处理的超时时间\nribbon:\n  ReadTimeout: 10000\n  ConnectTimeout: 10000\n\n# mybaits-plus配置\nmybatis-plus:\n  # MyBatis Mapper所对应的XML文件位置\n  mapper-locations: classpath:/mapper/*Mapper.xml\n  # 自定义TypeHandler\n  type-handlers-package: com.joolun.cloud.common.data.mybatis.typehandler\n  global-config:\n    sql-parser-cache: true\n    # 关闭MP3.0自带的banner\n    banner: false\n    db-config:\n      # 主键类型\n      id-type: auto\n\n#swagger公共信息\nswagger:\n  title: JooLun API\n  description: JooLun微信快速开发框架\n  license: Powered By joolun\n  licenseUrl: http://www.joolun.com/\n  terms-of-service-url: http://www.joolun.com/\n  authorization:\n    name: OAuth\n    auth-regex: ^.*$\n    authorization-scope-list:\n      - scope: server\n        description: server all\n    token-url-list:\n      - http://${GATEWAY-HOST:base-gateway}:${GATEWAY-PORT:9999}/auth/oauth/token\n\n## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      # 无需token访问的url,如果子模块重写这里的配置就会被覆盖\n      release-urls:\n        - /actuator/**\n        - /v2/api-docs\n    resource:\n      loadBalanced: true\n      token-info-uri: http://${AUTH-HOST:base-auth}/oauth/check_token\n##阿里云配置\naliyun:\n  oss:\n    region: oss-cn-zhangjiakou\n    endpoint: oss-cn-zhangjiakou.aliyuncs.com\n    access-key-id: LTAI2ppRUSjC9q5F\n    access-key-secret: jKqtsTxbRInIyD3BALPsjX6z0UzZ33\n    bucket: joolun-base-test\n##文件存放目录配置\njoolun-file:\n  home-dir:\n    windows: D:/joolun-file/\n    linux: /mnt/install/joolun-file/','1458b5d7f414ef60ed614d6ae5a0387f','2010-05-05 00:00:00','2019-11-19 17:25:53',NULL,'192.168.1.13','U',''),
(4,153,'base-auth-dev.yml','DEFAULT_GROUP','','# 数据源\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_upms}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\n  freemarker:\n    allow-request-override: false\n    allow-session-override: false\n    cache: true\n    charset: UTF-8\n    check-template-location: true\n    content-type: text/html\n    enabled: true\n    expose-request-attributes: false\n    expose-session-attributes: false\n    expose-spring-macro-helpers: true\n    prefer-file-system-access: true\n    suffix: .ftl\n    template-loader-path: classpath:/templates/','f8f481e11e51d1afc0cbc86d78c1a78a','2010-05-05 00:00:00','2019-11-21 14:54:23',NULL,'192.168.1.13','U',''),
(5,154,'base-upms-admin-dev.yml','DEFAULT_GROUP','','## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: admin\n      client-secret: admin\n      scope: server\n      # 无需token访问的url\n      release-urls:\n        - /actuator/**\n        - /v2/api-docs\n        - /user/register\n\n# 数据源\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_upms}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\n\n# Logger Config sql日志\nlogging:\n  level:\n    com.joolun.cloud.upms.admin.mapper: debug\n\nbase:\n  # 租户表维护\n  tenant:\n    column: tenant_id\n    tables:\n      - sys_user\n      - sys_role\n      - sys_organ\n      - sys_log\n  #数据权限配置\n  datascope:\n    mapperIds:\n      - com.joolun.cloud.upms.admin.mapper.SysUserMapper.getUserVosPage\n        \n#邮箱配置\nemail:\n  mailSmtpHost: smtpdm.aliyun.com\n  mailSmtpUsername: 你的邮箱\n  mailSmtpPassword: 邮箱密码\n  siteName: JooLun科技','8b490e968f4e65ab797fc4b7e5838900','2010-05-05 00:00:00','2019-11-21 14:54:34',NULL,'192.168.1.13','U',''),
(6,155,'base-codegen-dev.yml','DEFAULT_GROUP','','## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: gen\n      client-secret: gen\n      scope: server\n# 数据源配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: ${MYSQL-USER:root}\n      password: ${MYSQL-PWD:root}\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_upms}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\n  resources:\n    static-locations: classpath:/static/,classpath:/views/\n\n\nbase:\n  tenant:\n    column: tenant_id\n    tables:\n      - sys_datasource','b86ba529d8e534057301a8c8bd3566ea','2010-05-05 00:00:00','2019-11-21 14:54:45',NULL,'192.168.1.13','U',''),
(7,156,'base-weixin-admin-dev.yml','DEFAULT_GROUP','','## spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: weixin\r\n      client-secret: weixin\r\n      scope: server\r\n      # 无需token访问的url\r\n      release-urls:\r\n        - /actuator/**\r\n        - /v2/api-docs\r\n        - /portal/**\r\n        - /ws/**\r\n        - /open/notify/**\r\n        - /open/auth/**\r\n        - /api/**\r\n# 数据源配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    druid:\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      username: ${MYSQL-USER:root}\r\n      password: ${MYSQL-PWD:root}\r\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_wx}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\r\n  resources:\r\n    static-locations: classpath:/static/,classpath:/views/\r\n\r\n# Logger Config sql日志\r\nlogging:\r\n  level:\r\n    com.joolun.cloud.weixin.admin.mapper: debug\r\n    \r\n# 租户表维护\r\nbase:\r\n  tenant:\r\n    column: tenant_id\r\n    tables:\r\n      - wx_app\r\n      - wx_menu\r\n      - wx_user\r\n      - wx_auto_reply\r\n      - wx_msg\r\n      - wx_mass_msg\r\n  #数据权限配置\r\n  datascope:\r\n    mapperIds:\r\n      - com.joolun.cloud.weixin.mp.mapper.WxAppMapper.selectPage\r\n\r\n# 微信第三方平台配置，请自行申请\r\nwx:\r\n  component:\r\n    appId: wxedb42aa502a8b83d\r\n    appSecret: a76dc60602a6783058bf1661433ebb28\r\n    token: gh_26fd6cdbd78b\r\n    aesKey: DYnwvIfD47b7QkIKrO6a3OKgI172VrRaXLil48JEjr0','25710e4190c73db8ecbce11fc932a340','2010-05-05 00:00:00','2019-11-21 14:54:56',NULL,'192.168.1.13','U',''),
(8,157,'base-mall-admin-dev.yml','DEFAULT_GROUP','','## spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: weixin\r\n      client-secret: weixin\r\n      scope: server\r\n      # 无需token访问的url\r\n      release-urls:\r\n        - /actuator/**\r\n        - /v2/api-docs\r\n        - /api/**\r\n# 数据源配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    druid:\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      username: ${MYSQL-USER:root}\r\n      password: ${MYSQL-PWD:root}\r\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_mall}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\r\n  resources:\r\n    static-locations: classpath:/static/,classpath:/views/\r\n\r\n# Logger Config sql日志\r\nlogging:\r\n  level:\r\n    com.joolun.cloud.mall.admin.mapper: debug\r\n    \r\n# 租户表维护\r\nbase:\r\n  tenant:\r\n    column: tenant_id\r\n    tables:\r\n      - goods_category\r\n      - goods_spu\r\n      - goods_spu_spec\r\n      - goods_sku\r\n      - goods_sku_spec_value\r\n      - goods_spec\r\n      - goods_spec_value\r\n      - goods_appraises\r\n      - shopping_cart\r\n      - order_info\r\n      - order_item\r\n      - order_logistics\r\n      - order_logistics_detail\r\n      - user_address\r\n      - user_collect\r\n      - material\r\n      - material_group\r\n      - notice\r\n      - notice_item\r\n      - order_refunds\r\n  #商城相关配置\r\n  mall:\r\n    #支付、物流回调地址域名，注：快递100不支持https回调\r\n    notify-host: http://test.joolun.com\r\n    #快递100授权key\r\n    logistics-key: NXQCqRoe7950','a283d550abaa9262e058d002fcb046dd','2010-05-05 00:00:00','2019-11-21 14:55:06',NULL,'192.168.1.13','U',''),
(142,158,'base-cms-admin-dev.yml','DEFAULT_GROUP','','## spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: weixin\r\n      client-secret: weixin\r\n      scope: server\r\n      # 无需token访问的url\r\n      release-urls:\r\n        - /actuator/**\r\n        - /v2/api-docs\r\n        - /api/**\r\n# 数据源配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    druid:\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      username: ${MYSQL-USER:root}\r\n      password: ${MYSQL-PWD:root}\r\n      url: jdbc:mysql://${MYSQL-HOST:base-mysql}:${MYSQL-PORT:3306}/${MYSQL-DB:base_cms}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true\r\n  resources:\r\n    static-locations: classpath:/static/,classpath:/views/\r\n\r\n# Logger Config sql日志\r\nlogging:\r\n  level:\r\n    com.joolun.cloud.cms.admin.mapper: debug\r\n    \r\n# 租户表维护\r\nbase:\r\n  tenant:\r\n    column: tenant_id\r\n    tables:\r\n      - cms_channel\r\n      - cms_post\r\n      - cms_post_info','2f9371777c14a21c1e8a3be472b704bd','2010-05-05 00:00:00','2019-11-21 14:55:14',NULL,'192.168.1.13','U','');

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `roles` */

insert  into `roles`(`username`,`role`) values 
('nacos','ROLE_ADMIN');

/*Table structure for table `tenant_capacity` */

DROP TABLE IF EXISTS `tenant_capacity`;

CREATE TABLE `tenant_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='租户容量信息表';

/*Data for the table `tenant_capacity` */

/*Table structure for table `tenant_info` */

DROP TABLE IF EXISTS `tenant_info`;

CREATE TABLE `tenant_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='tenant_info';

/*Data for the table `tenant_info` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `users` */

insert  into `users`(`username`,`password`,`enabled`) values 
('nacos','$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
