/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.weixin.common.constant.WxReturnCode;
import com.joolun.cloud.weixin.common.entity.ImageManager;
import com.joolun.cloud.weixin.admin.config.mp.WxMpConfiguration;
import com.joolun.cloud.common.core.util.FileUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.bean.material.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信素材
 *
 * @author JL
 * @date 2019-03-23 21:26:35
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxmaterial")
public class WxMaterialController {

	/**
	 * 上传非图文微信素材
	 * @param mulFile
	 * @param appId
	 * @param mediaType
	 * @return
	 */
	@PostMapping("/materialFileUpload")
	//	@PreAuthorize("@ato.hasAuthority('wxmp_wxmaterial_add')")
	public R materialFileUpload(@RequestParam("file") MultipartFile mulFile,
								@RequestParam("title") String title,
								@RequestParam("introduction") String introduction,
								@RequestParam("appId") String appId,
								@RequestParam("mediaType") String mediaType) {
		try {
			WxMpMaterial material = new WxMpMaterial();
			material.setName(mulFile.getOriginalFilename());
			if(WxConsts.MediaFileType.VIDEO.equals(mediaType)){
				material.setVideoTitle(title);
				material.setVideoIntroduction(introduction);
			}
			File file = FileUtils.multipartFileToFile(mulFile);
			material.setFile(file);
			WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
			WxMpMaterialUploadResult wxMpMaterialUploadResult = wxMpMaterialService.materialFileUpload(mediaType,material);
			WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem wxMpMaterialFileBatchGetResult = new WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem();
			wxMpMaterialFileBatchGetResult.setName(file.getName());
			wxMpMaterialFileBatchGetResult.setMediaId(wxMpMaterialUploadResult.getMediaId());
			wxMpMaterialFileBatchGetResult.setUrl(wxMpMaterialUploadResult.getUrl());
			return R.ok(wxMpMaterialFileBatchGetResult);
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("上传非图文微信素材失败" + e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("上传失败", e);
			return R.failed(e.getLocalizedMessage());
		}
	}

	/**
	 * 新增图文消息
	 * @param data
	 * @return
	 */
	@PostMapping("/materialNews")
	@PreAuthorize("@ato.hasAuthority('wxmp_wxmaterial_add')")
	public R materialNewsUpload(@RequestBody JSONObject data) {
		try {
			String appId = data.getStr("appId");
			JSONArray jSONArray = data.getJSONArray("articles");
			List<WxMpMaterialNews.WxMpMaterialNewsArticle> articles = jSONArray.toList(WxMpMaterialNews.WxMpMaterialNewsArticle.class);
			WxMpMaterialNews news = new WxMpMaterialNews();
			news.setArticles(articles);
			WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
			WxMpMaterialUploadResult wxMpMaterialUploadResult = wxMpMaterialService.materialNewsUpload(news);
			return R.ok(wxMpMaterialUploadResult);
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("新增图文失败" + e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("新增图文失败", e);
			return R.failed(e.getLocalizedMessage());
		}
	}

	/**
	 *修改图文消息
	 * @param data
	 * @return
	 */
	@PutMapping("/materialNews")
	@PreAuthorize("@ato.hasAuthority('wxmp_wxmaterial_edit')")
	public R materialNewsUpdate(@RequestBody JSONObject data) {
		try {
			String appId = data.getStr("appId");
			String mediaId = data.getStr("mediaId");
			JSONArray jSONArray = data.getJSONArray("articles");
			List<WxMpMaterialNews.WxMpMaterialNewsArticle> articles = jSONArray.toList(WxMpMaterialNews.WxMpMaterialNewsArticle.class);
			WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
			WxMpMaterialArticleUpdate wxMpMaterialArticleUpdate = new WxMpMaterialArticleUpdate();
			wxMpMaterialArticleUpdate.setMediaId(mediaId);
			int index = 0;
			for(WxMpMaterialNews.WxMpMaterialNewsArticle article : articles){
				wxMpMaterialArticleUpdate.setIndex(index);
				wxMpMaterialArticleUpdate.setArticles(article);
				wxMpMaterialService.materialNewsUpdate(wxMpMaterialArticleUpdate);
				index++;
			}
			return new R<>();
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("修改图文失败" + e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改图文失败", e);
			return R.failed(e.getLocalizedMessage());
		}
	}

	/**
	 * 上传图文消息内的图片获取URL
	 * @param mulFile
	 * @return
	 */
	@PostMapping("/newsImgUpload")
	//	@PreAuthorize("@ato.hasAuthority('wxmp_wxmaterial_add')")
	public String newsImgUpload(@RequestParam("file") MultipartFile mulFile,@RequestParam("appId") String appId) throws Exception {
		File file = FileUtils.multipartFileToFile(mulFile);
		WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
		WxMediaImgUploadResult wxMediaImgUploadResult = wxMpMaterialService.mediaImgUpload(file);
		Map<Object, Object> responseData = new HashMap<>();
		responseData.put("link", wxMediaImgUploadResult.getUrl());
		return JSONUtil.toJsonStr(responseData);
	}

	/**
	 * 通过id删除微信素材
	 * @param
	 * @return R
	 */
	@SysLog("删除微信素材")
	@DeleteMapping
	@PreAuthorize("@ato.hasAuthority('wxmp_wxmaterial_del')")
	public R materialDel(String id,String appId){
		WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
		try {
			return  R.ok(wxMpMaterialService.materialDelete(id));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("删除微信素材失败", e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
<<<<<<< HEAD
	 * 分页查询
	 * @param page 分页对象
	 * @param type
	 * @return
	 */
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('wxmp_wxmaterial_index')")
	public R getWxMaterialPage(Page page,String appId, String type) {
		try {
		  WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
		  int count = (int)page.getSize();
		  int offset = (int)page.getCurrent()*count-count;
		  if(WxConsts.MaterialType.NEWS.equals(type)){
			  return  R.ok(wxMpMaterialService.materialNewsBatchGet(offset,count));
		  }else{
			  return  R.ok(wxMpMaterialService.materialFileBatchGet(type,offset,count));
		  }
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("查询素材失败", e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 分页查询2
	 * @param type
	 * @return
	 */
	@GetMapping("/page-manager")
//	@PreAuthorize("@ato.hasAuthority('wxmp_wxmaterial_index')")
	public String getWxMaterialPageManager(Integer count, Integer offset, String appId, String type) throws WxErrorException {
		List<ImageManager> listImageManager = new ArrayList<>();
		WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
		List<WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem> list = wxMpMaterialService.materialFileBatchGet(type,offset,count).getItems();
		list.forEach(wxMaterialFileBatchGetNewsItem -> {
			ImageManager imageManager = new ImageManager();
			imageManager.setName(wxMaterialFileBatchGetNewsItem.getMediaId());
			imageManager.setUrl(wxMaterialFileBatchGetNewsItem.getUrl());
			imageManager.setThumb(wxMaterialFileBatchGetNewsItem.getUrl());
			listImageManager.add(imageManager);
		});
		return JSONUtil.toJsonStr(listImageManager);
	}

	/**
	* 获取微信视频素材
	* @param
	* @return R
	*/
	@GetMapping("/materialVideo")
	@PreAuthorize("@ato.hasAuthority('wxmp_wxmaterial_get')")
	public R getMaterialVideo(String appId,String mediaId){
	  WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
	  try {
		  return  R.ok(wxMpMaterialService.materialVideoInfo(mediaId));
	  } catch (WxErrorException e) {
		  e.printStackTrace();
		  log.error("获取微信视频素材失败", e);
		  return WxReturnCode.wxErrorExceptionHandler(e);
	  }
	}

	/**
	 * 获取微信素材直接文件
	 * @param
	 * @return R
	 */
	@GetMapping("/materialOther")
	@PreAuthorize("@ato.hasAuthority('wxmp_wxmaterial_get')")
	public ResponseEntity<byte[]> getMaterialOther(String appId, String mediaId,String fileName) throws Exception {
		try {
			WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
			//获取文件
			InputStream is = wxMpMaterialService.materialImageOrVoiceDownload(mediaId);
			byte[] body = new byte[is.available()];
			is.read(body);
			HttpHeaders headers = new HttpHeaders();
			//设置文件类型
			headers.add("Content-Disposition", "attchement;filename=" +  URLEncoder.encode(fileName, "UTF-8"));
			headers.add("Content-Type", "application/octet-stream");
			HttpStatus statusCode = HttpStatus.OK;
			//返回数据
			ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
			return entity;
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("获取微信素材直接文件失败", e);
			return null;
		}
	}

	/**
	 * 获取微信临时素材直接文件
	 * @param
	 * @return R
	 */
	@GetMapping("/tempMaterialOther")
	@PreAuthorize("@ato.hasAuthority('wxmp_wxmsg_index')")
	public ResponseEntity<byte[]> getTempMaterialOther(String appId, String mediaId,String fileName) throws Exception {
		try {
			WxMpMaterialService wxMpMaterialService = WxMpConfiguration.getMpService(appId).getMaterialService();
			//获取文件
			InputStream is = new FileInputStream(wxMpMaterialService.mediaDownload(mediaId));
			byte[] body = new byte[is.available()];
			is.read(body);
			HttpHeaders headers = new HttpHeaders();
			//设置文件类型
			headers.add("Content-Disposition", "attchement;filename=" +  URLEncoder.encode(fileName, "UTF-8"));
			headers.add("Content-Type", "application/octet-stream");
			HttpStatus statusCode = HttpStatus.OK;
			//返回数据
			ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
			return entity;
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("获取微信素材直接文件失败", e);
			return null;
		}
	}
}
