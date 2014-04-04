package com.CS429.newtodorpg;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

abstract class BaseActivity extends FragmentActivity {
	Intent intent;
	ActionBar actionbar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionbar = getActionBar();
		if(actionbar != null)
			setActionbar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.baseaction, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		
		case android.R.id.home:
//			NavUtils.navigateUpFromSameTask(this);
			Intent homeintent = new Intent(BaseActivity.this, MainActivity.class);
			startActivity(homeintent);
			finish();
			return true;
		
		case R.id.character_status:
			intent = new Intent(BaseActivity.this, CharacterActivity.class);
			startActivity(intent);
			finish();
			return true;
			
		case R.id.inventory:
			intent = new Intent(BaseActivity.this, InventoryActivity.class);
			startActivity(intent);
			finish();
			return true;
			
		case R.id.quests:
			PopupMenu popup = new PopupMenu(BaseActivity.this, (View)findViewById(R.id.quests));
			popup.getMenuInflater().inflate(R.menu.quest_menu, popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
	             public boolean onMenuItemClick(MenuItem item) {  
//	            	 Toast.makeText(getApplicationContext(), "you clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();
	            	 if(item.getTitle().equals("Vice List")){
	            		 Intent intent = new Intent(BaseActivity.this, ViceActivity.class);
	            		 startActivity(intent);
	            		 finish();
	            	 }
	            	 else if(item.getTitle().equals("Daily List")){
	            		 Intent intent = new Intent(BaseActivity.this, DailyActivity.class);
	            		 startActivity(intent);
	            		 finish();
	            	 }
	            	 else if(item.getTitle().equals("ToDo List")){
	            		 Intent intent = new Intent(BaseActivity.this, ToDoActivity.class);
	            		 startActivity(intent);
	            		 finish();
	            	 }
	                 return true;
	                }  
	               });  
			popup.show();
			return true;
			
		case R.id.rewards:
			intent = new Intent(BaseActivity.this, RewardsActivity.class);
			startActivity(intent);
			finish();
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void setActionbar(){
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);
		
		/*
		 * 	getActionBar().setIcon(R.drawable.ic_home);
		 *	getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_actionbar));
		 */
/*		
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab tab = actionbar.newTab()
							.setText("Vice")
							.setTabListener(mListener);
		actionbar.addTab(tab);
		
		tab = actionbar.newTab()
						.setText("Daily")
						.setTabListener(mListener);
		actionbar.addTab(tab);
		
		tab = actionbar.newTab()
						.setText("ToDoList")
						.setTabListener(mListener);
		actionbar.addTab(tab);
*/
//		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);		
//		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.quest_list,
//		          android.R.layout.simple_spinner_dropdown_item);
//		actionbar.setListNavigationCallbacks(mSpinnerAdapter, navigationListener);
		
	}
/*	
	protected void setHeader(int resId) {
		findViewById(R.id.character_activity).setOnClickListener(ImageViewClick);
		findViewById(R.id.vice_activity).setOnClickListener(ImageViewClick);
		findViewById(R.id.daily_activity).setOnClickListener(ImageViewClick);
		findViewById(R.id.todo_activity).setOnClickListener(ImageViewClick);
		findViewById(R.id.inventory_activity).setOnClickListener(ImageViewClick);
		findViewById(R.id.rewards_activity).setOnClickListener(ImageViewClick);
	}
*/	
	/** Header Handler */
/*	ImageView.OnClickListener ImageViewClick = new ImageView.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch(view.getId()) {
				case R.id.character_activity :
					intent = new Intent(BaseActivity.this, CharacterActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.vice_activity :
					intent = new Intent(BaseActivity.this, ViceActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.todo_activity :
					intent = new Intent(BaseActivity.this, ToDoActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.daily_activity :
					intent = new Intent(BaseActivity.this, DailyActivity.class);
					startActivity(intent);
					finish();
					break;	
				case R.id.inventory_activity :
					intent = new Intent(BaseActivity.this, InventoryActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.rewards_activity :
					intent = new Intent(BaseActivity.this, RewardsActivity.class);
					startActivity(intent);
					finish();
					break;
					
			}
		}
		
	};
*/	
/*	
	TabListener mListener = new TabListener(){
		private Fragment mFragment;
		
		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {

		}

		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
			if(mFragment == null){
				mFragment = Fragment.instantiate(BaseActivity.this, ViceActivity.class.getName());
				arg1.add(mFragment, arg0.toString());
			}
			else
				arg1.attach(mFragment);
			
			Log.d("[Tab]", arg0.getText() + " is selected");
		}

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
			
		}
	};
*/	
	
	ActionBar.OnNavigationListener navigationListener = new OnNavigationListener() {
	    @Override
	    public boolean onNavigationItemSelected(final int itemPosition,long itemId) {
	            switch(itemPosition){
	            
	            case 1:
	            	Log.d("DropDown", "number one");
	            	return true;
	            	
	            case 2:
	            	Log.d("DropDown", "number two");
	            	return true;
	            	
	            case 3:
	            	Log.d("DropDown", "number three");
	            	return true;
	            	
	            default:
	            	return true;
	            }
	    }
	};

	
}