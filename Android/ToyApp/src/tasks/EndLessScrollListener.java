package tasks;

import interfaces.OnTaskCompleted;
import android.content.Context;
import android.widget.AbsListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class EndLessScrollListener implements OnScrollListener, OnTaskCompleted {

	private Context _contextParent = null;
	private Boolean _isUpdating = Boolean.valueOf(false);
	
	
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
		
//		Toast locToast = Toast.makeText(_contextParent,
//				displayString, Toast.LENGTH_SHORT);
		//locToast.show();
		
		
		
		synchronized (this) {
			if(false == _isUpdating) {
				_isUpdating = Boolean.valueOf(true);
				Integer locCount = Integer.valueOf(parListView.getCount());
				new CitiesListManager(_contextParent, this, "ie", locCount).execute((Void)null); //TODO ne pas mettre le code pays en dur
			
			}
		
		}
		
		
		 
		
		
		
		
	}




	@Override
	public void onTaskCompleted(Object parObject) {

		_isUpdating = Boolean.valueOf(false);
		
	}
	
	
	

}
