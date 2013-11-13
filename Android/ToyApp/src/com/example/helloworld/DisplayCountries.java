package com.example.helloworld;

import interfaces.OnTaskCompleted;

import java.util.LinkedList;
import java.util.List;

import model.Country;
import model.SizeLimitedAdapter;
import tasks.CountriesListManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayCountries extends Activity implements OnTaskCompleted {

	private RelativeLayout _layout = null;
	private ListView _listeView = null;
	private Button _buttonChangeActivity = null;
	private SizeLimitedAdapter<Country> _countriesAdaptater = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.display_countries, null);

		_listeView = (ListView) _layout.findViewById(R.id.listViewCountry);

		LinkedList<Country> locObjectsList = new LinkedList<Country>();
		_countriesAdaptater = new SizeLimitedAdapter<Country>(this, 200,
				locObjectsList, 17367055); // 17367055 =
											// simple_list_item_single_choice

		_listeView.setAdapter(_countriesAdaptater);

		CountriesListManager locCountriesListManager = new CountriesListManager(
				this);

		locCountriesListManager.initData();

		_buttonChangeActivity = (Button) _layout
				.findViewById(R.id.buttonViewCities);

		_buttonChangeActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View parView) {

				//Avoir -1 if nothing is checked
				int locItemCheckedPosition = _listeView
						.getCheckedItemPosition();
				if (locItemCheckedPosition < 0) {
					locItemCheckedPosition = 0;
				}

				Country locCountryChecked = (Country) _countriesAdaptater
						.getItem(locItemCheckedPosition);

				String locCountryString = locCountryChecked.getNameToDisplay();

				Bundle locBundle = new Bundle();
				locBundle.putString("countryCode", locCountryString);

				Intent locIntent = new Intent(DisplayCountries.this,
						DisplayCities.class);
				locIntent.putExtras(locBundle);
				startActivity(locIntent);

			}

		});

		setContentView(_layout);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public void onTaskCompleted(Object parObject) {

		List<Country> locResult = (List<Country>) parObject;

		if ((parObject == null) || (locResult.isEmpty())) {
			Toast locToast = Toast.makeText(this,
					"Error when retrieving countries", Toast.LENGTH_LONG);
			locToast.show();
		} else {

			for (Country locCountry : locResult) {
				_countriesAdaptater.addLast(locCountry);
			}

			_countriesAdaptater.notifyDataSetChanged();

		}

	}
}
