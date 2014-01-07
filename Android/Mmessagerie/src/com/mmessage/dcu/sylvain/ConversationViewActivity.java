package com.mmessage.dcu.sylvain;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.mmessage.dcu.sylvain.controler.ConversationViewController;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Commands;
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.model.Message;
import com.mmessage.dcu.sylvain.model.SizeLimitedAdapter;
import com.mmessage.dcu.sylvain.model.TaskMessage;

public class ConversationViewActivity extends Activity implements
		OnTaskCompleted {

	private LinearLayout _layout = null;
	private ConversationViewController _controller = null;
	private TextView _conversationDetails;
	private EditText _message;
	private Button _submitMessage;
	private Button _refresh;
	private ListView _listeView;
	private SizeLimitedAdapter<Message> _messagesAdapter = null;

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

		_refresh = (Button) _layout
				.findViewById(R.id.ConversationViewRefresh);
		_refresh.setText("Refresh");
		_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				refresh();
			}
		});
		
		_listeView = (ListView) _layout.findViewById(R.id.ConversationViewListView);
		
		LinkedList<Message> locObjectsList = new LinkedList<Message>();

		_messagesAdapter = new SizeLimitedAdapter<Message>(this, 200,
				locObjectsList, 17367047);

		_listeView.setAdapter(_messagesAdapter);

		
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
			
			List<Message> locResult = (List<Message>) locTaskMessage
					.getResult();

			if ((locResult == null)) {
				Toast locToast = Toast.makeText(this,
						"Error when retrieving messages",
						Toast.LENGTH_LONG);
				locToast.show();
			} else {
				_messagesAdapter.resetData();
				for (Message locMessage : locResult) {
					_messagesAdapter.addLast(locMessage);
				}
				_messagesAdapter.notifyDataSetChanged();
				_listeView.setSelection(_messagesAdapter.getCount()-1);
			}
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
				refresh();
			} else {
				 Toast.makeText(getBaseContext(),"Failed to send message",Toast.LENGTH_LONG).show();
			}
			
		}

	}
	
	private void sendMessage() {
		_controller.sendMessage(_message.getText().toString());
		_message.setText("");;
	}
	
	private void refresh() {
		_controller.initData();
	}
}
