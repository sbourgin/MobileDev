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
	public void onTaskCompleted(Object parObject) {
		// TODO Auto-generated method stub
		
	}

}
