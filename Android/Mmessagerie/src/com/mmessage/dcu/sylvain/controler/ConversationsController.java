package com.mmessage.dcu.sylvain.controler;

import java.util.ArrayList;
import java.util.List;

import com.mmessage.dcu.sylvain.controler.manager.ConversationsManager;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Conversation;

public class ConversationsController implements OnTaskCompleted {

	private ConversationsManager _conversationsManager = new ConversationsManager(ConversationsController.this);
	private OnTaskCompleted _listener;
	
	public ConversationsController (OnTaskCompleted parListener) {		
		_listener = parListener;
		_conversationsManager.initData();
	}
	
	private List<Conversation> getAllConversations() {
		
		List<Conversation> locAllConversations = new ArrayList<Conversation>();
		while(_conversationsManager.hasNext()) {
			locAllConversations.add(_conversationsManager.next());
		}
		return locAllConversations;
	}

	@Override
	public void onTaskCompleted(Object parObject) {
		_listener.onTaskCompleted(getAllConversations());
	}
	

	
	
	
}
