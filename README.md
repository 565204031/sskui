# sskui 集合各种自定义控件，各种帮助类，持续更新。。。。

[App百度网盘下载地址](http://pan.baidu.com/s/1dDrq0G5)<br /> 
[http://pan.baidu.com/s/1qWuXPZA](http://pan.baidu.com/s/1dDrq0G5)<br /> 
### 网易新闻切换效果 WangYiActivity<br />

### QQ侧滑效果 QQSlidingMenuActivity<br />

### QQ空间下拉效果 QQPullActivity<br />

### 下拉刷新，上拉加载 RefreshActivity<br />

### 微信下拉拍照效果 WeixinslideActivity<br />

### 百度图片瀑布流（BaiduWaterfallActivity）<br />

为了方便大家copy我把加载图片代码写到WaterfallScrollView里面了。<br />
如果用得不爽，可以用我最近封装的LruCacheUtils<br />
使用前必须初始化一次 LruCacheUtils.initLruCache()<br />
LruCacheUtils.display(imageView,图片路径) 这个是显示原图<br />
LruCacheUtils.display(imageView,图片路径，图片宽度，图片高度)这个是缩放的图片。注意加载到SD卡的都是原图
<br />

### GIF动画引擎 GifSurfaceView <br />

### 自定义滑竿 DividingActivity <br />

    	<!-- 默认价钱 -->
        <attr name="upprice" format="integer"/>
         <attr name="downprice" format="integer"/>
         <!-- 区间最高价格 -->
          <attr name="firstprice" format="integer"/>
          <attr name="secondprice" format="integer"/>
          <attr name="thirdprice" format="integer"/>
          <attr name="fourthprice" format="integer"/>
          <attr name="fifthprice" format="integer"/>
          <!-- 区间刻度-->
          <attr name="firstscale" format="integer"/>
          <attr name="secondscale" format="integer"/>
          <attr name="thirdscale" format="integer"/>
          <attr name="fourthscale" format="integer"/>

### 雅虎News Digest 翻页效果 NewsDigestActivity<br />

### 微信通讯录字母导航效果 WxContactsActivity<br />


### LruCacheUtils 内存缓存，图片缩放，图片加载，图片缓存SD卡。

### ViewUtils 反射实现findViewById 工作效率提高了很多。

		
    