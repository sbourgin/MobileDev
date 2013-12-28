package com.mmessage.dcu.sylvain.controler;

import java.util.HashMap;
import java.util.Map;

import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.tasks.PostRESTTask;


public class CreateAccountController implements OnTaskCompleted{

	private String _urlPostUser = "http://message.eventhub.eu/users";
	private OnTaskCompleted _listener;
	
	public CreateAccountController(OnTaskCompleted parListener) {
		_listener = parListener;
	
	}
	
	public void createNewUser(String parUserName, String parPassword, String parEmailAddress) {
		
		Map<String, String> locParameters = new HashMap<String, String>();
		
		locParameters.put("username", parUserName);
		locParameters.put("password", parPassword);
		locParameters.put("email", parEmailAddress);
		
		new PostRESTTask(this, locParameters).execute(_urlPostUser);
	}

	@Override
	public void onTaskCompleted(Object parObject) {
	//TODO lire et traiter l'objet reçu pour envoyer boolean à view
		_listener.onTaskCompleted(parObject);
		
	}
	
	
	
}
