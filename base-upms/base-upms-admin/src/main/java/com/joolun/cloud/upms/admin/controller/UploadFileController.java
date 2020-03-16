package com.joolun.cloud.upms.admin.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.joolun.cloud.common.alioss.service.AliOssTemplate;
import com.joolun.cloud.common.core.util.FileUtils;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JL
 * @date 2019/07/14
 * 文件上传
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/file")
@Api(value = "file", tags = "文件上传")
public class UploadFileController {
	private final AliOssTemplate aliOssTemplate;

	/**
	 * 获取上传凭证（服务端签名后直传）
	 * @param dir
	 * @return
	 */
	@GetMapping("/signature")
	public R getSignature(String dir) {
		try {
			dir = StrUtil.format("{}/{}",TenantContextHolder.getTenantId(),  dir);
			return R.ok(aliOssTemplate.getSignature(dir));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return R.failed(e.getMessage());
		}
	}

	/**
	 * 上传文件
	 * @param mulFile
	 * @param dir
	 * @return
	 */
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile mulFile,
						@RequestParam("dir") String dir) throws IOException {
			File file = FileUtils.multipartFileToFile(mulFile);
			Map<Object, Object> responseData = new HashMap<>();
			dir = StrUtil.format("{}/{}",TenantContextHolder.getTenantId(),  dir);
			responseData.put("link", aliOssTemplate.uploadFile(file,dir));
			return JSONUtil.toJsonStr(responseData);
	}
}
