package com.example.helloworld;

import interfaces.OnTaskCompleted;
import tasks.GetTask;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnTaskCompleted {

	private RelativeLayout _layout = null;
	private ListView _liste = null;
	private Context context = null;
	private TextView _resultRequest = null;
// Use to wrap data	ArrayAdapter<Object> city = new ArrayAdapter<Object>();
	

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

		new GetTask(this).execute();

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

	@Override
	public void onTaskCompleted(Object parObject) {
		String locString = (String) parObject;
		_resultRequest.setText(locString);
	}


}
