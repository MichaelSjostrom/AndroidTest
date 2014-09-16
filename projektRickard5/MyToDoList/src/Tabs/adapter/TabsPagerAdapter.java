package Tabs.adapter;

import com.example.mytodolist.BandFragment;
import com.example.mytodolist.TidFragment;
import com.example.mytodolist.GenreFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter{
	
	public TabsPagerAdapter(FragmentManager fm){
		super(fm);
	}
	
	@Override 
	public Fragment getItem(int index){
		switch(index){
		case 0:
			return new BandFragment();
		case 1:
			return new TidFragment();
		case 2:
			return new GenreFragment();
		}
		return null;
	}
	@Override
	public int getCount(){
		return 3;
	}
}
