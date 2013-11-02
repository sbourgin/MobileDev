package com.example.helloworld;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.SizeLimitedAdapter;
import model.City;
import interfaces.OnTaskCompleted;
import tasks.CitiesListManager;
import tasks.EndLessScrollListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTaskCompleted {

	private RelativeLayout _layout = null;
	private ListView _liste = null;
	private TextView _debugTextView = null; // TODO delete
	private SizeLimitedAdapter<City> _citiesAdaptater = null; // TODO Improve :
																// mettre un
																// objet
																// city et
																// afficher
																// plus
																// d'informations
																// dessus

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

		// On récupère notre layout par désérialisation. La méthode inflate
		// retourne un View
		// C'est pourquoi on caste (on convertit) le retour de la méthode avec
		// le vrai type de notre layout, c'est-à-dire RelativeLayout
		_layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.activity_main, null);

		_debugTextView = (TextView) _layout.findViewById(R.id.debugTextView);

		// A remettre en remettant la listView dans la vue

		_liste = (ListView) _layout.findViewById(R.id.listView1);

		LinkedList<City> locObjectsList = new LinkedList<City>();
		
		for (int i = 0; i < 50; i++) { // TODO delete et lancer un fetch data à
			// l'init
			City locCity = new City();
			locCity.setCityName("Item" + i);
			locObjectsList.addLast(locCity);
		}

		// TODO Extends baseAdapter
		/*
		 * _citiesAdaptater = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, exemple);
		 */
		_citiesAdaptater = new SizeLimitedAdapter<City>(this, 200, locObjectsList);

		_liste.setAdapter(_citiesAdaptater);

		CitiesListManager locCitiesListManager = new CitiesListManager(this);

		_liste.setOnScrollListener(new EndLessScrollListener(
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

	// TODO delete
	public void setDebugTextView(String parTextToDisplay) {
		_debugTextView.setText(parTextToDisplay);
	}

}
