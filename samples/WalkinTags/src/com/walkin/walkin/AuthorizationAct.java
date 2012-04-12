package com.walkin.walkin;



import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;


import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * 用户授权页面
 * 		1.初始化OAuth对象
 * 		2.获取用户授权页面并填充至webView
 * 		3.根据载入的url判断匹配规则的结果执行跳转
 * 
 * @author bywyu
 *
 */
public class AuthorizationAct extends Activity {
	
	private static Handler exitHandler=null;
    public WebView authorizationView;
	String wuweibo="";
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.authorization_ui);
	    
	    //init
	  //  OAuth  oAuth = OAuth.getInstance();
	  //  oAuth.clear();
		
		Bundle bundle = getIntent().getExtras();    
		wuweibo=bundle.getString("Data");
	    
	    System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret",Weibo.CONSUMER_SECRET);
	    //获取被操作app的key、secret
	  //  String appKey = ConfigUtil.getInstance().getAppKey();
	   // String appSecret = ConfigUtil.getInstance().getAppSecret();
	  //  oAuth.setKeyAndSecret(appKey, appSecret);
	    
	    Weibo weibo = new Weibo();
    	RequestToken requestToken;
		try {
			 //progressDialog = ProgressDialog.show(AuthorizationAct.this, "Loading...", "Please wait...", true, false);
			requestToken =weibo.getOAuthRequestToken("weibo4android://SocialUpdateUserActivity");
			Log.d("Rock", requestToken+":requestToken");
			OAuthConstant.getInstance().setRequestToken(requestToken);
			Uri uri = Uri.parse(requestToken.getAuthenticationURL()+ "&display=mobile");
	   String url = uri+"";
	    Log.d("Rock", "onCreat() [Authoriz] url = "+url);
	   
	    initWebView(url);
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	
	    
	/*    webViewHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
//					progressDialog.dismiss();
					break;
			}
				super.handleMessage(msg);
			}};*/
	    
	      exitHandler = new Handler() {
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case 0:
						finish();
						break;
				}
					super.handleMessage(msg);
				}};

	/*authorizationView.setWebChromeClient(new WebChromeClient(){
					public void onProgressChanged(WebView view, int newProgress) 
					{
						Log.d("Rock", newProgress+"");
						if(isFirstRun){
//							if(isTimer){
//								isTimer = false;
//								if(mCheckTimerout !=null)
//								{
//									mCheckTimerout.removeCallbacks(startupTask);  //关闭20s延时
//								}
//								mCheckTimerout.postDelayed(startupTask,800);  //20s链接不上提示重试或退出
//								Log.i("Eagle","stopLoading 1");
//							}
						
						}
						if(newProgress >=90)//第一次调用，当网页加载大于70%及显示网页
						{
							Message msg= Message.obtain(webViewHandler,0);
			            	 msg.sendToTarget();
						
						}
						super.onProgressChanged(view, newProgress);
					}
					
					@Override
					public void onReceivedTitle(WebView view, String title) 
					{
						super.onReceivedTitle(view, title);
					}
				});*/
    }
	
	private void initWebView(String url) {
	authorizationView = (WebView) findViewById(R.id.authorizationView);
		authorizationView.requestFocusFromTouch(); 
	   // authorizationView.setWebViewClient(new WebViewC()); 
	    authorizationView.clearCache(true);
	    authorizationView.getSettings().setJavaScriptEnabled(true);
	    authorizationView.getSettings().setSupportZoom(true);
	    authorizationView.getSettings().setBuiltInZoomControls(true);
	    authorizationView.setWebViewClient(new WebViewC()); 
	    authorizationView.loadUrl(url);
	    
	    
    }

	
	
	
	
	
	
	
	
	
	class WebViewC extends WebViewClient{
		private int index = 0;
		@Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

			//view.loadUrl(url);
			return true;
        }
		
		
		public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
			 handler.proceed() ;
		 }

		@Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
	        super.onPageStarted(view, url, favicon);
	        Log.d("Rock", "onPageStarted url = "+url );
	        
	 
	        
	        if( url.contains("weibo4android") && index == 0){
	        	index ++;
	        	 Log.d("Rock", "onPageStarted asdf url = "+url );
	        	
            	Intent intent = new Intent(AuthorizationAct.this,SocialUpdateUserActivity.class);
            	Bundle mBundle = new Bundle();  
//				Log.d("Rock", position+"position");
            	mBundle.putString("Activity", "");//压入数据  
            	mBundle.putString("oauth_verifier_url", url);//压入数据  
            	mBundle.putString("Data", wuweibo);//压入数据  
            	intent.putExtras(mBundle);  
            	AuthorizationAct.this.startActivity(intent);
            	AuthorizationAct.this.finish();
           }
        }

		@Override
        public void onPageFinished(WebView view, String url) {
	        // TODO Auto-generated method stub
	        super.onPageFinished(view, url);
        }
	}
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
	public static Handler getExitHandler(){ 
        return exitHandler;
    }
	
	
}
