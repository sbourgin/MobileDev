package tasks;

import interfaces.Displayable;
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

		boolean isUpdateNeeded = false; 
		int locItemsCount = parListView.getCount();
		long locItemId = 0;
		boolean isScrollingDown;

		int locNewPosition = parListView.getLastVisiblePosition();

		if ( (_lastVisiblePosition < locNewPosition) || (locNewPosition == (parListView.getCount()-1) )) {

			int locLastItemVisible = parListView.getLastVisiblePosition();
			if ((locLastItemVisible + 60) >= locItemsCount) {
				isUpdateNeeded = true;
				locItemId = ((Displayable) parListView.getAdapter().getItem(
						parListView.getAdapter().getCount() - 1)).getIdOfItem();
			}

			isScrollingDown = true;
		} else {
			int locFirstItemVisible = parListView.getFirstVisiblePosition();
			if ((locFirstItemVisible - 60) <= 0) {
				isUpdateNeeded = true;
				locItemId = ((Displayable) parListView.getAdapter().getItem(0))
						.getIdOfItem();

			}
			isScrollingDown = false;
		}

		if (isUpdateNeeded) {

			_citiesListManager.updateCitiesList(isScrollingDown, locItemId);
		}

		_lastVisiblePosition = locNewPosition;

	}

}
