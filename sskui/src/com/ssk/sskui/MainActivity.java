package com.ssk.sskui;

import com.ssk.sskui.utils.ViewUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * 主页
 * @author 杀死凯 QQ565204031
 *
 */
public class MainActivity extends Activity implements OnItemClickListener{

	private ListView listview;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//反射findViewById
		ViewUtils.initViews(this);
		listview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                new String[]{
				"网易新闻切换"
				,"QQ侧滑"
				,"QQ空间下拉效果"
				,"下拉刷新，上拉加载(重绘实现)"
				,"微信下拉拍照效果"
				,"百度图片瀑布流,缓存SD卡,LruCache"
				,"GIF动画引擎"
				,"自定义滑竿"
				,"News Digest 翻页效果"
				}));
		listview.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		  switch (position) {
		case 0:
			//网易新闻切换
			intent =new Intent(this, WangYiActivity.class);
			startActivity(intent);
			break;
		case 1:
			//QQ侧滑
			intent =new Intent(this, QQSlidingMenuActivity.class);
			startActivity(intent);
			break;
		case 2:
			//QQ空间下拉
			intent =new Intent(this, QQPullActivity.class);
			startActivity(intent);
			break;
		case 3:
			//下拉刷新，上拉加载
			intent =new Intent(this, RefreshActivity.class);
			startActivity(intent);
			break;
		case 4:
			//微信下拉拍照效果
			intent =new Intent(this, WeixinslideActivity.class);
			startActivity(intent);
			break;
		case 5:
			//百度图片瀑布流,缓存SD卡,LruCache
			intent =new Intent(this, BaiduWaterfallActivity.class);
			startActivity(intent);
			break;
		case 6:
			//GIF动画引擎
			intent =new Intent(this, GifActivity.class);
			startActivity(intent);
			break;
		case 7:
			//自定义滑竿
			intent =new Intent(this, DividingActivity.class);
			startActivity(intent);
			break;
		case 8:
			//News Digest 翻页效果
			intent =new Intent(this, NewsDigestActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
