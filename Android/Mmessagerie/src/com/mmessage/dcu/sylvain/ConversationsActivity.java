package com.mmessage.dcu.sylvain;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.mmessage.dcu.sylvain.controler.ConversationsController;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Commands;
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.model.SizeLimitedAdapterOriginal;
import com.mmessage.dcu.sylvain.model.TaskMessage;

public class ConversationsActivity extends Activity implements OnTaskCompleted {

	private LinearLayout _layout = null;
	private ListView _listeView = null;
	private SizeLimitedAdapterOriginal<Conversation> _conversationsAdapter = null;
	private Button _conversationsPlus = null;
	private ConversationsController _controller = null;
	private Button _refresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_layout = (LinearLayout) LinearLayout.inflate(this,
				R.layout.activity_conversations, null);

		_listeView = (ListView) _layout.findViewById(R.id.conversationListView);

		LinkedList<Conversation> locObjectsList = new LinkedList<Conversation>();

		_conversationsAdapter = new SizeLimitedAdapterOriginal<Conversation>(this, 200,
				locObjectsList, 17367047);

		_listeView.setAdapter(_conversationsAdapter);

		_conversationsPlus = (Button) _layout
				.findViewById(R.id.conversationsPlus);

		_conversationsPlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent locIntent = new Intent(ConversationsActivity.this,
						CreateConversationActivity.class);
				startActivity(locIntent);

			}
		});

		_listeView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int parPosition, long id) {
				Conversation locConversationChecked = (Conversation) _conversationsAdapter
						.getItem(parPosition);
				Long idConversation = locConversationChecked.getIdOfItem();
				Bundle locBundle = new Bundle();
				locBundle.putLong("conversationID", idConversation);

				Intent locIntent = new Intent(ConversationsActivity.this,
						ConversationViewActivity.class);
				locIntent.putExtras(locBundle);
				startActivity(locIntent);

			}

		});
		
		
		_refresh = (Button) _layout
				.findViewById(R.id.conversationsRefreshButton);
		_refresh.setText("Refresh");
		_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				_controller.refresh();
			}
		});
		

		setContentView(_layout);

		_controller = new ConversationsController(ConversationsActivity.this);

	}


	@Override
	public void onTaskCompleted(Object parObject) {

		TaskMessage locTaskMessage = (TaskMessage) parObject;

		if (locTaskMessage.getCommand().equals(Commands.GET_ALL_CONVERSATIONS)) {

			List<Conversation> locResult = (List<Conversation>) locTaskMessage
					.getResult();

			if ((locResult == null)) {
				Toast locToast = Toast.makeText(this,
						"Error when retrieving conversations",
						Toast.LENGTH_LONG);
				locToast.show();
			} else {
				_conversationsAdapter.resetData();
				for (Conversation locConversation : locResult) {
					_conversationsAdapter.addLast(locConversation);
				}
				_conversationsAdapter.notifyDataSetChanged();
			}
		}

	}

	
}
