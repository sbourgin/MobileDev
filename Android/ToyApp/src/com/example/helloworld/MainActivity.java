package com.example.helloworld;

import interfaces.OnTaskCompleted;

import java.util.LinkedList;
import java.util.List;

import model.City;
import model.SizeLimitedAdapter;
import tasks.CitiesListManager;
import tasks.EndLessScrollListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTaskCompleted {

	private RelativeLayout _layout = null;
	private ListView _listeView = null;
	private SizeLimitedAdapter<City> _citiesAdaptater = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// On récupère notre layout par désérialisation. La méthode inflate
		// retourne un View
		// C'est pourquoi on caste (on convertit) le retour de la méthode avec
		// le vrai type de notre layout, c'est-à-dire RelativeLayout
		_layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.activity_main, null);

		// A remettre en remettant la listView dans la vue

		_listeView = (ListView) _layout.findViewById(R.id.listView1);

		LinkedList<City> locObjectsList = new LinkedList<City>();
		_citiesAdaptater = new SizeLimitedAdapter<City>(this, 200,
				locObjectsList);

		_listeView.setAdapter(_citiesAdaptater);

		CitiesListManager locCitiesListManager = new CitiesListManager(this);

		locCitiesListManager.initData();

		_listeView.setOnScrollListener(new EndLessScrollListener(
				locCitiesListManager));

		setContentView(_layout);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTaskCompleted(Object parObject) {

		if (parObject == null) {
			Toast locToast = Toast.makeText(this,
					"Error when retrieving cities", Toast.LENGTH_LONG);
			locToast.show();
		} else {
			List<City> locCitiesList = (List<City>) parObject;

			for (City locCity : locCitiesList) {
				_citiesAdaptater.addLast(locCity); // TODO gérer si c'est un
													// scroll up or Down !!
				_citiesAdaptater.notifyDataSetChanged();

			}

		}

	}

}
