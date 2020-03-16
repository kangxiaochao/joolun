/*
 *    Copyright (c) 2018-2050, joolun All rights reserved
 */

package com.joolun.cloud.upms.admin;

import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.core.env.StandardEnvironment;

/**
 * @author JL
 * @date 2018/10/7
 * <p>
 * 加解密单元测试
 */
public class BaseUpmsApplicationTest {
	@Test
	public void testJasypt() {
		// 对应application-dev.yml 中配置的根密码
//		System.setProperty("jasypt.encryptor.password", "joolun");
//		StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
//
//		//加密方法
//		System.out.println(stringEncryptor.encrypt("test"));
//
//		//解密方法
//		System.out.println(stringEncryptor.decrypt("OBjpUV3zuZHIUzeGbpxLRw=="));
	}
}
