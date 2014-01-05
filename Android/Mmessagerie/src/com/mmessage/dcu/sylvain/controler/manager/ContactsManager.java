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
import com.mmessage.dcu.sylvain.model.Contact;
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.tasks.GetRESTTask;

public class ContactsManager implements ItemManager, Iterator<Contact>,
		OnTaskCompleted {

	private List<Contact> _contacts = new LinkedList<Contact>();
	private ListIterator<Contact> _iterator = null; 
	private String _urlPostUser = "http://message.eventhub.eu/users";
	private OnTaskCompleted _listener;
	
	// TODO attention si on fait un refresh data, l'itérator merde
	
	public ContactsManager(OnTaskCompleted parListener) {
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
	public Contact next() {
		return _iterator.next();
	}

	@Override
	public Contact previous() {
		return _iterator.previous();
	}

	@Override
	public void initData() {
		new GetRESTTask(this).execute(_urlPostUser);
		
	}

	@Override
	public void onTaskCompleted(Object parObject) {
		
		boolean isContactsListSucess = true;

		if (parObject != null) {

			String locStringFromServer = (String) parObject;

			JSONParser locParser = new JSONParser();
			JSONArray locContactsJSONArray = null;

			try {
				JSONObject locAnswerJSON = (JSONObject) locParser
						.parse(locStringFromServer);
				locContactsJSONArray = (JSONArray) locAnswerJSON
						.get("users");
			} catch (ParseException e) {
				isContactsListSucess = false;
			}

			if (isContactsListSucess) {

				for (int i = 0; i < locContactsJSONArray.size(); i++) {

					Contact locContact = null;

					boolean isContactValid = true;
					try {
						JSONObject locContactJSON = (JSONObject) locContactsJSONArray
								.get(i);
						locContact = new Contact();
						locContact.fillStates(locContactJSON);
					} catch (Exception e) {
						isContactValid = false;
					}

					if (isContactValid) {
						_contacts.add(locContact);

					}

				}

				_iterator = _contacts.listIterator();
				_listener.onTaskCompleted(Boolean.valueOf(true));

			} else {
				_listener.onTaskCompleted(Boolean.valueOf(false));
			}

		} else {
			_listener.onTaskCompleted(Boolean.valueOf(false));
		}
	
	}

}
