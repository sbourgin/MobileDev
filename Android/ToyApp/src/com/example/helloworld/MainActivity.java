package com.example.helloworld;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{

	private RelativeLayout _layout = null;
	private ListView _liste = null;
	private Context context = null;
	
	private OnClickListener _clickListener = new OnClickListener() {
		@Override
		public void onClick(View parView) {
			/*			
			switch(parView.getId()) {
	
			case R.id.buttonBold: 							//TODO TODO
				break;			
			case R.id.buttonItalic:
				break;	
			case R.id.buttonUnderline:
				break;		
			} */
			
		}
	};
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
   	    context = this;
		
   	    
   	    
   	    
   	    
   	    
		// On récupère notre layout par désérialisation. La méthode inflate retourne un View
	    // C'est pourquoi on caste (on convertit) le retour de la méthode avec le vrai type de notre layout, c'est-à-dire RelativeLayout
		_layout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.activity_main, null);
	    _liste = (ListView) _layout.findViewById(R.id.listView1);
		_liste.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		 
		//Population de la liste
		 final List<String> locItemsList = new ArrayList<String>();
		 for(int i=0; i<50;i++) {
			 locItemsList.add("Item" + i);
		 }
		
		 //OnTimeClickListener
		 _liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> adapterView,
			    View view,
			    int position,
			    long id) {
				  
				  int index =  _liste.getCheckedItemPosition();
				  
				  String item = "L'item sélectionné est : " + index;
				  
					 Toast plouf = Toast.makeText(context,(CharSequence) item,Toast.LENGTH_LONG);
					 plouf.show();
			  }
			});
		 
		 //On set l'adaptateur
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, locItemsList);
		    _liste.setAdapter(adapter);
		    	
		    
		    
	    setContentView(_layout);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
