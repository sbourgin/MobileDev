package com.mmessage.dcu.sylvain;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmessage.dcu.sylvain.controler.CreateConversationController;
import com.mmessage.dcu.sylvain.interfaces.Displayable;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Contact;

public class CreateConversationActivity extends Activity implements
		OnTaskCompleted {
	private LinearLayout _layout = null;
	private CreateConversationController _controller = null;
	private Contact _selectedContact = null;
	private TextView _contactName;
	private Button _changeContact;
	private List<Displayable> _allContacts = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_layout = (LinearLayout) LinearLayout.inflate(this,
				R.layout.activity_create_conversation, null);
		setContentView(_layout);

		_contactName = (TextView) _layout
				.findViewById(R.id.CreateConversationContactName);
		_contactName.setText("Addresse : No Contact selected");

		_changeContact = (Button) _layout
				.findViewById(R.id.CreateConversationChangeContactButton);
		_changeContact.setText("Change Addressee");

		_changeContact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (_allContacts == null) {
					Toast.makeText(getBaseContext(), "Contacts are not available, try again",
							Toast.LENGTH_LONG).show();
				} else {
					String locTitle = "Select a contact";
					createDialogSelectItem(locTitle, _allContacts);
				}
			}
		});

		_controller = new CreateConversationController(
				CreateConversationActivity.this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_conversation, menu);
		return true;
	}

	@Override
	public void onTaskCompleted(Object parObject) {
		_allContacts = (List<Displayable>) parObject;
	}

	public void createDialogSelectItem(String parTitle,
			final List<Displayable> parList) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(parTitle);

		ArrayAdapter<Displayable> locAdapter = new ArrayAdapter<Displayable>(
				this, android.R.layout.simple_list_item_single_choice, parList);

		builder.setSingleChoiceItems(locAdapter, 0,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						_selectedContact = (Contact) parList.get(which);
						dialog.cancel();
						_contactName.setText("Addresse : " + _selectedContact
								.getTitleToDisplay());
					}
				});

		AlertDialog dialog = builder.create();

		dialog.show();
	}

}
