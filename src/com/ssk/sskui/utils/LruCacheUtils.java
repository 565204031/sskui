package com.ssk.sskui.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

/**
 * 图片缓存帮助类
 * @author 杀死凯 QQ565204031
 * 
 */
public class LruCacheUtils {

	
	//缓存Image的类，当存储Image的大小大于LruCache设定的值，系统自动释放内存 
    private static LruCache<String, Bitmap> mMemoryCache;  
    //默认路径
    private final static String SD_PATHDefault = Environment.getExternalStorageDirectory().getAbsolutePath().toString(); 
    //路径
    private static String SD_PATH;
    //下载图片时间
    public static int timeout=5000;
    public static void initLruCache(String path){
    	LruCacheUtils.SD_PATH=path;
    	 int maxMemory = (int) Runtime.getRuntime().maxMemory();    
         int mCacheSize = maxMemory / 8;  
         mMemoryCache=new LruCache<String, Bitmap>(mCacheSize){
			
			 //必须重写此方法，来测量Bitmap的大小  
			@Override
			protected int sizeOf(String key, Bitmap value) {
				  return value.getRowBytes() * value.getHeight(); 
			}
		};
    }
    /**
     * 显示图片到控件
     * @param iv
     * @param url
     */
    public static void display(ImageView iv,String url){
    	display(iv,url,0,0);
    }
    /**
     * 显示图片到控件
     * @param iv 显示控件
     * @param url http路径
     * @param reqWidth  期望图片宽度
     * @param reqHeight 期望图片高度
     */
    public static void display(ImageView iv,String url,int reqWidth,int reqHeight){
    	LoadingImageTask loading=new LoadingImageTask(iv);
    	loading.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url,reqWidth+"",reqHeight+"");  
    	//loading.execute(url,reqWidth+"",reqHeight+"");
    }
    private static class LoadingImageTask extends AsyncTask<String,Void,Bitmap>{

    	private ImageView iv;
    	public LoadingImageTask(ImageView iv){
    		this.iv=iv;
    	}
		@Override
		protected Bitmap doInBackground(String... params) {
			String name=params[0].substring(params[0].lastIndexOf("/")+1);
			int reqWidth=Integer.parseInt(params[1]);
			int reqHeight=Integer.parseInt(params[2]);
	    
			Bitmap bitmap=null;
			//查看内存是否有该图片
			bitmap=getBitmapFromMemCache(SD_PATH+"/"+name);
			if(bitmap==null){
				//查看sd卡是否有该图片
				bitmap=SDloadImage(SD_PATH+"/"+name,reqWidth,reqHeight);
			}
			if(bitmap==null){
				//网络下载到本地
				bitmap=loadImage(params[0],reqWidth,reqHeight);
			}
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if(result!=null){
				iv.setImageBitmap(result);;
			}
		}
	}
    /**
	 * 网络加载图片，保存到SD卡
	 * @param 图片URL
	 * @return 返回压缩后的图片
	 * @throws IOException 
	 * @author 杀死凯 QQ565204031
	 */
	private static Bitmap loadImage(String strurl,int reqWidth,int reqHeight){
		try {
			URL url = new URL(strurl);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(timeout);
			String name=strurl.substring(strurl.lastIndexOf("/")+1);
			if(conn.getResponseCode()==200)
			{
				InputStream is=conn.getInputStream();
				//判断目录是否存在，没有则创建
				File file = new File(SD_PATH);
				if (!file.exists()) {
					file.mkdir();
				}
				//下载图片到sd卡
				FileOutputStream os=new FileOutputStream(SD_PATH+"/"+name);
				int len=0;
				byte[] buffer=new byte[1024];
				//返回-1表示读完
				while((len=is.read(buffer))!=-1)
				{
					os.write(buffer,0,len);
				}
				os.close();
				is.close();
				return getZoomBitmap(SD_PATH+"/"+name,reqWidth,reqHeight);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
    
	 /** 
     * 添加Bitmap到内存缓存 
     * @param key 
     * @param bitmap
     * @author 杀死凯 QQ565204031 
     */  
    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {    
        if (getBitmapFromMemCache(key) == null && bitmap != null) { 
        	if(mMemoryCache==null)
        	{
        		initLruCache(SD_PATHDefault);
        	}
            mMemoryCache.put(key, bitmap);    
            
        }    
    }    
       
    /** 
     * 从内存缓存中获取一个Bitmap 
     * @param key 
     * @return 
     * @author 杀死凯 QQ565204031
     */  
    public static Bitmap getBitmapFromMemCache(String key) {   
    	if(mMemoryCache==null)
    	{
    		initLruCache(SD_PATHDefault);
    	}
        return mMemoryCache.get(key);    
    }  
	/**
	 * 
	 * @param SD卡路径
	 * @param 压缩宽度
	 * @return 压缩后的图片
	 * @author 杀死凯 QQ565204031
	 */
	public static Bitmap getZoomBitmap(String path,int reqWidth,int reqHeight) {
		//原图
		//Bitmap bitmap = BitmapFactory.decodeFile(path);
		BitmapFactory.Options options=new Options();
		//如果为true，options.outWidth 返回图片宽度，图片不会加载到内存
		options.inJustDecodeBounds=true;
		BitmapFactory.decodeFile(path,options);
		//等于0表示不压缩
		if(reqWidth>0&&reqHeight>0)
		{
			//压缩图片比例
			int zoomimageWidth=options.outWidth/reqWidth;
			int zoomimageHeight=options.outHeight/reqHeight;
			//按照比例大的来缩放
			options.inSampleSize=zoomimageWidth>zoomimageHeight?zoomimageWidth:zoomimageHeight;
		}
		//加载到内存
		options.inJustDecodeBounds=false;
		
		Bitmap bitmap=BitmapFactory.decodeFile(path,options);
		//将Bitmap 加入内存缓存  
		addBitmapToMemoryCache(path, bitmap);  
		//内存缓存获得图片
		return getBitmapFromMemCache(path);
	}
	/**
	 * @param 图片本地路径
	 * @return 返回null表示SD卡无图
	 * @author 杀死凯 QQ565204031
	 */
	public static Bitmap SDloadImage(String path,int reqWidth,int reqHeight){
		return getZoomBitmap(path,reqWidth,reqHeight);
	}
}
