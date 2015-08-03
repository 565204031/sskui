package com.ssk.sskui.view;

import java.io.IOException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GifSurfaceView extends SurfaceView implements Callback{

	Movie movie;
	private Handler handler=new Handler();
	//管理SurfaceView的东西
	private SurfaceHolder holder;
	//设置控件大小
	private float scale_w,scale_h;
	//持续时间
	private int continuetime;
	public GifSurfaceView(Context context) {
		super(context);
		holder=this.getHolder();
		holder.addCallback(this);
		try {
			movie=Movie.decodeStream(context.getAssets().open("text2.gif"));
			//SD卡图片用这个方法
			//movie=Movie.decodeFile(pathName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 计算高度
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//获取父容器指定的宽高和模式
		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
		int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
		//获取gif图片的宽高
		int gifWidth = movie.width();
		int gifHeight = movie.height();
		int measuredWidth = (modeWidth == MeasureSpec.EXACTLY)?sizeWidth:gifWidth;
		int measuredHeight = (modeHeight == MeasureSpec.EXACTLY)?sizeHeight:gifHeight;
		scale_h = (float)measuredHeight/gifHeight;
		scale_w = (float)measuredWidth/gifWidth;
		setMeasuredDimension(measuredWidth, measuredHeight);
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	//循环不断的重绘画面
	private Runnable run=new Runnable() {
		
		@Override
		public void run() {
			draw();
			//先移除，再添加消息队列
			handler.removeCallbacks(run);
		    handler.postDelayed(run,20);
		}
	};

	/**
	 * 绘制GIF
	 * @author 杀死凯 QQ565204031
	 */
	private void draw() {
		//从管理者获得画布
       Canvas canvas=holder.lockCanvas();
       //同步锁，只能一个人画
       synchronized(canvas)
       {
    	   if(canvas!=null)
    	   {
    		   canvas.save();
    		   canvas.scale(scale_w,scale_h);
    		   //画
    		   movie.draw(canvas, 0, 0);
    		   canvas.restore();
    		   //解锁
        	   holder.unlockCanvasAndPost(canvas);
    	   }
    	   //设置gif的时间点
    	    continuetime=(int) (System.currentTimeMillis()%movie.duration());
    	    movie.setTime(continuetime);	
			Log.i("INFO",continuetime+"/"+movie.duration());
       }
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//holder 回调,创建成功
		handler.post(run);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//holder 回调,改变
		
	}
	//holder 回调,销毁
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//holder 回调,销毁
		handler.removeCallbacks(run);
	}

}
