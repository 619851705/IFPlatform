package com.dcim;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse; 
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

public class Example { 

	public static void main(String[] args) throws Exception{
		
		/*CloseableHttpClient httpclient = HttpClients.createDefault();
		//HttpGet httpget = new HttpGet("http://www.jianshu.com/search?q=123&page=1&type=notes");
		URI uri = new URIBuilder()
				.setScheme("http")
				.setHost("www.jianshu.com")
				.setPath("/search")
				.setParameter("q","123")
				.setParameter("page", "1")
				.setParameter("type","note")
				.build();
		HttpGet httpGet = new HttpGet(uri);
		System.out.println("uri:"+httpGet.getURI());
		CloseableHttpResponse response = httpclient.execute(httpGet);
		try {
		    System.out.println("-----"+response.getStatusLine());
		    //HttpEntity entity = response.getEntity();
		    //System.out.println("++="+ EntityUtils.toString(entity));
		} finally {
		    response.close();
		}*/
		/*HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,HttpStatus.SC_OK,"OK");
		response.addHeader("Set-Cookie", "c1=a; path=/; domain=localhost");
		response.addHeader("Set-Cookie","c2=b; path=\"/\", c3=c; domain=\"localhost\"");
		response.setHeader("nocookie", "123457htht");*/
		
		/*System.out.println("getAllHeaders:"+response.getAllHeaders().toString());
		System.out.println("getEntity:"+response.getEntity());
		System.out.println("getLocale:"+response.getLocale());
		System.out.println("getParams:"+response.getParams().toString());
		System.out.println("getProtocolVersion:"+response.getProtocolVersion());
		System.out.println("getStatusLine:"+response.getStatusLine());
		System.out.println("getStatusCode:"+response.getStatusLine().getStatusCode());
		System.out.println("getReasonPhrase:"+response.getStatusLine().getReasonPhrase());
		System.out.println("1_heads:"+response.getFirstHeader("Set-Cookie"));
		System.out.println("l_heads"+response.getLastHeader("nocookie"));
		System.out.println("a_heads:"+response.getHeaders("Set-Cookie"));
		System.out.println("cookie:"+response.getHeaders("nocookie").toString());*/
		
/*		System.out.println("1_heads:"+response.getFirstHeader("Set-Cookie"));
		System.out.println("l_heads:"+response.getLastHeader("Set-Cookie"));*/
		
		/*HeaderIterator hi = response.headerIterator("Set-Cookie");
		while(hi.hasNext()){
			System.out.println(hi.next());
		}*/
		
		/*HeaderElementIterator hei = new BasicHeaderElementIterator(response.headerIterator("Set-Cookie"));
		while(hei.hasNext()){
			HeaderElement elements = hei.nextElement();
			//System.out.println(elements.getName() + " = " + elements.getValue());
			NameValuePair[] params = elements.getParameters();
			for(int i=0;i<params.length;i++){
				System.out.println(params[i]);
			}
		}*/
		
		/*ResponseHandler<String> rh = new ResponseHandler<String>() {

			@Override
			public String handleResponse(HttpResponse arg0) throws ClientProtocolException, IOException {
				int statue = response.getStatusLine().getStatusCode();
				if(statue >=200 && statue<300){
					HttpEntity entity = response.getEntity();
					return entity!=null ? EntityUtils.toString(entity):null;
				}else{
					throw new ClientProtocolException("response status:"+status);
				}
			}
			
		};*/
		
		HttpClientContext context = HttpClientContext.create();
		HttpClientConnectionManager manager = new BasicHttpClientConnectionManager();
		HttpRoute route = new HttpRoute(new HttpHost("127.0.0.1", 9080));
		ConnectionRequest connRequest = manager.requestConnection(route, null);
		HttpClientConnection conn = connRequest.get(10, TimeUnit.SECONDS);
		System.out.println("-----------------------"+conn.isOpen());
		if(conn.isOpen()){
			System.out.println("-----------------------"+conn.isOpen());
			manager.connect(conn, route, 1000, context);
			manager.routeComplete(conn, route, context);
		}
		
		PoolingHttpClientConnectionManager cm =new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpCLient = HttpClients.createDefault();
		String[] uri = {
				"http://www.domain1.com/",
				"http://www.domain2.com/",
				"http://www.domain3.com/"				
		};
		GetThread[] threads = new GetThread[uri.length];
		for(int i =0 ;i<uri.length;i++){
			System.out.println("get:"+uri[i]);
			HttpGet httpGet = new HttpGet(uri[i]);
			threads[i] = new GetThread(httpCLient, httpGet);
		}
		for(int j=0;j<uri.length;j++){
			System.out.println("start:"+uri[j]);
			threads[j].start();
		}
		for(int z=0;z<uri.length;z++){
			System.out.println("join:"+uri[z]);
			threads[z].join();
		}
	}
	

	
	
}
	

