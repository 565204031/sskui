package com.ssk.sskui;

import com.nineoldandroids.view.ViewHelper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 微信下拉眼睛出现
 * @author 杀死凯 QQ565204031
 *
 */
public class WeixinslideActivity extends Activity implements OnTouchListener{

	private RelativeLayout chat;
	private float downY;
	private float fingerRoll;
	private float mChatOriginY;
	private ImageView iv_eye;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weixinslide_main);
		chat=(RelativeLayout) findViewById(R.id.rl2);
		chat.setOnTouchListener(this);
		iv_eye=(ImageView) findViewById(R.id.iv_eye);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//滑动高度
		int limit = iv_eye.getHeight() + 100;
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				
				//记录按下位置
				downY=event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				fingerRoll = event.getY() - downY;
				if(fingerRoll>0)
				{
				   //往下滑
					ViewHelper.setTranslationY(chat, fingerRoll*0.8f);
				}
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				if(fingerRoll>0)
				{
					fingerRoll = event.getY() - downY;
					mChatOriginY = chat.getY();
					if(fingerRoll<limit)
					{
						//高度不够回弹
						chat.setY(mChatOriginY-fingerRoll*0.8f);
					}else
					{
						//高度可以，出现眼睛
						chat.setY(chat.getHeight());
					}
				}
				break;
		}
		return true;
	}
}
