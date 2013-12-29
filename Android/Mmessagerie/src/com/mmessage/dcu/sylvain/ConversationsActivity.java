package com.mmessage.dcu.sylvain;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ConversationsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversations);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.conversations, menu);
		return true;
	}

}
