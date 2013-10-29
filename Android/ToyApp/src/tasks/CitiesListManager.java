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
import android.os.Looper;

public class CitiesListManager extends AsyncTask<Void, Void, Void> {

	private Integer _magicNumber = null;
	private OnTaskCompleted _listener = null;
	private EndLessScrollListener _endLessScrollListener = null;
	private final String _httpLink = "http://honey.computing.dcu.ie/city/cities.php?";
	private String _responseString = null;
	private String _countryCode = null;
	private String _debugString = null; // TODO delete

	public CitiesListManager(Context parContext, EndLessScrollListener parScrollListener, String parCountryCode, Integer parNumber) {
		_listener = (OnTaskCompleted) parContext;
		_magicNumber = parNumber;
		_endLessScrollListener = parScrollListener;
		_countryCode = parCountryCode;
	}



	@Override
	protected Void doInBackground(Void... params) {	
		
		_magicNumber++;

		_endLessScrollListener.onTaskCompleted(null);
		
		
		// http://stackoverflow.com/questions/3505930/make-an-http-request-with-android

		HttpClient locHttpclient = new DefaultHttpClient();
		HttpResponse locHttpResponse;

		StringBuilder locUrl = new StringBuilder();
		locUrl.append(_httpLink).append("id=").append(_magicNumber).append("&cn=").append(_countryCode); 
		
		_debugString = locUrl.toString();
		
		try {
			locHttpResponse = locHttpclient
					.execute(new HttpGet(
							locUrl.toString()));
			StatusLine locStatusLine = locHttpResponse.getStatusLine();
			if (locStatusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream locOut = new ByteArrayOutputStream();
				locHttpResponse.getEntity().writeTo(locOut);
				locOut.close();
				_responseString = locOut.toString();
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

		return null;
		
	
	}
	
	@Override
	protected void onPostExecute(Void parString) {

		boolean isCitiesListSucess = true;
		JSONParser locParser = new JSONParser();
		JSONArray locCitiesJSONArray = null;
		List<String> locCitiesList = new ArrayList<String>();

		try {
			JSONObject locAnswerJSON = (JSONObject) locParser
					.parse(_responseString);

			locCitiesJSONArray = (JSONArray) locAnswerJSON.get("result");

			for (int i = 0; i < locCitiesJSONArray.size(); i++) {

				JSONObject locCityJSON = (JSONObject) locCitiesJSONArray.get(i);
				String locCityName = (String) locCityJSON.get("city");
				locCitiesList.add(locCityName);

			}

		} catch (ParseException e) {
			e.printStackTrace();
			isCitiesListSucess = false;
		}

		
		_listener.setDebugTextView(_debugString);
		
		if (isCitiesListSucess) {
			_listener.onTaskCompleted(locCitiesList);
		} else {
			_listener.onTaskCompleted(null);
		}
		
		
	}	

}
