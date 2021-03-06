package com.ssk.sskui.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.ssk.sskui.R;
import android.view.View;

public class ViewUtils {

	public static void initViews(Object activity)
	{
		initViews(activity,null);
	}
	public static void initViews(Object activity,Object view){
		//类模板
		Class<?> cls=activity.getClass();
		//获得字段getDeclaredFields包括public、private和proteced
		//getFields只能是public
		Field[] fields=cls.getDeclaredFields();
		//获得方法
		//cla.getMethods();
		for(Field f:fields)
		{
			//获得字段类型
			Class<?> ctype=f.getType();
			//继承View的字段
			if(View.class.isAssignableFrom(ctype))
			{
				//强制获得私有字段
				f.setAccessible(true);
				try {
					//拿到调用方法
					Method findviewbyid;
					if(view!=null){
						Class<?> clsview=view.getClass();
						findviewbyid=clsview.getMethod("findViewById",new Class[]{int.class});
					}else{
						findviewbyid=cls.getMethod("findViewById",new Class[]{int.class});
					}
					Class<?> idclass=R.id.class;
					
					Field fid=idclass.getDeclaredField(f.getName());
					//静态方法，不需要传对象，所以随便搞一个
					Object obj=fid.get(fid);
					//invoke(对象，参数) 参数在R里面,所以要反射R
					Object value;
					if(view!=null){
						value=findviewbyid.invoke(view,new Object[]{obj});
					}else{
						value=findviewbyid.invoke(activity,new Object[]{obj});
					}
					//对象，返回值
					f.set(activity, value);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
