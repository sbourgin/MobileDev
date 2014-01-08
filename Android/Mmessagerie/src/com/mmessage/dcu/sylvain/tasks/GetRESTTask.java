package com.mmessage.dcu.sylvain.tasks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import com.mmessage.dcu.sylvain.controler.MainActivityController;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Commands;
import com.mmessage.dcu.sylvain.model.TaskMessage;

public class GetRESTTask extends AsyncTask<String, Void, String> {

	private OnTaskCompleted _listener = null;
	private Commands _command;
	private int _httpCode = 0;

	public GetRESTTask(OnTaskCompleted parOnTaskCompleted,
			Commands parCommandType) {
		_listener = parOnTaskCompleted;
		_command = parCommandType;
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

			get.addHeader("Authorization",
					"Basic " + MainActivityController.getAuthentification());
			locHttpResponse = locHttpclient.execute(get);
			StatusLine locStatusLine = locHttpResponse.getStatusLine();
			_httpCode = locStatusLine.getStatusCode();
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
		TaskMessage locTaskMessage = new TaskMessage(_command, _httpCode,
				parString);
		_listener.onTaskCompleted(locTaskMessage);

	}

}
