package com.ssk.sskui.view;

import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class QQSlidingMenu extends HorizontalScrollView{

	private int widthPixels;
	private int mMenuRightOffset;
	private ViewGroup mMenu;
	private ViewGroup mMain;
	private int mMenuWidth;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			int scrollDis = (Integer) msg.obj;
			//平滑的移动
			QQSlidingMenu.this.smoothScrollTo(scrollDis, 0);
		}
	};
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if(changed){
			//假设视图改变
			this.scrollTo(mMenuWidth, 0);
		}
	}
	public QQSlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		//获得屏幕宽高
		WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm=new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		widthPixels=dm.widthPixels;
		mMenuRightOffset=widthPixels/4;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//设置子控件宽高
		LinearLayout wrapper = (LinearLayout) this.getChildAt(0);
		//获取menu
		mMenu = (ViewGroup) wrapper.getChildAt(0);
		//获取main
		mMain = (ViewGroup) wrapper.getChildAt(1);
		//mMenu的宽度为屏幕的3/4
		mMenuWidth=widthPixels-mMenuRightOffset;
		mMenu.getLayoutParams().width=mMenuWidth;
		//mMain的宽度为屏幕的宽度
		mMain.getLayoutParams().width=widthPixels;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(ev.getAction()==MotionEvent.ACTION_UP){
			Message msg=handler.obtainMessage() ;
			//判断滑动的距离，显示mMain或mMenu
			int ScrollX=this.getScrollX();
			int span=mMenuWidth-widthPixels/2;
			//如果移动的距离没有超过mMenu一半，则显示mMenu，否则就显示mMain
			if(ScrollX<span){
				//显示mMenu
				msg.obj=0;
			}else{
				//显示mMain
				msg.obj=mMenuWidth;
			}
			//延迟执行
			handler.sendMessage(msg);
		}
		return super.onTouchEvent(ev);
	}
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		//增量0-1 从右滑到左
		float Record=(float)l/mMenuWidth;
		//Menu效果
		//缩放1-0.7
		float MenuScale=(float) (1.0f-0.3*Record);
		ViewHelper.setScaleX(mMenu, MenuScale);
		ViewHelper.setScaleY(mMenu, MenuScale);
		//透明度 1-0.2
		float Menualpha=(float) (1.0f-0.8*Record);
		ViewHelper.setAlpha(mMenu, Menualpha);
		//确保mMenu不会滚出去
		ViewHelper.setTranslationX(mMenu,(float) (l*0.8));
		
		//Main效果
		//缩放1-0.8
		float mMainScale=(float) (0.8f+0.2f*Record);
		ViewHelper.setScaleY(mMain, mMainScale);
		ViewHelper.setScaleX(mMain, mMainScale);
		super.onScrollChanged(l, t, oldl, oldt);
	}

}
