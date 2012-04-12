package com.walkin.walkin;


import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;
import com.walkin.common.MyButton;
import com.walkin.service.InbiWebViewClient;
import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

public class InbiExchangeActivity extends BgWalkinActivity   {
	/** Called when the activity is first created. */
	
	private Button pre;
	private Button next;
	private Button home;
	private MyButton myhome;
	protected static InbiWebViewClient gkweb;
	private static boolean  m_bIsHomebtn = false;
	public WebView webview;
	public boolean isWebviewBack = false;
    public String start_url="";
    public boolean StartupNoError = true;
    public static Handler mProgressHandler; 
    private ProgressDialog progressDialog; 
    public boolean isFirstRun = true;
	private Button goback;
	private static Context context;
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    
	private static final String TAG = "Rock";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inbiexchangeweb_act);
		context = this;
		user_mservice.setActivity(this);
		
		
		gkweb = new InbiWebViewClient(InbiExchangeActivity.this);
		webview = (WebView) findViewById(R.id.mwebview);
		webview.getSettings().setJavaScriptEnabled(true);//设置是否支持JavaScript 
		webview.getSettings().setSupportZoom(true);    //设置是否支持缩放 
		webview.getSettings().setBuiltInZoomControls(true); //设置是否显示内建缩放工具 
		webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webview.setHorizontalScrollBarEnabled(false);  
		webview.setInitialScale(1);  
		webview.setHorizontalScrollbarOverlay(true);  
		 continueLayout();
		 goback= (Button) findViewById(R.id.goback);
			goback.setOnClickListener(new gobackListener()); 
		 
			 progressDialog = new ProgressDialog(this);
		     progressDialog.setIndeterminate(true);
		     progressDialog.setMessage("请稍候...");
		     progressDialog.setCancelable(true);
		        
			 mProgressHandler = new Handler() {   
		            @Override  
		            public void handleMessage(Message msg) { 
		                switch (msg.what){   
		                case 1 :   //网页跳转等待
		                	if(progressDialog !=null)
		                		try{
		                		progressDialog.show();
		                		}catch(Exception e){
		                		}
		                    break;   
		                case 2 :   
		                	if(progressDialog !=null){
		                		try{
		                			progressDialog.dismiss();
		                		}catch(Exception e){
		                		}
		                	}
		                    break;
		                case 3 :   
		                	AlertDialog.Builder b = new AlertDialog.Builder(InbiExchangeActivity.this);
		        			b.setTitle("抱歉");
		        			b.setMessage("联网错误");
		        			b.setPositiveButton("重试",	new DialogInterface.OnClickListener() {
		        						public void onClick(DialogInterface dialog,int which) {
		        							continueLayout();
		        						}
		        					});
		        			
		        			b.show();
		                    break;
			            case 20://清除缓存
			            	if(webview!=null)
			            		webview.clearCache(true);
			            	break;  
			            case 10:
			            	if(webview !=null){
			            		String url = SppaConstant.WALKIN_URL_INBIWEB+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN);
			            		webview.loadUrl(url);
			            	}
			            	break;
		                }
		                super.handleMessage(msg);
		             } 
		        };   
			
			myhome = new MyButton(this);
	        home = (Button)findViewById(R.id.bt_home);
	        Integer[] mHomeState = { R.drawable.bt_home,R.drawable.fhome, R.drawable.fhome };
	        home.setBackgroundDrawable(myhome.setbg(mHomeState));
	        pre = (Button) findViewById(R.id.bt_arrow_left);
	        Integer[] mPreState = { R.drawable.right,R.drawable.right, R.drawable.cright };
//	        Integer[] mPreState = { R.drawable.bt_arrow_left,R.drawable.bt_arrow_left, R.drawable.bt_arrow_left_p };
	        pre.setBackgroundDrawable(myhome.setbg(mPreState));
	        next = (Button) findViewById(R.id.bt_arrow_right);
	        Integer[] mNextState = { R.drawable.left,R.drawable.left, R.drawable.cleft};
