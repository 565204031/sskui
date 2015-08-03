package com.ssk.sskui.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Http帮助类
 * @author Administrator
 *
 */
public class HttpUtils {

	//请求时间
	private final int CONNECTIME=5000;
	//请求类型POST,GET
	private String method;
	//URL路径
	private String path;
	//回调
	private HttpCallback callback;
	//参数
	private Map<String,String> data;
	
	public HttpUtils(String path,Map<String,String> data,String method,HttpCallback callback){
		this.path=path;
		this.data=data;
		this.method=method;
		this.callback=callback;
	}
	//发送请求
	public void doRequest(){
		HttpTask task = new HttpTask();
		task.execute("");
	}
	/**
	 * 提交Post请求
	 * @return
	 */
	private String dopost(){
		String result=null;
		try {
			List <NameValuePair> params = new ArrayList <NameValuePair>();
			for(Map.Entry<String, String> entry : data.entrySet()){
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			HttpPost conn=new HttpPost(path);
			conn.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response=new DefaultHttpClient().execute(conn);
			
			//返回状态
			int code=response.getStatusLine().getStatusCode();
			if(code==200){
				//成功
				result = EntityUtils.toString(response.getEntity()); 
				if(result!=null){
					callback.onSuccess(result);
				}
			}else{
				//失败
				callback.onFailure(code+"");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	} 
	/**
	 * 提交GET请求
	 * @return
	 */
	private String doget(){
		String result=null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("?");
			for(Map.Entry<String, String> entry : data.entrySet()){
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			sb.deleteCharAt(sb.length()-1);
			
			HttpGet conn=new HttpGet(path+sb.toString());
			HttpResponse response=new DefaultHttpClient().execute(conn);
			
			//返回状态
			int code=response.getStatusLine().getStatusCode();
			if(code==200){
				//成功
				HttpEntity entity=response.getEntity();
				result=EntityUtils.toString(entity);
				if(result!=null){
					callback.onSuccess(result);
				}
			}else{
				//失败
				callback.onFailure(code+"");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	} 
	private class HttpTask extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(method.equals("GET")){
				return doget();
			}else if(method.equals("POST")){
				return dopost();
			}else{
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}
	/**
	 * 回调接口
	 * @author 杀死凯 QQ565204031
	 *
	 */
	public interface HttpCallback{
		public void onSuccess(String result);
		public void onFailure(String result);
	}
}
