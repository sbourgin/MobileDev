package com.mmessage.dcu.sylvain;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmessage.dcu.sylvain.controler.ConversationViewController;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Commands;
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.model.TaskMessage;

public class ConversationViewActivity extends Activity implements
		OnTaskCompleted {

	private LinearLayout _layout = null;
	private ConversationViewController _controller = null;
	private TextView _conversationDetails;
	private EditText _message;
	private Button _submitMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle locReceiveBundle = this.getIntent().getExtras();
		Long locConversationId = locReceiveBundle.getLong("conversationID");

		_layout = (LinearLayout) LinearLayout.inflate(this,
				R.layout.activity_conversation_view, null);

		_conversationDetails = (TextView) _layout
				.findViewById(R.id.ConversationViewConversationDetails);

		_message = (EditText) _layout
				.findViewById(R.id.ConversationViewMessage);

		_submitMessage = (Button) _layout
				.findViewById(R.id.ConversationViewSubmit);
		_submitMessage.setText("Submit Message");
		_submitMessage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessage();
			}

		});

		setContentView(_layout);

		_controller = new ConversationViewController(
				ConversationViewActivity.this, locConversationId);
	}

	@Override
	public void onTaskCompleted(Object parObject) {
		TaskMessage locTaskMessage = (TaskMessage) parObject;

		if (locTaskMessage.getCommand().equals(Commands.GET_ALL_MESSAGES)) {
			// TODO
		} else if (locTaskMessage.getCommand().equals(
				Commands.GET_ALL_CONVERSATIONS)) {

			if (locTaskMessage.getResult() != null) {

				Conversation locConversation = (Conversation) locTaskMessage
						.getResult();

				String locConversationDetails = locConversation
						.getFullTextToDisplay()
						+ " with : "
						+ locConversation.getTitleToDisplay();

				_conversationDetails.setText(locConversationDetails);

			} else {
				Toast.makeText(getBaseContext(),
						"Error while retrieving conversation details",
						Toast.LENGTH_SHORT).show();
			}

		} else if (locTaskMessage.getCommand().equals(Commands.SEND_MESSAGE)) {
			if(locTaskMessage.getHttpCode() == HttpStatus.SC_OK) {
				Toast.makeText(getBaseContext(),"Message sent !",Toast.LENGTH_LONG).show();
				//TODO ajouter refresh vue, de manière générale ajouter refresh conversation et cette vue
			} else {
				 Toast.makeText(getBaseContext(),"Failed to send message",Toast.LENGTH_LONG).show();
			}
			
		}

	}
	
	private void sendMessage() {
		_controller.sendMessage(_message.getText().toString());
		_message.setText("");;
	}
}
