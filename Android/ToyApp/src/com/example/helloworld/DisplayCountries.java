package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayCountries extends Activity {

	private RelativeLayout _layout = null;
	private TextView _textView = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.display_countries, null);
		
		
		_textView = (TextView) _layout.findViewById(R.id.textViewActivityCountries);
		
		_textView.setText("PLOUF");
		
		
		setContentView(_layout);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
