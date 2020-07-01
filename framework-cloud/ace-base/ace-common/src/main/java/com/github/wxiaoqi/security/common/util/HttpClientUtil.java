package com.github.wxiaoqi.security.common.util;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @author xufeng 
* @Description: httpclient工具类
* @date 2015年12月4日 上午11:57:26 
* @version V1.0  
*
 */
public class HttpClientUtil {

	private static Logger _log = LoggerFactory.getLogger(HttpClientUtil.class);

	@SuppressWarnings("finally")
	public static String sendPostRequest(String url, Map<String, String> params) {

		HttpClient httpClient = HttpClients.createDefault();

		HttpPost method = new HttpPost(url);
		
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(30000).setConnectTimeout(30000).build();
		method.setConfig(requestConfig);
		
		String resData = "";
		try {
			List<NameValuePair> values = new ArrayList<NameValuePair>();
			if(params != null){
				for(String key : params.keySet()){
					values.add(new BasicNameValuePair(key , params.get(key)));
				}
			}
			HttpEntity entity = new UrlEncodedFormEntity(values, "UTF-8"); 
//			entity.setContentType("application/json");
			method.setEntity(entity);
			HttpResponse result = httpClient.execute(method);

			resData = EntityUtils.toString(result.getEntity());
			
		} catch (UnsupportedCharsetException e) {
			_log.error("异常 UnsupportedCharsetException:{}{}" , url , params , e);
		} catch (ClientProtocolException e) {
			_log.error("异常 ClientProtocolException:{}{}" , url , params , e);
		} catch (ParseException e) {
			_log.error("异常 ParseException:{}{}" , url , params , e);
		} catch (IOException e) {
			_log.error("异常 IOException:{}{}" , url , params , e);
		} catch (Exception e) {
			_log.error("异常 Exception:{}{}" , url , params , e);
		}finally{
			method.releaseConnection();
			return resData;
		}
	}
	
	@SuppressWarnings("finally")
	public static String sendPostJsonRequest(String url, Object params) {
		
		HttpClient httpClient = HttpClients.createDefault();
		
		HttpPost method = new HttpPost(url);
		
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(3000).setConnectTimeout(3000).build();
		method.setConfig(requestConfig);
		
		String resData = "";
		try {
			StringEntity entity = new StringEntity(JacksonJsonUtil.beanToJson(params) , "utf-8");//解决中文乱码问题     
            entity.setContentType("application/json");  
			method.setEntity(entity);
			HttpResponse result = httpClient.execute(method);
			
			resData = EntityUtils.toString(result.getEntity());
			
		} catch (UnsupportedCharsetException e) {
			_log.error("异常 UnsupportedCharsetException:{}{}" , url , params , e);
		} catch (ClientProtocolException e) {
			_log.error("异常 ClientProtocolException:{}{}" , url , params , e);
		} catch (ParseException e) {
			_log.error("异常 ParseException:{}{}" , url , params , e);
		} catch (IOException e) {
			_log.error("异常 IOException:{}{}" , url , params , e);
		} catch (Exception e) {
			_log.error("异常 Exception:{}{}" , url , params , e);
		}finally{
			method.releaseConnection();
			return resData;
		}
	}

	@SuppressWarnings("finally")
	public static String sendGetRequest(String url, Map<String, String> params) {

		HttpClient httpClient = HttpClients.createDefault();
		HttpGet getMethod = null;
		String resData = "";
		try {
			
			List<NameValuePair> values = new ArrayList<NameValuePair>();
			if(params != null){
				for(String key : params.keySet()){
					values.add(new BasicNameValuePair(key , params.get(key)));
				}
			}
			
			getMethod = new HttpGet(url + "&" + EntityUtils.toString(new UrlEncodedFormEntity(values)));
			
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(3000).setConnectTimeout(3000).build();
			getMethod.setConfig(requestConfig);
			
			HttpResponse result = httpClient.execute(getMethod);

			resData = EntityUtils.toString(result.getEntity());
			
		} catch (UnsupportedCharsetException e) {
			_log.error("异常 UnsupportedCharsetException:{}{}" , url , params , e);
		} catch (ClientProtocolException e) {
			_log.error("异常 ClientProtocolException:{}{}" , url , params , e);
		} catch (ParseException e) {
			_log.error("异常 ParseException:{}{}" , url , params , e);
		} catch (IOException e) {
			_log.error("异常 IOException:{}{}" , url , params , e);
		} catch (Exception e) {
			_log.error("异常 Exception:{}{}" , url , params , e);
		}finally{
			if(getMethod != null){
				getMethod.releaseConnection();
			}
			return resData;
		}
	}