//	        Integer[] mNextState = { R.drawable.bt_arrow_right,R.drawable.bt_arrow_right, R.drawable.bt_arrow_right_p};
	        next.setBackgroundDrawable(myhome.setbg(mNextState));
			// stopServiceButton.setOnClickListener(new
			// stopServiceButtonListener());
	        
	        pre.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	isWebviewBack = true;
	                webview.goBack(); 
	            }
	        });
	        next.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	isWebviewBack = false;
	                webview.goForward();
	            }
	        });
	     
	
	        home.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	String url = SppaConstant.WALKIN_URL_INBIWEB+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN);
	                webview.loadUrl(url);
	                Integer[] mPreState1 = { R.drawable.left,R.drawable.focus, R.drawable.focus };
	                next.setBackgroundDrawable(myhome.setbg(mPreState1));
	                m_bIsHomebtn=true;
	            }
	        });
	}
	class gobackListener implements OnClickListener {
		public void onClick(View v) {
			finish();
		}
	}
	/*创建menu
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		//设置menu界面为res/menu/menu.xml
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	处理菜单事件
	public boolean onOptionsItemSelected(MenuItem item)
	{
		//得到当前选中的MenuItem的ID,
		int item_id = item.getItemId();

		switch (item_id)
		{
			case R.id.about:
				 新建一个Intent对象 
				break;
			case R.id.exit:
				EconomizeDetailsActivity.this.finish();
				break;
		}
		return true;
	}*/
	public Handler btnHandler = new Handler() {
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        if (webview.canGoBack()) {
	            pre.setEnabled(true);
	            Integer[] mPreState = { R.drawable.cright,R.drawable.focus, R.drawable.focus };
	            pre.setBackgroundDrawable(myhome.setbg(mPreState));
	        } else {
	            pre.setEnabled(false);
	            Integer[] mPreState = { R.drawable.right,R.drawable.focus, R.drawable.focus };
	            pre.setBackgroundDrawable(myhome.setbg(mPreState));
	        }
	        if (webview.canGoForward()) {
	            next.setEnabled(true);
	            Integer[] mPreState = { R.drawable.cleft,R.drawable.focus, R.drawable.focus };
	            next.setBackgroundDrawable(myhome.setbg(mPreState));
	            if(m_bIsHomebtn){
	            	m_bIsHomebtn = false;
	            	next.setEnabled(false);
	                Integer[] mPreState1 = { R.drawable.left,R.drawable.focus, R.drawable.focus };
	                next.setBackgroundDrawable(myhome.setbg(mPreState1));
	            }
	        } else {
	        	m_bIsHomebtn = false;
	            next.setEnabled(false);
	            Integer[] mPreState = { R.drawable.left,R.drawable.focus, R.drawable.focus };
	            next.setBackgroundDrawable(myhome.setbg(mPreState));
	        }
	    }
	};    
	public void continueLayout() {
		new Thread(redirect).start();//打开网页
	    
//	    webview.setVerticalScrollBarEnabled(false);
	    webview.setWebViewClient(new InbiWebViewClient(this));
	    webview.setWebChromeClient(new WebChromeClient(){
	        @Override
			public boolean onJsAlert(WebView view, String url, String message,final JsResult result) 
			{
				Builder builder = new Builder(InbiExchangeActivity.this);
				builder.setTitle("提示对话框");
				builder.setMessage(message);
				builder.setPositiveButton(android.R.string.ok,new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						result.confirm();
					}
				});
				builder.setCancelable(false);
				builder.create();
				builder.show();
				return true;
			};
//			boolean isTimer = true;
			 
			@Override
			public void onProgressChanged(WebView view, int newProgress) 
			{
			Log.d("Rock", newProgress+":newProgress");
				if(isFirstRun){
					Log.d("Rock", isFirstRun+":isFirstRun");
//					if(isTimer){
//						isTimer = false;
//						if(mCheckTimerout !=null)
//						{
//							mCheckTimerout.removeCallbacks(startupTask);  //关闭20s延时
//						}
//						mCheckTimerout.postDelayed(startupTask,800);  //20s链接不上提示重试或退出
//						Log.i("Eagle","stopLoading 1");
//					}
//					if(newProgress >=100 )//第一次调用，当网页加载大于70%及显示网页
//					{
//						&& StartupFinished &&StartupNoError
					//	StartupFinished = false;
						StartupNoError = true;
//						if(isStopWcc)
//							return;
				//		mCheckTimerout.removeCallbacks(startupTask);  //关闭20s延时
						isFirstRun = false;
						webViewHandler.sendEmptyMessage(0);//显示浏览器
						if (newProgress>90) {
						      Message msg = Message.obtain(SettingWebActivity.mProgressHandler,2);
				        	msg.sendToTarget();
						}
//						mHandler.sendEmptyMessage(0);//刷新提示信息
					/*	try{
							if(StartupActivity.getStartupHandler() !=null){
				            	Message message = Message.obtain(StartupActivity.getStartupHandler(),0);
				                message.sendToTarget();
				                }
			            	}catch (Exception e){
			           }*/
					}
//				}
				super.onProgressChanged(view, newProgress);
			}
			
			@Override
			public void onReceivedTitle(WebView view, String title) 
			{
				super.onReceivedTitle(view, title);
			}
		});
	}
	    public static Handler getMainProgressHandler(){
	        return mProgressHandler;
	    }
	    /*private Runnable startupTask = new Runnable(){   
	        public void run() { 
	        	if((isFirstRun == false) &&(isStopWcc==true))
	        		return;
	                isStopWcc = true;
	        }   
	    };  */ 
	    private Runnable redirect = new Runnable() {
	        public void run() {
	            try {
	            	start_url =SppaConstant.WALKIN_URL_INBIWEB+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN);
	                webview.loadUrl(start_url);
	                Message msg = Message.obtain(InbiExchangeActivity.mProgressHandler,1);
	        		msg.sendToTarget();
	                Log.d("Rock",start_url+"45456787545");
	            } catch (Exception e) {
	            }
	        }
	    };
	    public final Handler webViewHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	        Log.d("Rock", webViewHandler+":webViewHandler");
	            if(webview !=null){
	                webview.setVisibility(View.VISIBLE);
	                webview.requestFocus();
	                Log.d("Rock", webViewHandler+"2222222");
	            }
	        }
	    };
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				 if (webview.canGoBack()) {
		            	isWebviewBack = true;
		                webview.goBack();
		                return true;
		            } else {
				//AlertDialog.Builder b = new AlertDialog.Builder(this);
				//b.setTitle(R.string.quit_title);
				//b.setMessage(R.string.quit_desc);
				//b.setNegativeButton(R.string.cancel,
				//		new DialogInterface.OnClickListener() {
				//			public void onClick(DialogInterface dialog, int which) {
				//			}
				//		});
				//b.setPositiveButton(R.string.confirm,
				//		new DialogInterface.OnClickListener() {
				//			public void onClick(DialogInterface dialog,
				//					int whichButton) {
								finish();
				//			}
				//		});
				//b.show();
				 return true;
		            }
		        }else {
		            return super.onKeyDown(keyCode, event);
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
}