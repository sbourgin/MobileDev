package com.mmessage.dcu.sylvain.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

import com.mmessage.dcu.sylvain.controler.MainActivityController;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;

public class PostRESTTask extends AsyncTask<String, Void, String> {

	private OnTaskCompleted _listener = null;
	private List<NameValuePair> _parameters;
	private boolean _isAuthentificationNeeded;

	public PostRESTTask(OnTaskCompleted parOnTaskCompleted, boolean parIsAuthentificationNeeded, List<NameValuePair> parNameValuePairs) {
		_listener = parOnTaskCompleted;
		_parameters = parNameValuePairs;
		_isAuthentificationNeeded = parIsAuthentificationNeeded;
	}

	@Override
	protected String doInBackground(String... params) {
		
		String locUrl = params[0];
		String locResponseString = "";		
		
		HttpClient httpclient = new DefaultHttpClient();
    	HttpPost httppost = new HttpPost(locUrl);
    	httppost.addHeader("Accept", "application/json");
    	if(_isAuthentificationNeeded) {
    		httppost.addHeader("Authorization", "Basic " + MainActivityController.getAuthentification());
    	}
	    try {
	    	//TODO mettre en dynamique avec la map des attributs

	        httppost.setEntity(new UrlEncodedFormEntity(_parameters));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        locResponseString =  String.valueOf(response.getStatusLine().getStatusCode());
	        
	    } catch (ClientProtocolException e) {
	        return null;
	    } catch (IOException e) {
	    	return null;
	    }
	    return locResponseString;

	}

	@Override
	protected void onPostExecute(String parString) {
		_listener.onTaskCompleted(parString);

	}

}
