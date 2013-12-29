package com.mmessage.dcu.sylvain;

import com.mmessage.dcu.sylvain.controler.MainActivityController;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnTaskCompleted {

	private RelativeLayout _layout = null;
	private EditText _userName = null;
	private EditText _password = null;
	private Button _createAccount = null;
	private Button _signIn = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		_layout = (RelativeLayout) RelativeLayout.inflate(this,
				R.layout.activity_main, null);
		
		_userName =  (EditText) _layout.findViewById(R.id.mainUserName);
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
		
		
		_signIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivityController controller = new MainActivityController(MainActivity.this);
				controller.logInUser(_userName.getText().toString(), _password.getText().toString());
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

	@Override
	public void onTaskCompleted(Object parObject) {
		String reponse = (String) parObject;
		_userName.setText(reponse);
		
	}

}
