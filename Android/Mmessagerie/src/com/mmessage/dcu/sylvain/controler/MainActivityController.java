package com.mmessage.dcu.sylvain.controler;

import android.util.Base64;

import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.tasks.GetRESTTask;

public class MainActivityController implements OnTaskCompleted {
	private String _urlPostUser = "http://message.eventhub.eu/users";
	private OnTaskCompleted _listener;
	
	public MainActivityController (OnTaskCompleted parListener) {
		_listener = parListener;
	
	}
	
	
	public void logInUser(String parUserName, String parPassword) {
		String authentication = "cil:cil"; //TODO change
		String encoding = Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
		
		new GetRESTTask(this, encoding).execute(_urlPostUser);
	}
	
	
	@Override
	public void onTaskCompleted(Object parObject) {
		//TODO lire et traiter l'objet reçu pour envoyer boolean à view
		_listener.onTaskCompleted(parObject);
		
	}

}
