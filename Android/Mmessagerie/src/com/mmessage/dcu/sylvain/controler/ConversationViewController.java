package com.mmessage.dcu.sylvain.controler;

import java.util.ArrayList;
import java.util.List;

import com.mmessage.dcu.sylvain.controler.manager.ConversationManager;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Message;

public class ConversationViewController implements OnTaskCompleted {

	private ConversationManager _conversationManager = null;

		private OnTaskCompleted _listener;
	
	public ConversationViewController (OnTaskCompleted parListener, Long parConversationId) {		
		_listener = parListener;
		_conversationManager = new ConversationManager(ConversationViewController.this, parConversationId);
		_conversationManager.initData();
		
	}
	
	private List<Message> getAllMessages() {
		
		List<Message> locAllConversations = new ArrayList<Message>();
		while(_conversationManager.hasNext()) {
			locAllConversations.add(_conversationManager.next());
		}
		return locAllConversations;
	}

	@Override
	public void onTaskCompleted(Object parObject) {
		//TODO 
		_listener.onTaskCompleted(getAllMessages());
	}
	

	
	
	
}
