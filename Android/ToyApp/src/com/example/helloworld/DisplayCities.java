package com.example.helloworld;

import interfaces.OnTaskCompleted;

import java.util.LinkedList;
import java.util.List;

import model.City;
import model.SizeLimitedAdapter;
import tasks.CitiesListManager;
import tasks.EndLessScrollListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DisplayCities extends Activity implements OnTaskCompleted {

	private RelativeLayout _layout = null;
	private ListView _listeView = null;
	private SizeLimitedAdapter<City> _citiesAdaptater = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle locReceiveBundle = this.getIntent().getExtras();
	    final String locCountryCode = locReceiveBundle.getString("countryCode");
		
		
		// On récupère notre layout par désérialisation. La méthode inflate
		// retourne un View
		// C'est pourquoi on caste (on convertit) le retour de la méthode avec
		// le vrai type de notre layout, c'est-à-dire RelativeLayout
		_layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.display_cities, null);

		// A remettre en remettant la listView dans la vue

		_listeView = (ListView) _layout.findViewById(R.id.listViewCities);

		LinkedList<City> locObjectsList = new LinkedList<City>();
		_citiesAdaptater = new SizeLimitedAdapter<City>(this, 200,
				locObjectsList, 17367047); // 17367047 R.layout.simple_expandable_list_item_2

		_listeView.setAdapter(_citiesAdaptater);

		CitiesListManager locCitiesListManager = new CitiesListManager(this, locCountryCode);

		locCitiesListManager.initData();

		_listeView.setOnScrollListener(new EndLessScrollListener(
				locCitiesListManager));
	
		
		_listeView.setOnItemClickListener(new OnItemClickListener() {
	 
			@Override	
		public void onItemClick(AdapterView<?> parent, View view,
	                int parPosition, long id) {

	       City locCity = (City) _citiesAdaptater.getItem(parPosition);
	       String display= locCity.get_cityName();
	       Toast.makeText(getBaseContext(),display,Toast.LENGTH_SHORT).show();
	       
	       
	       Bundle locBundle = new Bundle();
				locBundle.putString("cityName", locCity.get_cityName());
				locBundle.putDouble("latitude", locCity.get_latitude());
				locBundle.putDouble("longitude", locCity.get_longitude());
			
			Intent locIntent = new Intent(DisplayCities.this,
					DisplayCity.class);
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

		if (parObject == null) {
			Toast locToast = Toast.makeText(this,
					"Error when retrieving cities", Toast.LENGTH_LONG);
			locToast.show();
		} else {

			List<Object> locResult = (List<Object>) parObject;

			Boolean isScrollingDown = (Boolean) locResult.get(0);

			List<City> locCitiesList = (List<City>) locResult.get(1);

			for (City locCity : locCitiesList) {

				if (isScrollingDown.booleanValue()) {
					_citiesAdaptater.addLast(locCity);
				} else {
					_citiesAdaptater.addFirst(locCity);
				}

			}

			_citiesAdaptater.notifyDataSetChanged();

		}

	}

}
