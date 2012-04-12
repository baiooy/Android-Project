package com.walkin.walkin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mobclick.android.MobclickAgent;
import com.walkin.walkin.R;

import com.walkin.bean.GridInfo;
import com.walkin.common.GridAdapter;

import com.walkin.service.BadgesMenuService;

import com.walkin.service.UserMenuService;
import com.walkin.spp.SppaConstant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BadgesGridActivity extends Activity {
//	private MyButton myhome;
	private GridView gridview;
	private List<GridInfo> list;
	private GridAdapter adapter;
//	private	Button Button_ME;
	private Button goback ;
    private MyTask mTask;  
	private TextView TextView_user_name; 
    private TextView TextView_Loading;
	private ProgressBar progressBar_Loading;
	private ArrayList<String> arraylist ; 
	@SuppressWarnings("unused")
	private static Context context;
	protected final static BadgesMenuService badges_mservice = BadgesMenuService.getInstance();
    protected final static UserMenuService user_mservice = UserMenuService.getInstance();
    int data_canshu;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.badgesgrid_act);
		
		context = this;
		badges_mservice.setActivity(this);
		user_mservice.setActivity(this);
		TextView_Loading=(TextView) findViewById(R.id.TextView_Loading);
		progressBar_Loading=(ProgressBar) findViewById(R.id.progressBar_Loading);
//		 Button_ME=(Button) findViewById(R.id.Button_ME);
//		 Button_ME.setText(user_mservice.getInbiBalance());
//	        myhome = new MyButton(this);
//		    Integer[] mHomeState = { R.drawable.mapinfo_redarrow,R.drawable.key_touch, R.drawable.key_touch };
//		    Button_ME.setBackgroundDrawable(myhome.setbg(mHomeState));
//		    Button_ME.setOnClickListener(new Button_MEListener());
		    
		    Bundle bundle = getIntent().getExtras();    
	 		 data_canshu=bundle.getInt("Data");
		    goback= (Button) findViewById(R.id.goback);
			goback.setOnClickListener(new gobackListener()); 
			
			 mTask = new MyTask();  
	         mTask.execute(); 
	         
	         
	        TextView_user_name= (TextView) findViewById(R.id.TextView_user_name);
	        if (data_canshu==0) {
	        	TextView_user_name.setText((String)user_mservice.getList_jobj_item().get(0).get(UserMenuService.response_user_userName)+TextView_user_name.getText());
			}else{
				TextView_user_name.setText("");
			}
			
	        
	        
			
		
		
		
	}
	  private class MyTask extends AsyncTask<String, Integer, String> {  
	         private static final String TAG = "Rock";

			//onPreExecute方法用于在执行后台任务前做一些UI操作  
	         @Override  
	         protected void onPreExecute() {  
	        	 TextView_Loading.setVisibility(View.VISIBLE); 
	        	 progressBar_Loading.setVisibility(View.VISIBLE); 
	             Log.i(TAG, "onPreExecute() called");  
	         }  
	           
	         //doInBackground方法内部执行后台任务,不可在此方法内修改UI  
	         @Override  
	         protected String doInBackground(String... params) {  
	             Log.i(TAG, "doInBackground(Params... params) called"); 
	             String urlcons = null;
	             /*String urlcons = SppaConstant.WALKIN_URL_BADGES
		         +"list?"
		         +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN);*/
	             if (data_canshu==0) {
	            	// http://122.195.135.91:2861/api/badges/users/x/list
                     urlcons = SppaConstant.WALKIN_URL_BADGES
			         +"users/"
			         +user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)
			         +"/list"
			         +"?userId="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_ID)
			         +"&"
			         +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
	             	 
				}else{
	                urlcons = SppaConstant.WALKIN_URL_BADGES
			         +"list?"
			         +UserMenuService.USER_REGISTER_RESPONSE_TOKEN+"="+user_mservice.getValue(UserMenuService.USER_REGISTER_RESPONSE_TOKEN)+"&apikey=BYauu6D9";
				}
	    
//	             ?token=MjkyOjEzMjUwNjcyMDU3Njk6MjNlNjdhYmNjMzc0Y2M0ODI1MDg0NDA3ODQ3YzUzYWU%3D
	             Log.d("Rock", urlcons);
	        	 badges_mservice.setRetrieveUrl(urlcons);
	        	 badges_mservice.retrieveBadgesInfo();
	             return null;  
	         }  
	         
	         //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
	         @Override  
	         protected void onPostExecute(String result) {  
	        	 Log.i(TAG, "onPostExecute(Result result) called");  
	        	 TextView_Loading.setVisibility(View.GONE); 
	        	 progressBar_Loading.setVisibility(View.GONE); 
	        	 
	        	 
	        	 gridview = (GridView) findViewById(R.id.gridview);
	        	 list = new ArrayList<GridInfo>();
	        	 for (int i = 0; i <  badges_mservice.getList_jobj_item().size(); i++) {
	list.add(new GridInfo((String)badges_mservice.getList_jobj_item().get(i).get(BadgesMenuService.JSONObj_item_name),
			(String)badges_mservice.getList_jobj_item().get(i).get(BadgesMenuService.JSONObj_item_imagesURL)));
				}
	     		adapter = new GridAdapter(BadgesGridActivity.this);
	     		adapter.setList(list);
	     		gridview.setAdapter(adapter);
	     		gridview.setOnItemClickListener(new OnItemClickListener() {
	     			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	     					long arg3) {
	     				//System.out.println("click index:" + arg2);
	     				//if(arg2==0){
	     				
	     			Map<String, Object> item = badges_mservice.getList_jobj_item().get(arg2);
	     					Intent intent=new Intent();
	     					intent.setClass(BadgesGridActivity.this, BadgesDetailActivity.class);
	     					Bundle bundle=new  Bundle();
	     					 arraylist = new ArrayList<String>(); 
	     					 arraylist.add((String) item.get(BadgesMenuService.JSONObj_item_description));
	     					 arraylist.add((String) item.get(BadgesMenuService.JSONObj_item_imagesURL));
	     					 arraylist.add((String) item.get(BadgesMenuService.JSONObj_item_name));
	     					 arraylist.add((String) item.get(BadgesMenuService.JSONObj_item_notification));
	     					bundle.putStringArrayList("Data", arraylist);//压入数据  
	     					intent.putExtras(bundle);
	     					startActivityForResult(intent, 0);
	     				//}
	     			}
	     		}

	     		);
	        	 
//	        	 mData = getData(brands_mservice.getList_jobj_item());
//      		 MyAdapter adapter = new MyAdapter(BrandsActivity.this);
//  			 setListAdapter(adapter);
	         }  
	           
	         //onCancelled方法用于在取消执行中的任务时更改UI  
	         @Override  
	         protected void onCancelled() {  
	        	 Log.i(TAG, "onCancelled() called");
	        	 
//	             LinearLayout_inbi_congriation.setVisibility(View.GONE);
	         }  
	     }
	class Button_MEListener implements OnClickListener{
		public void onClick(View v) {
			
			Intent intents = new Intent();
			intents.setClass(BadgesGridActivity.this, BadgesListActivity.class);
			startActivityForResult(intents, 0);

		}
	}
	  class gobackListener implements OnClickListener{
			public void onClick(View v) {
				finish();
			}
		}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			Intent intent = getIntent();
//			Bundle b =intent.getExtras();
//			Log.d("Rock",b.getString("str1")+"00000");
			setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();
            break;
		case 1:
		//	intent = getIntent();
//			Log.d("Rock",b.getString("str1")+"00000");
		//	setResult(1, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
		//	finish();
            break;
		default:
	           break;
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