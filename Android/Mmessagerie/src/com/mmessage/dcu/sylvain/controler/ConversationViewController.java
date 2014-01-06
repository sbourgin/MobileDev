package com.mmessage.dcu.sylvain.controler;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;

import com.mmessage.dcu.sylvain.controler.manager.ConversationManager;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Commands;
import com.mmessage.dcu.sylvain.model.Message;
import com.mmessage.dcu.sylvain.model.TaskMessage;

public class ConversationViewController implements OnTaskCompleted {

	private ConversationManager _conversationManager = null;

	private OnTaskCompleted _listener;

	public ConversationViewController(OnTaskCompleted parListener,
			Long parConversationId) {
		_listener = parListener;
		_conversationManager = new ConversationManager(
				ConversationViewController.this, parConversationId);
		_conversationManager.initData();

	}

	private List<Message> getAllMessages() {

		List<Message> locAllConversations = new ArrayList<Message>();
		while (_conversationManager.hasNext()) {
			locAllConversations.add(_conversationManager.next());
		}
		return locAllConversations;
	}

	@Override
	public void onTaskCompleted(Object parObject) {

		TaskMessage locTaskMessage = (TaskMessage) parObject;

		if (locTaskMessage.getCommand().equals(Commands.GET_A_CONVERSATION)) {

			TaskMessage locTaskMessageToView;

			if (locTaskMessage.getHttpCode() == HttpStatus.SC_OK) {
				locTaskMessageToView = new TaskMessage(
						Commands.GET_A_CONVERSATION, HttpStatus.SC_OK,
						getAllMessages());

			} else {
				locTaskMessageToView = new TaskMessage(
						Commands.GET_A_CONVERSATION, HttpStatus.SC_BAD_REQUEST,
						null);
			}

			_listener.onTaskCompleted(locTaskMessageToView);

		}
	}

}
