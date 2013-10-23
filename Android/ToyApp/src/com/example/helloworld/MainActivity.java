package com.example.helloworld;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
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
   	
		// On récupère notre layout par désérialisation. La méthode inflate retourne un View
	    // C'est pourquoi on caste (on convertit) le retour de la méthode avec le vrai type de notre layout, c'est-à-dire RelativeLayout
		_layout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.activity_main, null);
	    
		 _liste = (ListView) _layout.findViewById(R.id.listView1);
		 if(_liste == null) {
			 Toast plouf = Toast.makeText(this,"Raté",0);
			 plouf.show();
		 } else {
		 List<String> exemple = new ArrayList<String>();
		 
		 for(int i=0; i<50;i++) {
			 exemple.add("Item" + i);
		 }
		
		         
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple);
		    _liste.setAdapter(adapter);
		
		 }
	    setContentView(_layout);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
