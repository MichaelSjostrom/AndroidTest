package com.example.mytodolist;

public class Band {
	
	byte[] _imageId;
	int _id;
	String _bandName;
	String _time;
	
	public Band(){
		
	}
	
	public Band(byte[] _imageId, int id, String bandName, String time){
		this._imageId = _imageId;
		this._id = id;
		this._bandName = bandName;
		this._time = time;
	}
	
	public Band(String bandName, String time){
		this._bandName = bandName;
		this._time = time;
	}
	
	public byte[] getImageId(){
		
		return _imageId;
	}
	
	public void setImageId(byte[] _imageId){
		this._imageId = _imageId;
	}
	
	public int getID(){
		return this._id;
	}
	
	public void setID(int id){
		this._id = id;
	}
	
	public String getName(){
		return this._bandName;
	}
	
	public void setName(String bandName){
		this._bandName = bandName;
	}
	
	public String getTime(){
		return this._time;
	}
	
	public void setTime(String time){
		this._time = time;
	}
	
}
