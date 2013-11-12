package com.example.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

		_textView = (TextView) _layout
				.findViewById(R.id.textViewActivityCountries);

		_textView.setText("PLOUF");

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
}
