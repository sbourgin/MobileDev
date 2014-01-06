package com.mmessage.dcu.sylvain;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;

import com.mmessage.dcu.sylvain.controler.ConversationViewController;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;

public class ConversationViewActivity extends Activity implements OnTaskCompleted {

	private LinearLayout _layout = null;
	private ConversationViewController _controller = null;
	
	//locBundle.putLong("conversationID", idConversation);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle locReceiveBundle = this.getIntent().getExtras();
		Long locConversationId = locReceiveBundle.getLong("conversationID");
		
		_layout = (LinearLayout) LinearLayout.inflate(this,
				R.layout.activity_conversation_view, null);
		setContentView(_layout);
		
		_controller = new ConversationViewController(ConversationViewActivity.this, locConversationId);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conversation_view, menu);
		return true;
	}

	@Override
	public void onTaskCompleted(Object parObject) {
		// TODO Auto-generated method stub
		
	}

}
