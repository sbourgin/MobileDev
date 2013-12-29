package com.mmessage.dcu.sylvain.tasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.os.AsyncTask;

import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;

public class PostRESTTask extends AsyncTask<String, Void, String> {

	private OnTaskCompleted _listener = null;
	private Map _parameters;

	public PostRESTTask(OnTaskCompleted parOnTaskCompleted, Map parParameters) {
		_listener = parOnTaskCompleted;
		_parameters = parParameters;
	}

	@Override
	protected String doInBackground(String... params) {

		//TODO Ca ne marche pas, erreur 500., ça a eu marcher (cf liste user !!)
		
		
	//	http://stackoverflow.com/questions/15870636/how-to-execute-restful-post-requests-from-android
		
		String locUrl = params[0];
		String locResponseString = "";
/*
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(locUrl);
		httppost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF8");
		
		try {
		    // Add your data
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		    nameValuePairs.add(new BasicNameValuePair("username", "Sylvain2"));
		    nameValuePairs.add(new BasicNameValuePair("password", "Sylvain2"));
		    nameValuePairs.add(new BasicNameValuePair("email", "sykvain2@sylvain.com"));
		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    
		    // Execute HTTP Post Request
		    HttpResponse response = httpclient.execute(httppost);
		    
		    locResponseString =  EntityUtils.toString(response.getEntity());
		    

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

*/		
		
		HttpPost httppost;
        DefaultHttpClient httpclient;
        ResponseHandler <String> res=new BasicResponseHandler();  
        List<NameValuePair> nameValuePairs;
        String bytesSent;

        httppost = new HttpPost(locUrl);  
       httppost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF8");
        HttpParams parameters = new BasicHttpParams();  

        HttpProtocolParams.setContentCharset(parameters, "UTF-8");

        httpclient = new DefaultHttpClient(parameters);
        nameValuePairs = new ArrayList<NameValuePair>(2);  
	    nameValuePairs.add(new BasicNameValuePair("username", "Sylvain2"));
	    nameValuePairs.add(new BasicNameValuePair("password", "Sylvain2"));
	    nameValuePairs.add(new BasicNameValuePair("email", "sykvain2@sylvain.com"));
        try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        try {
			bytesSent = httpclient.execute(httppost, res);
			locResponseString=bytesSent;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return (locResponseString);

	}

	@Override
	protected void onPostExecute(String parString) {
		_listener.onTaskCompleted(parString);

	}

}
