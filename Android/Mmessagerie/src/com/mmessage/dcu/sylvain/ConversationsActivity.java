package com.mmessage.dcu.sylvain;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.model.SizeLimitedAdapter;

public class ConversationsActivity extends Activity implements OnTaskCompleted {

	private LinearLayout _layout = null;
	private ListView _listeView = null;
	private SizeLimitedAdapter<Conversation> _conversationsAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_layout = (LinearLayout) LinearLayout.inflate(this,
				R.layout.activity_conversations, null);

		_listeView = (ListView) _layout.findViewById(R.id.conversationListView);

		LinkedList<Conversation> locObjectsList = new LinkedList<Conversation>();

		ListAdapter _conversationsAdapter = new SizeLimitedAdapter<Conversation>(
				this, 200, locObjectsList, 17367055); // 17367055 =
		// simple_list_item_single_choice

		_listeView.setAdapter(_conversationsAdapter);

		/*
		 * _buttonChangeActivity.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View parView) {
		 * 
		 * //Avoir -1 if nothing is checked int locItemCheckedPosition =
		 * _listeView .getCheckedItemPosition(); if (locItemCheckedPosition < 0)
		 * { locItemCheckedPosition = 0; }
		 * 
		 * Country locCountryChecked = (Country) _countriesAdaptater
		 * .getItem(locItemCheckedPosition);
		 * 
		 * String locCountryString = locCountryChecked.getNameToDisplay();
		 * 
		 * Bundle locBundle = new Bundle(); locBundle.putString("countryCode",
		 * locCountryString);
		 * 
		 * Intent locIntent = new Intent(DisplayCountries.this,
		 * DisplayCities.class); locIntent.putExtras(locBundle);
		 * startActivity(locIntent);
		 * 
		 * }
		 * 
		 * });
		 */

		setContentView(_layout);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.conversations, menu);
		return true;
	}

	@Override
	public void onTaskCompleted(Object parObject) {

		List<Conversation> locResult = (List<Conversation>) parObject;

		if ((parObject == null) || (locResult.isEmpty())) {
			Toast locToast = Toast.makeText(this,
					"Error when retrieving conversations", Toast.LENGTH_LONG);
			locToast.show();
		} else {
			_conversationsAdapter.resetData();
			for (Conversation locConversation : locResult) {
				_conversationsAdapter.addLast(locConversation);
			}

			_conversationsAdapter.notifyDataSetChanged();

		}

	}

}
