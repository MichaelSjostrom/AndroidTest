package com.example.mytodolist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "bandManager";
	private static final String TABLE_BANDS = "bands";
	private static final String TABLE_TIME = "time";
	private static final String TABLE_PLAYLIST = "playlist";
	
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "band_name";
    private static final String KEY_TIME = "time";
    private static final String KEY_IMAGE = "image";
    
    private static final String TABLE_BANDS_TABLE = "CREATE TABLE " + TABLE_BANDS + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
			+ KEY_TIME + " TEXT," + KEY_IMAGE + " BLOB" + ")";
    
    private static final String TABLE_TIME_TABLE = "CREATE TABLE " + TABLE_TIME + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
			+ KEY_TIME + " TEXT" + ")";
    
    private static final String TABLE_PLAYLIST_TABLE = "CREATE TABLE " + TABLE_PLAYLIST + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
			+ KEY_TIME + " TEXT," + KEY_IMAGE + " BLOB" + ")";
    
    
    public DatabaseHandler(Context context){
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db){
    	
    	db.execSQL(TABLE_BANDS_TABLE);
    	
    	db.execSQL(TABLE_TIME_TABLE);
    	
    	db.execSQL(TABLE_PLAYLIST_TABLE);
    	
    	
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    	db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BANDS);
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIME);
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
    	
    	
    	onCreate(db);
    }
    
    public void addBand(Band band, String table){
    	
    	//Log.d("todoLog", "Hejsan" + table);
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	
    	values.put(KEY_ID, band.getID());
    	values.put(KEY_NAME, band.getName());
    	values.put(KEY_TIME, band.getTime());
    	values.put(KEY_IMAGE, band.getImageId());
    	
    	db.insert(table, null, values);
    	Log.d("logTag", "Database....");
    	db.close();
    	
    }
    
    public Band getBand(String id, String table){
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor cursor = db.query(table, new String[]{ KEY_ID, KEY_NAME, KEY_TIME, KEY_IMAGE }, 
    			KEY_NAME + "=?", new String[] { id }, null, null, null, null);
    		
    	if(cursor != null){
    		cursor.moveToFirst();    	
    	}
    	Band band = new Band(cursor.getBlob(1), Integer.parseInt(cursor.getString(0)), cursor.getString(1),
    			cursor.getString(2));
    	
    		cursor.close();
    	
    	return band;
    
    }
    
    public List<Band> getAllBands(String key){
    	List<Band> bandList = new ArrayList<Band>();
    	
    	String selectQuery  = "SELECT * FROM " + TABLE_BANDS + " ORDER BY " + key;
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	//Log.d("todoLog", "Bajs");
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	
    	if(cursor.moveToFirst()){
    		do{
    			Band band = new Band();
    			band.setID(Integer.parseInt(cursor.getString(0)));
    			band.setName(cursor.getString(1));
    			band.setTime(cursor.getString(2));
    			
    			bandList.add(band);
    			
    		}while(cursor.moveToNext());
    	}
    	
    	return bandList;
    
    	
    }
    
    public List<Band> getPlaylist(){
    	List<Band> playList = new ArrayList<Band>();
    	String selectQuery  = "SELECT * FROM " + TABLE_PLAYLIST + " ORDER BY " + KEY_TIME;
    	
    	SQLiteDatabase db = this.getWritableDatabase();

    	
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	
    	if(cursor.moveToFirst()){
    		do{
    			Band band = new Band();
    			band.setID(Integer.parseInt(cursor.getString(0)));
    			band.setName(cursor.getString(1));
    			band.setTime(cursor.getString(2));
    			
    			playList.add(band);
    			
    		}while(cursor.moveToNext());
    	}
    	cursor.close();
    	return playList;
    }
    
    
    public int updateBand(Band band){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	
    	values.put(KEY_NAME, band.getName());
    	values.put(KEY_TIME, band.getTime());
    	
    	return db.update(TABLE_BANDS, values, KEY_ID + "=?",
    			new String[] { String.valueOf(band.getID()) });
    	
    }
    public void deleteAllBands(String table){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(table, null, null);
    	db.close();
    }
    
    public void deleteBand(Band band){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_BANDS, KEY_ID + " = ?",
    			new String[]{ String.valueOf(band.getID()) });
    	db.close();
    }
    
    public void deleteBand(/*int id,*/ String s){
    	SQLiteDatabase db = this.getWritableDatabase();
    	//Log.d("logTag", new String[] {String.valueOf(id)} + "<<<<<<<<borttagen");
    	db.delete(TABLE_PLAYLIST, KEY_NAME + " = ?", new String[] {s});
    	//db.delete(TABLE_PLAYLIST, KEY_ID + " = ?", new String[] {String.valueOf(id)});
    	db.close();
    }
    public int findId(String s){
    	int k = 0;
    	
    	String selectQuery = "SELECT * FROM " + TABLE_PLAYLIST;
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	if(cursor.moveToFirst()){
    		do{
    			k++;
    			//Log.d("logTag", cursor.getString(1) + " - " +  s);
    			if(cursor.getString(1).equalsIgnoreCase(s)){
    				//Log.d("logTag", "lyckades!!!" + k);
    				cursor.close();
    				return k;
    			}
    		}while(cursor.moveToNext());
    	}
    	cursor.close();
    	return k;
    	
    }
    
    public boolean checkIfInTable(String s){
    	boolean k = true;
    	
    	String selectQuery = "SELECT * FROM " + TABLE_PLAYLIST;
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	if(cursor.moveToFirst()){
    		do{
    			
    			Log.d("logTag", cursor.getString(1) + " - " +  s);
    			if(cursor.getString(1).equalsIgnoreCase(s)){
    				Log.d("logTag", "lyckades!!!" + k);
    				cursor.close();
    				k = false;

    				return k;
    			}
    		}while(cursor.moveToNext());
    	}
    	cursor.close();
    	return k;
    	
    }
	
}
