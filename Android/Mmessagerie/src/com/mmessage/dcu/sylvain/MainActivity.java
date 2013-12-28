package com.mmessage.dcu.sylvain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private RelativeLayout _layout = null;
	private EditText _login = null;
	private EditText _password = null;
	private Button _createAccount = null;
	private Button _signIn = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		_layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.activity_main, null);
		
		_login =  (EditText) _layout.findViewById(R.id.mainUserName);
		_password = (EditText) _layout.findViewById(R.id.mainPassword);
		_createAccount = (Button) _layout.findViewById(R.id.mainCreateAccount);
		_signIn = (Button) _layout.findViewById(R.id.mainSignIn);
		
		_createAccount.setText("Create an account"); //TODO add : label OR 
		_signIn.setText("Sign In");
		
		_createAccount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, CreateAccount.class);
				startActivity(intent);

				
			}
		});
		
		setContentView(_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
