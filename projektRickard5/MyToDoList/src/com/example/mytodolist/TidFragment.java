package com.example.mytodolist;

import java.util.ArrayList;
import java.util.List;

import com.example.mytodolist.R;



import Tabs.adapter.TabsPagerAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class TidFragment extends ListFragment{
	
		//Här skrev micke väldigt nyss
	/*************************VISSA BITAR******************************/
		// Hejsan!
	private DatabaseHandler db;
	
	private static final String TABLE_BANDS = "bands";
	private static final String TABLE_PLAYLIST = "playlist";
	final static String TAG_1 = "FRAGMENT_1";
	final static String KEY_MSG_1 = "FRAGMENT1_MSG";
	
	private static final String KEY_TIME = "time";
	private String band_Name;
	int i;
	
	private ListView listView;
    private List<Band> bandItems= new ArrayList<Band>();
    private CustomListViewAdapter adapter;


	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
				
		View rootView = inflater.inflate(R.layout.fragment_tid, container, false);
   	 
   	 listView = (ListView) rootView.findViewById(android.R.id.list);
       
		db = new DatabaseHandler(getActivity());

		List<Band> bands = db.getAllBands(KEY_TIME);
    	bandItems.clear();
	    	for(Band b : bands ){
	    		
	    		Band band = new Band(b.getImageId(), i+1, b.getName(), b.getTime());
	    		bandItems.add(band);

	    	}
 	  
	    	ImageView tv = (ImageView)rootView.findViewById(R.id.target);
	    	  tv.setOnDragListener(DropListener);
	    	  
	    	  adapter = new CustomListViewAdapter(getActivity(),
	                  R.layout.list_item, bandItems);
	          
	          setListAdapter(adapter);
	    	  
	    	  listView.setOnItemLongClickListener(new OnItemLongClickListener() {
	    		  
	    		  public boolean onItemLongClick(AdapterView<?> arg0, View v, int pos, long id){
	    			  
	    			//skapar en TextView kopplat till TextViewn "id:name"
	    			  TextView band = (TextView) v.findViewById(R.id.name);

	    			  i=pos;
	    			  band_Name = band.getText().toString();
	    			  //Toast.makeText(getActivity(), "Text klicked "+ band_Name, Toast.LENGTH_SHORT).show();
	    			  
	    			  View.DragShadowBuilder dragShadow = new DragShadow(v);
	    			  Log.d("logTag", "Kan ej dra");
	    			  ClipData data = ClipData.newPlainText("", "");
	    			  
	    			  ((MainActivity) getActivity()).vibrateNow();
	    			  
	    			  v.startDrag(data, dragShadow, band, 0);
	    			  
	    			  return false;
	    		  }
	    		  
	    	  });
        
        
        return rootView;
    }
	
	@Override 
	public void onListItemClick(ListView l, View v, int position, long id) {
	  //TODO Insert desired behavior here.

		
		
		  Log.d("logTag", "Kort klick fungerar");
		    
		  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			

		  final Dialog dialog = new Dialog(getActivity());
		  
		  //AssetManager am = context.getAssets();
		  //InputStream is = am.open("test.txt");
		  
		  
		  
		  final TextView band_name = (TextView) v.findViewById(R.id.name);
		  dialog.setTitle(band_name.getText());
		  dialog.setContentView(R.layout.dialog_box);
		  dialog.setCancelable(true);
			
		  //set up text
		  
	
          TextView text = (TextView) dialog.findViewById(R.id.TextView01);
          
	        //konverterar bandnamn till ett ord samt små bokstäver
			 String band_info = db.getBand(band_name.getText().toString(), TABLE_BANDS).getName();
			 
			 String band_info_lower = band_info.toLowerCase();
			 
			 String band_info_final = band_info_lower.replaceAll("\\s","");
          
          //hämtar hela texten från strings.xml
          String htmlText = getString(getResources().getIdentifier(band_info_final, "string", "com.example.mytodolist"));
         
          
          text.setText(Html.fromHtml(htmlText));
          
          //statiskt alternativ
          //text.setText(Html.fromHtml(getString(R.string.metallica)));
          
        
          //set up image view    
          String img_name = "alert_dialog_dart_icon_"+band_name.getText();
          
          String img_name_lower = img_name.toLowerCase();
          
          String img_name_final = img_name_lower.replaceAll("\\s", "_");
          
          
          Resources res = getResources();
          int resourceId = res.getIdentifier(img_name_final, "drawable", "com.example.mytodolist" );
          Log.d("logTag", img_name_final);
          ImageView img = (ImageView) dialog.findViewById(R.id.ImageView01);
          
          img.setImageResource(resourceId);
          
          //set up button
          Button button = (Button) dialog.findViewById(R.id.Button01);
          Button buttonAdd = (Button) dialog.findViewById(R.id.button_add);
          button.setOnClickListener(new OnClickListener() {
          @Override
              public void onClick(View v) {
        	  dialog.cancel();
              }
          });
          buttonAdd.setOnClickListener(new OnClickListener(){
        	  @Override
        	  public void onClick(View v){
        		 
					if(db.checkIfInTable(band_name.getText().toString())){
						db.addBand(db.getBand(band_name.getText().toString(), TABLE_BANDS), TABLE_PLAYLIST);
						dialog.cancel();
						Toast.makeText(getActivity(), band_name.getText().toString() + " added", Toast.LENGTH_LONG).show();
						FragmentManager fm = getFragmentManager();
						FragmentTransaction ft = fm.beginTransaction();
						GenreFragment llf = new GenreFragment();
						ft.replace(R.id.genreFragment, llf);
						ft.commit();
					
					}
					else{
						dialog.cancel();
						Toast.makeText(getActivity(), band_name.getText().toString() + " already added!", Toast.LENGTH_LONG).show();
					}
        		  
        	  }
          });
          //now that the dialog is set up, it's time to show it    
          dialog.show();
		
		
	  };
	
	private class DragShadow extends View.DragShadowBuilder
	{
		//Skapar en gråbox
		ColorDrawable greyBox;
		public DragShadow(View view) {
			super(view);
			greyBox = new ColorDrawable(Color.LTGRAY);
		}
		@Override
		public void onDrawShadow(Canvas canvas) {
			super.onDrawShadow(canvas);
			//arena av skuggan
			greyBox.draw(canvas);
			
		}
		@Override
		public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) 
		{
			//sätter upp skuggytan
			View v = getView();
			
			//halverar storleken på objektet som dras
			int height = v.getHeight();
			int width = v.getWidth();
			
			//Ställer in boxen
			greyBox.setBounds(0, 0, width, height);
			
			//Ställer in skuggan
			shadowSize.set(width, height);
			
			shadowTouchPoint.set(width/2, height/2);
			
			
		}
		
	}
	
	OnDragListener DropListener = new OnDragListener()
	{
		
		public boolean onDrag(View v, DragEvent event) {
			
			int dragEvent = event.getAction();
			
			switch(dragEvent)
			{
				case DragEvent.ACTION_DRAG_ENTERED:
					//Log.d("TodoLog", "ENTERED");
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					//Log.d("TodoLog", "EXITED");
					break;
				case DragEvent.ACTION_DROP:
					
					
					
					if(db.checkIfInTable(band_Name)){
						db.addBand(db.getBand(band_Name, TABLE_BANDS), TABLE_PLAYLIST);
						Toast.makeText(getActivity(), band_Name + " added", Toast.LENGTH_LONG).show();
						
						//Restart GenreFragment when update is made
						FragmentManager fm = getFragmentManager();
						FragmentTransaction ft = fm.beginTransaction();
						GenreFragment llf = new GenreFragment();
						ft.replace(R.id.genreFragment, llf);
						ft.commit();
					}
					else{
						Toast.makeText(getActivity(), band_Name + " already added!", Toast.LENGTH_LONG).show();
					}
					
					

					break;
			}
			return true;
		}
		
	};
	
	
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  	  
	  }

}
