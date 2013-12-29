package com.mmessage.dcu.sylvain.controler;

import android.util.Base64;

import com.mmessage.dcu.sylvain.interfaces.OnTaskCompleted;
import com.mmessage.dcu.sylvain.tasks.GetRESTTask;

public class MainActivityController implements OnTaskCompleted {
	
	private static String _authentication = "";
	
	private String _urlPostUser = "http://message.eventhub.eu/users";
	private OnTaskCompleted _listener;
	
	public MainActivityController (OnTaskCompleted parListener) {
		_listener = parListener;
	
	}
	
	
	public void logInUser(String parUserName, String parPassword) {
		String authentication = "cil:cil"; //TODO change
//		String authentification = parUserName + ":" + parPassword;	
		String encoding = Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
		_authentication = encoding;
		
		new GetRESTTask(this).execute(_urlPostUser);
	}
	
	
	@Override
	public void onTaskCompleted(Object parObject) {
		if(parObject==null) {
			_listener.onTaskCompleted(Boolean.valueOf(false));
		} else {
			_listener.onTaskCompleted(Boolean.valueOf(true));
		}
	}
	
	public static String getAuthentification() {
		return _authentication;
	}

}
