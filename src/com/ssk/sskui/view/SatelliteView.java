package com.ssk.sskui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * 微信雷达加好友
 * @author 杀死凯 QQ565204031
 *
 */
public class SatelliteView extends View{

	//线条画笔
	private Paint mPaintLine;
	//圆画笔
	private Paint mPaintSector;
	private int height;
	private int width;
	//旋转角度
	private float angle=0;
	private Matrix mMatrix;
	private Handler handler=new Handler();
	private Runnable runnable=new Runnable() {
		
		@Override
		public void run() {
			mMatrix=new Matrix();
			//每次旋转+0.5
			angle+=0.5;
			mMatrix.postRotate(angle, width/2, height/2);
			SatelliteView.this.invalidate();
			handler.postDelayed(runnable,20);
		}
	};
	public SatelliteView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
		height=context.getResources().getDisplayMetrics().heightPixels;
		width=context.getResources().getDisplayMetrics().widthPixels;
		handler.postDelayed(runnable,20);
	}

	/**
	 * 初始化画笔
	 */
	private void initPaint() {
		mPaintLine=new Paint();
		mPaintLine.setColor(Color.parseColor("#A1A1A1"));
		//画笔宽度
		mPaintLine.setStrokeWidth(3);
		//抗锯齿
		mPaintLine.setAntiAlias(true);
		//画笔风格
		mPaintLine.setStyle(Style.STROKE);
		
		mPaintSector=new Paint();
		mPaintSector.setColor(0x9D00ff00);
		//抗锯齿
		mPaintSector.setAntiAlias(true);
		mMatrix=new Matrix();
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		//雷达
		//圆心点X,圆心点Y,半径,画笔
		canvas.drawCircle(width/2, height/2, width/6, mPaintLine);
		//3分之1
		canvas.drawCircle(width/2, height/2, 2*width/6, mPaintLine);
		//2分之1多一点
		canvas.drawCircle(width/2, height/2, 11*width/20, mPaintLine);
		//2分之1多很多
		canvas.drawCircle(width/2, height/2, 16*width/20, mPaintLine);
		
		//渐变的圆
		//透明到不透明灰色
		Shader mShader= new SweepGradient(width/2, height/2, Color.TRANSPARENT, Color.parseColor("#AAAAAAAA"));
		//设置渐变到画笔
		mPaintSector.setShader(mShader);
		
		//不替换原来的矩阵
		canvas.concat(mMatrix);
		
		//绘制圆
		canvas.drawCircle(width/2, height/2, 16*width/20, mPaintSector);
	
		super.onDraw(canvas);
	}
	
	

}
