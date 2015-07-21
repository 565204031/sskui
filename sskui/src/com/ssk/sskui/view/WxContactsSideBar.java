package com.ssk.sskui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 微信通讯录字母导航
 * @author 杀死凯 QQ565204031
 *
 */
public class WxContactsSideBar extends View{

	public String[] letter= {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	private Paint paint=new Paint();
	//中间显示字母TextView
	private TextView tv_content;
	private setWxSideBarListener listener;
	//选中下标
	private int selectindex=-1;
	public WxContactsSideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public void setListener(setWxSideBarListener listener){
		this.listener=listener;
	}
	public void setTextView(TextView tv_content){
		this.tv_content=tv_content;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height=getHeight();
		int width=getWidth();
		//每个字母的宽度
		int single=height/letter.length;
		for(int i=0;i<letter.length;i++){
			//文字颜色
			paint.setColor(Color.BLACK);
			//加粗
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			//抗锯齿
			paint.setAntiAlias(true);
			//字体大小,dp转px
			paint.setTextSize(dp2px(14));
			float textleft= (width-paint.measureText(letter[i]))/2;
			float texttop= single*(i+1);
			
			if(selectindex==i){
				//如果是选中的字母，颜色要不一样
				paint.setColor(Color.RED);
			}
			canvas.drawText(letter[i],textleft, texttop, paint);
			//重置
			paint.reset();
		}
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		float y=event.getY();
		int oldinde=selectindex;
		//选中的下标
		int index=(int) ((y/getHeight())*letter.length);
		switch (event.getAction()) {
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			setBackgroundDrawable(new ColorDrawable(0x00000000));
			tv_content.setVisibility(View.GONE);
			selectindex=-1;
			break;
		default:
			//除了up其他事件执行
			setBackgroundDrawable(new ColorDrawable(0x50000000));
			if(index!=oldinde&&index<=letter.length-1){
				//选中回调
				listener.onTouchChanged(letter[index]);
				tv_content.setText(letter[index]);
				tv_content.setVisibility(View.VISIBLE);
			}
			selectindex=index;
			//重绘
			invalidate();
			break;
		}
		return true;
	}
	/**
	 * dp转px
	 * @param dp
	 * @return
	 */
	private int dp2px(int dp){
		//获得密度
		float scale= this.getContext().getResources().getDisplayMetrics().density;
		//4舍5如，返回像素
		return (int) ((scale*dp)+0.5f);
	}
	/**
	 * 回调接口
	 * @author 杀死凯 QQ 565204031
	 *
	 */
	public interface setWxSideBarListener{
		public void onTouchChanged(String s);
	}
	
}
