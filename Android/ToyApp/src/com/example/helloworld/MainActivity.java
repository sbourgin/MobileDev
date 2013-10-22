package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity{

	private RelativeLayout locLayout = null;
	private TextView locWelcomeText = null;
	private Button locButtonChangeWelcomeText = null;
	
	private OnClickListener locClickListener = new OnClickListener() {
		@Override
		public void onClick(View parView) {
			
			switch(parView.getId()) {
	
			case R.id.buttonChangeTextWelcome:
				locWelcomeText.setText("I'm fine and you ??");
				break;			
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
   	
		// On récupère notre layout par désérialisation. La méthode inflate retourne un View
	    // C'est pourquoi on caste (on convertit) le retour de la méthode avec le vrai type de notre layout, c'est-à-dire RelativeLayout
		locLayout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.activity_main, null);
	    
		
		// Text view :  puis on récupère TextView grâce à son identifiant
	    locWelcomeText = (TextView) locLayout.findViewById(R.id.textWelcome);
	    locWelcomeText.setText("Hi Sylvain !! !! !!");
	    
	    
	    //Button
	    locButtonChangeWelcomeText = (Button) locLayout.findViewById(R.id.buttonChangeTextWelcome);
	    locButtonChangeWelcomeText.setText("How are you ?");
	    locButtonChangeWelcomeText.setOnClickListener(locClickListener);
	    
	    
	    setContentView(locLayout);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
