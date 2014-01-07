package com.mmessage.dcu.sylvain;

import android.app.Activity;
import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle locReceiveBundle = this.getIntent().getExtras();
		Long locConversationId = locReceiveBundle.getLong("conversationID");

		_layout = (LinearLayout) LinearLayout.inflate(this,
				R.layout.activity_conversation_view, null);

		_conversationDetails = (TextView) _layout
				.findViewById(R.id.ConversationViewConversationDetails);

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

		}

	}

}
