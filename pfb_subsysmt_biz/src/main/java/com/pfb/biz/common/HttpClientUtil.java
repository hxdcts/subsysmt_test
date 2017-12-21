package com.pfb.biz.common;

import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pfb.common.util.JSONUtil;

/**
 * 
* @desc: 远程调用类 增加 ContentType
* @author: cts
* @createTime: 2017年12月8日 下午6:04:15
* @history:
* @version: v1.0
 */
@SuppressWarnings("deprecation")
public class HttpClientUtil {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	// 接口地址
	private String apiURL = "";
	private HttpClient httpClient = null;
	private HttpPost method = null;
	private long startTime = 0L;
	private long endTime = 0L;
	/** 0.成功 1.执行方法失败 2.协议错误 3.网络错误 */
	private int status = 0;
	
	/** 
	 * 绕过验证 
	 *   
	 * @return 
	 * @throws NoSuchAlgorithmException  
	 * @throws KeyManagementException  
	 */  
	public SSLContext ignoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {  
	    SSLContext sc = SSLContext.getInstance("SSLv3");  
	  
	    // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法  
	    X509TrustManager trustManager = new X509TrustManager() {  
	        @Override  
	        public void checkClientTrusted(  
	                java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                String paramString) throws CertificateException {  
	        }  
	  
	        @Override  
	        public void checkServerTrusted(  
	                java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                String paramString) throws CertificateException {  
	        }  
	  
	        @Override  
	        public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
	            return null;  
	        }  
	    };  
	  
	    sc.init(null, new TrustManager[] { trustManager }, null);  
	    return sc;  
	} 

	/**
	 * 接口地址
	 * 
	 * @param url
	 *            请求地址
	 */
	public HttpClientUtil(String url) {
		if (url != null) {
			this.apiURL = url;
		}
		if (apiURL != null) {
			httpClient = new DefaultHttpClient();
			method = new HttpPost(apiURL);
		}
	}

	/**
	 * 发送post请求， 数据格式JSON
	 * 
	 * @param jsonParamsStr
	 *            json格式的数据
	 * @param log
	 *            日志（可为空）
	 * @return
	 * @author zhang.hui@pufubao.net
	 * @date 2017年2月14日 下午2:22:11
	 * 
	 */
	public String sendJsonPost(String jsonParamsStr, Logger log) {
		
		if (log == null) {
			log = logger;
		}
		String body = null;
		log.info("-----------请求上游数据 : " + jsonParamsStr);

		if (method != null & jsonParamsStr != null && !"".equals(jsonParamsStr.trim())) {
			try {
				//采用绕过验证的方式处理https请求  
			    SSLContext sslcontext = ignoreVerifySSL();  
			    // 设置协议http和https对应的处理socket链接工厂的对象 
			    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
				           .register("http", PlainConnectionSocketFactory.INSTANCE)  
				           .register("https", new SSLConnectionSocketFactory(sslcontext))  
				           .build();  
			    PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
				HttpClients.custom().setConnectionManager(connManager); 
			    //创建自定义的httpclient对象  
			    httpClient = HttpClients.custom().setConnectionManager(connManager).build();  
//			    CloseableHttpClient client = HttpClients.createDefault();  
				
//				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// 建立一个NameValuePair数组，用于存储欲传送的参数
//				params.add(new BasicNameValuePair("json", jsonParamsStr));
				// 添加参数
				StringEntity stringEntity = new StringEntity(jsonParamsStr,Charset.forName(HTTP.UTF_8));
				stringEntity.setContentType(ContentType.APPLICATION_JSON.toString());
			    method.setEntity(stringEntity);
				// 请求开始执行时间
				startTime = System.currentTimeMillis();
				// 设置编码
				HttpResponse response = httpClient.execute(method);
				// 请求结束时间
				endTime = System.currentTimeMillis();
				// 请求状态
				int statusCode = response.getStatusLine().getStatusCode();
				log.info("statusCode:" + statusCode);
				log.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
				if (statusCode != HttpStatus.SC_OK) {
					log.error("Method failed:" + response.getStatusLine());
					status = 1;
				}
				// Read the response body
				body = EntityUtils.toString(response.getEntity());
				log.info("----------上游返回数据 {}",body);
			} catch (Exception e) {
				// 发生网络异常
				log.error("exception occurred!\n" + ExceptionUtils.getFullStackTrace(e));
				// 网络错误
				status = 3;
			} finally {
				log.info("调用接口状态：" + status);
			}

		}
		return body;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}
	
	public static void main(String[] args) {
		Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sd", "sad");
		String jsonParamsStr = JSONUtil.toJSONString(map);
		String sendJsonPost = new HttpClientUtil("https://api.mf.pufubao.net/api/callback/pay-notice").sendJsonPost(jsonParamsStr , log);
	System.out.println(sendJsonPost);
	}
}
