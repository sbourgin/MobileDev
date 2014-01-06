package com.mmessage.dcu.sylvain;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mmessage.dcu.sylvain.controler.CreateConversationController;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Contact;

public class CreateConversationActivity extends Activity implements OnTaskCompleted{
	private LinearLayout _layout = null;
	private CreateConversationController _controller = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_layout = (LinearLayout) LinearLayout.inflate(this,
				R.layout.activity_create_conversation, null);
		setContentView(_layout);
		
		_controller = new CreateConversationController(CreateConversationActivity.this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_conversation, menu);
		return true;
	}

	@Override
	public void onTaskCompleted(Object parObject) {
		
		List<Contact> locAllContacts = (List<Contact>) parObject;
		
		List<String> locListe = new ArrayList<String>();
		
		for(Contact locContact:locAllContacts) {
			locListe.add(locContact.getTitleToDisplay());
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Select a contact");

		ArrayAdapter<String> locAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, locListe);
		
		
		
		builder.setSingleChoiceItems(locAdapter, 0, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getBaseContext(),"PLIFFF",Toast.LENGTH_LONG).show();
				
			}
		});
		
		AlertDialog dialog = builder.create();
		
		
		
		dialog.show();
	}

}
