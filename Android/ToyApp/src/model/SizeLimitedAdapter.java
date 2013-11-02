package model;

import interfaces.Displayable;

import java.util.LinkedList;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SizeLimitedAdapter<E extends Displayable> extends BaseAdapter { //TODO Mettre le nom et autre générique

	/*http://www.vogella.com/articles/AndroidListView/article.html#androidlists_adapterintro*/
	
	private LayoutInflater _inflater;
	private final int MAX_SIZE;
	private LinkedList<E> _objectsList = null;
	private Context _context;
	
	public SizeLimitedAdapter(Context parContext, int parMAX_SIZE, LinkedList<E> parObjectsList) {
		MAX_SIZE=parMAX_SIZE;
		_context = parContext;
		_objectsList = parObjectsList;
		_inflater = LayoutInflater.from(parContext);
	}
	
	@Override
	public int getCount() {
		return _objectsList.size();
	}

	@Override
	public Object getItem(int parPosition) {
		return _objectsList.get(parPosition);
	}

	@Override
	public long getItemId(int parPosition) {
		//return citiesList.get(parPosition).get_id();
		return 0; //TODO
	}

	@Override
	public View getView(int parPosition, View parConvertView, ViewGroup parParent) {
	/*	 if( parConvertView == null ){
		        //We must create a View:
			 parConvertView = _inflater.inflate(android.R.layout.simple_list_item_1, parParent, false);
		    }
		    //Here we can do changes to the convertView, such as set a text on a TextView 
		    //or an image on an ImageView.
	*/	 
		 	
	//	  LayoutInflater inflater = (LayoutInflater) _context
	//		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	/*		    View rowView = _inflater.inflate(R.layout.test_list_item, parParent, false);
			    TextView textView = (TextView) rowView.findViewById(R.id.text1);
			    textView.setText(_objectsList.get(parPosition).getNameToDisplay());
		*/ 
		
		
		//TODO You should make this comment visible in you UI.
		
		ViewHolder holder;   
		if (parConvertView == null) { 
			parConvertView = _inflater.inflate(R.layout.simple_list_item_1,parParent, false);
			 holder = new ViewHolder(); 
			 holder.tv = (TextView) parConvertView.findViewById(R.id.text1);  
			 parConvertView.setTag(holder); 
			 } else { 
			   holder = (ViewHolder) parConvertView.getTag(); 
			 }
			  City profile = (City) _objectsList.get(parPosition);

             holder.tv.setText(profile.getNameToDisplay());
			
		 
		 
		    return parConvertView;
	}
	
	public void addLast(E parObject) {
		_objectsList.addLast(parObject);
		if(getCount() > MAX_SIZE) {
			_objectsList.removeFirst();
		}
		
	}
	
	public void addFirst(E parObject) {
		_objectsList.addFirst(parObject);
		if(getCount() > MAX_SIZE) {
			_objectsList.removeLast();
		}
	}
	

	static class ViewHolder
	{
	TextView tv;
	}

}
