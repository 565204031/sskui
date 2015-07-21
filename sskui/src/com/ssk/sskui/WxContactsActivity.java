package com.ssk.sskui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ssk.sskui.utils.CharacterParser;
import com.ssk.sskui.utils.ViewUtils;
import com.ssk.sskui.view.WxContactsSideBar;
import com.ssk.sskui.view.WxContactsSideBar.setWxSideBarListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 微信通讯录
 * @author 杀死凯 QQ565204031
 *
 */
public class WxContactsActivity extends Activity{

	private WxContactsSideBar sidebar;
	private TextView tv_content;
	private ListView listview;
	private CharacterParser characterparser;
	private List<WxContactModel> contactsdata;
	private String[] date={"周杰伦","姚贝娜","辛晓琪","徐小凤","巫启贤","Usher","陶晶莹","陶喆","宋祖英","宋飞","容祖儿","七公主","齐秦","朴树","朴智妍","欧豪","南拳妈妈","那英","莫文蔚","毛阿敏","李小璐","李娜","可米小子","筷子兄弟","贾玲","金莎","INFINITE","韩庚","何洁","郭德纲","龚琳娜","飞儿乐团","飞轮海","二胡","EXO","大张伟","侧田","贝多芬","蔡卓妍","啊里巴巴","毕加索","仓木麻衣","丁当","奥特曼","棒棒堂","阿里妈妈"};
	private Adapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wxcontacts_activity);
		ViewUtils.initViews(this);
		sidebar.setTextView(tv_content);
		sidebar.setListener(new setWxSideBarListener() {
			@Override
			public void onTouchChanged(String s) {
				listview.setSelection(adapter.getSelect(s));
			}
		});
		
		contactsdata=InitData(date);
		//list排序
		Collections.sort(contactsdata, new PinYinComparator());
		adapter=new Adapter(WxContactsActivity.this,contactsdata);
		listview.setAdapter(adapter);
	}
	public List<WxContactModel> InitData(String[] date){
		List<WxContactModel> listdata=new ArrayList<WxContactModel>();
		characterparser=CharacterParser.getInstance();
		for(int i=0;i<date.length;i++){
			WxContactModel model=new WxContactModel();
			String pinyin=characterparser.getSelling(date[i]);
			model.setSpell(pinyin.substring(0,1).toUpperCase());
			model.setName(date[i]);
			listdata.add(model);
			Log.i("INFO",pinyin);
		}
		return listdata;
	}
	private class Adapter extends BaseAdapter{
		private List<WxContactModel> contactsdata;
		private Context mContext;
		public Adapter(Context mContext,List<WxContactModel> data){
			this.contactsdata=data;
			this.mContext=mContext;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return contactsdata.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewholder;
			if(convertView==null){
				viewholder=new ViewHolder();
				convertView=LayoutInflater.from(mContext).inflate(R.layout.wxcontacts_itme, null);
				viewholder.tv_spell=(TextView) convertView.findViewById(R.id.tv_spell);
				viewholder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
				convertView.setTag(viewholder);
			}else{
				viewholder=(ViewHolder) convertView.getTag();
			}
			WxContactModel info=this.contactsdata.get(position);
			//获得首字母组的最前的下标
			int groupindex=getSelect(info.spell);
			
			if(position==groupindex){
				viewholder.tv_spell.setVisibility(View.VISIBLE);
			}else{
				viewholder.tv_spell.setVisibility(View.GONE);
			}
			viewholder.tv_name.setText(info.getName());
			viewholder.tv_spell.setText(info.getSpell());
			return convertView;
		}
		public class ViewHolder{
			private TextView tv_spell;
			private TextView tv_name;
		}
		public int getSelect(String pinyin){
			for(int i=0;i<getCount();i++){
				String frstpinyin=contactsdata.get(i).getSpell();
				if(frstpinyin.equals(pinyin)){
				//首个A组的或B组。。。。。
				return i;
				}
			}
			return -1;
		}
	}
	private class PinYinComparator implements Comparator<WxContactModel>{

		@Override
		public int compare(WxContactModel info1, WxContactModel info2) {
			return info1.getSpell().compareTo(info2.getSpell());
		}
	}
	//实体类
	public static class WxContactModel{
		private String spell;
		private String name;
		public String getSpell() {
			return spell;
		}
		public void setSpell(String spell) {
			this.spell = spell;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
}
