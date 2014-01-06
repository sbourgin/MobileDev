package com.mmessage.dcu.sylvain;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmessage.dcu.sylvain.controler.CreateConversationController;
import com.mmessage.dcu.sylvain.interfaces.Displayable;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Commands;
import com.mmessage.dcu.sylvain.model.Contact;
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.model.Message;
import com.mmessage.dcu.sylvain.model.TaskMessage;

public class CreateConversationActivity extends Activity implements
		OnTaskCompleted {
	private LinearLayout _layout = null;
	private CreateConversationController _controller = null;
	private Contact _selectedContact = null;
	private TextView _contactName;
	private Button _changeContact;
	private List<Displayable> _allContacts = null;
	private Button _submitMessage;
	private EditText _conversationName;
	private EditText _message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_layout = (LinearLayout) LinearLayout.inflate(this,
				R.layout.activity_create_conversation, null);

		_contactName = (TextView) _layout
				.findViewById(R.id.CreateConversationContactName);
		_contactName.setText("Addresse : No Contact selected");

		_conversationName = (EditText) _layout
				.findViewById(R.id.CreateConversationConversationName);
		_conversationName.setText("Conversation Name");

		_changeContact = (Button) _layout
				.findViewById(R.id.CreateConversationChangeContactButton);
		_changeContact.setText("Change Addressee");

		_changeContact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (_allContacts == null) {
					Toast.makeText(getBaseContext(),
							"Contacts are not available, try again",
							Toast.LENGTH_LONG).show();
				} else {
					String locTitle = "Select a contact";
					createDialogSelectItem(locTitle, _allContacts);
				}
			}
		});

		_controller = new CreateConversationController(
				CreateConversationActivity.this);

		_message = (EditText) _layout
				.findViewById(R.id.CreateConversationMessage);

		_submitMessage = (Button) _layout
				.findViewById(R.id.CreateConversationSubmit);
		_submitMessage.setText("Submit Message");
		_submitMessage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessage();
			}
		});

		setContentView(_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_conversation, menu);
		return true;
	}

	@Override
	public void onTaskCompleted(Object parObject) {

		TaskMessage locTaskMessage = (TaskMessage) parObject;

		if (locTaskMessage.getCommand().equals(Commands.GET_ALL_USERS)
				&& locTaskMessage.getHttpCode() == HttpStatus.SC_OK) {
			_allContacts = (List<Displayable>) locTaskMessage.getResult();
		} else if (locTaskMessage.getCommand().equals(Commands.SEND_MESSAGE)) {
			if(locTaskMessage.getHttpCode() == HttpStatus.SC_OK) {
				Toast.makeText(getBaseContext(),"Message sent !",Toast.LENGTH_LONG).show();
				Intent locIntent = new Intent(CreateConversationActivity.this,
						ConversationsActivity.class);
				startActivity(locIntent);
				
			} else {
				 Toast.makeText(getBaseContext(),"Failed to send message",Toast.LENGTH_LONG).show();
			}
			
		}

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
						_contactName.setText("Addresse : "
								+ _selectedContact.getTitleToDisplay());
					}
				});

		AlertDialog dialog = builder.create();

		dialog.show();
	}

	public void sendMessage() {
		List<Contact> locAdressee = new ArrayList<Contact>();
		locAdressee.add(_selectedContact);
		Conversation locConversation = new Conversation(_conversationName
				.getText().toString(), locAdressee);

		_controller.sendMessage(locConversation, _message.getText().toString());
	}

}
