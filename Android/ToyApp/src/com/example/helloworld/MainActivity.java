package com.example.helloworld;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private RelativeLayout _layout = null;
	private ListView _liste = null;
	private Context context = null;
	private TextView _resultRequest = null;

	private OnClickListener _clickListener = new OnClickListener() {
		@Override
		public void onClick(View parView) {
			/*
			 * switch(parView.getId()) {
			 * 
			 * case R.id.buttonBold: //TODO TODO break; case R.id.buttonItalic:
			 * break; case R.id.buttonUnderline: break; }
			 */

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;

		new ATRequestGet().execute(null, null, null);

		// On récupère notre layout par désérialisation. La méthode inflate
		// retourne un View
		// C'est pourquoi on caste (on convertit) le retour de la méthode avec
		// le vrai type de notre layout, c'est-à-dire RelativeLayout
		_layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.activity_main, null);

		_resultRequest = (TextView) _layout.findViewById(R.id.resultRequest);

		// A remettre en remettant la listView dans la vue
		/*
		 * _liste = (ListView) _layout.findViewById(R.id.listView1);
		 * _liste.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		 * 
		 * //Population de la liste final List<String> locItemsList = new
		 * ArrayList<String>(); for(int i=0; i<50;i++) { locItemsList.add("Item"
		 * + i); }
		 * 
		 * //OnTimeClickListener _liste.setOnItemClickListener(new
		 * AdapterView.OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> adapterView, View
		 * view, int position, long id) {
		 * 
		 * int index = _liste.getCheckedItemPosition();
		 * 
		 * String item = "L'item sélectionné est : " + index;
		 * 
		 * // Toast plouf = Toast.makeText(context,(CharSequence)
		 * item,Toast.LENGTH_LONG); // plouf.show(); } });
		 * 
		 * //On set l'adaptateur ArrayAdapter<String> adapter = new
		 * ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_single_choice, locItemsList);
		 * _liste.setAdapter(adapter);
		 */
		setContentView(_layout);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// A BOUGER
	// http://stackoverflow.com/questions/6611663/how-to-get-json-content-from-a-restful-server-to-a-android-client
	public ArrayList<String> fetchTwitterPublicTimeline() {
		ArrayList<String> listItems = new ArrayList<String>();
		try {
			URL twitter = new URL(
					"http://twitter.com/statuses/public_timeline.json");
			URLConnection tc = twitter.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					tc.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				JSONArray ja = new JSONArray(line);
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = (JSONObject) ja.get(i);
					listItems.add(jo.getString("text"));
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listItems;
	}

	private class ATRequestGet extends AsyncTask<Void, Void, Void> {

		String responseString = null;

		@Override
		protected Void doInBackground(Void... arg0) {

			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;

			try {
				response = httpclient.execute(new HttpGet(
						"http://honey.computing.dcu.ie/city/city.php?id=34"));
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					responseString = out.toString();
				} else {
					// Closes the connection.
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}
			} catch (ClientProtocolException e) {
				// TODO Handle problems..
			} catch (IOException e) {
				// TODO Handle problems..
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void parVoid) {

			_resultRequest.setText(responseString);

			Toast plaf = Toast.makeText(context, "hello", Toast.LENGTH_LONG);
			plaf.show();
		}

	}

}
