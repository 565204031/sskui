package com.ssk.sskui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ssk.sskui.utils.HttpUtils;
import com.ssk.sskui.utils.HttpUtils.HttpCallback;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * HttpUtilsDemo;
 * @author 杀死凯 QQ565204031
 *
 */
public class HttpUtilsDemoActivity extends Activity implements HttpCallback  {
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Map<String,String> data;
		data=new HashMap<String, String>();
//		data.put("cl", "3");
//		data.put("wd", "yinyue");
		HttpUtils http=new HttpUtils("http://www.baidu.com", data, "GET",this);
		http.doRequest();
	
	}

	@Override
	public void onSuccess(String result) {
       Log.i("info",result);
	}

	@Override
	public void onFailure(String result) {
		// TODO Auto-generated method stub
		 Log.i("info",result);
	}
}
