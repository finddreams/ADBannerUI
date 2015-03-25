package com.finddreams.adbanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;



/**
 * @Description:WebView界面，带自定义进度条显示
 * @author http://blog.csdn.net/finddreams
 */ 
public class BaseWebActivity extends Activity {

	protected ProgressWebView mWebView;
	private ProgressBar web_progressbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baseweb);

		mWebView = (ProgressWebView) findViewById(R.id.baseweb_webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		initData();
	}

	protected void initData() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String url = bundle.getString("url");

		// if(!TextUtils.isEmpty(url)&&TextUtils.isEmpty(title)){
		mWebView.loadUrl(url);

		// }

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mWebView = null;

	}

}
