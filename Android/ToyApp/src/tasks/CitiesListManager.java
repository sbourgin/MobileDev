package tasks;

import interfaces.OnTaskCompleted;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;

public class CitiesListManager extends AsyncTask<Void, Void, Void> {

	private Integer _magicNumber = null;
	private OnTaskCompleted _listener = null;

	public CitiesListManager(Context parContext, Integer parNumber) {
		_listener = (OnTaskCompleted) parContext;
		_magicNumber = parNumber;
	}



	@Override
	protected Void doInBackground(Void... params) {
	//	Looper.prepare();

		_magicNumber++;

		

	//	Looper.loop();
		return null;
	}
	
	@Override
	protected void onPostExecute(Void parString) {

		_listener.onTaskCompleted(_magicNumber);
		
		
	}	

}
