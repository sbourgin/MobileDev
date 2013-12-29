package com.mmessage.dcu.sylvain.tasks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import com.mmessage.dcu.sylvain.MainActivity;
import com.mmessage.dcu.sylvain.controler.MainActivityController;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;

public class GetRESTTask extends AsyncTask<String, Void, String> {

	private OnTaskCompleted _listener = null;

	public GetRESTTask(OnTaskCompleted parOnTaskCompleted) {
		_listener = parOnTaskCompleted;
	}

	@Override
	protected String doInBackground(String... params) {

		// http://stackoverflow.com/questions/3505930/make-an-http-request-with-android

		HttpClient locHttpclient = new DefaultHttpClient();
		HttpResponse locHttpResponse;

		String locUrl = params[0];

		String locResponseString = "";

		try {

			HttpGet get = new HttpGet(locUrl.toString());
			get.addHeader("Accept", "application/json");

			get.addHeader("Authorization", "Basic " + MainActivityController.getAuthentification());
			locHttpResponse = locHttpclient.execute(get);
			StatusLine locStatusLine = locHttpResponse.getStatusLine();
			if (locStatusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream locOut = new ByteArrayOutputStream();
				locHttpResponse.getEntity().writeTo(locOut);
				locOut.close();
				locResponseString = locOut.toString();
			} else {
				// Closes the connection.
				locHttpResponse.getEntity().getContent().close();
				throw new IOException(locStatusLine.getReasonPhrase());
			}
		} catch (Exception e) {
			locResponseString = null;
		}

		return locResponseString;

	}

	@Override
	protected void onPostExecute(String parString) {
		_listener.onTaskCompleted(parString);

	}

}
