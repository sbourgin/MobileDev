package com.mmessage.dcu.sylvain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmessage.dcu.sylvain.controler.CreateAccountController;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;

public class CreateAccount extends Activity implements OnTaskCompleted{

	private LinearLayout _layout = null;
	private EditText _userName = null;
	private EditText _password = null;
	private EditText _email = null;
	private Button _createAccount = null;
	private TextView _messageUser;
	private CreateAccountController _services;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_services = new CreateAccountController(this);
		
		_layout = (LinearLayout) RelativeLayout.inflate(this,
				R.layout.activity_create_account, null);
		
		_messageUser = (TextView) _layout.findViewById(R.id.createAccountTextMessage);
		
		_messageUser.setText("Please fill your details");
		
		_userName =  (EditText) _layout.findViewById(R.id.createAccountUserName);
		_password = (EditText) _layout.findViewById(R.id.createAccountPassword);
		_email = (EditText) _layout.findViewById(R.id.createAccountEmail);
		_createAccount = (Button) _layout.findViewById(R.id.createAccountSubmit);
		
		_createAccount.setText("Submit");
		
		_createAccount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				_services.createNewUser(_userName.getText().toString(), _password.getText().toString(), _email.getText().toString());
			}
		});
		
		setContentView(_layout);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_account, menu);
		return true;
	}

	@Override
	public void onTaskCompleted(Object parObject) {
		
		String locObject = (String) parObject;
		
		_messageUser.setText(locObject);
		
		/*
		Boolean locIsAccountCreated = (Boolean) parObject;
		
		if(locIsAccountCreated) {
			Intent intent = new Intent(CreateAccount.this, MainActivity.class);
			startActivity(intent);
		} else {
			_messageUser.setText("Impossible to create your account, please try with "
					+ "another username or email address");
		}
		
		*/
		
	}



}
