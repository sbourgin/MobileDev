package tasks;

import interfaces.OnTaskCompleted;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;

public class CitiesListManager extends AsyncTask<Void, Void, Void> {

	private Integer _magicNumber = Integer.valueOf(0);
	private OnTaskCompleted _listener = null;

	public CitiesListManager(Context parContext) {
		_listener = (OnTaskCompleted) parContext;
	}

	@Override
	protected void onPostExecute(Void parString) {

	}

	@Override
	protected Void doInBackground(Void... params) {
		Looper.prepare();

		_magicNumber++;

		_listener.onTaskCompleted(_magicNumber);

		Looper.loop();
		return null;
	}

}
