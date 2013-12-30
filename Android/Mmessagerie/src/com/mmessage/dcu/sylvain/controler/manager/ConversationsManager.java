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
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.tasks.GetRESTTask;

public class ConversationsManager implements ItemManager,
		Iterator<Conversation>, OnTaskCompleted {

	private List<Conversation> _conversations = new LinkedList<Conversation>();
	private ListIterator<Conversation> _iterator = null; // TODO chelou d'avoir
															// besoin du cast
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
		new GetRESTTask(this).execute(_urlPostUser);

	}

	@Override
	public void onTaskCompleted(Object parObject) {

		boolean isConversationsListSucess = true;
		List<Conversation> locResult = new ArrayList<Conversation>();

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

			}

		} else {
			_listener.onTaskCompleted(Boolean.valueOf(false));
		}
	}
}
