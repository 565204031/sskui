package com.ssk.sskui;

import com.ssk.sskui.view.GifSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

/**
 * GIF动画页面
 * @author 杀死凯 QQ565204031
 *
 */
public class GifActivity extends Activity{

	private FrameLayout fl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gif_main);
		fl=(FrameLayout) findViewById(R.id.fl);
		GifSurfaceView gif=new GifSurfaceView(this);
		fl.addView(gif);
	}
}
