package com.joolun.cloud.common.alioss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.joolun.cloud.common.alioss.config.AliOssConfigProperties;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * ali OSS
 * 各操作调用
 */
@AllArgsConstructor
public class AliOssTemplate {
	private final String accessKeyId;// 请填写您的AccessKeyId。
	private final String accessKeySecret; // 请填写您的AccessKeySecret。
	private final String endpoint; // 请填写您的 endpoint。
	private final String bucket; // 请填写您的 bucketname 。
	private final String host; // host的格式为 bucketname.endpoint

	/**
	 * 获取上传凭证（服务端签名后直传）
	 *
	 * @param dir 用户上传文件时指定的文件夹。
	 * @return
	 */
	public Map<String, String> getSignature(String dir) throws UnsupportedEncodingException {
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		long expireTime = 30;
		long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
		Date expiration = new Date(expireEndTime);
		PolicyConditions policyConds = new PolicyConditions();
		policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
		policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

		String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
		byte[] binaryData = postPolicy.getBytes("utf-8");
		String encodedPolicy = BinaryUtil.toBase64String(binaryData);
		String postSignature = ossClient.calculatePostSignature(postPolicy);

		Map<String, String> respMap = new LinkedHashMap<>();
		respMap.put("accessKeyId", accessKeyId);
		respMap.put("policy", encodedPolicy);
		respMap.put("signature", postSignature);
		respMap.put("dir", dir);
		respMap.put("host", host);
		respMap.put("expire", String.valueOf(expireEndTime / 1000));

		return respMap;
	}

	/**
	 * 上传文件
	 * @param file
	 * @param dir 用户上传文件时指定的文件夹。
	 */
	public String uploadFile(File file,String dir) {
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		String key = dir + UUID.randomUUID()+ "." + suffix;
		// 创建PutObjectRequest对象。
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, file);
		// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
		// ObjectMetadata metadata = new ObjectMetadata();
		// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
		// metadata.setObjectAcl(CannedAccessControlList.Private);
		// putObjectRequest.setMetadata(metadata);

		// 上传文件。
		ossClient.putObject(putObjectRequest);

		// 关闭OSSClient。
		ossClient.shutdown();
		// 解析结果
		String resultStr = host + "/" + key;
		return resultStr;
	}
}
