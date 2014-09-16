package com.example.mytodolist;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import Tabs.adapter.TabsPagerAdapter;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;





public class MainActivity extends FragmentActivity implements ActionBar.TabListener  {
	
	private ArrayList<String> testData = new ArrayList<String>(); 
	private ArrayList<String> searchData = new ArrayList<String>(); 
	private ArrayAdapter<String> adapter;	
	
	private DatabaseHandler db;
	
	private static final String TABLE_BANDS = "bands";

	
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	
	Vibrator v;
	
	private String[] tabs = {"Band", "Tid", "Min Spellista"};
	
	Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.alert_dialog_dart_icon_green_day);
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , testData);
		return true;
	    
	    
	    /*SearchManager searchManager =
	    		(SearchManager)getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = 
	    		(SearchView)menu.findItem(R.id.search).getActionView();
	    
	    if (null != searchView )
        {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);   
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() 
        {
        	//Samtidigt som man skriver
            public boolean onQueryTextChange(String newText) 
            {
            	//MainActivity.this.adapter.getFilter().filter(newText);
                // this is your adapter that will be filtered
            	//SharedPreferences prefs = getSharedPreferences("my_pref", MODE_PRIVATE);
            	//SharedPreferences.Editor edit = prefs.edit();
            	//edit.putString("MID", newText);
            	//edit.commit();
            	
                
                return true;
            }
            //När man tryckt sök
            public boolean onQueryTextSubmit(String query) 
            {

            	if(testData.contains(query)){
            		Toast.makeText(getApplication(), "Fann den!!", Toast.LENGTH_SHORT).show();
            		searchData.add(query);
            		
            		//Intent intent = new Intent(null, BandFragment.class);
            		//Log.d("logTag", "Avklarat");
            		//startActivity(intent);
            		//Log.d("logTag", "Klarat");
            		finish();
            		startActivity(getIntent());
            		
            		
            		
            	
            	}
            	else{
            		
            			searchData.clear();
            			finish();
            			startActivity(getIntent());
                	
            		
            		Toast.makeText(getApplication(), "SYND!!", Toast.LENGTH_SHORT).show();
            	}
            
            	
            	
                
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
 
*/
        //return super.onCreateOptionsMenu(menu);
//------------------------------------------------------------------------------------
	}
	
	void creatHelpScreen(){
		final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
    	
    	dialog.setContentView(R.layout.helper);
    	        
    	ImageView image = (ImageView) dialog.findViewById(R.id.helpImage);
    	
    	Log.d("TodoLog", "crash");
    	image.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View View3) {
	    	        dialog.cancel();
	    	} 
	    });
    	
    	dialog.show();
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (pref.getBoolean("firstTimeRun", true)){
        	
        	creatHelpScreen();
        	
	    	getSharedPreferences("PREFERENCE", MODE_PRIVATE)
            .edit()
            .putBoolean("firstTimeRun", false)
            .commit();
	    	
        }
        
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        //actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);    
        
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		
		byte imageInByte[] = stream.toByteArray();
	  	
	  	db = new DatabaseHandler(this);
		
		db.deleteAllBands(TABLE_BANDS);
		
		db.addBand(new Band(imageInByte, 1, "Green Day", "13:00"), TABLE_BANDS);
    	db.addBand(new Band(imageInByte,2,"Metallica", "15:00"), TABLE_BANDS);
    	db.addBand(new Band(imageInByte,3,"Justin Bieber", "09:00"), TABLE_BANDS);
    	db.addBand(new Band(imageInByte,4,"Amy Diamond", "11:00"), TABLE_BANDS);
    	db.addBand(new Band(imageInByte,5,"Avicii", "16.00"), TABLE_BANDS);
    	db.addBand(new Band(imageInByte,6,"The Beatles", "17.00"), TABLE_BANDS);
    	db.addBand(new Band(imageInByte,7,"Korn", "18.00"), TABLE_BANDS);
    	db.addBand(new Band(imageInByte,8,"The Hives", "19.00"), TABLE_BANDS);
    	db.addBand(new Band(imageInByte,9,"Black Sabbath", "20.00"), TABLE_BANDS);
    	db.addBand(new Band(imageInByte,10,"Eddie Meduza", "21.00"), TABLE_BANDS);
    	db.addBand(new Band(imageInByte,11,"Rammstein", "22.00"), TABLE_BANDS);
        
        
 
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
 
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
 
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
	
 
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
 
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
       
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
    
    public void vibrateNow(){
    	v.vibrate(200);
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {

		//Log.d("TodoLog", "default"+item.getTitle());
		
		
		
	    switch (item.getItemId()) {
	
	    case R.id.action_pdf:
	    	Log.d("TodoLog", "fuck of and die");
	    	
	    	 File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/lab.pdf");
	    	 Intent target = new Intent(Intent.ACTION_VIEW);
	    	 target.setDataAndType(Uri.fromFile(file),"application/pdf");
	    	 target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

	    	 Intent intent = Intent.createChooser(target, "Open File");
	    	 try {
	    	     startActivity(intent);
	    	 } catch (ActivityNotFoundException e) {
	    		 Log.d("TodoLog", "file not found"); 
	    		 
	    		 //Instruct the user to install a PDF reader here, or something
	    	 } 

	        break;
	    	case R.id.action_help:
	    	
	    	final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
	    	
	    	dialog.setContentView(R.layout.helper);
	    	        
	    	ImageView image = (ImageView) dialog.findViewById(R.id.helpImage);
	    	
	    	Log.d("TodoLog", "crash");
	    	image.setOnClickListener(new View.OnClickListener(){
		    	public void onClick(View View3) {
		    	        dialog.cancel();
		    	} 
		    });
		    
	    	
	    	dialog.show();
	    	
	    	
	        break;

	    default:
	    	Log.d("TodoLog", "default "+android.R.id.home);
	    	
	    	
	    	
	    	
	        return super.onOptionsItemSelected(item);
	    }
	    return false;
	}
    
}




	
	
	
	
    

