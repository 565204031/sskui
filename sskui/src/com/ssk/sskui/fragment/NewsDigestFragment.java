package com.ssk.sskui.fragment;

import com.nineoldandroids.view.ViewHelper;
import com.ssk.sskui.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDigestFragment extends Fragment{

	private int index;
	private View view;
	private ImageView top_img;
	private int widthPixels;
	private int heightPixels;
	private TextView tv_content,tv_number;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle=getArguments();
		index=bundle.getInt("index", 0);
		view=inflater.inflate(R.layout.newsdugest_fragment, null);
		top_img=(ImageView) view.findViewById(R.id.top_img);
		
		tv_number=	(TextView) view.findViewById(R.id.tv_number);
		tv_content=	(TextView) view.findViewById(R.id.tv_content);
		tv_number.setText(index+"");
		//获得屏幕宽高
		WindowManager wm=(WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm=new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		widthPixels=dm.widthPixels;
		heightPixels=dm.heightPixels;
		
		return view;
	}
	/**
	 * 当前页面效果
	 */
	public void Scroll(int position,float positionOffset)
	{
		//向右移动
	    ViewHelper.setTranslationX(top_img,positionOffset/2);
	}
	/**
	 * 下个页面效果
	 */
	public void ScrollNext(int position,float positionOffset)
	{
		//向左移动
		if(position==index-1)
		{
			//必须为前一个page 才能执行
			ViewHelper.setTranslationX(top_img,((positionOffset-widthPixels)/2));
		}
	}
}
