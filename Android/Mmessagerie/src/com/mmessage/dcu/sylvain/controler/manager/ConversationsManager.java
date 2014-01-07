package com.mmessage.dcu.sylvain.controler.manager;

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
import com.mmessage.dcu.sylvain.model.TaskMessage;
import com.mmessage.dcu.sylvain.tasks.GetRESTTask;

public class ConversationsManager implements ItemManager,
		Iterator<Conversation>, OnTaskCompleted {

	private List<Conversation> _conversations = new LinkedList<Conversation>();
	private ListIterator<Conversation> _iterator = null; 
	private String _urlPostUser = "http://message.eventhub.eu/conversations";
	private OnTaskCompleted _listener;

	// TODO attention si on fait un refresh data, l'itérator merde

	public ConversationsManager(OnTaskCompleted parListener) {
		_listener = parListener;
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
	public Conversation next() {
		return _iterator.next();
	}

	@Override
	public Conversation previous() {
		return _iterator.previous();
	}

	@Override
	public void initData() {
		new GetRESTTask(this, Commands.GET_ALL_CONVERSATIONS).execute(_urlPostUser);

	}
	
	public Conversation getConversationById(Long parConversationId) {
		
		boolean locConversationFound = false;
		Conversation locConversation = null;
		ListIterator<Conversation> locIterator = _conversations.listIterator();
		
		while(locIterator.hasNext() || locConversationFound) {
			locConversation = locIterator.next();
			if(locConversation.getIdOfItem() == parConversationId) {
				locConversationFound=true;
			}
		}
		return locConversation;
	}
	
	@Override
	public void onTaskCompleted(Object parObject) {

		TaskMessage locTaskMessage = (TaskMessage) parObject;
		
		boolean isConversationsListSucess = true;

		if (locTaskMessage.getHttpCode() != HttpStatus.SC_OK) {
			isConversationsListSucess = false;
		}
		
		if (locTaskMessage.getResult() != null && isConversationsListSucess) {

			String locStringFromServer = (String) locTaskMessage.getResult();

			JSONParser locParser = new JSONParser();
			JSONArray locConversationsJSONArray = null;

			try {
				JSONObject locAnswerJSON = (JSONObject) locParser
						.parse(locStringFromServer);
				locConversationsJSONArray = (JSONArray) locAnswerJSON
						.get("conversations");
			} catch (ParseException e) {
				isConversationsListSucess = false;
			}

			if (isConversationsListSucess) {

				for (int i = 0; i < locConversationsJSONArray.size(); i++) {

					Conversation locConversation = null;

					boolean isConversationValid = true;
					try {
						JSONObject locConversationJSON = (JSONObject) locConversationsJSONArray
								.get(i);
						locConversation = new Conversation();
						isConversationValid = locConversation.fillStates(locConversationJSON);
					} catch (Exception e) {
						isConversationValid = false;
					}

					if (isConversationValid) {
						_conversations.add(locConversation);

					}

				}

				_iterator = _conversations.listIterator();
			}

		}
		TaskMessage locTaskMessageToController;
		
		
		if (isConversationsListSucess) {			
			locTaskMessageToController = new TaskMessage(Commands.GET_ALL_CONVERSATIONS, HttpStatus.SC_OK, Boolean.valueOf(true));
		} else {
			locTaskMessageToController = new TaskMessage(Commands.GET_ALL_CONVERSATIONS, HttpStatus.SC_BAD_REQUEST, Boolean.valueOf(false));
		}
		
		_listener.onTaskCompleted(locTaskMessageToController);
	}
	
}
