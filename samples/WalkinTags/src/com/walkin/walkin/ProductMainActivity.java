package com.walkin.walkin;



import com.walkin.walkin.R;
import com.walkin.common.MyButton;
import com.walkin.service.MarkerMenuService;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class ProductMainActivity extends TabActivity {
    private Button inButton ;
    private MyButton myhome;
    TextView tv;
	TextView tv_nbuy;
	TextView tv_pbuy;
	String	enname;
	private Animation myAnimation_Alpha;
	protected final static MarkerMenuService marker_mservice = MarkerMenuService.getInstance();
	private static Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_main_act);
        context = this;
		marker_mservice.setActivity(this);
        
        DisplayMetrics  dm = new DisplayMetrics();  
	    getWindowManager().getDefaultDisplay().getMetrics(dm);  
	    int screenWidth = dm.widthPixels;  
	    
	    
        
        Intent intent_str = this.getIntent();
		Bundle b =intent_str.getExtras();
		String	markerStr = b.getString("Data");
		int can_int = Integer.parseInt(markerStr) ;
		String	markerID = b.getString("markerID");
			enname = b.getString("name");
        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabSpec;
        Intent intent;
        TabView01 tabView01 = null;//自定义tab
//        intent = new Intent().setClass(this, BrandsActivity.class);
        
		 intent = new Intent(this, HoorSelectionActivity.class);
		Bundle mBundle = new Bundle();  
		mBundle.putString("markerID", markerID);//压入数据  
		intent.putExtras(mBundle);  
        
        tabView01 = new TabView01(this, getString(R.string.tabs_1_tab_1),1);
        tabSpec = tabHost.newTabSpec("today").setIndicator(tabView01).setContent(intent);
        tabHost.addTab(tabSpec);
        TabView02 tabView02 = null;//自定义tab
        
        
        intent = new Intent(this, InStoreActivity.class);
		 mBundle = new Bundle();  
		mBundle.putString("markerID", markerID);//压入数据  
		intent.putExtras(mBundle);  
        tabView02 = new TabView02(this, getString(R.string.tabs_1_tab_2),2);
        tabSpec = tabHost.newTabSpec("commodity").setIndicator(tabView02).setContent(intent);
        tabHost.addTab(tabSpec);
        TabView03 tabView03 = null;//自定义tab
        intent = new Intent(this, InActivity.class);
		 mBundle = new Bundle();  
		mBundle.putString("markerID", markerID);//压入数据  
		intent.putExtras(mBundle);  
        tabView03 = new TabView03(this, getString(R.string.tabs_1_tab_3),3);
        tabSpec = tabHost.newTabSpec("in").setIndicator(tabView03).setContent(intent);
        tabHost.addTab(tabSpec);
        TabView04 tabView04 = null;//自定义tab
        intent = new Intent(this, InStoreGamesActivity.class);
		 mBundle = new Bundle();  
		mBundle.putString("markerID", markerID);//压入数据  
		mBundle.putString("enname", enname);//压入数据  
		intent.putExtras(mBundle);  
        tabView04 = new TabView04(this, getString(R.string.tabs_1_tab_4),4);
        tabSpec = tabHost.newTabSpec("friend").setIndicator(tabView04).setContent(intent);
        tabHost.addTab(tabSpec);
        TabView05 tabView05 = null;//自定义tab
        intent = new Intent(this, SocialActivity.class);
		 mBundle = new Bundle();  
		mBundle.putString("markerID", markerID);//压入数据  
		mBundle.putString("enname", enname);//压入数据  
		intent.putExtras(mBundle);  
        tabView05 = new TabView05(this, getString(R.string.tabs_1_tab_5),5);
        tabSpec = tabHost.newTabSpec("me").setIndicator(tabView05).setContent(intent);
        tabHost.addTab(tabSpec);
        tabHost.setCurrentTab(can_int);
        
        
        
        inButton=(Button) findViewById(R.id.inButton);
        myhome = new MyButton(this);
        Integer[] mHomeState = { R.drawable.inbuttoncentertab,R.drawable.inbuttoncentertab, R.drawable.inbuttoncentertab };
	    inButton.setBackgroundDrawable(myhome.setbg(mHomeState));
	  //  signsButton.setPadding(0,0, 0, 10);/***dimension***/
	    inButton.setWidth(screenWidth*1/3+10);
	    inButton.setOnClickListener(new inButtonListener());
	    
	    if (marker_mservice.getList_jobj_item_nextMarkers().size()==0) {
	    	inButton.setVisibility(View.GONE);
	    }else{
	    	  inButton.setVisibility(View.VISIBLE);
	    }
	    
	    
      //  setCustomTitle(getString(R.string.app_name));
    }
    class inButtonListener implements OnClickListener{
		public void onClick(View v) {
			myAnimation_Alpha = AnimationUtils.loadAnimation(ProductMainActivity.this,R.anim.my_alpha_action);
			inButton.startAnimation(myAnimation_Alpha);
			if (marker_mservice.getList_jobj_item_nextMarkers().size()==0) {
				AlertDialog.Builder b =new AlertDialog.Builder(context);
            	b.setTitle("抱歉");
            	b.setMessage("暂无店内marker");
            	b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                    }
                });
                b.show();
			}else{
			Intent intent = new Intent(ProductMainActivity.this, StringOGLTutorialInStore.class);

			// intent.putExtra("urlstr", WccConstant.SEARCH_URL);
			Bundle mBundle = new Bundle();
			mBundle.putString("name", enname);// 压入数据
			intent.putExtras(mBundle);
			startActivityForResult(intent, 0);
			}
		}
	}
    
    class TabView01 extends LinearLayout{
        TextView tv;
        public TabView01(Context context,String label,int mode) {
            super(context);
           
            tv = new TextView(context);
//          tv.setText("aaaaa");
          tv.setGravity(Gravity.CENTER);
//          tv.setTextColor(Color.BLACK);
          tv.setPadding(0,0, 0, 0);/***dimension***/
//          tv.setBackgroundColor(R.color.beige);
          tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
        		  60, (float) 0.0));
          setGravity(Gravity.CENTER);
          setOrientation(LinearLayout.VERTICAL);
          tv.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tab_bg_selector06));
          addView(tv);
          tv = new TextView(context);
    }
    }
    class TabView02 extends LinearLayout{
        TextView tv;
        public TabView02(Context context,String label,int mode) {
            super(context);
           
            tv = new TextView(context);
//          tv.setText("aaaaa");
          tv.setGravity(Gravity.CENTER);
//          tv.setTextColor(Color.BLACK);
          tv.setPadding(0,0, 0, 0);/***dimension***/
//          tv.setBackgroundColor(R.color.beige);
          tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
         		 60, (float) 0.0));
          setGravity(Gravity.LEFT|Gravity.CENTER);
          setOrientation(LinearLayout.VERTICAL);
          tv.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tab_bg_selector02));
          addView(tv);
          tv = new TextView(context);
    }
    }
    class TabView03 extends LinearLayout{
        TextView tv;
        public TabView03(Context context,String label,int mode) {
            super(context);
           
            tv = new TextView(context);
//          tv.setText("aaaaa");
          tv.setGravity(Gravity.CENTER);
//          tv.setBackgroundColor(Color.TRANSPARENT);
//          tv.setTextColor(Color.WHITE);
          tv.setPadding(0,0, 0, 0);/***dimension***/
          tv.setBackgroundColor(R.color.aquamarine);
          tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
        		  LayoutParams.WRAP_CONTENT, (float) 0.0));
          setGravity(Gravity.CENTER);
          setOrientation(LinearLayout.VERTICAL);
          addView(tv);
          tv = new TextView(context);
   //  setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tab_bg_selector03));
    }
    }
    class TabView04 extends LinearLayout{
        TextView tv;
        public TabView04(Context context,String label,int mode) {
            super(context);
           
            tv = new TextView(context);
//          tv.setText("aaaaa");
          tv.setGravity(Gravity.CENTER);
//          tv.setBackgroundColor(R.color.tomato);
//          tv.setTextColor(Color.WHITE);
          tv.setPadding(0,0, 0, 0);/***dimension***/
          tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
         		 60, (float) 0.0));
          setGravity(Gravity.RIGHT|Gravity.CENTER);
          setOrientation(LinearLayout.VERTICAL);
          tv.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tab_bg_selector04));
          addView(tv);
          tv = new TextView(context);
    }
    }
    class TabView05 extends LinearLayout{
        TextView tv;
        public TabView05(Context context,String label,int mode) {
            super(context);
           
            tv = new TextView(context);
//          tv.setText("aaaaa");
          tv.setGravity(Gravity.CENTER);
//          tv.setTextColor(Color.WHITE);
          tv.setPadding(0,0, 0, 0);/***dimension***/
//          tv.setBackgroundColor(R.color.darkgreen);
          tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
         		 60, (float) 0.0));
          setGravity(Gravity.CENTER);
          setOrientation(LinearLayout.VERTICAL);
          tv.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tab_bg_selector05));
          addView(tv);
          
          tv = new TextView(context);
    }
    }
	protected void onRestart() {
		//if (marker_mservice.getList_jobj_item_deals().size()<2) {
		    	/*if (marker_mservice.getList_jobj_item_nextMarkers().size()==0) {
					inButton.setVisibility(View.GONE); 
				}else{
				//	progressDialog = ProgressDialog.show(StoreHomeActivity.this, "Loading...", "Please wait...", true, true);
					inButton.setVisibility(View.VISIBLE); 
				}*/
		//}
		
        
		// TODO Auto-generated method stub
		super.onRestart();
	}
   /* 
    protected void setCustomTitle(String msg) {
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_titlebar);
        TextView tv = (TextView)getWindow().findViewById(R.id.headerTitleTxtVw);
        tv.setText(msg);
    }*/
}