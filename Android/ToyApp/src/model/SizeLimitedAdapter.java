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
			parConvertView = _inflater.inflate(
					_ressource, parParent, false);
			locHolder = new ViewHolder();
			locHolder._mainText = (TextView) parConvertView
					.findViewById(R.id.text1);
			locHolder._subtitle = (TextView) parConvertView
					.findViewById(R.id.text2);
			parConvertView.setTag(locHolder);
		} else {
			locHolder = (ViewHolder) parConvertView.getTag();
		}

		Displayable locItemToDisplay = (Displayable) _objectsList
				.get(parPosition);

		locHolder._mainText.setText(locItemToDisplay.getNameToDisplay());

		if (_ressource == 17367047) { // simple_expandable_list_item_2 
			locHolder._subtitle.setText(locItemToDisplay.getSubtitle());
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

	static class ViewHolder {
		TextView _mainText;
		TextView _subtitle;
	}

}
