package com.ssk.sskui.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.ssk.sskui.R;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;;

public class WaterfallScrollView extends ScrollView implements OnTouchListener{

	public String[] imgs=new String[]{
			"http://c.hiphotos.baidu.com/image/w%3D230/sign=16fbb2fac9ef76093c0b9e9c1edda301/35a85edf8db1cb13535b8a74df54564e92584ba2.jpg"
			,"http://e.hiphotos.baidu.com/image/pic/item/8b82b9014a90f603aff88e463b12b31bb051ed63.jpg"
			,"http://e.hiphotos.baidu.com/image/w%3D230/sign=67d52833014f78f0800b9df049300a83/4d086e061d950a7b9e30f8a308d162d9f2d3c954.jpg"
			,"http://e.hiphotos.baidu.com/image/pic/item/ac4bd11373f082020e683dd249fbfbedaa641b60.jpg"
			,"http://h.hiphotos.baidu.com/image/w%3D230/sign=59ab138e49ed2e73fce9812fb700a16d/6c224f4a20a44623662659689b22720e0df3d7c8.jpg"
			,"http://a.hiphotos.baidu.com/image/w%3D230/sign=8cd2be42f9edab6474724ac3c734af81/b999a9014c086e063548ffc200087bf40bd1cb53.jpg"
			,"http://d.hiphotos.baidu.com/image/pic/item/241f95cad1c8a7860ab88a876509c93d71cf50ad.jpg"
			,"http://c.hiphotos.baidu.com/image/pic/item/562c11dfa9ec8a1349279ce9f503918fa0ecc0fe.jpg"
			,"http://a.hiphotos.baidu.com/image/pic/item/2f738bd4b31c87017187c55a267f9e2f0708ff5d.jpg"
			,"http://a.hiphotos.baidu.com/image/pic/item/2f738bd4b31c87017187c55a267f9e2f0708ff5d.jpg"
			,"http://d.hiphotos.baidu.com/image/pic/item/d439b6003af33a8702f7451bc45c10385343b51d.jpg"
			,"http://d.hiphotos.baidu.com/image/pic/item/d439b6003af33a8702f7451bc45c10385343b51d.jpg"
			,"http://a.hiphotos.baidu.com/image/pic/item/f9198618367adab4c215ab9c8ad4b31c8601e42c.jpg"
			,"http://a.hiphotos.baidu.com/image/pic/item/9922720e0cf3d7ca6b6d2578f01fbe096b63a920.jpg"
			,"http://e.hiphotos.baidu.com/image/pic/item/bd3eb13533fa828b3219b216ff1f4134970a5a08.jpg"
			,"http://b.hiphotos.baidu.com/image/pic/item/83025aafa40f4bfbca021459014f78f0f63618e8.jpg"
			,"http://a.hiphotos.baidu.com/image/pic/item/f603918fa0ec08faf110a8a45bee3d6d55fbdaa5.jpg"
			,"http://d.hiphotos.baidu.com/image/pic/item/34fae6cd7b899e518caf6f1d40a7d933c8950d91.jpg"
			,"http://d.hiphotos.baidu.com/image/pic/item/9922720e0cf3d7ca60ef2e83f01fbe096b63a92d.jpg"
			,"http://g.hiphotos.baidu.com/image/pic/item/79f0f736afc37931e486bb18e8c4b74543a91110.jpg"
			,"http://a.hiphotos.baidu.com/image/pic/item/86d6277f9e2f0708d0bb010eeb24b899a901f206.jpg"
			,"http://a.hiphotos.baidu.com/image/pic/item/0eb30f2442a7d93340de8e73af4bd11373f001bf.jpg"
			,"http://c.hiphotos.baidu.com/image/pic/item/c9fcc3cec3fdfc03f85cb6b1d63f8794a4c22696.jpg"
			,"http://g.hiphotos.baidu.com/image/pic/item/b2de9c82d158ccbfda3671331bd8bc3eb1354135.jpg"
			,"http://d.hiphotos.baidu.com/image/pic/item/0df431adcbef76094019cb9c2cdda3cc7cd99ea6.jpg"
			,"http://h.hiphotos.baidu.com/image/pic/item/e7cd7b899e510fb3f831af9adb33c895d1430c97.jpg"
			,"http://h.hiphotos.baidu.com/image/pic/item/bd315c6034a85edf02f8c7204b540923dd5475b8.jpg"
			,"http://b.hiphotos.baidu.com/image/pic/item/4b90f603738da977d0f1af28b251f8198718e3d3.jpg"
			,"http://b.hiphotos.baidu.com/image/pic/item/9f510fb30f2442a7585aada4d343ad4bd113025e.jpg"
			,"http://a.hiphotos.baidu.com/image/pic/item/7dd98d1001e93901d9c13a5979ec54e737d19692.jpg"
			,"http://e.hiphotos.baidu.com/image/pic/item/ac345982b2b7d0a2e00ab3fac9ef76094a369a92.jpg"

	};
	
