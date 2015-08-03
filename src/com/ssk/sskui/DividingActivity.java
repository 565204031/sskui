package com.ssk.sskui;

import com.ssk.sskui.utils.ViewUtils;

import android.app.Activity;
import android.os.Bundle;

/**
 * 自定义滑竿页面
 * @author 杀死凯 QQ565204031
 *
 */
public class DividingActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dividing_activity);
		ViewUtils.initViews(this);
	}
}
