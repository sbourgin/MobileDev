package model;

import interfaces.Displayable;

import java.util.LinkedList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SizeLimitedAdapter<E extends Displayable> extends BaseAdapter { //TODO Mettre le nom et autre générique

	private LayoutInflater _inflater;
	private int MAX_SIZE;
	private LinkedList<E> citiesList = new LinkedList<E>();
	
	public SizeLimitedAdapter(int parMax_size) {
		MAX_SIZE=parMax_size;
	}
	
	@Override
	public int getCount() {
		return citiesList.size();
	}

	@Override
	public Object getItem(int parPosition) {
		return citiesList.get(parPosition);
	}

	@Override
	public long getItemId(int parPosition) {
		//return citiesList.get(parPosition).get_id();
		return 0; //TODO
	}

	@Override
	public View getView(int parPosition, View parConvertView, ViewGroup parParent) {
		 if( parConvertView == null ){
		        //We must create a View:
			 parConvertView = _inflater.inflate(android.R.layout.simple_list_item_1, parParent, false);
		    }
		    //Here we can do changes to the convertView, such as set a text on a TextView 
		    //or an image on an ImageView.
		    return parConvertView;
	}
	
	public void addLast(E parObject) {
		citiesList.addLast(parObject);
		if(getCount() > MAX_SIZE) {
			citiesList.removeFirst();
		}
		
	}
	
	public void addFirst(E parObject) {
		citiesList.addFirst(parObject);
		if(getCount() > MAX_SIZE) {
			citiesList.removeLast();
		}
	}
	
	

}
