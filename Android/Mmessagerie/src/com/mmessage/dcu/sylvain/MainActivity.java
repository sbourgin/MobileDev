package com.mmessage.dcu.sylvain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmessage.dcu.sylvain.controler.MainActivityController;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;

public class MainActivity extends Activity implements OnTaskCompleted {

	private LinearLayout _layout = null;
	private EditText _userName = null;
	private EditText _password = null;
	private Button _createAccount = null;
	private Button _signIn = null;
	private TextView _messageUser;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		_layout = (LinearLayout) RelativeLayout.inflate(this,
				R.layout.activity_main, null);
		
		_messageUser = (TextView) _layout.findViewById(R.id.mainTextMessage);
		
		_messageUser.setText("Please fill your details");
		
		_userName =  (EditText) _layout.findViewById(R.id.mainUserName);
		_password = (EditText) _layout.findViewById(R.id.mainPassword);
		_createAccount = (Button) _layout.findViewById(R.id.mainCreateAccount);
		_signIn = (Button) _layout.findViewById(R.id.mainSignIn);
		
		_createAccount.setText("Create an account");  
		_signIn.setText("Sign In");
		
		_createAccount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
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
	public void onTaskCompleted(Object parObject) {
		Boolean locAnswerFromServer = (Boolean) parObject;
		if(locAnswerFromServer) {
			Intent intent = new Intent(MainActivity.this, ConversationsActivity.class);
			startActivity(intent);	
		} else {
			_messageUser.setText("Authentication failed");
			Toast locToast = Toast.makeText(this,
					"Authentication failed", Toast.LENGTH_LONG);
			locToast.show();
		}
		
	}

}
