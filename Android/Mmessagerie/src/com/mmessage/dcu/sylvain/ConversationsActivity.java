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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mmessage.dcu.sylvain.controler.ConversationsController;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.model.SizeLimitedAdapter;

public class ConversationsActivity extends Activity implements OnTaskCompleted {

	private LinearLayout _layout = null;
	private ListView _listeView = null;
	private SizeLimitedAdapter<Conversation> _conversationsAdapter = null;
	private Button _conversationsPlus = null;
	private ConversationsController _controller = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_layout = (LinearLayout) LinearLayout.inflate(this,
				R.layout.activity_conversations, null);

		_listeView = (ListView) _layout.findViewById(R.id.conversationListView);

		LinkedList<Conversation> locObjectsList = new LinkedList<Conversation>();

		_conversationsAdapter = new SizeLimitedAdapter<Conversation>(this, 200,
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

		setContentView(_layout);

		_controller = new ConversationsController(ConversationsActivity.this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.conversations, menu);
		return true;
	}

	@Override
	public void onTaskCompleted(Object parObject) {

		List<Conversation> locResult = (List<Conversation>) parObject;

		if ((parObject == null) || (locResult.isEmpty())) {
			Toast locToast = Toast.makeText(this,
					"Error when retrieving conversations", Toast.LENGTH_LONG);
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
