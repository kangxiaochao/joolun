package com.joolun.cloud.mall.common.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 快递100工具类
 */
public class Kuaidi100Util {

	/**
	 * 实时查询请求地址
	 */
	private static final String SUBSCRIBE_URL = "http://poll.kuaidi100.com/poll";
	/**
	 * 授权key
	 */
	private String key;

	public Kuaidi100Util(String key) {
		this.key = key;
	}
	/**
	 * 订阅请求方法
	 * @param company				快递公司编码
	 * @param number				快递单号
	 * @param from					出发地城市
	 * @param to					目的地城市
	 * @param callbackurl			回调地址URL
	 * @param salt					加密签名字符串
	 * @param resultv2				行政区域解析
	 * @param autoCom				智能判断单号归属快递公司
	 * @param interCom				开启国际版
	 * @param departureCountry		出发国编码
	 * @param departureCom			出发国快递公司编码
	 * @param destinationCountry	目的国编码
	 * @param destinationCom		目的国快递公司编码
	 * @param phone					手机号
	 * @return
	 */
	public String subscribeData(String company, String number, String from, String to, String callbackurl, String salt, int resultv2, int autoCom,
								int interCom, String departureCountry, String departureCom, String destinationCountry, String destinationCom, String phone) {

		StringBuilder param = new StringBuilder("{");
		param.append("\"company\":\"").append(company).append("\"");
		param.append(",\"number\":\"").append(number).append("\"");
		param.append(",\"from\":\"").append(from).append("\"");
		param.append(",\"to\":\"").append(to).append("\"");
		param.append(",\"key\":\"").append(this.key).append("\"");
		param.append(",\"parameters\":{");
		param.append("\"callbackurl\":\"").append(callbackurl).append("\"");
		param.append(",\"salt\":\"").append(salt).append("\"");
		if(1 == resultv2) {
			param.append(",\"resultv2\":1");
		} else {
			param.append(",\"resultv2\":0");
		}
		if(1 == autoCom) {
			param.append(",\"autoCom\":1");
		} else {
			param.append(",\"autoCom\":0");
		}
		if(1 == interCom) {
			param.append(",\"interCom\":1");
		} else {
			param.append(",\"interCom\":0");
		}
		param.append(",\"departureCountry\":\"").append(departureCountry).append("\"");
		param.append(",\"departureCom\":\"").append(departureCom).append("\"");
		param.append(",\"destinationCountry\":\"").append(destinationCountry).append("\"");
		param.append(",\"destinationCom\":\"").append(destinationCom).append("\"");
		param.append(",\"phone\":\"").append(phone).append("\"");
		param.append("}");
		param.append("}");

		Map<String, String> params = new HashMap<String, String>();
		params.put("schema", "json");
		params.put("param", param.toString());

		return this.post(params);
	}

	/**
	 * 发送post请求
	 */
	public String post(Map<String, String> params) {
		StringBuffer response = new StringBuffer("");

		BufferedReader reader = null;
		try {
			StringBuilder builder = new StringBuilder();
			for (Map.Entry<String, String> param : params.entrySet()) {
				if (builder.length() > 0) {
					builder.append('&');
				}
				builder.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				builder.append('=');
				builder.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] bytes = builder.toString().getBytes("UTF-8");

			URL url = new URL(SUBSCRIBE_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(bytes);

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			String line = "";
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return response.toString();
	}
}
