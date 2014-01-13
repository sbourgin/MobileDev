package com.mmessage.dcu.sylvain.controler;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.view.Gravity;

import com.mmessage.dcu.sylvain.controler.manager.ConversationManager;
import com.mmessage.dcu.sylvain.controler.manager.ConversationsManager;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Commands;
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.model.Message;
import com.mmessage.dcu.sylvain.model.TaskMessage;
import com.mmessage.dcu.sylvain.tasks.PostRESTTask;

public class ConversationViewController implements OnTaskCompleted {

	private ConversationManager _conversationManager = null;
	private ConversationsManager _conversationsManager = null;
	private OnTaskCompleted _listener;
	private Long _conversationId;
	private String _urlMessages = "http://message.eventhub.eu/conversations/%d/messages";

	public ConversationViewController(OnTaskCompleted parListener,
			Long parConversationId) {
		_conversationId = parConversationId;
		_listener = parListener;
		_conversationManager = new ConversationManager(
				ConversationViewController.this, parConversationId);
		_conversationsManager = new ConversationsManager(
				ConversationViewController.this);
	}

	public void initData() {
		_conversationManager.initData();
		_conversationsManager.initData();

	}

	private List<Message> getAllMessages() {

		List<Message> locAllMessages = new ArrayList<Message>();
		while (_conversationManager.hasNext()) {
			Message locMessage = _conversationManager.next();

			if (locMessage.getSender().getTitleToDisplay()
					.equals(MainActivityController.getUserName())) {
				locMessage.setGravity(Gravity.RIGHT);
			} else {
				locMessage.setGravity(Gravity.LEFT);
			}
			locAllMessages.add(locMessage);
		}
		return locAllMessages;
	}

	public void sendMessage(String parMessage) {
		String locUrl = String.format(_urlMessages, _conversationId);

		List<NameValuePair> locNameValuePairs = new ArrayList<NameValuePair>();
		locNameValuePairs.add(new BasicNameValuePair("content", parMessage));

		new PostRESTTask(this, true, Commands.SEND_MESSAGE, locNameValuePairs)
				.execute(locUrl);
	}

	@Override
	public void onTaskCompleted(Object parObject) {

		TaskMessage locTaskMessage = (TaskMessage) parObject;

		if (locTaskMessage.getCommand().equals(Commands.GET_ALL_MESSAGES)) {

			TaskMessage locTaskMessageToView;

			if (locTaskMessage.getHttpCode() == HttpStatus.SC_OK) {
				locTaskMessageToView = new TaskMessage(
						Commands.GET_ALL_MESSAGES, HttpStatus.SC_OK,
						getAllMessages());

			} else {
				locTaskMessageToView = new TaskMessage(
						Commands.GET_ALL_MESSAGES, HttpStatus.SC_BAD_REQUEST,
						null);
			}

			_listener.onTaskCompleted(locTaskMessageToView);

		} else if (locTaskMessage.getCommand().equals(
				Commands.GET_ALL_CONVERSATIONS)
				&& locTaskMessage.getResult().equals(Boolean.valueOf(true))) {
			Conversation locConversation = _conversationsManager
					.getConversationById(_conversationId);

			TaskMessage locTaskMessageToView = new TaskMessage(
					Commands.GET_ALL_CONVERSATIONS, HttpStatus.SC_OK,
					locConversation);
			_listener.onTaskCompleted(locTaskMessageToView);

		} else if (locTaskMessage.getCommand().equals(Commands.SEND_MESSAGE)) {

			TaskMessage locTaskMessageToView;

			if (locTaskMessage.getHttpCode() == HttpStatus.SC_OK) {
				locTaskMessageToView = new TaskMessage(Commands.SEND_MESSAGE,
						HttpStatus.SC_OK, null);
			} else {
				locTaskMessageToView = new TaskMessage(Commands.SEND_MESSAGE,
						HttpStatus.SC_BAD_REQUEST, null);
			}

			_listener.onTaskCompleted(locTaskMessageToView);

		}
	}

}
