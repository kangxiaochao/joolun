package com.joolun.cloud.common.core.constant;

/**
 * @author
 */
public interface CommonConstants {
	/**
	 * 是
	 */
	String YES = "1";
	/**
	 * 否
	 */
	String NO = "0";
	/**
	 * header 中租户ID
	 */
	String TENANT_ID = "tenant-id";
	/**
	 * header 中Authorization
	 */
	String AUTHORIZATION = "Authorization";
	/**
	 * 数据权限字段
	 */
	String SCOPENAME = "organ_id";
	/**
	 * upms数据库
	 */
	String UPMS_DATABASE = "base_upms";
	/**
	 * 删除
	 */
	String STATUS_DEL = "1";
	/**
	 * 正常
	 */
	String STATUS_NORMAL = "0";

	/**
	 * 锁定
	 */
	String STATUS_LOCK = "9";

	/**
	 * 菜单
	 */
	String MENU = "0";
	/**
	 * 按钮
	 */
	String BUTT = "1";

	/**
	 * 菜单根节点
	 */
	String MENU_TREE_ROOT_ID = "-1";

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * 前端工程名
	 */
	String FRONT_END_PROJECT = "joolun-ui";

	/**
	 * 后端工程名
	 */
	String BACK_END_PROJECT = "joolun";

	/**
	 * 邮箱
	 */
	String EMAIL = "email";

	/**
	 * 短信
	 */
	String SMS = "sms";

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 0;
	/**
	 * 失败标记
	 */
	Integer FAIL = 1;

	/**
	 * 默认存储bucket
	 */
	String BUCKET_NAME = "joolun";

	/**
	 * 微信存储bucket
	 */
	String BUCKET_NAME_WX = "joolun-wx";

	/**
	 * 邮箱发送类型
	 */
	String EMAIL_SEND_TYPE_REGISTER = "register";

	/**
	 * 树形父类ID
	 */
	String PARENT_ID = "0";

	/**
	 * 数据权限类型
	 * 0全部，1自定义，2本级及子级，3本级
	 */
	Integer DS_TYPE_0 = 0;
	Integer DS_TYPE_1 = 1;
	Integer DS_TYPE_2 = 2;
	Integer DS_TYPE_3 = 3;

	/**
	 * 管理员角色编码
	 */
	String ROLE_CODE_ADMIN = "ROLE_ADMIN";

	/**
	 * 机构类型1、公司；2、部门；3、小组；4、其它
	 */
	String ORGAN_TYPE_1 = "1";
	String ORGAN_TYPE_2 = "2";
	String ORGAN_TYPE_3 = "3";
	String ORGAN_TYPE_4 = "4";
}
