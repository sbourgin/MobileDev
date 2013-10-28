package tasks;

import interfaces.OnTaskCompleted;

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

import android.content.Context;
import android.os.AsyncTask;

public class GetTask extends AsyncTask<String, String, String> {

	String _responseString = null;
	OnTaskCompleted _listener = null;
	
	public GetTask(Context parContext) {
		_listener = (OnTaskCompleted) parContext;
	}
	
	@Override
	protected String doInBackground(String... arg0) {

		//http://stackoverflow.com/questions/3505930/make-an-http-request-with-android
		
		HttpClient locHttpclient = new DefaultHttpClient();
		HttpResponse locHttpResponse;

		try {
			locHttpResponse = locHttpclient.execute(new HttpGet(
					"http://honey.computing.dcu.ie/city/cities.php?id=34&cn=ie"));
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
	protected void onPostExecute(String parVoid) {

		
		/*Use simple Json
		  				JSONArray ja = new JSONArray(line);
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = (JSONObject) ja.get(i);
					listItems.add(jo.getString("text"));
		 */
		JSONParser locParser = new JSONParser();
		JSONArray locCitiesJSONArray = null;
		List<String> locCitiesList = new ArrayList<String>();
		
		try {			
			JSONObject locAnswerJSON = (JSONObject) locParser.parse(_responseString);
				
			locCitiesJSONArray = (JSONArray) locAnswerJSON.get("result");
			
			for(int i=0; i<locCitiesJSONArray.size(); i++) {
				
				JSONObject locCityJSON = (JSONObject) locCitiesJSONArray.get(i);
				String locCityName = (String) locCityJSON.get("city");
				locCitiesList.add(locCityName);
				
			}
			
			
			
			System.out.println("plouf");
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//	JSONObject locJsonObject = ;
		
		
		
		_listener.onTaskCompleted(locCitiesList.toString());
		
//		String tmp  =locResponseString.toString();
//		tmp.toString();
//		_resultRequest.setText(locResponseString);

//		Toast plaf = Toast.makeText(context, "hello", Toast.LENGTH_LONG);
//		plaf.show();
	}

}