package tasks;

import interfaces.OnTaskCompleted;
import android.content.Context;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Toast;

public class EndLessScrollListener implements OnScrollListener, OnTaskCompleted {

	private Context _contextParent = null;
	private Boolean _isUpdating = Boolean.valueOf(false);
	private int _lastVisiblePosition = 0;
	
	
	public EndLessScrollListener(Context parContextParent) {
		_contextParent = parContextParent;
	}
	
	
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
			
	}

	@Override
	public void onScrollStateChanged(AbsListView parListView, int parScrollState) {
		
	
//		int lastVisible = parListView.getLastVisiblePosition();
//		int count = parListView.getCount();
		
//		String displayString = "Scroling, last visible " + lastVisible + " / " + count;
		
		int locNewPosition = parListView.getLastVisiblePosition();
		String locMessage = null;
		if(_lastVisiblePosition < locNewPosition) {
			locMessage = "down";
		} else {
			locMessage = "up";
		}
		
		
		Toast locToast = Toast.makeText(_contextParent,
				locMessage, Toast.LENGTH_SHORT);
		locToast.show();
		
		_lastVisiblePosition = locNewPosition;
		
		/*
		
		synchronized (this) {
			if(false == _isUpdating) {
				_isUpdating = Boolean.valueOf(true);
				Integer locCount = Integer.valueOf(parListView.getCount());
				new CitiesListManager(_contextParent, this, "us", locCount).execute((Void)null); //TODO ne pas mettre le code pays en dur
			
			}
		
		}
		
		*/
		 
		
		
		
		
	}




	@Override
	public void onTaskCompleted(Object parObject) {

		_isUpdating = Boolean.valueOf(false);
		
	}


	@Override //TODO DELETE 
	public void setDebugTextView(String parTextToDisplay) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
