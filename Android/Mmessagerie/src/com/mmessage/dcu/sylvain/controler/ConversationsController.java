package com.mmessage.dcu.sylvain.controler;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;

import com.mmessage.dcu.sylvain.controler.manager.ConversationsManager;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Commands;
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.model.TaskMessage;

public class ConversationsController implements OnTaskCompleted {

	private ConversationsManager _conversationsManager = new ConversationsManager(
			ConversationsController.this);
	private OnTaskCompleted _listener;

	public ConversationsController(OnTaskCompleted parListener) {
		_listener = parListener;
		_conversationsManager.initData();
	}

	private List<Conversation> getAllConversations() {

		List<Conversation> locAllConversations = new ArrayList<Conversation>();
		while (_conversationsManager.hasNext()) {
			locAllConversations.add(_conversationsManager.next());
		}
		return locAllConversations;
	}

	@Override
	public void onTaskCompleted(Object parObject) {
		TaskMessage locTaskMessage = (TaskMessage) parObject;

		if (locTaskMessage.getCommand().equals(Commands.GET_ALL_CONVERSATIONS)) {

			TaskMessage locTaskMessageToView;

			if (locTaskMessage.getResult().equals(Boolean.valueOf(true))) {

				locTaskMessageToView = new TaskMessage(
						Commands.GET_ALL_CONVERSATIONS, HttpStatus.SC_OK,
						getAllConversations());

			} else {
				locTaskMessageToView = new TaskMessage(
						Commands.GET_ALL_CONVERSATIONS,
						HttpStatus.SC_BAD_REQUEST, null);
			}

			_listener.onTaskCompleted(locTaskMessageToView);

		}

	}

	public void refresh() {
		_conversationsManager.initData();
	}

}
