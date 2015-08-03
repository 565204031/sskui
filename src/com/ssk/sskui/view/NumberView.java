package com.ssk.sskui.view;


import com.ssk.sskui.R.color;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NumberView extends LinearLayout {

	private TextView tv_minus,tv_plus,tv_number;
	private final int TV_MINUS=1;
	private final int TV_PLUS=2;
	private Context mContext;
	public NumberView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext=context;
		LayoutParams lp=new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1);
		tv_minus=new TextView(mContext);
		tv_minus.setId(TV_MINUS);
		tv_minus.setText("-");
		tv_minus.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
		tv_minus.setTextColor(color.black);
		tv_minus.setGravity(Gravity.CENTER);
		tv_minus.getPaint().setFakeBoldText(true);
		tv_minus.setLayoutParams(lp);
		
		
		tv_plus=new TextView(mContext);
		tv_plus.setId(TV_PLUS);
		tv_plus.setText("+");
		tv_plus.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
		tv_plus.setTextColor(color.black);
		tv_plus.setGravity(Gravity.CENTER);
		tv_plus.getPaint().setFakeBoldText(true);
		tv_plus.setLayoutParams(lp);
		
		
		tv_number=new TextView(mContext);
		tv_number.setText("1");
		tv_number.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
		tv_number.setTextColor(color.black);
		tv_number.setGravity(Gravity.CENTER);
		tv_number.getPaint().setFakeBoldText(true);
		tv_number.setLayoutParams(lp);
		TextClick textclick=new TextClick();
		tv_minus.setOnClickListener(textclick);
		tv_plus.setOnClickListener(textclick);
		tv_number.setOnClickListener(textclick);
		addView(tv_minus,0);
		addView(tv_number,1);
		addView(tv_plus,2);
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		
	}
	public int getnumber(){
		return Integer.parseInt(tv_number.getText().toString());
	}
	private class TextClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			int number=0;
			switch (v.getId()) {
			case TV_MINUS:
				//点击减
				number=Integer.parseInt(tv_number.getText().toString());
				if(number>1)
				{
					
					tv_number.setText(number-1+"");
				}
				break;
			case TV_PLUS:
				//点击加
				number=Integer.parseInt(tv_number.getText().toString());
				if(number<99)
				{
					tv_number.setText(number+1+"");
				}
				break;
			default:
				break;
			}
		}
		
	}

}