	// 缓存Image的类，当存储Image的大小大于LruCache设定的值，系统自动释放内存 
    private LruCache<String, Bitmap> mMemoryCache;  
	//SD卡，根目录
	private final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath().toString(); 
	//每页多少张图片
	public static final int PAGE_COUNT=15;
	//当前第几页
	private int page;
	//每页宽度
	private int columuWidth;
	//第一个LinearLayout
	private LinearLayout first_ll;
	//第二个LinearLayout
	private LinearLayout second_ll;
	//第三个LinearLayout
	private LinearLayout third_ll;
	
	private LinearLayout content_ll;
	//是否第一次加载
	private boolean isloaded;
	//滑动控件高度
	private int scrollViewHeight;
	private int firstHeight;
	private int thirdHeight;
	private int secondHeight;
	public WaterfallScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnTouchListener(this);
		 //获取系统分配给每个应用程序的最大内存，每个应用系统分配32M  
        int maxMemory = (int) Runtime.getRuntime().maxMemory();    
        int mCacheSize = maxMemory / 8;  
        //给LruCache分配1/8 4M  
		mMemoryCache=new LruCache<String, Bitmap>(mCacheSize){
			
			 //必须重写此方法，来测量Bitmap的大小  
			@Override
			protected int sizeOf(String key, Bitmap value) {
				  return value.getRowBytes() * value.getHeight(); 
			}
			
		};
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if(!isloaded)
		{
			//第一次加载
			//初始化控件
			scrollViewHeight=getHeight();
			first_ll=(LinearLayout) findViewById(R.id.first_ll);
			second_ll=(LinearLayout) findViewById(R.id.second_ll);
			third_ll=(LinearLayout) findViewById(R.id.third_ll);
			content_ll=(LinearLayout) findViewById(R.id.content_ll);
			columuWidth=first_ll.getWidth();
			isloaded=true;
			//加载图片
			loadiMages();
		}
	}
	 /** 
     * 添加Bitmap到内存缓存 
     * @param key 
     * @param bitmap
     * @author 杀死凯 QQ565204031 
     */  
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {    
        if (getBitmapFromMemCache(key) == null && bitmap != null) {    
            mMemoryCache.put(key, bitmap);    
        }    
    }    
       
    /** 
     * 从内存缓存中获取一个Bitmap 
     * @param key 
     * @return 
     * @author 杀死凯 QQ565204031
     */  
    public Bitmap getBitmapFromMemCache(String key) {    
        return mMemoryCache.get(key);    
    }   
	private void loadiMages() {
		int startindex=page*PAGE_COUNT;
		int endindex=(page+=1)*PAGE_COUNT;
		Log.i("INFo", imgs.length+"");
		if(startindex<imgs.length)
		{
			Toast.makeText(getContext(), "正在玩命加载", 1000).show();
			if(endindex>imgs.length)
			{
				endindex=imgs.length;
			}	
			for(int i=startindex;i<endindex;i++)
			{
				//setImage(imgs[i],columuWidth,200);
				LoadingImageTask task=new LoadingImageTask();
				task.execute(imgs[i]);
			}
		}else
		{
			Toast.makeText(getContext(), "没有更多图片了", 1000).show();
		}
	}
	private class LoadingImageTask extends AsyncTask<String,Void,Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap=null;
			//返回null表示SD没有缓存
			bitmap=SDloadImage(params[0]);
			if(bitmap==null)
			{
				//网络下载到本地
				bitmap=loadImage(params[0]);
			}
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			setImage(result,columuWidth,result.getWidth());
		}
	}
	/**
	 * @param 图片URL
	 * @return 返回压缩后的图片
	 * @author 杀死凯 QQ565204031
	 */
	private Bitmap SDloadImage(String strurl){
		String name=strurl.substring(strurl.lastIndexOf("/")+1);
		return getZoomBitmap(SD_PATH+"/sskui/"+name,200,220);
	}
	/**
	 * 网络加载图片，保存到SD卡
	 * @param 图片URL
	 * @return 返回压缩后的图片
	 * @throws IOException 
	 * @author 杀死凯 QQ565204031
	 */
	private Bitmap loadImage(String strurl){
		try {
			URL url = new URL(strurl);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			String name=strurl.substring(strurl.lastIndexOf("/")+1);
			if(conn.getResponseCode()==200)
			{
				InputStream is=conn.getInputStream();
				//判断目录是否存在，没有则创建
				File file = new File(SD_PATH+"/sskui");
				if (!file.exists()) {
					file.mkdir();
				}
				//下载图片到sd卡
				FileOutputStream os=new FileOutputStream(SD_PATH+"/sskui/"+name);
				int len=0;
				byte[] buffer=new byte[1024];
				//返回-1表示读完
				while((len=is.read(buffer))!=-1)
				{
					os.write(buffer,0,len);
				}
				os.close();
				is.close();
				
				return getZoomBitmap(SD_PATH+"/sskui/"+name,200,220);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @param SD卡路径
	 * @param 压缩宽度
	 * @return 压缩后的图片
	 * @author 杀死凯 QQ565204031
	 */
	private Bitmap getZoomBitmap(String path,int reqWidth,int reqHeight) {
		//原图
		//Bitmap bitmap = BitmapFactory.decodeFile(path);
		BitmapFactory.Options options=new Options();
		//如果为true，options.outWidth 返回图片宽度，图片不会加载到内存
		options.inJustDecodeBounds=true;
		BitmapFactory.decodeFile(path,options);
		
		//压缩图片比例
		int zoomimageWidth=options.outWidth/reqWidth;
		int zoomimageHeight=options.outHeight/reqHeight;
		options.inJustDecodeBounds=false;
		//按照比例大的来缩放
		options.inSampleSize=zoomimageWidth>zoomimageHeight?zoomimageWidth:zoomimageHeight;
		
		Bitmap bitmap=BitmapFactory.decodeFile(path,options);
		//将Bitmap 加入内存缓存  
		addBitmapToMemoryCache(path, bitmap);  
		//内存缓存获得图片
		return getBitmapFromMemCache(path);
	}
    /**
     * 加载图片
     */
	private void setImage(Bitmap bitumap,int width,int height) {
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(width, height);
		ImageView iv=new ImageView(getContext());
		iv.setPadding(5,5,5,5);
		iv.setLayoutParams(param);
		iv.setImageBitmap(bitumap);
		iv.setScaleType(ScaleType.FIT_XY);
		//将ImageView 插入到高度最小的LinearLayour中
		LinearLayout view=getLinearLayout(height);
		view.addView(iv);
	}
	/**
	 * 获得当前高度最小LinearLayout
	 * @param 图片高度进行累加
	 * @return
	 * @author 杀死凯 QQ565204031
	 */
	private LinearLayout getLinearLayout(int height) {
        if(firstHeight<=secondHeight)
        {
        	if(first_ll.getHeight()<=third_ll.getHeight())
        	{
        		//first_ll最小
        		firstHeight+=height;
        		return first_ll;
        	}else
        	{
        		//third_ll最小
        		thirdHeight+=height;
        		return third_ll;
        	}
        }else
        {
        	if(secondHeight<=thirdHeight)
        	{
        		//second_ll最小
        		secondHeight+=height;
        		return second_ll;
        	}else
        	{
        		//third_ll最小
        		thirdHeight+=height;
        		return third_ll;
        	}
        }
	}

	private int LastscrollY=0;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			//收到消息，加载更多
			int scrollY=WaterfallScrollView.this.getScrollY();
			if(scrollY==LastscrollY)
			{
				//滑动结束，加载更多
				if(scrollViewHeight+scrollY>=content_ll.getHeight())
				{
					//加载更多
					loadiMages();
				}
			}else
			{
				//还在滑动等待
				LastscrollY=scrollY;
				handler.sendEmptyMessageDelayed(0,5);
			}
		};
	};
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			handler.sendEmptyMessageDelayed(0, 5);
		}
		return false;
	}

}
