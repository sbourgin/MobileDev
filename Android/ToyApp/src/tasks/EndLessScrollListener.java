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

		boolean isUpdateNeeded = false; // TODO tester si le code est bon

		int locItemsCount = parListView.getCount();

		boolean isScrollingDown;

		int locNewPosition = parListView.getLastVisiblePosition();

		if (_lastVisiblePosition < locNewPosition) {

			int locLastItemVisible = parListView.getLastVisiblePosition();
			if ((locLastItemVisible + 20) >= locItemsCount) {
				isUpdateNeeded = true;
			}

			isScrollingDown = true;
		} else {
			int locFirstItemVisible = parListView.getFirstVisiblePosition();
			if (locFirstItemVisible - 20 <= 0) {
				isUpdateNeeded = true;
			}
			isScrollingDown = false;
		}

		if (isUpdateNeeded) {
			_citiesListManager.updateCitiesList(isScrollingDown);
		}

		_lastVisiblePosition = locNewPosition;

	}

}
