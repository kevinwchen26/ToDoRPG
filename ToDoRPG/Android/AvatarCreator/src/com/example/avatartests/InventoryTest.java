package com.example.avatartests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.avatarcreator.Avatar;
import com.example.avatarcreator.Helmet;
import com.example.avatarcreator.Inventory;
import com.example.avatarcreator.InventoryActivity;
import com.example.avatarcreator.R;
import com.example.avatarcreator.UserInfo;

public class InventoryTest extends ActivityInstrumentationTestCase2<InventoryActivity> {
	// Variables
	private Activity mActivity;
	private ListView lv;
	private GridView gridview;
	private ImageView iv;
	
	
	public InventoryTest() {
		super(InventoryActivity.class);
		
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		// Create inventory
        Helmet helmet = new Helmet(1, "light helmet", R.drawable.light_helmet);
        Inventory inventory = new Inventory();
        inventory.initHelmet(helmet);
        // Populate inventory
        inventory.add(new Helmet(1, "wiz hat", R.drawable.wiz_hat));
        inventory.add(new Helmet(1, "light helmet", R.drawable.light_helmet));
        inventory.add(new Helmet(1, "wiz hat", R.drawable.wiz_hat));
        inventory.add(new Helmet(1, "light helmet", R.drawable.light_helmet));
        inventory.add(new Helmet(1, "wiz hat", R.drawable.wiz_hat));
        inventory.add(new Helmet(1, "light helmet", R.drawable.light_helmet));
        
        UserInfo.avatar = new Avatar(R.drawable.dark, inventory);
        
        // Get Activity metadata
		mActivity = this.getActivity();
		
		lv = (ListView)mActivity.findViewById(R.id.equipment_list);
		gridview = (GridView)mActivity.findViewById(R.id.inventory_grid_view);
		iv = (ImageView)mActivity.findViewById(R.id.character_image);
		
		
		
	}
	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@SuppressLint("NewApi")
	public void testPreConditions(){
	    assertTrue(lv != null);
	    assertEquals(lv.getCount(), 1);
	    assertEquals(gridview.getCount(), 6);
	    assertTrue(iv.getDrawable() != null);
	}
	
	
	 @Test
     public void testPopUpMenu() {
             ListAdapter adapter = gridview.getAdapter();
             getInstrumentation().runOnMainSync(new Runnable(){
                     @Override
                     public void run(){
                             gridview.requestFocus();
                             gridview.setSelection(0);
                     }
             });
//             gridview.setSelection(0);
             ImageView sv = (ImageView)gridview.getSelectedView();
             assertTrue(sv.getDrawable() != null);
             getInstrumentation().runOnMainSync(new Runnable(){
                 @Override
                 public void run(){
                         gridview.requestFocus();
                         gridview.setSelection(5);
                 }
             });
             assertTrue(sv.getDrawable() != null);
     }

}
