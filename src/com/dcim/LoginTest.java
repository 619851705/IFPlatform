package com.dcim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

public class LoginTest {
	private CloseableHttpClient client;
	public HttpPost httpPost;
	public HttpGet httpGet;
	public String urlString;
	
	
	public void Login(String username,String password,String url) throws Exception{
		client = HttpClients.createDefault();
		httpPost = new HttpPost(url);
		System.out.println("request line:"+httpPost.getRequestLine());
		List<NameValuePair> params = new ArrayList<NameValuePair>(); 
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password",password));
		httpPost.setEntity(new UrlEncodedFormEntity(params));
		
		/*ResponseHandler<String> responseHandle = new ResponseHandler<String>() {
			
			@Override
			public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if(status >=200 &&status<300){
					HttpEntity entity = response.getEntity();
					return entity !=null ? EntityUtils.toString(entity):null;
				}else{
					throw new ClientProtocolException("response status:"+status);
				}
			}
		};
		String responseBody = client.execute(httpPost, responseHandle);
		System.out.println("response Body:"+responseBody);*/
		
		HttpResponse response = client.execute(httpPost);
		System.out.println("----"+response.getStatusLine()+"----");
		HttpEntity entity = response.getEntity();
		System.out.println("response content:"+EntityUtils.toString(entity));
		
		/*HeaderIterator hi = response.headerIterator();
		while(hi.hasNext()){
			System.out.println("我是cookie:"+hi.next());
		}*/
		
		/*HeaderElementIterator hei = new BasicHeaderElementIterator(response.headerIterator());
		while(hei.hasNext()){
			HeaderElement elements = hei.nextElement();
			NameValuePair[] params2 = elements.getParameters();
			for(int i=0;i<params2.length;i++){
				System.out.println("name-value:"+params2[i]);
			}
		}*/
		//关闭对象流
		EntityUtils.consume(entity);
	}
	
	public void successLogin(){
		httpGet = new HttpGet();
	}
	
	public static void main(String[] args) throws Exception{
		LoginTest logindcim = new LoginTest();
		logindcim.Login("admin","e10adc3949ba59abbe56e057f20f883e","http://127.0.0.1:9080/dcim-modeling-server/api/user/login");
	}
}
