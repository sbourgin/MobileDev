package com.mmessage.dcu.sylvain.controler.manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.apache.http.HttpStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mmessage.dcu.sylvain.interfaces.ItemManager;
import com.mmessage.dcu.sylvain.interfaces.Iterator;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Commands;
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.model.Message;
import com.mmessage.dcu.sylvain.model.TaskMessage;
import com.mmessage.dcu.sylvain.tasks.GetRESTTask;

public class ConversationManager implements ItemManager, Iterator<Message>,
		OnTaskCompleted {

	private List<Message> _messages = new LinkedList<Message>();
	private ListIterator<Message> _iterator = null;
	private String _urlPostUser = "http://message.eventhub.eu/conversations/%d/messages";
	private OnTaskCompleted _listener;

	// TODO attention si on fait un refresh data, l'itérator merde

	public ConversationManager(OnTaskCompleted parListener,
			Long parConversationId) {
		_listener = parListener;
		_urlPostUser = String.format(_urlPostUser, parConversationId);
	}

	@Override
	public boolean hasNext() {
		return _iterator.hasNext();
	}

	@Override
	public boolean hasPrevious() {
		return _iterator.hasPrevious();
	}

	@Override
	public Message next() {
		return _iterator.next();
	}

	@Override
	public Message previous() {
		return _iterator.previous();
	}

	@Override
	public void initData() {
		new GetRESTTask(this, Commands.GET_ALL_MESSAGES)
				.execute(_urlPostUser);

	}

	@Override
	public void onTaskCompleted(Object parObject) {

		TaskMessage locTaskMessage = (TaskMessage) parObject;

		boolean isMessagesListSucess = true;

		if (locTaskMessage.getHttpCode() != HttpStatus.SC_OK) {
			isMessagesListSucess = false;
		}

		if (locTaskMessage.getResult() != null && isMessagesListSucess) {

			String locStringFromServer = (String) locTaskMessage.getResult();

			JSONParser locParser = new JSONParser();
			JSONArray locMessagesJSONArray = null;

			try {
				JSONObject locAnswerJSON = (JSONObject) locParser
						.parse(locStringFromServer);
				locMessagesJSONArray = (JSONArray) locAnswerJSON
						.get("messages");
			} catch (Exception e) {
				isMessagesListSucess = false;
			}

			if (isMessagesListSucess) {

				_messages.clear();
				
				for (int i = 0; i < locMessagesJSONArray.size(); i++) {

					Message locMessage = null;

					boolean isMessageValid = true;
					try {
						JSONObject locMessageJSON = (JSONObject) locMessagesJSONArray
								.get(i);
						locMessage = new Message();
						isMessageValid = locMessage.fillStates(locMessageJSON);
					} catch (Exception e) {
						isMessageValid = false;
					}

					if (isMessageValid) {
						_messages.add(locMessage);

					}

				}

				_iterator = _messages.listIterator();
			}

		}

		TaskMessage locTaskMessageToController;
		
		if (isMessagesListSucess) {
			locTaskMessageToController = new TaskMessage(Commands.GET_ALL_MESSAGES, HttpStatus.SC_OK, null);
		} else {
			locTaskMessageToController = new TaskMessage(Commands.GET_ALL_MESSAGES, HttpStatus.SC_BAD_REQUEST, null);
		}

		_listener.onTaskCompleted(locTaskMessageToController);
		
	}

}
