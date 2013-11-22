package com.mmessage.dcu.sylvain.tasks;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.os.AsyncTask;

import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;

public class PostRESTTask extends AsyncTask<String, Void, String> {

	private OnTaskCompleted _listener = null;

	public PostRESTTask(OnTaskCompleted parOnTaskCompleted) {
		_listener = parOnTaskCompleted;
	}

	@Override
	protected String doInBackground(String... params) {

		// http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/

		String locUrl = params[0];
		String locResponseString = "";

		// String locUrl = "https://selfsolve.apple.com/wcResults.do";
		try {
			URL obj = new URL(locUrl);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + locUrl);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			locResponseString = response.toString();
		} catch (Exception e) {
			locResponseString = null;
		}

		// print result
		return (locResponseString);

	}

	@Override
	protected void onPostExecute(String parString) {
		_listener.onTaskCompleted(parString);

	}

}
