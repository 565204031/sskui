package com.ssk.sskui.view;



import com.ssk.sskui.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;
import android.util.Log;

public class QQParallaxScrollListView extends ListView{

	private ImageView mImageView;
	//图片最大可以拉伸高度
	private int mDrawableMaxHeight=-1;
	//图片初始高度
	private int mImageViewHeight=-1;
	//图片默认高度
	private int mDefaultImageViewHeight=-1;
	public QQParallaxScrollListView(Context context) {
		super(context);
		init(context);
	}
	public QQParallaxScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public QQParallaxScrollListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		// TODO Auto-generated method stub
		mDefaultImageViewHeight=context.getResources().getDimensionPixelSize(R.dimen.size_default_height);
	}
	//设置缩放级别
    public void setViewsBounds(double zoom)
    {
    	//设置图片默认高度
    	if(mImageViewHeight==-1)
    	{
    		mImageViewHeight=mImageView.getHeight();
        	if(mImageViewHeight<=0)
        	{
        		mImageViewHeight=mDefaultImageViewHeight;
        	}
    	}
    	//定义ImageView最大可拉伸的高度
    	//drawable高度/ImageView的高度=drawable宽度/ImageView的宽度
    	double ratio=((double)mImageView.getDrawable().getIntrinsicWidth())/((double)mImageView.getHeight());
    	double height=mImageView.getDrawable().getIntrinsicHeight()/ratio;
    	mDrawableMaxHeight=(int) (height*(zoom>1?zoom:1));
    }
    
    //设置图片
    public void setParallaxImageView(ImageView iv)
    {
    	mImageView=iv;
    	mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
    //滑动调用
    @Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    	//上拉图片缩小
    	android.util.Log.i("123213","aaaaaaa");
    	View firstView=(View) mImageView.getParent();
    	//firstView.getTop() 推出去的距离 -
    	if(firstView.getTop()<0&&mImageView.getHeight()>mImageViewHeight)
    	{
    		    //下拉后才能运行
    			mImageView.getLayoutParams().height=Math.max(mImageView.getHeight()+firstView.getTop(), mImageViewHeight);
    			firstView.layout(firstView.getLeft(), 0, firstView.getRight(), firstView.getHeight());
    			mImageView.requestLayout();
    	}
    	super.onScrollChanged(l, t, oldl, oldt);
	}
    //当下拉超出边界的时候回调，传一些参数供开发者处理效果
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
    		int scrollY, int scrollRangeX, int scrollRangeY,
    		int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
    	boolean isCollapse=resizeoverScrollBy(deltaX, deltaY, scrollX,
        		scrollY, scrollRangeX, scrollRangeY,
        		maxOverScrollX, maxOverScrollY, isTouchEvent);
    	
    	//true 已经拉到极限，不能往下拉
    	//false 还能往下拉
    	return isCollapse?true:super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
    			scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }
	private boolean resizeoverScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		if(mImageView.getHeight()<=mDrawableMaxHeight&&isTouchEvent)
		{
			/**
			 *  让图片高度不断增加和减小
			 *  改变ImageView 的高度，然后重新调整大小
			 *  高度不能超过mDrawableMaxHeight
			 */
			
			/**
			 * deltaY 
			 * 超出滑动时候每毫秒滑动的距离
			 * （-代表往下拉：+代表往上拉）
			 * 大小更具用户滑动的快慢决定的，一般的速度-50~50
			 */
			if(deltaY<0)
			{
				//往下拉，增加高度
				mImageView.getLayoutParams().height=mImageView.getHeight()-deltaY;
				//从新调整大小
				mImageView.requestLayout();
			}else
			{
				//图片缩小
				if(mImageView.getHeight()>mImageViewHeight)
				{
					mImageView.getLayoutParams().height=mImageView.getHeight()-deltaY>mImageViewHeight?mImageView.getHeight()-deltaY:mImageViewHeight;
					mImageView.requestLayout();
				}
			}
		}
		return false;
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(ev.getAction()==MotionEvent.ACTION_UP)
		{
			ResetAnimation mResetAnimation=new ResetAnimation(mImageView,mImageViewHeight);
			mResetAnimation.setDuration(300);
			mImageView.startAnimation(mResetAnimation);
		}
		return super.onTouchEvent(ev);
	}
	public class ResetAnimation extends Animation
	{
		private int ivheight;
		private int targetheighr;
        private int extraheight;
        private ImageView iv;
		//恢复原来的图片大小
		public ResetAnimation(ImageView iv,int targetheighr)
		{
			this.iv=iv;
			this.ivheight=iv.getHeight();
			this.targetheighr=targetheighr;
			//高度差
			this.extraheight=this.ivheight-this.targetheighr;
		}
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			Log.i("info", "interpolatedTime="+interpolatedTime);
			iv.getLayoutParams().height=(int) (ivheight-extraheight*interpolatedTime);
			iv.requestLayout();
			super.applyTransformation(interpolatedTime, t);
		}
	}
    
}
