package com.ssk.sskui;

import com.ssk.sskui.utils.ViewUtils;
import com.ssk.sskui.view.SatelliteView;

import android.app.Activity;
import android.os.Bundle;

/**
 * 微信雷达加好友
 * @author 杀死凯 QQ565204031
 *
 */
public class SatelliteActivity extends Activity{

	private SatelliteView satellite;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.satellite_activity);
		ViewUtils.initViews(this);
	}
}
