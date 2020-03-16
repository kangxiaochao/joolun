package com.joolun.cloud.weixin.admin.config.ma;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.google.common.collect.Maps;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.weixin.admin.config.open.WxOpenConfiguration;
import com.joolun.cloud.weixin.admin.service.WxUserService;
import com.joolun.cloud.weixin.common.constant.MyReturnCode;
import com.joolun.cloud.weixin.common.constant.WxMaConstants;
import com.joolun.cloud.weixin.common.dto.MallUserInfoDTO;
import com.joolun.cloud.weixin.common.entity.ThirdSession;
import com.joolun.cloud.weixin.common.entity.WxApp;
import com.joolun.cloud.weixin.admin.service.WxAppService;
import com.joolun.cloud.weixin.common.entity.WxUser;
import com.joolun.cloud.weixin.common.feign.FeignMallUserInfoService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 小程序Configuration
 * @author JL
 *
 */
@Configuration
public class WxMaConfiguration {
	/**
	 * 全局缓存WxMaService
	 */
	private static Map<String, WxMaService> maServices = Maps.newHashMap();

	/**
	 * 全局缓存WxMaMessageRouter
	 */
	private static Map<String, WxMaMessageRouter> routers = Maps.newHashMap();

	private static RedisTemplate redisTemplate;
	private static WxAppService wxAppService;
	public WxMaConfiguration(RedisTemplate redisTemplate, WxAppService wxAppService){
		this.redisTemplate = redisTemplate;
		this.wxAppService = wxAppService;
	}
	/**
	 *  获取全局缓存WxMaService
	 * @param appId
	 * @return
	 */
	public static WxMaService getMaService(String appId) {
		WxMaService wxMaService = maServices.get(appId);
		if(wxMaService == null) {
			WxApp wxApp = wxAppService.getById(appId);
			if(wxApp!=null) {
				if(CommonConstants.YES.equals(wxApp.getIsComponent())){//第三方平台
					wxMaService = WxOpenConfiguration.getOpenService().getWxOpenComponentService().getWxMaServiceByAppid(appId);
					maServices.put(appId, wxMaService);
					routers.put(appId, newRouter(wxMaService));
				}else{
					WxMaInRedisConfigStorage configStorage = new WxMaInRedisConfigStorage(redisTemplate);
					configStorage.setAppid(wxApp.getId());
					configStorage.setSecret(wxApp.getSecret());
					configStorage.setToken(wxApp.getToken());
					configStorage.setAesKey(wxApp.getAesKey());
					wxMaService = new WxMaServiceImpl();
					wxMaService.setWxMaConfig(configStorage);
					maServices.put(appId, wxMaService);
					routers.put(appId, newRouter(wxMaService));
				}
			}
		}
		return wxMaService;
	}

	/**
	 * 移除WxMaService缓存
	 * @param appId
	 */
	public static void removeWxMaService(String appId){
		maServices.remove(appId);
		routers.remove(appId);
	}

	private static WxMaMessageRouter newRouter(WxMaService service) {
		final WxMaMessageRouter router = new WxMaMessageRouter(service);
		return router;
	}

	/**
	 * 通过request获取appId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getAppId(HttpServletRequest request) {
		String appId = request.getHeader("app-id");
		return appId;
	}

	/**
	 * 通过request获取WxApp
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static WxApp getApp(HttpServletRequest request) throws Exception {
		String appId = getAppId(request);
		WxApp wxApp = wxAppService.getById(appId);
		if(wxApp==null) {
			throw new Exception("系统内无此小程序：" + appId);
		}
		return wxApp;
	}

}
