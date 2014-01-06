package com.mmessage.dcu.sylvain.controler.manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
import com.mmessage.dcu.sylvain.tasks.GetRESTTask;

public class ConversationManager implements ItemManager, Iterator<Message>, OnTaskCompleted{

	private List<Message> _messages = new LinkedList<Message>();
	private ListIterator<Message> _iterator = null; 
	private String _urlPostUser = "http://message.eventhub.eu/conversations/%d/messages";
	private OnTaskCompleted _listener;

	// TODO attention si on fait un refresh data, l'itérator merde
	
	public ConversationManager(OnTaskCompleted parListener, Long parConversationId) {
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
		new GetRESTTask(this, Commands.GET_A_CONVERSATION).execute(_urlPostUser);
		
	}

	@Override
	public void onTaskCompleted(Object parObject) {
		
		
		// TODO Analyser les données et les filer à la vue qui va les afficher
		
		/*
		 * 		boolean isConversationsListSucess = true;

		if (parObject != null) {

			String locStringFromServer = (String) parObject;

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
						locConversation.fillStates(locConversationJSON);
					} catch (Exception e) {
						isConversationValid = false;
					}

					if (isConversationValid) {
						_conversations.add(locConversation);

					}

				}

				_iterator = _conversations.listIterator();
				_listener.onTaskCompleted(Boolean.valueOf(true));

			} else {
_listener.onTaskCompleted(Boolean.valueOf(false));
			}

		} else {
			_listener.onTaskCompleted(Boolean.valueOf(false));
		}
	}
		 */
		
	}

}
