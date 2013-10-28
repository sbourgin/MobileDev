package com.example.helloworld;

import java.util.ArrayList;
import java.util.List;

import interfaces.OnTaskCompleted;
import tasks.EndLessScrollListener;
import tasks.GetTask;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTaskCompleted {

	private RelativeLayout _layout = null;
	private ListView _liste = null;
	private Context _context = null;
	private TextView _resultRequest = null;
	private ArrayAdapter<String> _citiesAdaptater = null;  //TODO Improve : mettre un objet city et afficher plus d'informations dessus

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
		_context = this;

		new GetTask(this).execute();

		// On récupère notre layout par désérialisation. La méthode inflate
		// retourne un View
		// C'est pourquoi on caste (on convertit) le retour de la méthode avec
		// le vrai type de notre layout, c'est-à-dire RelativeLayout
		_layout = (RelativeLayout) RelativeLayout.inflate(_context,
				R.layout.activity_main, null);

//		_resultRequest = (TextView) _layout.findViewById(R.id.resultRequest);

		// A remettre en remettant la listView dans la vue
		
		
		 _liste = (ListView) _layout.findViewById(R.id.listView1);
		 if(_liste == null) {
			 Toast plouf = Toast.makeText(this,"Raté",0);
			 plouf.show();
		 } else {
		 List<String> exemple = new ArrayList<String>();
		 
		 for(int i=0; i<50;i++) {
			 exemple.add("Item" + i);
		 }
		
		         
		    _citiesAdaptater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple);
		    _liste.setAdapter(_citiesAdaptater);
		    _liste.setOnScrollListener(new EndLessScrollListener(_context));
		    
		    
		 }

		 
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

		Integer locMagicNumer = (Integer) parObject;
		
		Toast locToast = Toast.makeText(_context,
				locMagicNumer.toString(), Toast.LENGTH_SHORT);
		locToast.show();	
		
		
		
		
/*		
		if (parObject == null) {
			Toast locToast = Toast.makeText(_context,
					"Error when retrieving cities", Toast.LENGTH_LONG);
			locToast.show();
		} else {
			List<String> locCitiesList = (List<String>) parObject;
			
			for(String locCity:locCitiesList) {
				_citiesAdaptater.add(locCity);
				_citiesAdaptater.notifyDataSetChanged();
				
			}
*/			

		}

	

}
