package tasks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import interfaces.OnTaskCompleted;
import android.content.Context;
import android.os.AsyncTask;

public class GetRESTTask extends AsyncTask<String, Void, String> {

	private OnTaskCompleted _listener = null;
	private String _debugString = null; // TODO delete

	public GetRESTTask(OnTaskCompleted parOnTaskCompleted) {
		_listener = parOnTaskCompleted;
	}



	@Override
	protected String doInBackground(String... params) {	
				
		// http://stackoverflow.com/questions/3505930/make-an-http-request-with-android

		HttpClient locHttpclient = new DefaultHttpClient();
		HttpResponse locHttpResponse;
		
		String locUrl = params[0];
		
		_debugString = locUrl.toString();
		
		String locResponseString = "";
		
		try {
			locHttpResponse = locHttpclient
					.execute(new HttpGet(
							locUrl.toString()));
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
		} catch (ClientProtocolException e) {
			// TODO Handle problems..
		} catch (IOException e) {
			// TODO Handle problems..
		}

		
		return locResponseString;
		
	
	}

	

	@Override
	protected void onPostExecute(String parString) {

		_listener.onTaskCompleted(parString);
		
	}	

}
