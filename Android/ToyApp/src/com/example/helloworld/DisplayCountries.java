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
	private TextView _textView = null;
	private ListView _listeView = null;
	private SizeLimitedAdapter<Country> _countriesAdaptater = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.display_countries, null);

		_textView = (TextView) _layout
				.findViewById(R.id.textViewActivityCountries);

		_listeView = (ListView) _layout.findViewById(R.id.listViewCountry);
		
		LinkedList<Country> locObjectsList = new LinkedList<Country>();
		_countriesAdaptater = new SizeLimitedAdapter<Country>(this, 200,
				locObjectsList);

		_listeView.setAdapter(_countriesAdaptater);

		CountriesListManager locCountriesListManager = new CountriesListManager(
				this);

		locCountriesListManager.initData();

		Button locButton = (Button) _layout.findViewById(R.id.button1);

		locButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View parView) {

				Intent intent = new Intent(DisplayCountries.this,
						DisplayCities.class);
				startActivity(intent);

			}

		});

		setContentView(_layout);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
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
