package com.mmessage.dcu.sylvain.controler;

import java.util.ArrayList;
import java.util.List;

import com.mmessage.dcu.sylvain.controler.manager.ContactsManager;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Contact;
import com.mmessage.dcu.sylvain.model.Message;

public class CreateConversationController implements OnTaskCompleted {

	private ContactsManager _contactsManager = new ContactsManager(CreateConversationController.this);
	private OnTaskCompleted _listener;
	
	public CreateConversationController (OnTaskCompleted parListener) {		
		_listener = parListener;
		_contactsManager.initData();
	}
	
	private List<Contact> getAllContacts() {
		
		List<Contact> locAllContacts = new ArrayList<Contact>();
		while(_contactsManager.hasNext()) {
			locAllContacts.add(_contactsManager.next());
		}
		return locAllContacts;
	}

	public boolean sendMessage(Message parMessage) {
		//Créer conversation et/ou récupérer son id
		
		//Poster le message
		
		return false; //TODO
	}
	
	
	@Override
	public void onTaskCompleted(Object parObject) {
		//TODO gérer le cas où on réenvoit false et éviter de faire la suite et prévenir la vue
		_listener.onTaskCompleted(getAllContacts());
		
		
		
	}
	

	
	
	
}
