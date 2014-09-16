package com.example.mytodolist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.mytodolist.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
 
public class GenreFragment extends ListFragment {
	
	private DatabaseHandler db;
	

	
	private String band_Name;
	
	private View rootView;
	
	private TextView band_name;
	private TextView textView;
	int i;
	int k;
	
	final int images = R.drawable.ic_launcher;
    
    //private ListView listView;
    private List<Band> bandItems= new ArrayList<Band>();
    private CustomListViewAdapter adapter;
    private String table_playlist = "playlist";
    
   // View view;
    
    
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	

    	
    	db = new DatabaseHandler(getActivity());
    	//db.deleteAllBands(table_playlist);
    	//view = inflater.inflate(R.layout.fragment_genre, null);
    	
    	List<Band> playList2 = db.getPlaylist();
    		
            //listView = (ListView) view.findViewById(android.R.id.list);
            
            i=0;
            bandItems.clear();
    	for (Band b : playList2) {
            Band band = new Band(b.getImageId(), i+1, b.getName(), b.getTime());
            bandItems.add(band);
            //Log.d("logTag", "Band: " + band.getName());
            i++;
        }
    	
    	adapter = new CustomListViewAdapter(getActivity(),
                R.layout.list_item, bandItems);
        
        setListAdapter(adapter);
        
        rootView = inflater.inflate(R.layout.fragment_genre, container, false);  
        
        Button button1 = (Button)rootView.findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		
        		final Dialog dialog = new Dialog(getActivity());
        		
        		dialog.setTitle("Delete playlist?");
        		dialog.setCancelable(true);
        		dialog.setContentView(R.layout.dialog_box2);
        		
        		Button button1 = (Button)dialog.findViewById(R.id.Button1);
        		Button button2 = (Button)dialog.findViewById(R.id.Button2);
  	          	button1.setOnClickListener(new OnClickListener() {
	        	  @Override
	              public void onClick(View v) {
		        	  db = new DatabaseHandler(getActivity());
		        	  db.deleteAllBands(table_playlist);
		        	  
		        	  bandItems.clear();

			          	List<Band> playList2 = db.getPlaylist();
			          	i=0;
			            bandItems.clear();
			    	for (Band b : playList2) {
			            Band band = new Band(b.getImageId(), i+1, b.getName(), b.getTime());
			            bandItems.add(band);
			            //Log.d("logTag", "Band: " + band.getName());
			            i++;
			        }
			        	
			        	adapter = new CustomListViewAdapter(getActivity(),
			                    R.layout.list_item, bandItems);
			            
			            setListAdapter(adapter);
		        	 
		        	  dialog.cancel();
		        	}

		        	  
	 
	          });
  	          	button2.setOnClickListener(new OnClickListener(){
  	          		@Override
  	          		public void onClick(View v){
  	          			dialog.cancel();
  	          		}
  	          	});
  	          dialog.show();
        		
        		
        		
        	}
        });

        return rootView;
    }
    
    
    @Override 
	public void onListItemClick(ListView l, View v, int position, long id) {
    	//TODO Insert desired behavior here.
    	i = position;
    	   	
    	TextView v1 = (TextView)v.findViewById(R.id.name);
		
		final String band_Name = v1.getText().toString();
		final String band_Name2 = v1.getText().toString();
		//Log.d("logTag", v1.getText().toString());
			
		  final Dialog dialog = new Dialog(getActivity());	  
		  
		  //remove title from dialog
		  dialog.getWindow();
		  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  
		  //Set contents for dialog
		  dialog.setContentView(R.layout.dialog_delete_from_playlist);
		  dialog.setCancelable(true);
		  
		  textView = (TextView) dialog.findViewById(R.id.are_you_sure);
		  SpannableString spanString = new SpannableString("Are you sure you want to delete " + band_Name2 + " from your playlist");
		  //spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
		  spanString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 31, 32 + band_Name2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		  //spanString.setSpan(new StyleSpan(Typeface.ITALIC), 31, 32 + band_Name2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		  
		  //Log.d("logTag", spanString);
		  textView.setText(spanString);
		  
		  
		  
		  
		  

          //set up button
          Button delete = (Button) dialog.findViewById(R.id.delete);
          delete.setOnClickListener(new OnClickListener() {
        	  @Override
              public void onClick(View v) {

	        	  db = new DatabaseHandler(getActivity());
	        	  k = 0;
	        	  
	        	  db.deleteBand(band_Name);
	        	  //db.deleteAllBands(table_playlist);
	        	  
	        	  bandItems.clear();
		          	List<Band> playList2 = db.getPlaylist();
		          	i=0;
		        	for(Band b : playList2 ){
		        		
		        		Band band = new Band(b.getImageId(), i+1, b.getName(), b.getTime());
		                bandItems.add(band);

		                i++;

		        	}
		        	
		    		adapter = new CustomListViewAdapter(getActivity(),
		                    R.layout.list_item, bandItems);
		    		setListAdapter(adapter);

	        	  dialog.cancel();
	        	  
              }
          });
          
          Button cancel = (Button) dialog.findViewById(R.id.cancel);
          cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				dialog.cancel();
				
			}
        	  
          });
          
          //now that the dialog is set up, it's time to show it    
          dialog.show();
		
		
	  };
	  
	  @Override
		 public void onCreate(Bundle savedInstanceState) {
		  
		  
		  //getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
		  super.onCreate(savedInstanceState);
		  
	  }
	  
    

}