	/**
	 * 
	 * @param url the web will be connected 
	 * @param headers data will be sent
	 * @param raw data will be sent
	 */
	@SuppressWarnings("finally")
	public static String sentPostHttpNew(String url,Map<String, String> headers,String rawStr){
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost method = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(30000).setConnectTimeout(30000).build();
		method.setConfig(requestConfig);
		String resData="" ;
		try {
			if (headers != null) {
				Set<String> keys = headers.keySet();
				for (Iterator<String> i = keys.iterator(); i.hasNext();) {
					String key = (String) i.next();
					method.addHeader(key, headers.get(key));
				}
			}

			StringEntity entity = new StringEntity(rawStr , "utf-8");//解决中文乱码问题
			entity.setContentType("application/json");
			method.setEntity(entity);

			HttpResponse result = httpClient.execute(method);
			resData = EntityUtils.toString(result.getEntity());

		} catch (UnsupportedCharsetException e) {
			_log.error("异常 UnsupportedCharsetException:{}{}" , url , headers , e);
		} catch (ClientProtocolException e) {
			_log.error("异常 ClientProtocolException:{}{}" , url , headers , e);
		} catch (ParseException e) {
			_log.error("异常 ParseException:{}{}" , url , headers , e);
		} catch (IOException e) {
			_log.error("异常 IOException:{}{}" , url , headers , e);
		} catch (Exception e) {
			_log.error("异常 Exception:{}{}" , url , headers , e);
		}finally{
			method.releaseConnection();
			return resData;
		}
	}
	
	/**
	 * post请求
	 * @param url 请求地址
	 * @param timeOut 超时时间（秒）
	 * @param params 参数
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String sendPostRequest(String url,int timeOut, Map<String, String> params) {

		HttpClient httpClient = HttpClients.createDefault();

		HttpPost method = new HttpPost(url);
		
		timeOut = timeOut*1000;
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
		method.setConfig(requestConfig);
		
		String resData = "";
		try {
			List<NameValuePair> values = new ArrayList<NameValuePair>();
			if(params != null){
				for(String key : params.keySet()){
					values.add(new BasicNameValuePair(key , params.get(key)));
				}
			}
			HttpEntity entity = new UrlEncodedFormEntity(values, "UTF-8"); 
//			entity.setContentType("application/json");
			method.setEntity(entity);
			HttpResponse result = httpClient.execute(method);

			resData = EntityUtils.toString(result.getEntity());
			
		} catch (UnsupportedCharsetException e) {
			_log.error("异常 UnsupportedCharsetException:{}{}" , url , params , e);
		} catch (ClientProtocolException e) {
			_log.error("异常 ClientProtocolException:{}{}" , url , params , e);
		} catch (ParseException e) {
			_log.error("异常 ParseException:{}{}" , url , params , e);
		} catch (IOException e) {
			_log.error("异常 IOException:{}{}" , url , params , e);
		} catch (Exception e) {
			_log.error("异常 Exception:{}{}" , url , params , e);
		}finally{
			method.releaseConnection();
			return resData;
		}
	}
	
	public static String httpBasicAuth(String url,String userName,String password,Object params) {
		String resData = "";
		CloseableHttpClient createDefault = null;
		try {
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
	        credsProvider.setCredentials(AuthScope.ANY,
	                new UsernamePasswordCredentials(userName, password));
	        createDefault = HttpClients.custom()
	                .setDefaultCredentialsProvider(credsProvider)
	                .build();
			HttpPost post = new HttpPost(url);
			StringEntity entity = new StringEntity(JacksonJsonUtil.beanToJson(params) , "utf-8");//解决中文乱码问题     
			entity.setContentEncoding("UTF-8");    
			entity.setContentType("application/json");  
			
			post.setEntity(entity);
			CloseableHttpResponse result = createDefault.execute(post);
			resData = EntityUtils.toString(result.getEntity());
		} catch (UnsupportedCharsetException e) {
			_log.error("异常 UnsupportedCharsetException:{}{}" , url , params , e);
		} catch (ClientProtocolException e) {
			_log.error("异常 ClientProtocolException:{}{}" , url , params , e);
		} catch (ParseException e) {
			_log.error("异常 ParseException:{}{}" , url , params , e);
		} catch (IOException e) {
			_log.error("异常 IOException:{}{}" , url , params , e);
		} catch (Exception e) {
			_log.error("异常 Exception:{}{}" , url , params , e);
		} finally {
			if(createDefault!=null) {
				try {
					createDefault.close();
				} catch (IOException e) {
					_log.error("异常 Exception:{}{}" , url , params , e);
				}
			}
		}
		return resData;
	}
	
	public static void main(String[] args) {
//		String requestUrl = "http://fzg-blockchain-api.bcfzg.com/api/LandDocument";
//		LandDocument LandDocument = new LandDocument();
//		LandDocument.setId(Utils.getGUID());
//		LandDocument.setName("testdoc005");
//		LandDocument.setHash("9F86D081884C7D659A2ddddCD15D6C15B0F00A08");
//		LandDocument.setReference("https://testurl.com/");
//		LandDocument.setDescription("描述");
//		LandDocument.setType("LAND_SALES_AGREEMENT");
//		
//		String sendPostJsonRequest = sendPostJsonRequest(requestUrl, LandDocument);
//		System.out.println(sendPostJsonRequest);
		
	}
	
	
}
