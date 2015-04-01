关注finddreams，一起分享，一起进步：   http://blog.csdn.net/finddreams/article/details/44619589

   今天给大家带来一点干货，就是横向循环滚动的广告条。
   有点类似淘宝的banner广告位,可以手势滑动,也会依据固定时间间隔自动滚动,滑到尽头时会一直循环。过渡非常的平滑。从网络中获取图片，并缓存到SD卡当中，做为没有网络的时候可以显示，提高用户的体验，点击每个广告条会进入相应的url界面，封装的好，使用起来非常的方便。
   下面就让我们来看看运行的效果图，看看是不是你要找的：
   
   ![这里写图片描述](http://img.blog.csdn.net/20150325110434241)

   <p>
   记得之前为了做这样的效果，从网络上找了很多的源码。结果发现有很多的源码的效果要么是滑到尽头到第一个广告时，会从第二，三个广告中穿过，过渡非常的不平滑。要么就是封装的不够好，平滑效果是实现了，但却不能点击，或者说不能从网络上加载图片。
   这两种情况都是不符合我们商业项目开发的。因为广告条会变，所以必须从后台获取图片的url地址，以及对应的广告信息的Url。
   那今天给大家带来的干货就是为了解决这些问题，还你一个完美的广告条的实现。
   我们都知道，广告条的效果一般的做法就是使用ViewPager加上一个定时器Time,TimerTask或者是handler来定时的滚动广告图片，同时控制广告的指示小点点的选中与未选中时显示的状态图片。 
   <p>
   
　　今天分享的广告条的做法有点不一样，但是效果确相比ViewPager更加的有趣。我们使用的是ViewFlow。
   **1.首先自定义一个ViewFlow类：**
因为这个类的代码量比较大，出于篇幅考虑，代码我就不贴了，见最下面的下载链接，下载源码可以自己去研究一下，具体的实现原理。

**2.然后定义一个CircleFlowIndicator类** 
这个类是来控制广告条中小圆点的滚动，从效果图中我们可以看到，三个小圆点的滚动也是非常的平滑的移动过去，让人感觉很流畅，比世面上很多的App中实现的那种广告小圆点的效果也要好很多。
具体代码依然见源码；

**3.接下来我们就在布局文件中开始使用了**
```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.finddreams.adbanner.MainActivity" >

    <FrameLayout
        android:id="@+id/framelayout"
        android:layout_width="fill_parent"
        android:layout_height="300dip"
        android:orientation="vertical" >

        <com.finddreams.bannerview.ViewFlow
            android:id="@+id/viewflow"
            android:layout_width="fill_parent"
            android:layout_height="fill_parent" />

        <FrameLayout
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom"
            android:background="@color/transparent"
            android:gravity="center"
            android:padding="3dip" >

            <com.finddreams.bannerview.CircleFlowIndicator
                android:id="@+id/viewflowindic"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal|bottom"
                android:padding="2dip"
                app:activeColor="#ff0000"
                app:activeType="fill"
                app:circleSeparation="20dip"
                app:inactiveColor="#ffffff"
                app:inactiveType="fill"
                app:radius="4dip" />
        </FrameLayout>
    </FrameLayout>

</RelativeLayout>
```

**4.然后我们就可以在Activity中调用了，具体的代码是：**
```
	private void initView() {
		mViewFlow = (ViewFlow) findViewById(R.id.viewflow);
		mFlowIndicator = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
	}

	private void initBanner(ArrayList<String> imageUrlList) {
		
		mViewFlow.setAdapter(new ImagePagerAdapter(this, imageUrlList,
				linkUrlArray, titleList).setInfiniteLoop(true));
		mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，				
		mViewFlow.setFlowIndicator(mFlowIndicator);
		mViewFlow.setTimeSpan(4500);
		mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
		mViewFlow.startAutoFlowTimer(); // 启动自动播放
	}
}
```
**5.有一个很关键的就是ImagePagerAdapter这个适配器，因为加载网络图片是在这个类里实现的，还有广告条的点击，进入一个Web界面的实现。在这里加载网络图片我们使用了一个很火的开源项目，UniversalImageLoader(异步加载网络图片) ，相信大家也并不陌生了。**
ImagePagerAdapter.class 类：


**6.点击广告条进入一个带进度条的WebView的Activity，这个效果我之前的博客中就有介绍过，详情可见：[仿微信中加载网页时带线行进度条的WebView的实现 ](http://blog.csdn.net/finddreams/article/details/44172)**

到这里就可以实现完美的广告条了，哦，对了因为是加载网络图片，所以加的要加上访问网络权限哦，不然就显示不了网络图片，还有因为用到了UniversalImageLoader把图片离线缓存到SD卡当中，所以也是加权限的。
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
终于实现了循环滚动，平滑过渡的广告条效果，真心让人松了一口气。为了不让初学者继续走前人的崎岖路，快速的进步。本着分享开源的精神，把源码发布出来，供大家学习，记住 分享开源会让你学到更多！



   
   

