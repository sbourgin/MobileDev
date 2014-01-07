package com.mmessage.dcu.sylvain.model;

import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mmessage.dcu.sylvain.ConversationViewActivity;
import com.mmessage.dcu.sylvain.ConversationsActivity;
import com.mmessage.dcu.sylvain.R;
import com.mmessage.dcu.sylvain.interfaces.Displayable;

public class SizeLimitedAdapter<E extends Displayable> extends BaseAdapter {

	/*
	 * http://www.vogella.com/articles/AndroidListView/article.html#
	 * androidlists_adapterintro
	 */

	private LayoutInflater _inflater;
	private final int MAX_SIZE;
	private LinkedList<E> _objectsList = null;
	private int _ressource;

	public SizeLimitedAdapter(Context parContext, int parMAX_SIZE,
			LinkedList<E> parObjectsList, int parRessource) {
		
		MAX_SIZE = parMAX_SIZE;
		_objectsList = parObjectsList;
		_inflater = LayoutInflater.from(parContext);
		_ressource = parRessource;
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
		return _objectsList.get(parPosition).getIdOfItem();
	}

	@Override
	public View getView(int parPosition, View parConvertView,
			ViewGroup parParent) {

		
		ViewHolder locHolder;
		if (parConvertView == null) {
			
			parConvertView = _inflater.inflate(R.layout.custom_list_view ,null);
			
			locHolder = new ViewHolder();
			
				
			locHolder._mainText = (TextView) parConvertView.findViewById(R.id.listViewEditText);
						
			locHolder._subtitle = (TextView) parConvertView
					.findViewById(R.id.listViewTextView);
			
			parConvertView.setTag(locHolder);
		} else {
			locHolder = (ViewHolder) parConvertView.getTag();
		}

		final Displayable locItemToDisplay = (Displayable) _objectsList
				.get(parPosition);

		//TODO problème si le texte fait plus de deux lignes 
		
		locHolder._mainText.setText(locItemToDisplay.getTitleToDisplay());
		locHolder._mainText.setGravity(locItemToDisplay.getGravity());

		
		if(locItemToDisplay.getGravity() == Gravity.RIGHT) {
			parConvertView.setBackgroundColor(Color.LTGRAY);
		} else if (locItemToDisplay.getGravity() == Gravity.LEFT) {
			parConvertView.setBackgroundColor(Color.YELLOW);
		}
		
		if (_ressource == 17367047) { // simple_expandable_list_item_2 
			locHolder._subtitle.setText(locItemToDisplay.getFullTextToDisplay());
			locHolder._subtitle.setGravity(locItemToDisplay.getGravity());
		}
				
		return parConvertView;
	}

	public void addLast(E parObject) {
		_objectsList.addLast(parObject);
		if (getCount() > MAX_SIZE) {
			_objectsList.removeFirst();
		}

	}

	public void addFirst(E parObject) {
		_objectsList.addFirst(parObject);
		if (getCount() > MAX_SIZE) {
			_objectsList.removeLast();
		}
	}
	
	public void resetData() {
		_objectsList.clear();
	}

	static class ViewHolder {
		TextView _mainText;
		TextView _subtitle;
	}

}
