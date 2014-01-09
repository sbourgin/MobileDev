package com.mmessage.dcu.sylvain.controler;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mmessage.dcu.sylvain.controler.manager.ContactsManager;
import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.model.Commands;
import com.mmessage.dcu.sylvain.model.Contact;
import com.mmessage.dcu.sylvain.model.Conversation;
import com.mmessage.dcu.sylvain.model.TaskMessage;
import com.mmessage.dcu.sylvain.tasks.PostRESTTask;

public class CreateConversationController implements OnTaskCompleted {

	private ContactsManager _contactsManager = new ContactsManager(
			CreateConversationController.this);
	private String _urlConversation = "http://message.eventhub.eu/conversations";
	private String _urlMessages = "http://message.eventhub.eu/conversations/%d/messages";
	private String _messageToSend = null;
	private OnTaskCompleted _listener;

	public CreateConversationController(OnTaskCompleted parListener) {
		_listener = parListener;
		_contactsManager.initData();
	}

	private List<Contact> getAllContacts() {

		List<Contact> locAllContacts = new ArrayList<Contact>();
		while (_contactsManager.hasNext()) {
			locAllContacts.add(_contactsManager.next());
		}
		return locAllContacts;
	}

	public void sendMessage(Conversation parConversation, String parMessage) {

		_messageToSend = parMessage;
		createNewConversation(parConversation);

	}

	private void createNewConversation(Conversation parConversation) {

		List<NameValuePair> locNameValuePairs = new ArrayList<NameValuePair>(3);
		locNameValuePairs.add(new BasicNameValuePair("name", parConversation
				.getFullTextToDisplay()));

		for (Contact locContact : parConversation.getAddresseeList()) {
			locNameValuePairs.add(new BasicNameValuePair("user_id", String
					.valueOf(locContact.getIdOfItem())));
		}

		new PostRESTTask(this, true, Commands.CREATE_CONVERSATION,
				locNameValuePairs).execute(_urlConversation);

	}

	@Override
	public void onTaskCompleted(Object parObject) {

		TaskMessage locTaskMessage = (TaskMessage) parObject;

		if (locTaskMessage.getCommand().equals(Commands.GET_ALL_USERS)) {

			TaskMessage locTaskMessageToView;

			if (locTaskMessage.getHttpCode() == HttpStatus.SC_OK) {
				locTaskMessageToView = new TaskMessage(Commands.GET_ALL_USERS,
						HttpStatus.SC_OK, getAllContacts());
			} else {
				locTaskMessageToView = new TaskMessage(Commands.GET_ALL_USERS,
						HttpStatus.SC_BAD_REQUEST, null);
			}

			_listener.onTaskCompleted(locTaskMessageToView);

		} else if (locTaskMessage.getCommand().equals(
				Commands.CREATE_CONVERSATION)) {

			Long locConversationId = null;

			if (locTaskMessage.getHttpCode() == HttpStatus.SC_OK) {

				String locStringFromServer = (String) locTaskMessage
						.getResult();
				JSONParser locParser = new JSONParser();
				JSONObject locResult = null;

				try {
					JSONObject locAnswerJSON = (JSONObject) locParser
							.parse(locStringFromServer);

					locResult = (JSONObject) locAnswerJSON.get("conversation");
					locConversationId = (Long) locResult.get("id");

				} catch (Exception e) {
					// nothing todo
				}

			}

			if (locConversationId != null) {

				String locUrl = String.format(_urlMessages, locConversationId);

				List<NameValuePair> locNameValuePairs = new ArrayList<NameValuePair>();
				locNameValuePairs.add(new BasicNameValuePair("content",
						_messageToSend));

				new PostRESTTask(this, true, Commands.SEND_MESSAGE,
						locNameValuePairs).execute(locUrl);

			}

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
