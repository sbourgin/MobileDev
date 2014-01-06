package com.mmessage.dcu.sylvain.controler;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mmessage.dcu.sylvain.controler.manager.ContactsManager;
import com.mmessage.dcu.sylvain.controler.manager.ConversationsManager;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Commands;
import com.mmessage.dcu.sylvain.model.Contact;
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.model.Message;
import com.mmessage.dcu.sylvain.tasks.PostRESTTask;

public class CreateConversationController implements OnTaskCompleted {

	private ContactsManager _contactsManager = new ContactsManager(CreateConversationController.this);
	private ConversationsManager _conversationsManager = new ConversationsManager(CreateConversationController.this);
	private String _urlConversation = "http://message.eventhub.eu/conversations";
	private String _urlMessages = "http://message.eventhub.eu/conversations/%d/messages";
	private Message _messageToSend = null;
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

	public void sendMessage(Conversation parConversation, Message parMessage) {
		
		_messageToSend = parMessage;
		//Créer conversation et/ou récupérer son id
		createNewConversation(parConversation);
		//Poster le message
		
	}
	
	private void createNewConversation(Conversation parConversation) {
		
		List<NameValuePair> locNameValuePairs = new ArrayList<NameValuePair>(3);
	    locNameValuePairs.add(new BasicNameValuePair("name", parConversation.getFullTextToDisplay()));
	    
	    for(Contact locContact:parConversation.getAddresseeList()) {
	    	locNameValuePairs.add(new BasicNameValuePair("user_id", String.valueOf(locContact.getIdOfItem())));
	    }
	   	    
	    new PostRESTTask(this, true, Commands.CREATE_CONVERSATION, locNameValuePairs).execute(_urlConversation);
	    
	}
	
	@Override
	public void onTaskCompleted(Object parObject) {
		//TODO gérer le cas où on réenvoit false et éviter de faire la suite et prévenir la vue
		_listener.onTaskCompleted(getAllContacts());
		
		
		
	}
	

	
	
	
}
