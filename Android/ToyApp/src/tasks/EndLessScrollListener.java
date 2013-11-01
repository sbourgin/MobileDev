package tasks;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class EndLessScrollListener implements OnScrollListener {

	private CitiesListManager _citiesListManager = null;

	private int _lastVisiblePosition = 0;
	
	
	public EndLessScrollListener(CitiesListManager parCitiesListManager) {
		_citiesListManager = parCitiesListManager;
		
	}
	
	
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
			
	}

	@Override
	public void onScrollStateChanged(AbsListView parListView, int parScrollState) {
		
		@SuppressWarnings("unused")
		int count = parListView.getCount();
		boolean isScrollingDown;
		
		int locNewPosition = parListView.getLastVisiblePosition();
		
		if(_lastVisiblePosition < locNewPosition) {
			isScrollingDown = true;
		} else {
			isScrollingDown = false;
		}
		
		_citiesListManager.updateCitiesList(isScrollingDown);
		
		/*
		Toast locToast = Toast.makeText(_contextParent,
				locMessage, Toast.LENGTH_SHORT);
		locToast.show();
		*/
		_lastVisiblePosition = locNewPosition;
		
		
	}
	

}
